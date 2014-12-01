package replayer;


import java.util.ArrayList;

import microbench.BenchSharedObject;
import microbench.BenchThread;
import recorder.RandomTable;
import recorder.RecordTable;
import scheduler.Scheduler;
import test.TestThread;

public class Replayer {
	private final static int NumberOfThreads = 2;
	private final static int TotalOperation= 500000;
	private final static int SPENumber = 5;

	
	public static void main(String[] args) throws InterruptedException{
		Thread threadNow = Thread.currentThread();


		//replayer

		Scheduler sd = Scheduler.getScheduler();
		
//		TestThread[] T = new TestThread[NumberOfThreads];
		
		//create working threads
		BenchThread[] T = new BenchThread[NumberOfThreads];
		BenchSharedObject s;
        for (int k=0;k<NumberOfThreads;k++) 
        {
        	s = new BenchSharedObject(SPENumber);
        	T[k] = new BenchThread(s,TotalOperation/NumberOfThreads);
			sd.addThread(T[k]);
        }

		long st,et;
		st=System.currentTimeMillis();


		//run scheduler
		for (int k=0;k<NumberOfThreads;k++)
		{
			T[k].start();
		}

//		sd.run();

		for (int k=0;k<NumberOfThreads;k++)
		{
			T[k].join();
		}

        et=System.currentTimeMillis();
		System.out.println(et-st);
		BenchSharedObject.printTop5();

//		BenchSharedObject.printTables();

		System.out.println("=========================");
	}
	
}