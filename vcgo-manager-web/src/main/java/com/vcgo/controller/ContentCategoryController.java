package com.vcgo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vcgo.common.pojo.EasyUITreeNode;
import com.vcgo.common.pojo.JsonResult;
import com.vcgo.content.service.ContentCategoryService;

@Controller
public class ContentCategoryController {

	
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryItems(@RequestParam(name="id",defaultValue="0")Long id) {
		return contentCategoryService.getContentCateogoryitems(id);
	}
	@RequestMapping("/content/category/create")
	@ResponseBody
	public JsonResult createContentCatogory(Long parentId,String name) {
		return contentCategoryService.addContentCategoryItem(parentId, name);
	}
}
