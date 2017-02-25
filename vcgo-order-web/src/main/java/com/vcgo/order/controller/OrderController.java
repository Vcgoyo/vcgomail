package com.vcgo.order.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vcgo.common.pojo.JsonResult;
import com.vcgo.common.utils.CookieUtils;
import com.vcgo.common.utils.JsonUtils;
import com.vcgo.order.pojo.OrderInfo;
import com.vcgo.order.service.OrderService;
import com.vcgo.pojo.TbItem;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order/order-cart")
	public String orderCart(Model model,HttpServletRequest request){
		
		String cookieValue = CookieUtils.getCookieValue(request, "CART_INFO", true);
		List<TbItem> list = JsonUtils.jsonToList(cookieValue, TbItem.class);
		for (TbItem tbItem : list) {
			tbItem.setImage(StringUtils.isNotBlank(tbItem.getImage())?tbItem.getImage().split(",")[0]:"");
		}
		model.addAttribute("cartList", list);
		
		return "order-cart";
	}
	@RequestMapping("/order/create")
	public String orderAdd(Model model,OrderInfo orderInfo){
		JsonResult jsonResult = orderService.orderCommit(orderInfo);
		model.addAttribute("orderId",jsonResult.getData());
		model.addAttribute("payment", orderInfo.getPayment());
		model.addAttribute("date", new DateTime().plusDays(3));
		return "success";
	}
}
