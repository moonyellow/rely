package test;

import scheduler.Scheduler;
import java.util.*;

public class MainTest {
	
	public static Scheduler sd;
	
	public static void main(String[] args) {
		sd = Scheduler.getScheduler();
		
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
			threadOrderlist.add(threadNow.getName());
			
			t.start();
			t.suspend();
			sd.addThread(t);
		}
		
		
	}
}
