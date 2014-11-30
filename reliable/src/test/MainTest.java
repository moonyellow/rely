package test;

import scheduler.Scheduler;
import java.util.*;

public class MainTest {
	public static void main(String[] args) {
		/*
		Scheduler sched = new Scheduler();
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		sched.start();
		Thread.currentThread().setPriority(2);
		sched.addThread(Thread.currentThread());
		

		Thread t1 = new TestThread("TT");
		sched.addThread(t1);
		t1.setPriority(Thread.MIN_PRIORITY);
		t1.start();
	

		
		Thread t2 = new TestThread("+++");
		sched.addThread(t2);
		t2.setPriority(Thread.MIN_PRIORITY);
		t2.start();
		
		
		

		for (int i = 0; i < 1000; i++)
			System.out.print(i + " Main");*/
		//Scheduler sd = Scheduler.getScheduler();
		
		
		
		Thread t = null;
		Thread threadNow = Thread.currentThread();

		//Order list
		ArrayList<String> threadOrderlist = new ArrayList<String>();
		
		
	
		//recorder
		char c;
		
		
		for(int i=0;i<5;i++)
		{
			c = (char) ('A' + i);
			t = new TestThread(c+"");
//insert	--lock--
			threadOrderlist.add(threadNow.getName());
			//sd.addThread(t);
			t.start();
			//t.suspend();
		}
		
		
	}
}
