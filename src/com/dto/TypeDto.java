package com.dto;

import java.util.List;
import java.util.Map;

import com.entity.*;
public class TypeDto {

	private QuickType Type;
	
	private List<Map<String,Object>> news;

	public QuickType getNewsType() {
		return Type;
	}

	public void setNewsType(QuickType newsType) {
		this.Type = newsType;
	}

	public List<Map<String, Object>> getNews() {
		return news;
	}

	public void setNews(List<Map<String, Object>> news) {
		this.news = news;
	}
	
	
}
