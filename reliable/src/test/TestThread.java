package test;

public class TestThread extends Thread {

	private String  haha = "";
	public TestThread(String haha){
		this.haha = haha;
	}
	@Override
	public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i < 1000; i++)
				System.out.print(i + " "+haha);
	}

}
