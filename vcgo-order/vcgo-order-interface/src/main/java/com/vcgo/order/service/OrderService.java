package com.vcgo.order.service;

import com.vcgo.common.pojo.JsonResult;
import com.vcgo.order.pojo.OrderInfo;

public interface OrderService {
	JsonResult orderCommit(OrderInfo orderInfo);
}
