package behaviour;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class StorageManager {

	FileInputStream fis;
	FileOutputStream fos;
	ObjectOutputStream oos;
	ObjectInputStream ois;

	
	public void saveList(String s,ArrayList<model.Pair> al){
		File f = new File(s);
		while(f.exists()){
			f = new File(s+"0");
		}

	
		
		
		try {
			fos = new FileOutputStream(s+".memory");
			oos = new ObjectOutputStream(fos);
			
			oos.writeObject(al);
			
			oos.close();
			fos.close(); 
		} catch ( Exception e){
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<model.Pair> readList(String fileName){
		ArrayList<model.Pair> al;
		try{
			fis = new FileInputStream(new File(fileName+".memory"));
			ois = new ObjectInputStream(fis);
			
			al = (ArrayList<model.Pair>) ois.readObject();
			
			ois.close();
			fis.close();
			
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
		return al;
	}
	public ArrayList<String> getList() {
		Path currentRelativePath = Paths.get("");
		File folder = new File(currentRelativePath.toAbsolutePath().toString());
		File[] listOfFiles = folder.listFiles();
		
		ArrayList<String> als = new ArrayList<>();
				
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".memory")) {
		        als.add(listOfFiles[i].getName().substring(0, listOfFiles[i].getName().length()-7));
		      } 
		    }
		
		return als;
	}
	
	
	
}
