package com.hit.exception;

import java.io.IOException;

@SuppressWarnings("serial")
public class HardDiskException extends IOException
{
	
	public enum ActionError 
	{
		PAGE_FAULT,
		PAGE_REPLACEMENT;	
	}
	
	public HardDiskException()
	{
	}
	
	public HardDiskException(String msg)
	{
		System.out.println(msg);
	}
	
	public HardDiskException(String msg, ActionError status)
	{
		System.out.println(msg+status);
	}
}
