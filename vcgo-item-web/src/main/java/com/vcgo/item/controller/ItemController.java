package com.vcgo.item.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vcgo.item.pojo.Item;
import com.vcgo.pojo.TbItem;
import com.vcgo.pojo.TbItemDesc;
import com.vcgo.service.ItemService;

@Controller
public class ItemController {
	
	
	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/{itemid}")
	public String ItemPage(@PathVariable Long itemid,Model model){
		
		TbItem tbItem = itemService.geTbItemById(itemid);
		Item item=new Item();
		BeanUtils.copyProperties(tbItem, item);
		TbItemDesc tbItemDesc=itemService.getTbItemDescByItemId(itemid);
		
		model.addAttribute("item",item);
		model.addAttribute("itemDesc",tbItemDesc);
		
		return "item";
	}
}
