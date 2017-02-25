package com.vcgo.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vcgo.common.pojo.JsonResult;
import com.vcgo.common.utils.CookieUtils;
import com.vcgo.common.utils.JsonUtils;
import com.vcgo.pojo.TbItem;
import com.vcgo.service.ItemService;

@Controller
public class CartController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/cart/add/{itemId}")
	public String cartPage(@PathVariable Long itemId,@RequestParam(defaultValue="1") Integer num,HttpServletRequest request,
			HttpServletResponse response){
		List<TbItem> list=getTbItemsByCookie(request);
		boolean flag=false;
			for (TbItem tbItem : list) {
				if(tbItem.getId()==itemId.longValue()){
					tbItem.setNum(tbItem.getNum()+num);
					flag=true;
					break;
				}
			}
		if(!flag){
			TbItem item = itemService.geTbItemById(itemId);
			item.setNum(num);
			String image = item.getImage();
			if (StringUtils.isNotBlank(image)) {
				String[] images = image.split(",");
				item.setImage(images[0]);
			}
			list.add(item);
		}
		CookieUtils.setCookie(request, response, "CART_INFO", JsonUtils.objectToJson(list),80000,true);
		return "cartSuccess";
	}
	
	private List<TbItem> getTbItemsByCookie(HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, "CART_INFO",true);
		if(!StringUtils.isNotBlank(cookieValue)){
			return new ArrayList<>();
		}
		List<TbItem> list = JsonUtils.jsonToList(cookieValue, TbItem.class);
		return list;
	}
	@RequestMapping("/cart/cart")
	public String CartEnd(HttpServletRequest request) {
		List<TbItem> list = getTbItemsByCookie(request);
		request.setAttribute("cartList", list);
		//返回逻辑视图
		return "cart";
	}
	@RequestMapping("/cart/update/num/{itemid}/{num}")
	@ResponseBody
	public JsonResult updateItemNum(@PathVariable Long itemid,@PathVariable Integer num,HttpServletRequest request,HttpServletResponse response){
		List<TbItem> list = getTbItemsByCookie(request);
		for (TbItem tbItem : list) {
			if(tbItem.getId()==itemid.longValue()){
				tbItem.setNum(num);
				break;
			}
		}
		CookieUtils.setCookie(request, response, "CART_INFO", JsonUtils.objectToJson(list),80000,true);
		return JsonResult.ok();
	}
	@RequestMapping("/cart/delete/{itemid}")
	public String deleteCartItem(@PathVariable Long itemid,HttpServletRequest request,HttpServletResponse response) {
		List<TbItem> list = getTbItemsByCookie(request);
		for (TbItem tbItem : list) {
			if(tbItem.getId()==itemid.longValue()){
				list.remove(tbItem);
				break;
			}
		}
		CookieUtils.setCookie(request, response, "CART_INFO", JsonUtils.objectToJson(list),80000,true);
		return "redirect:/cart/cart.html";
	}
}
