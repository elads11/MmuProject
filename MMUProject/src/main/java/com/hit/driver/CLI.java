package com.hit.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;

import com.hit.util.MMULogger;
import com.hit.view.View;

public class CLI extends Observable implements Runnable,View 
{

	private static final String LRU = "LRU";
	private static final String MRU = "MRU";
	private static final String RANDOM = "RANDOM";
	private static final String START = "start";
	private static final String STOP = "stop";

	private Scanner cin;
	private PrintWriter cout;

	public CLI(InputStream in, OutputStream out)
	{
		this.cin = new Scanner(in);
		this.cout = new PrintWriter(out);
	}
	
	@Override
	public void run()
	{
		start();
	}

	@Override
	public void start() 
	{
		String[] algoAndCapacity = null;
		String buffer = " ";
		
		while (!buffer.toLowerCase().equals(STOP))
		{
			write("please press 'start' to start");
				buffer = this.cin.nextLine();

			
			if(buffer.equals(STOP))
				break;
			while(!buffer.toLowerCase().equals(START))
			{
				write("not a valid command \nplease enter 'start' to start");
				buffer = cin.nextLine();
			}
		
			do
			{
				write("please enter required algorithm and RAM capacity");
				buffer = cin.nextLine();
				algoAndCapacity = buffer.split(" ");
			}
			while(!(algoAndCapacity.length == 2));

			while ((!is_valid_algo(algoAndCapacity[0])) || (!is_integer(algoAndCapacity[1])))
			{
				MMULogger.getInstance().write("CLI: not a valid command entered", Level.SEVERE);
				write("not a valid command \nplease enter valid algorithm and capacity");
				buffer = cin.nextLine();
				algoAndCapacity = buffer.split(" ");
			}
			write("Thank you");
			//MMULogger.getInstance().write("RC:"+algoAndCapacity[1], Level.INFO);

			
		} 
		
		System.out.println("stopped");
		cin.close();
		cout.close();
		setChanged();
		notifyObservers(algoAndCapacity);
		return;
	}
	
	public void write(String s)
	{	
	    cout.println(s);
		cout.flush();
	}
	
	public boolean is_valid_algo(String s)
	{
		if(s.toUpperCase().equals(LRU)|| s.toUpperCase().equals(MRU) || s.toUpperCase().equals(RANDOM))
		{
			return true;
		}
		return false;
	}
	
	public boolean is_integer(String s)
	{
		try
		{
			Integer.parseInt(s);
			return true;
		}
		catch (Exception e){}
		return false;
	}
}