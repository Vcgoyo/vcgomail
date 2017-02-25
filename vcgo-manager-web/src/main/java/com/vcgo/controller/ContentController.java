package com.vcgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vcgo.common.pojo.EasyUIDataGridResult;
import com.vcgo.common.pojo.JsonResult;
import com.vcgo.content.service.ContentService;
import com.vcgo.pojo.TbContent;

@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult	 getContentItems(Integer page,Integer rows,Long categoryId) {
		 return contentService.getContentItems(page, rows,categoryId);
	}
	@RequestMapping("/content/save")
	@ResponseBody
	public  JsonResult addContentItem(TbContent item) {
		 return contentService.addContentItem(item);
	}
}
