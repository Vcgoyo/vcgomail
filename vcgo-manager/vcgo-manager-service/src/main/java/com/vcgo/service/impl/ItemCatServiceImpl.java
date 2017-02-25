package com.vcgo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vcgo.common.pojo.EasyUITreeNode;
import com.vcgo.mapper.TbItemCatMapper;
import com.vcgo.pojo.TbItemCat;
import com.vcgo.pojo.TbItemCatExample;
import com.vcgo.pojo.TbItemCatExample.Criteria;
import com.vcgo.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	
	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		TbItemCatExample tbItemCatExample=new TbItemCatExample();
		Criteria criteria = tbItemCatExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbItemCat> selectByExample = tbItemCatMapper.selectByExample(tbItemCatExample);
		
		List<EasyUITreeNode> eList=new ArrayList<>();
		for (TbItemCat tItemCat : selectByExample) {
			EasyUITreeNode easyUITreeNode=new EasyUITreeNode();
			easyUITreeNode.setId(tItemCat.getId());
			easyUITreeNode.setText(tItemCat.getName());
			easyUITreeNode.setState(tItemCat.getIsParent()?"closed":"open");
			eList.add(easyUITreeNode);
		}
		return eList;
	}

}
