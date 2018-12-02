package com.hit.memoryunits;

import java.util.HashMap;
import java.util.Map;

public class RAM 
{
	private int initialCapacity;
	private Map<Long, Page<byte[]>> pagesMapInRam;
	
	public RAM(int initialCapacity)
	{
		this.initialCapacity = initialCapacity;
		this.pagesMapInRam = new HashMap<>(this.initialCapacity);
	}
	
	public Map<Long, Page<byte[]>> getPages()
	{
		return this.pagesMapInRam;
	}
	
	public void setPages(Map<Long, Page<byte[]>> pages)
	{
		this.pagesMapInRam = pages;
	}
	
	public Page<byte[]> getPage(Long pageId)
	{
		return this.pagesMapInRam.get(pageId);
	}
	
	public void addPage(Page<byte[]> addPage)
	{
		this.pagesMapInRam.put(addPage.getPageId(), addPage);
	}
	
	public void removePage(Page<byte[]> pageToRemove)
	{
		this.pagesMapInRam.remove(pageToRemove.getPageId());
	}
	
	public Page<byte[]>[] getPages(Long[] pageIds)
	{
		@SuppressWarnings("unchecked")
		Page<byte[]>[] requestedPagesArr = new Page[pageIds.length];
		for (int i = 0; i < requestedPagesArr.length; i++) 
		{
			if(this.pagesMapInRam.containsKey(pageIds[i]))
				requestedPagesArr[i] = this.pagesMapInRam.get(pageIds[i]);
			else
			{
				requestedPagesArr[i] = null;	
			}
		}
		return requestedPagesArr;
	}
	
	public void addPages(Page<byte[]>[] addPages)
	{
		for (Page<byte[]> page : addPages) 
		{
			this.addPage(page);
		}
	}
	
	public void removePages(Page<byte[]>[] remvoePages)
	{
		for (Page<byte[]> page : remvoePages) 
		{
			this.removePage(page);
		}
	}
	
	public int getInitialCapacity()
	{
		return this.initialCapacity;
	}
	
	public void setInitialCapacity(int initialCapacity)
	{
		this.initialCapacity = initialCapacity;
	}
	
	public int getMapSize()
	{
		return this.pagesMapInRam.size();
	}
}