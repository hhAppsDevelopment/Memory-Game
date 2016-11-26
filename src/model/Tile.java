package model;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Tile extends JPanel{

	Pair p;
	int i;
	JLabel label;
	
	public Tile(Pair p,int i){
		this.p = p;
		this.i = i;
		
		label = new JLabel();
		label.setText(p.getText(i));
		this.add(label);
		this.setLayout(new GridLayout(1,1));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
	}
	
	public int getId(){
		
		return this.p.getId();
		
	}
	
	public int getI(){
		
		return this.i;
		
	}
	
	public void found(){
		
		label.setText("");
		
	}
}
