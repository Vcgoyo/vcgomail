package com.vcgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vcgo.common.pojo.JsonResult;
import com.vcgo.search.service.SearchItemService;

@Controller
public class IndexManageController {
	
	
	
	@Autowired
	private SearchItemService searchItemService;
	
	@RequestMapping("/index/import")
	@ResponseBody
	public JsonResult indexImport(){
		return searchItemService.importItemsToIndex();
	}
}
