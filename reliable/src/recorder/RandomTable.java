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

public class RandomTable implements Serializable {
	public static LinkedHashMap<String,ArrayList<String>> table = new LinkedHashMap<String,ArrayList<String>>();	
	private static final String ObjectsFilePath = "/home/kevin/rely/reliable/randomTable";
	
	private static RandomTable instance = null;
	private RandomTable()
	{;}
	
	public synchronized static RandomTable getRandomTable(String method)
	{
		if(method.equals("record"))
		{
			if(instance == null)
				instance = new RandomTable();
		
		}
		else if(method.equals("replay"))
		{
			if(instance == null)
				instance = new RandomTable();
//			table = null;

		    try
		    {
		      //FileInputStream inputFileStream = new FileInputStream(fileName);
		      FileInputStream inputFileStream = new FileInputStream(ObjectsFilePath);
		      ObjectInputStream objectInputStream = new ObjectInputStream(inputFileStream);
		      table = (LinkedHashMap<String,ArrayList<String>>)objectInputStream.readObject();
		      objectInputStream.close();
		      inputFileStream.close();
		      System.out.println(table.size());
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



	public void record(String threadName, String randomValue)
	{
		synchronized(this)
		{
			if(!table.containsKey(threadName))
				table.put(threadName, new ArrayList<String>());
			table.get(threadName).add(randomValue);
		}
	}

	public String get(String threadName)
	{
		String randomValue =null;
		synchronized(this)
		{
			randomValue = table.get(threadName).get(0)+"";
			table.get(threadName).remove(0);
		}
		return randomValue;
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