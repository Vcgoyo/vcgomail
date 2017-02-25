package com.vcgo.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vcgo.common.utils.JsonUtils;
import com.vcgo.content.service.ContentService;
import com.vcgo.pojo.TbContent;
import com.vcgo.portal.pojo.AD1Node;

@Controller
public class IndexController {
	
	@Autowired
	private ContentService contentService;
	
	@Value("${AD1_CATEGORYID}")
	private Long AD1_CATEGORYID;
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value ("${AD1_WIDTH_B}")
	private Integer AD1_WIDTH_B;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_HEIGHT_B;
	
	@RequestMapping("/index")
	public String index(Model model){
		List<AD1Node> nodes=new ArrayList<AD1Node>();
		//此处枚举替换
		List<TbContent> list = contentService.getContentsByCatagoryId(AD1_CATEGORYID);
		
		for (TbContent tbContent : list) {
			AD1Node ad1Node=new AD1Node();
			ad1Node.setAlt(tbContent.getTitle());
			ad1Node.setHeight(AD1_HEIGHT);
			ad1Node.setHeightB(AD1_HEIGHT_B);
			ad1Node.setHref(tbContent.getUrl());
			ad1Node.setSrc(tbContent.getPic());
			ad1Node.setSrcB(tbContent.getPic2());
			ad1Node.setWidth(AD1_WIDTH);
			ad1Node.setWidthB(AD1_WIDTH_B);
			nodes.add(ad1Node);
		}
		String json = JsonUtils.objectToJson(list);
		model.addAttribute("AD1Node", json);
		
		return "index";
	}

}
