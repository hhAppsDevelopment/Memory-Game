package model;

import java.io.Serializable;

public class Pair implements Serializable {

	int id=2;
	String text1,text2;
	boolean firstTakenBoolean;
	
	public Pair(String text1,String text2,int id){
		
		this.text1 = text1;
		this.text2 = text2;
		this.id = id;
		firstTakenBoolean = false;
		
	}
	
	public String getText(int i){
		if (i==1){
			return text1;
		} else return text2;
	}
	
	public int getId(){
		
		return id;
		
	}
	
	public boolean firstTaken(){
		return firstTakenBoolean;
	}
	
	public void tookFirst(){
		firstTakenBoolean = true;
	}
	
}
