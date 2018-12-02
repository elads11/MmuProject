package com.hit.processes;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;

import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.memoryunits.Page;
import com.hit.util.MMULogger;

public class Process implements Callable <Boolean>
{
	private int processId;
	private MemoryManagementUnit mmu;
	private ProcessCycles processCycles;

	public Process(int id, MemoryManagementUnit mmu, ProcessCycles processCycles)
	{
		this.processId = id;
		this.mmu = mmu;
		this.processCycles = processCycles;
	}

	public int getId()
	{
		return this.processId;
	}

	public void setId(int Id)
	{
		this.processId = Id;
	}

	@Override
	public Boolean call()
	{

		for (ProcessCycle cycle : this.processCycles.getProcessCycles()) 
		{	
			Object pagesObject[] = cycle.getPages().toArray();
			Long[] pagesIds = Arrays.copyOf(pagesObject, pagesObject.length, Long[].class);
			Page<byte[]>[] pages = this.mmu.getPages(pagesIds);
			for (int i = 0; i < pages.length; i++) 
			{

				pages[i].setContent(cycle.getData().get(i));
				MMULogger.getInstance().write("GP:p"+this.getId()+" "+pages[i].getPageId()+" "+Arrays.toString(pages[i].getContent())+"\n", Level.INFO);
			}


		}
		return true;
	}

}


/*

 @Override
	public Boolean call()
	{
		final String EMPTY_STRING = "";
		List<ProcessCycle> cycles = this.processCycles.getProcessCycles();
		List<Long> pagesId;
		List<byte[]> data;
		Page<byte[]> page;
		Page<byte[]>[] pages;

		try
		{
			for(ProcessCycle cycle : cycles)
			{
				pagesId = cycle.getPages();
				data = cycle.getData();

				pages = this.mmu.getPages(pagesId.toArray(new Long[pagesId.size()]));
				for(int i = 0; i < pages.length; i++)
				{
					page = pages[i];
					page.setContent(data.get(i));
					MMULogger.getInstance().write("GP:p"+this.getId()+" "+pages[i].getPageId()+" "+Arrays.toString(pages[i].getContent()), Level.INFO);
				}
				MMULogger.getInstance().write(EMPTY_STRING, Level.INFO);
			}
		}

		catch(Exception e)
		{
			MMULogger.getInstance().write(e.getMessage(), Level.SEVERE);
			return false;
		}
		return true;
	}
 }

 */
