package com.mm.controller;

import com.mm.config.RedisUtil;
import com.mm.dto.OrderDto;
import com.mm.dto.StockDTO;
import com.mm.pojo.OrderMaster;
import com.mm.pojo.ProductCategory;
import com.mm.pojo.ProductInfo;
import com.mm.service.OrderService;
import com.mm.service.ProductCategoryservice;
import com.mm.service.ProductInfoService;
import com.mm.vo.ProductInfoVo;
import com.mm.vo.ProductListVo;
import com.mm.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/buyer/product")
@Slf4j
public class ProductInfoController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryservice productCategoryservice;
    @Autowired
    private RedisUtil redisUtil;

    @GetMapping(value = "/list",produces = "application/json;charset=utf-8")
    public ResponseVo findProductList() {

        List<ProductInfo> productInfos = productInfoService.findAll();
        List<Integer> categoryIds = productInfos.stream().map(ProductInfo::getCategoryType).distinct().collect(Collectors.toList());
        List<ProductCategory> categoryList = productCategoryservice.findByCategoryTypeIn(categoryIds);

        //data
        List<ProductListVo> data = new ArrayList<>();
        categoryList.stream().forEach(
            //获取同一类型的product集合
            c ->{
                ProductListVo productListVo=  new ProductListVo();
                productListVo.setCategoryName(c.getCategoryName());
                productListVo.setCategoryType(c.getCategoryType());
                productListVo.setProductList(new ArrayList<ProductInfoVo>());
                data.add(productListVo);
                //根据categoryType获取产品list
                productInfos.stream().filter(p -> c.getCategoryType() == p.getCategoryType()).forEach(
                    subp->{
                        //将product转换为productVo
                        ProductInfoVo productInfoVo = new ProductInfoVo();
                        BeanUtils.copyProperties(subp,productInfoVo);
                        productListVo.getProductList().add(productInfoVo);
                    }
                );
            }
        );
        //组装报文
        return ResponseVo.success(data);
    }


    /**
     * 防止并发。demo方法
     */
    @Autowired
    private OrderService orderService;
    @GetMapping(value = "/seckill/{productId}")
    public String seckill1(@PathVariable String productId){
        //这个是加锁防止并发问题
        Boolean lock = redisUtil.lock(productId, "");



        try {
            if (lock) {
                //查询产品库存。
                ProductInfo productInfo = productInfoService.findStock(productId);
                Long origStock = productInfo.getProductStock();
                //保存订单  //减少产品库存。
                if (productInfo.getProductStock() > 0) {
                    OrderDto orderDto = new OrderDto();
                    List<StockDTO> list = new ArrayList();
                    StockDTO stockDTO = new StockDTO();
                    stockDTO.setProductId(productId);
                    stockDTO.setProductQuantity(1L);
                    list.add(stockDTO);
                    orderDto.setStockDTOS(list);

                    orderService.createOrder(orderDto);
                }

                String res = "原来的库存" + origStock + ",购买一个之后的库存" + productInfo.getProductStock();
                System.out.println(res);
                return res;
            }
        } finally {
            redisUtil.unlock(productId);// 解锁。不管有异常还是正常执行都需要解锁。
            System.out.println("请再次尝试-=====");
            return "请再次尝试";
        }
    }

    @GetMapping(value = "/queryStock/{productId}")
    public Long queryStock(@PathVariable String productId) {
        ProductInfo productInfo = productInfoService.findStock(productId);
        if (productInfo != null) {
            return productInfo.getProductStock();
        }
        return 0L;
    }

    //开始秒杀 设置商品可秒杀的数量为50个
    @GetMapping(value = "/startseckill")
    public void startSeckill() {
        //这个校验一下。如果已经开始，就不要在执行这个方法了，
        redisUtil.add("1", "50");//给产品号为1设置初始值50
    }

    /**
     * 产品秒杀。demo方法.
     */
    @GetMapping(value = "/seckill2/{productId}")
    public String seckill2(@PathVariable String productId){
        //一个客户只能买一个。在redis中加入key为userId_秒杀号 的锁。过期时间设置为 秒杀 过期时间*2
        //这个是库存大于某个数就执行。而且是先到先得.这里redis中的产品数量已经减掉了。在最后秒杀结束 后统一减库存。
        Long reduce = redisUtil.reduce(productId);
        try {
            if (reduce > 0) {
                //查询产品库存。
                ProductInfo productInfo = productInfoService.findStock(productId);
                //保存订单  这里因为并发问题，不能减库存。在最后
                if (productInfo.getProductStock() > 0) {
                    //保存订单的相关地址、用户等信息
                    OrderMaster orderMaster = new OrderMaster();
                    orderService.secKillSaveOrder(orderMaster);
                }
                return "成功!";
            }
        } catch (Exception e) {
            redisUtil.increment(productId);//异常恢复产品个数
        } finally {
            System.out.println("请再次尝试-=====");
            return "请再次尝试";
        }
    }

    //结束秒杀后 设置商品可秒杀的数量为50个
    @GetMapping(value = "/endseckill")
    public void endSeckill() {
        //这里有问题。秒杀结束这个key就没有了。换成根据秒杀号查找当前所有记录。
        String val = redisUtil.getVal("1");//获取产品1当前的个数
        Long sellNum = 50 - Long.valueOf(val);//这里初始值就设置的是50
        //去统一减少库存。
        productInfoService.reduceStock("1", sellNum);
    }
}
