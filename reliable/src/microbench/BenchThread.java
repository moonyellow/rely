package microbench;

public class BenchThread extends Thread {
	
	BenchSharedObject s;
	int numOfOperation;
	
	public BenchThread(BenchSharedObject s, int numOfOperation)
	{
		this.s = s;
		this.numOfOperation = numOfOperation;
	}
	public void run()
	{
		for(int i=0;i<numOfOperation;i++)
		{
//			System.out.println(Thread.currentThread().getName() + "cur:" + i);
			s.updateData();
		}
//		try
//		{
//		Thread.sleep(2000);
//		}
//		catch(Exception e)
//		{;}
	}
}
