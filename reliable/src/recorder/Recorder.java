package recorder;

import java.util.ArrayList;

import microbench.BenchSharedObject;
import microbench.BenchThread;
import scheduler.Scheduler;
import test.TestThread;

public class Recorder {
	private final static int NumberOfThreads = 4;
	private final static int TotalOperation= 5000000;
	private final static int SPENumber = 5;

	
	public static void main(String[] args) throws InterruptedException{
		
		Scheduler sd = Scheduler.getScheduler();
		Thread t;
		Thread threadNow = Thread.currentThread();
		
		//recorder
		char c;
		
		
//		TestThread[] T = new TestThread[NumberOfThreads];
		
		
		//create working threads
		BenchThread[] T = new BenchThread[NumberOfThreads];
		BenchSharedObject s;
		long st,et;
		st=System.currentTimeMillis();
		
        for (int k=0;k<NumberOfThreads;k++) 
        {
        	s = new BenchSharedObject(SPENumber);
        	T[k] = new BenchThread(s,TotalOperation/NumberOfThreads);
			
			T[k].start();
//			T[k].suspend();
//			sd.addThread(T[k]);
        }
		
		//use sd to run scheduler
//		for (int k=0;k<NumberOfThreads;k++)
//		{
//
//		}
		
//		sd.run();
		
		for (int k=0;k<NumberOfThreads;k++)
		{
			T[k].join();
		}
		
        et=System.currentTimeMillis();
		System.out.println(et-st);	
		BenchSharedObject.printTop5();
		
//write object
		RecordTable.writeObject();
		RandomTable.writeObject();
		System.out.println("=========================");

	}
	
}

