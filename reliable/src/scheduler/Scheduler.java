package scheduler;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Scheduler extends Thread

{
	/*
	 * private LinkedList<Thread> queue;
	 * 
	 * private int timeSlice;
	 * 
	 * private static final int DEFAULT_TIME_SLICE = 5;
	 * 
	 * private static int index = 0;
	 * 
	 * public Scheduler() {
	 * 
	 * timeSlice = DEFAULT_TIME_SLICE;
	 * 
	 * queue = new LinkedList<Thread>();
	 * 
	 * }
	 * 
	 * public Scheduler(int quantum) {
	 * 
	 * timeSlice = quantum;
	 * 
	 * queue = new LinkedList<Thread>();
	 * 
	 * }
	 * 
	 * 
	 * public synchronized void addThread(Thread t) {
	 * 
	 * queue.addLast(t);
	 * 
	 * }
	 * 
	 * private void schedulerSleep() {
	 * 
	 * try {
	 * 
	 * Thread.sleep(timeSlice);
	 * 
	 * } catch (InterruptedException e) { } ;
	 * 
	 * }
	 * 
	 * public void run() {
	 * 
	 * Thread current;
	 * 
	 * // set the priority of the scheduler to the highest priority
	 * 
	 * this.setPriority(6);
	 * 
	 * while (true) {
	 * 
	 * try { synchronized (this) { if (!queue.isEmpty()) { if (index <
	 * queue.size()) { current = queue.get(index); index++; } else { current =
	 * queue.get(0); index++; } } else { current = null; } }
	 * 
	 * if ((current != null) && (current.isAlive())) { synchronized (this) {
	 * current.setPriority(4); }
	 * 
	 * System.out.println("* * * Context Switch * * * "); schedulerSleep();
	 * 
	 * synchronized (this) { current.setPriority(2); }
	 * 
	 * } else if (current!=null && !current.isAlive()) { synchronized (this) {
	 * queue.remove(current); index--; } } } catch (NullPointerException e3) { }
	 * ;
	 * 
	 * }
	 * 
	 * }
	 */

	private ConcurrentLinkedQueue<Thread> queue = new ConcurrentLinkedQueue<Thread>();

	private int timeSlice = 20;


	// private static final int DEFAULT_TIME_SLICE = 5;


	private static Scheduler instance = null;
	
	private Scheduler() {

	}
	
	
	public static Scheduler getScheduler(){
		if(instance == null) {
	         instance = new Scheduler();
	         instance.start();
	    }
	    return instance;
	}

	/*
	 * 
	 * adds a thread to the queue
	 */

	public void addThread(Thread t) {
		t.setPriority(1);
		queue.add(t);

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

		this.setPriority(10);

		/*
		 * for(Thread cur: queue) { cur.start(); cur.suspend(); }
		 */
		Iterator<Thread> iter = queue.iterator();

		while (true) {

			try {
				if (!queue.isEmpty()) {
					if (iter.hasNext()) {
						current = iter.next();
					} else {
						iter = queue.iterator();
						current = iter.next();
					}
				} else {
					current = null;
				}

				if ((current != null) && (current.isAlive())) {
					current.setPriority(4);
					current.resume();

					schedulerSleep();
 
//					System.out.println("* * * Context Switch to "
//							+ current.getName() + " * * * ");

					current.suspend();
					current.setPriority(1);

				}
				if((current != null) && (!current.isAlive())){
					iter.remove();
				}
				
			} catch (NullPointerException e3) {
			}
			;

		}

	}

}