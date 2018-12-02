package com.hit.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.hit.algorithm.IAlgoCache;
import com.hit.controller.MMUController;
import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.model.MMUModel;
import com.hit.view.MMUView;
import com.hit.config.*;

public class MMUDriver 
{
	public MMUDriver()
	{}

	public static void main(String[] args) 
	{
		List<String> configuration = null;  
		CLI cli = new CLI(System.in,System.out);
		MMUModel model = new MMUModel(configuration);
		MMUView view = new MMUView();
		MMUController controller = new MMUController(model, view);
		model.addObserver(controller);
		cli.addObserver(controller);
		view.addObserver(controller);
		new Thread(cli).start();
	}

}
