package scheduler;

import java.util.*;

public class Scheduler extends Thread

{

	private LinkedList<Thread> queue;

	private int timeSlice;

	private static final int DEFAULT_TIME_SLICE = 5; 

	private static int index = 0;

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

	public synchronized void addThread(Thread t) {

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
				synchronized (this) {
					if (!queue.isEmpty()) {
						if (index < queue.size()) {
							current = queue.get(index);
							index++;
						} else {
							current = queue.get(0);
							index++;
						}
					} else {
						current = null;
					}
				}

				if ((current != null) && (current.isAlive())) {
					synchronized (this) {
						current.setPriority(4);
					}

					System.out.println("* * * Context Switch * * * ");
					schedulerSleep();

					synchronized (this) {
						current.setPriority(2);
					}

				} else if (current!=null && !current.isAlive()) {
					synchronized (this) {
						queue.remove(current);
						index--;
					}
				}
			} catch (NullPointerException e3) {
			}
			;

		}

	}

}