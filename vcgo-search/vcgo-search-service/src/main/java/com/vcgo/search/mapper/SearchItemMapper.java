package com.vcgo.search.mapper;

import java.util.List;

import com.vcgo.common.pojo.SearchItem;

public interface SearchItemMapper {
	
	List<SearchItem> getSearchItems();
	
	SearchItem getSearchItemByid(long id);
}
