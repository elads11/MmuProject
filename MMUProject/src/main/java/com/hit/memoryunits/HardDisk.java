package com.hit.memoryunits;
import com.hit.exception.*;
import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;

import com.hit.exception.HardDiskException;
import com.hit.util.MMULogger;

public class HardDisk 
{
	private static final int _SIZE = 500;
	private  static final String DEFAULT_FILE_NAME = "";
	private static HardDisk instance = null;
	private HashMap<Long,Page<byte[]>> pagesInDisk;
	
	private HardDisk() 
	{
		this.pagesInDisk = new HashMap<>();
		File file = new File(DEFAULT_FILE_NAME);
		try 
		{
			file.createNewFile();
		}
		catch(IOException e)
		{
			e.getStackTrace();
		}
		
	} 
	
	public static HardDisk getInstance()
	{
		if(instance==null)
		{
			instance=new HardDisk();
		}
		return instance;
	}
	
	private void writeToDisk() throws FileNotFoundException, IOException
	{
		ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream(DEFAULT_FILE_NAME));
		try
		{
			out.writeObject(this.pagesInDisk);
			out.flush();
		}
		catch (IOException e) 
		{
			MMULogger.getInstance().write("HD:can't write to disk" + e.getMessage(), Level.SEVERE);
		}
		finally 
		{
			out.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void readFromDisk() throws FileNotFoundException, IOException
	{
		File yourFile = new File(DEFAULT_FILE_NAME);
		if (yourFile.length() == 0)
			return;
		
		ObjectInputStream in= new ObjectInputStream(new FileInputStream(DEFAULT_FILE_NAME));
		
		try
		{
			
			this.pagesInDisk=(HashMap<Long,Page<byte[]>>)in.readObject();
		}
		catch (Exception e) 
		{
			MMULogger.getInstance().write("HD:can't read from disk" + e.getMessage(), Level.SEVERE);
		}
		finally 
		{
			in.close();	
		}
	}
	
	public Page<byte[]> pageFault(Long id) throws HardDiskException
	{	
		try 
		{
			readFromDisk();
		} 
		catch (IOException e)
		{
			throw new HardDiskException();
		}
		//MMULogger.getInstance().write("PF:"+id, Level.INFO); //moved to memory management unit
		Page<byte[]> currPage= pagesInDisk.get(id);
		if((currPage == null)&&(this.pagesInDisk.size()<_SIZE))
		{
			currPage = new Page<byte[]>(id, null);
			pagesInDisk.put(id, currPage);
			try 
			{
				writeToDisk();
			} 
			catch (IOException e) 
			{
				//throw new HardDiskException();
			} 
		}
		
		return currPage;
	}
	
	public Page<byte[]> pageReplacement(Page<byte[]> moveToHdPage, Long moveToRamId) throws HardDiskException
	{
		try 
		{
			readFromDisk();
		} 
		catch (IOException e) 
		{
			throw new HardDiskException();
		}
		
		MMULogger.getInstance().write("PR:MTH "+moveToHdPage.getPageId()+" MTR "+moveToRamId, Level.INFO);
		
		if(this.pagesInDisk.containsKey(moveToHdPage.getPageId()))
		{
			this.pagesInDisk.put(moveToHdPage.getPageId(), moveToHdPage);
		}
		else
		{
			if(this.pagesInDisk.size()<_SIZE)
			{
				this.pagesInDisk.put(moveToHdPage.getPageId(), moveToHdPage);
			}
			else
			{
				//TODO handle exception
			}
		}
		
		try 
		{
			writeToDisk();
		} 
		catch (IOException e) 
		{
			throw new HardDiskException();
		} 
		Page<byte[]> currPage= pagesInDisk.get(moveToRamId);
		
		return currPage;
	}
		
	
}