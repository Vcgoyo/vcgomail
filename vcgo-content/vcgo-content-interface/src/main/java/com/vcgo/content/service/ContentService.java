package com.vcgo.content.service;

import java.util.List;

import com.vcgo.common.pojo.EasyUIDataGridResult;
import com.vcgo.common.pojo.JsonResult;
import com.vcgo.pojo.TbContent;

public interface ContentService {
	
	EasyUIDataGridResult getContentItems(Integer page,Integer rows,Long categoryId);
	
	JsonResult addContentItem(TbContent item);
	
	List<TbContent> getContentsByCatagoryId(long catagoryId);
}
