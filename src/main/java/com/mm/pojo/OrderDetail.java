package com.mm.pojo;

import javax.persistence.*;
import java.io.Serializable;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;

/**
 * generated by Generate POJOs.groovy 
 * <p>Date: Sat Nov 17 23:48:39 CST 2018.</p>
 *
 * @author 
 */

@Entity
@Data
@Table ( name ="order_detail" )
@DynamicInsert
@DynamicUpdate
@Slf4j
public class OrderDetail  implements Serializable {
    public OrderDetail() {
    }

    private static final long serialVersionUID = 3048065223659541935L;

    static {
        log.info("info==--");
        log.debug("debug====");
        log.warn("warn");
        log.error("error====");
        log.trace("trace====");

    }
    @Id
   	@Column(name = "detail_id" )
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(strategy = "uuid",name = "uuidGenerator")
	private String detailId;

   	@Column(name = "order_id" )
	private String orderId;

   	@Column(name = "product_id" )
	private String productId;

	/**
	 * 商品名称
	 */
   	@Column(name = "product_name" )
	private String productName;

	/**
	 * 当前价格,单位分
	 */
   	@Column(name = "product_price" )
	private Double productPrice;

	/**
	 * 数量
	 */
   	@Column(name = "product_quantity" )
	private Long productQuantity;

	/**
	 * 小图
	 */
   	@Column(name = "product_icon" )
	private String productIcon;

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

    /**
     * 初始化动作
     */
    @PrePersist
    protected void onCreate() {
        updateTime = new Date();

        log.info("PrePersist ========,创建的日期为【{}】",updateTime.toString());
    }

    @PostPersist
    protected void onPost() {
        log.info("PostPersist ========,创建的日期为【{}】",updateTime.toString());
    }
}
