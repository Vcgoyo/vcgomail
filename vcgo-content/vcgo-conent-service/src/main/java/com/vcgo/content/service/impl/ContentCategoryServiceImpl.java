package com.vcgo.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vcgo.common.pojo.EasyUITreeNode;
import com.vcgo.common.pojo.JsonResult;
import com.vcgo.content.service.ContentCategoryService;
import com.vcgo.mapper.TbContentCategoryMapper;
import com.vcgo.pojo.TbContentCategory;
import com.vcgo.pojo.TbContentCategoryExample;
import com.vcgo.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCateogoryitems(Long parentId) {
		TbContentCategoryExample tbContentCategoryExample=new TbContentCategoryExample();
		
		Criteria criteria = tbContentCategoryExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
		List<EasyUITreeNode> result=new ArrayList<>();
		for (TbContentCategory contentCategory : list) {
			
			EasyUITreeNode treeNode=new EasyUITreeNode();
			treeNode.setId(contentCategory.getId());
			treeNode.setText(contentCategory.getName());
			treeNode.setState(contentCategory.getIsParent()?"closed":"open");
			result.add(treeNode);
		}
		return result;
	}

	@Override
	public JsonResult addContentCategoryItem(Long parentid, String name) {
		TbContentCategory tbContentCategory=new TbContentCategory();
		tbContentCategory.setIsParent(false);
		tbContentCategory.setName(name);
		tbContentCategory.setParentId(parentid);
		tbContentCategory.setSortOrder(1);
		//状态。可选值:1(正常),2(删除)
		tbContentCategory.setStatus(1);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		tbContentCategoryMapper.insert(tbContentCategory);
		TbContentCategory parentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentid);
		parentCategory.setIsParent(true);
		tbContentCategoryMapper.updateByPrimaryKey(parentCategory);
		return JsonResult.ok(tbContentCategory);
	}

	@Override
	public JsonResult updateContentCategory(TbContentCategory item) {
		return null;
	}
	
	

}
