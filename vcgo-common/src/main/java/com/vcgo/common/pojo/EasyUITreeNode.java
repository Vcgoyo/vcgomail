package com.vcgo.common.pojo;

import java.io.Serializable;

public class EasyUITreeNode implements Serializable{
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	private String text;
	private String state;
}