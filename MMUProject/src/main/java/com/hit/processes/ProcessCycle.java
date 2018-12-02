package com.hit.processes;

import java.util.List;

public class ProcessCycle 
{
	private List<Long> pages;
	private int sleepMs;
	private List<byte[]> data;
	
	public ProcessCycle(List<Long> pages, int sleepMs, List<byte[]> data)
	{
		this.pages = pages;
		this.sleepMs = sleepMs;
		this.data = data;
	}
	
	public List<byte[]> getData()
	{
		return this.data;
	}
	
	public void setData(List<byte[]> data)
	{
		this.data = data;
	}
	
	public List<Long> getPages()
	{
		return this.pages;
	}
	
	public void setPages(List<Long> pages)
	{
		this.pages = pages;
	}
	
	public int getSleepMs()
	{
		return this.sleepMs;
	}
	
	public void setSleepMs(int sleepMs)
	{
		this.sleepMs = sleepMs;
	}
	
	public String toString()
	{
		return "Sleep MS: " + this.sleepMs + ", Pages: " + this.pages.toString() + ", Data: " + this.data.toString();
	}
}