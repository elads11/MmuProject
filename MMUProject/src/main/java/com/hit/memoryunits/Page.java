package com.hit.memoryunits;

import java.io.Serializable;

public class Page<T> implements Serializable
{
	private static final long serialVersionUID = 8436224213744344720L;
	private Long pageId;
	private T content;
	
	public Page(Long id, T content)
	{
		this.pageId = id;
		this.content = content;
	}
	
	public Long getPageId()
	{
		return pageId;
	}
	
	public void setPageId(Long pageId)
	{
		this.pageId = pageId;
	}
	
	public T getContent()
	{
		return this.content;
	}
	
	public void setContent(T content)
	{
		this.content = content;
	}
	
	@Override
	public int hashCode()
	{
		return this.pageId.hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return this.pageId.hashCode() == obj.hashCode();
	}
	
	@Override
	public String toString()
	{
		return "PageID: " + this.pageId + ", Content: " + this.content.toString();
	}
}