package com.vcgo.content.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vcgo.common.pojo.EasyUIDataGridResult;
import com.vcgo.common.pojo.JsonResult;
import com.vcgo.common.utils.JsonUtils;
import com.vcgo.content.jedis.JedisClient;
import com.vcgo.content.service.ContentService;
import com.vcgo.mapper.TbContentMapper;
import com.vcgo.pojo.TbContent;
import com.vcgo.pojo.TbContentExample;
import com.vcgo.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper tbContentMapper;
	@Resource 
	private JedisClient jedisClientCluster;
	
	@Override
	public EasyUIDataGridResult getContentItems(Integer page, Integer rows,Long categoryId) {
		
		PageHelper.startPage(page, rows);
		
		TbContentExample tbContentExample=new TbContentExample();
		Criteria criteria = tbContentExample.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tbContentMapper.selectByExample(tbContentExample);
		PageInfo<TbContent> pageInfo=new PageInfo<>(list);
		EasyUIDataGridResult dataGridResult=new EasyUIDataGridResult();
		
		dataGridResult.setTotal(pageInfo.getTotal());
		dataGridResult.setRows(list);
		return dataGridResult;
	}

	@Override
	public JsonResult addContentItem(TbContent item) {
		item.setCreated(new Date());
		item.setUpdated(new Date());
		tbContentMapper.insert(item);
		try {
			jedisClientCluster.hdel("INDEX_CONTENT", item.getCategoryId()+"");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return JsonResult.ok();
	}

	@Override
	public List<TbContent> getContentsByCatagoryId(long catagoryId) {
		
		try {
			String contentStr = jedisClientCluster.hget("INDEX_CONTENT", catagoryId+"");
			if(!StringUtils.isBlank(contentStr)){
				List<TbContent> tbContens = JsonUtils.jsonToList(contentStr, TbContent.class);
				return tbContens;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		TbContentExample tbContentExample=new TbContentExample();
		Criteria criteria = tbContentExample.createCriteria();
		criteria.andCategoryIdEqualTo(catagoryId);
		
		List<TbContent> list = tbContentMapper.selectByExample(tbContentExample);
		
		try {
			jedisClientCluster.hset("INDEX_CONTENT", catagoryId+"", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

}
