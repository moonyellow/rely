package recorder;

import microbench.BenchSharedObject;

public class RecordThread extends Thread {
	
	BenchSharedObject s;
	int numOfOperation;
	
	RecordThread(BenchSharedObject s, int numOfOperation)
	{
		this.s = s;
		this.numOfOperation = numOfOperation;
	}
	
	public void run()
	{
		for(int i=0;i<numOfOperation;i++)
		{
			System.out.print("Executing:" + i);
			s.updateData();
		}
	}
}
