package com.vcgo.content.service;

import java.util.List;

import com.vcgo.common.pojo.EasyUITreeNode;
import com.vcgo.common.pojo.JsonResult;
import com.vcgo.pojo.TbContentCategory;

public interface ContentCategoryService {
	
	List<EasyUITreeNode> getContentCateogoryitems(Long parentId);
	
	JsonResult addContentCategoryItem(Long parentid,String name);
	
	JsonResult updateContentCategory(TbContentCategory item);
}
