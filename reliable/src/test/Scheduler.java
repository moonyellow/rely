package test;

import java.util.*;

public class Scheduler extends Thread

{

	private LinkedList<Thread> queue;

	private int timeSlice;

	private static final int DEFAULT_TIME_SLICE = 1000; // 1 second

	public Scheduler() {

		timeSlice = DEFAULT_TIME_SLICE;

		queue = new LinkedList<Thread>();

	}

	public Scheduler(int quantum) {

		timeSlice = quantum;

		queue = new LinkedList<Thread>();

	}

	/*
	 * 
	 * adds a thread to the queue
	 */

	public void addThread(Thread t) {

		queue.addLast(t);

	}

	/*
	 * 
	 * this method puts the scheduler to sleep for a time quantum
	 */

	private void schedulerSleep() {

		try {

			Thread.sleep(timeSlice);

		} catch (InterruptedException e) {
		}
		;

	}

	public void run() {

		Thread current;

		// set the priority of the scheduler to the highest priority

		this.setPriority(6);

		while (true) {

			try {

				if(!queue.isEmpty()){
					current =  queue.removeFirst();
				}else{
					current = null;
				}

				if ((current != null) && (current.isAlive())) {

					current.setPriority(4);

					schedulerSleep();

					System.out.println("* * * Context Switch * * * ");
					
					current.setPriority(2);
					queue.addLast(current);
				}
				
					

			} catch (NullPointerException e3) {
			}
			;

		}

	}

}