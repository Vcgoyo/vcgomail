package com.vcgo.search.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vcgo.common.pojo.SearchResult;
import com.vcgo.search.service.SearchService;

@Controller
public class SearchController {

	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/search")
	public String searchByq(String q,@RequestParam(defaultValue="1") Integer page,Model model){
		
		try {
			q=new String(q.getBytes("iso8859-1"),"utf-8");
			
			
			SearchResult searchResult = searchService.doSearch(q, page,60);
			
			model.addAttribute("query",q);
			model.addAttribute("totalPages",searchResult.getTotalpage());
			model.addAttribute("itemList",searchResult.getList());
			model.addAttribute("page",page);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "search";
		
	}
}
