package microbench;

import java.util.Random;

import recorder.RandomTable;
import recorder.RecordTable;

public class BenchSharedObject {
	//public static final String METHOD = "replay";
//	public static RecordTable Retable;
//	public static RandomTable Rantable;
	// ///////////////////////////////////////////////////////
	//public boolean record = false;

	public static void printTop5() {
		System.out.println("total sum="
				+ (__data_0 + __data_1 + __data_2 + __data_3 + __data_4));
		System.out.println(__data_0);
		System.out.println(__data_1);
		System.out.println(__data_2);
		System.out.println(__data_3);
		System.out.println(__data_4);
	}

	static int __data_0 = 0;
	static int __data_1 = 0;
	static int __data_2 = 0;
	static int __data_3 = 0;
	static int __data_4 = 0;

	Random r;

	int size;

	public BenchSharedObject(int size) {
		this.r = new Random();
		this.size = size;

	}

	public void updateData() {
//		Retable= RecordTable.getRecordTable("replay");
//		Rantable = RandomTable.getRandomTable("replay");
		
		String index = null;

		int ranv = 0;
		//if (record == true) {
			
			ranv = r.nextInt(size);

			//Rantable.record(Thread.currentThread().getName(),  "__data_"+ranv);
			


//			index = Rantable.get(Thread.currentThread().getName());
//			Retable.check(index, Thread.currentThread().getName());

		
		if(ranv==0){
			__data_0++;
			//Retable.record( "__data_0", Thread.currentThread().getName());
		}else if(ranv==1){
			__data_1++;
			//Retable.record( "__data_1", Thread.currentThread().getName());
		}else if(ranv==2){
			__data_2++;
			//Retable.record( "__data_2", Thread.currentThread().getName());
		}else if(ranv==3){
			__data_3++;
			//Retable.record( "__data_3", Thread.currentThread().getName());
		}else if(ranv==4){
			__data_4++;
			//Retable.record( "__data_4", Thread.currentThread().getName());
		}
		
		
		
		
		//__data_0++;
		
//		if(index.equals("__data_0")){
//			__data_0++;
//		}else if(index.equals("__data_1")){
//			__data_1++;
//		}else if(index.equals("__data_2")){
//			__data_2++;
//		}else if(index.equals("__data_3")){
//			__data_3++;
//		}else if(index.equals("__data_4")){
//			__data_4++;
//		}
		
	}
	
}
