package com.mm.pojo;

import com.mm.dto.OrderMasterDTO;
import com.mm.myenum.OrderStatusEnum;
import com.mm.myenum.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * generated by Generate POJOs.groovy 
 * <p>Date: Sat Nov 17 23:48:58 CST 2018.</p>
 *
 * @author 
 */
@Entity
@Data
@Table ( name ="order_master" )
@DynamicInsert
@DynamicUpdate
public class OrderMaster  implements Serializable {

	private static final long serialVersionUID =  1929790331660036032L;

    @Id
   	@Column(name = "order_id" )
	private String orderId;

	/**
	 * 买家名字
	 */
   	@Column(name = "buyer_name" )
	private String buyerName;

	/**
	 * 买家电话
	 */
   	@Column(name = "buyer_phone" )
	private String buyerPhone;

	/**
	 * 买家地址
	 */
   	@Column(name = "buyer_address" )
	private String buyerAddress;

	/**
	 * 买家微信openid
	 */
   	@Column(name = "buyer_openid" )
	private String buyerOpenid;

	/**
	 * 订单总金额
	 */
   	@Column(name = "order_amount" )
	private Double orderAmount;

	/**
	 * 订单状态, 默认为新下单
	 */
   	@Column(name = "order_status" )
	private Integer orderStatus = OrderStatusEnum.NEW.getCode();

	/**
	 * 支付状态, 默认未支付
	 */
   	@Column(name = "pay_status" )
	private Integer payStatus= PayStatusEnum.UNPAY.getCode();

	/**
	 * 创建时间
	 */
   	@Column(name = "create_time" )
	private Date createTime;

	/**
	 * 修改时间
	 */
   	@Column(name = "update_time" )
	private Date updateTime;

   	public OrderMasterDTO convertToOrderMasterDto(){
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(this, orderMasterDTO);
        return orderMasterDTO;
    }


}
