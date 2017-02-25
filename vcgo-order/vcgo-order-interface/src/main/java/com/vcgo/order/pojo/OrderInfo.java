package com.vcgo.order.pojo;

import java.util.List;

import com.vcgo.pojo.TbOrder;
import com.vcgo.pojo.TbOrderItem;
import com.vcgo.pojo.TbOrderShipping;

public class OrderInfo extends TbOrder {

	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	private List<TbOrderItem> orderItems;
	private TbOrderShipping orderShipping; 
}
