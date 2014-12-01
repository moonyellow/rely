package microbench;

import java.util.Random;

import recorder.RandomTable;
import recorder.RecordTable;

public class BenchSharedObject {
	//public static final String METHOD = "replay";
	public static RecordTable Retable;
	public static RandomTable Rantable;
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
		Retable= RecordTable.getRecordTable("record");
		Rantable = RandomTable.getRandomTable("record");
		
		String index = null;

		int ranv = 0;

		//if (record == true) {
			
			ranv = r.nextInt(size);

			Rantable.record(Thread.currentThread().getName(),  "__data_"+ranv);
			
//			switch (ranv) {
//			case 0:
//				index = "__data_0";
//				break;
//			case 1:
//				index = "__data_1";
//				break;
//			case 2:
//				index = "__data_2";
//				break;
//			case 3:
//				index = "__data_3";
//				break;
//			case 4:
//				index = "__data_4";
//				break;
//			default:
//				break;
//			}
//			Rantable.record(Thread.currentThread().getName(),  index);
//			Retable.record( index, Thread.currentThread().getName());
		//}

//		if (record == false) {
//			index = Rantable.get(Thread.currentThread().getName());
////			System.out.println("here"+index);
//			Retable.check(index, Thread.currentThread().getName());
////			// System.out.println(Thread.currentThread().getName()+"="+index);
////		}

		
		if(ranv==0){
			__data_0++;
			Retable.record( "__data_0", Thread.currentThread().getName());
		}else if(ranv==1){
			__data_1++;
			Retable.record( "__data_1", Thread.currentThread().getName());
		}else if(ranv==2){
			__data_2++;
			Retable.record( "__data_2", Thread.currentThread().getName());
		}else if(ranv==3){
			__data_3++;
			Retable.record( "__data_3", Thread.currentThread().getName());
		}else if(ranv==4){
			__data_4++;
			Retable.record( "__data_4", Thread.currentThread().getName());
		}
	
		
//		if(index.equals("__data_0")){
//			__data_0++;
////			Retable.record( "__data_0", Thread.currentThread().getName());
//		}else if(index.equals("__data_1")){
//			__data_1++;
////			Retable.record( "__data_1", Thread.currentThread().getName());
//		}else if(index.equals("__data_2")){
//			__data_2++;
////			Retable.record( "__data_2", Thread.currentThread().getName());
//		}else if(index.equals("__data_3")){
//			__data_3++;
////			Retable.record( "__data_3", Thread.currentThread().getName());
//		}else if(index.equals("__data_4")){
//			__data_4++;
////			Retable.record( "__data_4", Thread.currentThread().getName());
//		}
		
	}
	
}
