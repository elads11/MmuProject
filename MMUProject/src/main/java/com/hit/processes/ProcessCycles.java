package com.hit.processes;

import java.util.List;

public class ProcessCycles 
{
	private List<ProcessCycle> processCycles;
	
	public ProcessCycles(List<ProcessCycle> processCycles)
	{
		this.processCycles = processCycles;
	}
	
	public List<ProcessCycle> getProcessCycles()
	{
		return this.processCycles;
	}
	
	public void setProcessCycles(List<ProcessCycle> processCycles)
	{
		this.processCycles = processCycles;
	}
	
	@Override
	public String toString()
	{
		return this.processCycles.toString();
	}
}