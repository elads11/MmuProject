package com.hit.memoryunits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import com.hit.algorithm.IAlgoCache;
import com.hit.exception.HardDiskException;
import com.hit.util.MMULogger;


public class MemoryManagementUnit 
{
	private IAlgoCache<Long, Long> algo;
	private RAM ram;
	
	public MemoryManagementUnit(int ramCapacity, IAlgoCache<Long, Long> algo)
	{
		this.algo = algo;
		ram = new RAM(ramCapacity);
		MMULogger.getInstance().write("RC:" + ramCapacity, Level.INFO);
		
	}
	
	public IAlgoCache<Long, Long> getAlgo()
	{
		return this.algo;
	}
	
	public void SetAlgo(IAlgoCache<Long, Long> someAlgo)
	{
		this.algo = someAlgo;
	}
	
	public RAM getRam()
	{
		return this.ram;
	}
	
	public void setRam(RAM someRam)
	{
		this.ram = someRam;
	}
	
	
	public Page<byte[]>[] getPages(Long[] pageIds) 
	{
		HardDisk hd = HardDisk.getInstance();
		@SuppressWarnings("unchecked")
		Page<byte[]>[] pageToReturn = new Page[pageIds.length];
		Page<byte[]> moveToHdPage = null;
		Long idPageReplace = null;
		
		for(int i=0; i<pageIds.length;i++)
		{
			if(algo.getElement(pageIds[i]) == null)
			{ 
				//if RAM is not full
				if(ram.getMapSize()<=ram.getInitialCapacity())
				{
					algo.putElement(pageIds[i],pageIds[i]);
					try {
						ram.addPage(hd.pageFault(pageIds[i]));
						MMULogger.getInstance().write("PF:"+pageIds[i], Level.INFO);
					} catch (HardDiskException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//else: Do logic of full RAM
				else
				{
					idPageReplace = algo.putElement(pageIds[i],pageIds[i]);
					moveToHdPage = ram.getPage(idPageReplace);
						try {
							ram.addPage(hd.pageReplacement(moveToHdPage, pageIds[i]));
						} catch (HardDiskException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}
				}
			}
			pageToReturn[i] = ram.getPage(pageIds[i]);
		}
		return pageToReturn;
	}
	
	
	
}