package com.vcgo.common.pojo;


import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {

	private Long total;
	private List list;
	private Long totalpage;
	
	public long getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(long totalpage) {
		this.totalpage = totalpage;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
}
