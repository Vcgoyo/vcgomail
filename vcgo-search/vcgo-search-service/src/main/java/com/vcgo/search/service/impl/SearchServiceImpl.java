package com.vcgo.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vcgo.common.pojo.SearchResult;
import com.vcgo.search.dao.SearchItemDAO;
import com.vcgo.search.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchItemDAO searchItemDAO;
	
	@Override
	public SearchResult doSearch(String q, int page, int rows) throws SolrServerException {
		
		SolrQuery query=new SolrQuery();
		query.set("q",q);
		if(page<1){
			page=1;
		}
		query.setStart((page-1)*rows);
		if(rows<1){
			rows=10;
		}
		query.setRows(rows);
		query.setHighlight(true);
		query.setHighlightSimplePre("<font style='color:red'>");
		query.setHighlightSimplePost("</font>");
		query.set("df","item_title");
		SearchResult searchResult = searchItemDAO.getSearchResult(query);
		
		long totalPage=searchResult.getTotal()/rows;
		if(searchResult.getTotal()%rows>0){
			totalPage++;
		}
		searchResult.setTotalpage(totalPage);
		
		return searchResult;
	}

}
