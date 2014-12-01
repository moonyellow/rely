package recorder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RecordTable implements Serializable {
	public static LinkedHashMap<String,ArrayList<String>> table = new LinkedHashMap<String,ArrayList<String>>();	
	private static final String ObjectsFilePath = "/home/kevin/rely/reliable/recordTable";
//Path to save the table
	
	private static RecordTable instance = null;
	private RecordTable()
	{;}
	
	public synchronized static RecordTable getRecordTable(String method)
	{
		if(method.equals("record"))
		{
			if(instance == null)
				instance = new RecordTable();
		
		}
		else if(method.equals("replay"))
		{
			if(instance == null)
				instance = new RecordTable();
			table = null;

		    try
		    {
		      //FileInputStream inputFileStream = new FileInputStream(fileName);
		      FileInputStream inputFileStream = new FileInputStream(ObjectsFilePath);
		      ObjectInputStream objectInputStream = new ObjectInputStream(inputFileStream);
		      table = (LinkedHashMap<String,ArrayList<String>>)objectInputStream.readObject();
		      objectInputStream.close();
		      inputFileStream.close();
		      System.out.println("aa"+table.size());
		      System.out.println(instance.table.get("__data_0").get(2));
		    }
		    catch(ClassNotFoundException e)
		    {
		      e.printStackTrace();
		    }
		    catch(IOException i)
		    {
		      i.printStackTrace();
		    }
			
		}
		return instance;
	}

//	public void readRecordTable(String fileName)
//	{
//		table = null;
//
//	    try
//	    {
//	      //FileInputStream inputFileStream = new FileInputStream(fileName);
//	      FileInputStream inputFileStream = new FileInputStream(ObjectsFilePath);
//	      ObjectInputStream objectInputStream = new ObjectInputStream(inputFileStream);
//	      table = (LinkedHashMap<String,ArrayList<String>>)objectInputStream.readObject();
//	      objectInputStream.close();
//	      inputFileStream.close();
//	      System.out.println("rec" + table.size());
//	    }
//	    catch(ClassNotFoundException e)
//	    {
//	      e.printStackTrace();
//	    }
//	    catch(IOException i)
//	    {
//	      i.printStackTrace();
//	    }
//	}
	
	
	public void record(String variableName, String threadName)
	{
		synchronized(this)
		{
			if(!table.containsKey(variableName))
				table.put(variableName, new ArrayList<String>());
			table.get(variableName).add(threadName);
		}
	}
	
	public void check(String variableName, String threadName)
	{
		
		
		String s=null;
//		System.out.println("want:  "+table.get(variableName).get(0) + "  now:" + threadName);
		while(true)
		{
			s = table.get(variableName).get(0);
			if(s.equals(threadName))
				break;
//			System.out.println("var" + variableName +"now" + s +"wait" + Thread.currentThread().getName());
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		synchronized(this)
		{
			table.get(variableName).remove(0);
		}
//			System.out.println(variableName+ "next="+ table.get(variableName).get(0));
		
	}
	
	public static void writeObject()
	{
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File(ObjectsFilePath)));
			// do the magic  
			oos.writeObject(table);
			// close the writing.
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}