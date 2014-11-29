package test;

import scheduler.Scheduler;

public class MainTest {
	public static void main(String[] args) {
		Scheduler sched = new Scheduler();
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		sched.start();
		Thread.currentThread().setPriority(2);
		sched.addThread(Thread.currentThread());
		

		Thread t1 = new TestThread("TT");
		sched.addThread(t1);
		t1.setPriority(Thread.MIN_PRIORITY);
		t1.start();
		
		
		/*
		Thread t2 = new TestThread("+++");
		sched.addThread(t2);
		t2.setPriority(Thread.MIN_PRIORITY);
		t2.start();
		*/
		
		

		//for (int i = 0; i < 1000; i++)
		//	System.out.print(i + " Main");
	}
}
