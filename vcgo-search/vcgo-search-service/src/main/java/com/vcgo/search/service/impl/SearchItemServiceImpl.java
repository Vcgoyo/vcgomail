package com.vcgo.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vcgo.common.pojo.JsonResult;
import com.vcgo.common.pojo.SearchItem;
import com.vcgo.search.mapper.SearchItemMapper;
import com.vcgo.search.service.SearchItemService;

@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired 
	private SearchItemMapper searchItemMapper;
	@Autowired
	private SolrServer  solrServer;
	
	@Override
	public JsonResult importItemsToIndex() {
		
		
		try {
			List<SearchItem> items = searchItemMapper.getSearchItems();
			
			for (SearchItem searchItem : items) {
				SolrInputDocument document=new SolrInputDocument();
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategory_name());
				document.addField("item_desc", searchItem.getItem_desc());
				solrServer.add(document);
			}
			solrServer.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonResult.build(500, "添加索引错误");
		} 
		return JsonResult.ok();
	}

}
