package com.vcgo.service;

import java.util.List;

import com.vcgo.common.pojo.EasyUITreeNode;

public interface ItemCatService {
	
	List<EasyUITreeNode> getItemCatList(long parentId);
}
