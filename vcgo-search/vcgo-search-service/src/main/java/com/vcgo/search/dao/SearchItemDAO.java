package com.vcgo.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vcgo.common.pojo.SearchItem;
import com.vcgo.common.pojo.SearchResult;

@Repository
public class SearchItemDAO {
	
	@Autowired
	private SolrServer  solrServer;
	
	public SearchResult getSearchResult(SolrQuery solrQuery) throws SolrServerException{
		
		SearchItem searchItem=new SearchItem();
		QueryResponse response = solrServer.query(solrQuery);
		SolrDocumentList results = response.getResults();
		SearchResult searchResult=new SearchResult();
		searchResult.setTotal(results.getNumFound());
		List<SearchItem> searchItems=new ArrayList<>();
		for (SolrDocument solrDocument : results) {
			SearchItem item=new SearchItem();
			item.setCategory_name((String)solrDocument.get("item_category_name"));
			item.setId((String)solrDocument.get("id"));
			item.setImage((String)solrDocument.get("item_image"));
			item.setItem_desc((String)solrDocument.get("item_desc"));
			item.setPrice((long)solrDocument.get("item_price"));
			item.setSell_point((String)solrDocument.get("item_sell_point"));
			
			Map<String, Map<String, java.util.List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			if(list!=null&&list.size()>0){
				item.setTitle(list.get(0));
			}else {
				item.setTitle((String)solrDocument.get("item_title"));
			}
			searchItems.add(item);
		}
		searchResult.setList(searchItems);
		return searchResult;
	}
}
