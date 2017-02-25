package com.vcgo.search.service;


import com.vcgo.common.pojo.SearchResult;

public interface SearchService {
	SearchResult doSearch (String q,int page,int rows) throws Exception;
}
