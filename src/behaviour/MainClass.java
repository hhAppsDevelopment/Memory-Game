package behaviour;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainClass {
	
	static ArrayList<model.Pair> pairsList = new ArrayList<model.Pair>();
	
	static JFrame frame = new JFrame("Memory game");
	static JPanel mainPanel = new JPanel();
	static JPanel teachersPanel = new JPanel();
	static JPanel studentsPanel = new JPanel();
	static JTextArea getValues = new JTextArea();
		
	model.Tile tile;
	JPanel showStats = new JPanel();	
	JPanel input = new JPanel();
	
	static int entryCount;
	static JTextField firstPair = new JTextField();
	static JTextField secondPair = new JTextField();
	
	
	JButton goTeachers = new JButton("I'm a teacher");
	JButton goStudents = new JButton("I'm a student");
	JButton about = new JButton("About");
	
	JButton goBack = new JButton("Back");
	
	JButton okButton = new JButton("Add");
	
	JLabel toFound;
	JLabel found;
	
	
	static Timer timer;
	static int interval;
	boolean first;
	model.Tile firstTile;
	int remaining;
	int initial;
	
	String aboutDescription = "This is a learning game that allows students to \ninteractively gather the required knowledge for any \npair-related subjects: such as vocabulary \nof a foreign language, or the multiplication table.";
	String foundText1 = "Not yet found: ";
	String foundText2 = "Found: ";
	String foundPairs = " pairs";
	
	static StorageManager sm;
	
	
	public static void main(String[] args){
		
		MainClass mc = new MainClass();		
		mc.setupSM();
		mc.setupGui();
		
		
	}
	
	public void setupSM(){
		sm = new StorageManager();

	}
	
	public void setupGui(){
		frame.setSize(495, 600);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-(frame.getSize().width/2), dim.height/2-frame.getSize().height/2);
		
		setupMain();
		
		
		frame.setContentPane(mainPanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);	
		frame.setVisible(true);
	}
	
	public void setupMain(){
			
		mainPanel.removeAll();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		frame.setSize(300, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-(frame.getSize().width/2), dim.height/2-frame.getSize().height/2);
		
		goTeachers.addActionListener(new onGoListener());
		goStudents.addActionListener(new onGoListener());
		about.addActionListener(new onMessageListener());

		
		goTeachers.setAlignmentX(Component.CENTER_ALIGNMENT);
		goStudents.setAlignmentX(Component.CENTER_ALIGNMENT);
		about.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(goTeachers);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(goStudents);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(about);
		mainPanel.add(Box.createVerticalGlue());
	}
	
	public void setupStudents(ArrayList<model.Pair> al){
		timer();
		
		frame.setSize(495, 600);
		
		first = true;
		remaining = 18;
		initial = remaining;
		studentsPanel.removeAll();
		studentsPanel.setLayout(null);
		studentsPanel.setBackground(Color.GRAY);
		
		goBack.addActionListener(new onGoListener());
		int x = 0;
		int z = 0;
		int y = 0;
		Collections.shuffle(al, new Random(System.nanoTime()));
		for(int i = 0; i<6; i++){
			for(int j = 0; j<6; j++){
				int k;
				double d = Math.random();
				
				
				if(d < 0.5 && z < 18){
					
					do{
						x = (int) Math.round(Math.random()*17);
						tile = new model.Tile(al.get(x),1);
					}while((al.get(x).firstTaken()));
					al.get(x).tookFirst();
					z++;
					
				}
				else if(y<18){
					tile = new model.Tile(al.get(y),2);
					y++;
				}else{
					do{
						x = (int) Math.round(Math.random()*17);
						tile = new model.Tile(al.get(x),1);
					}while((al.get(x).firstTaken()));
					al.get(x).tookFirst();
					z++;
				}
				
					
					
				
				tile.setLocation(7+80*i,7+80*j);
				tile.setSize(66,66);
				tile.setBackground(Color.WHITE);
				
				tile.addMouseListener(new TileListener());
				studentsPanel.add(tile);
															
				
			}
		
		
		}
		
		showStats.setLayout(new BoxLayout(showStats, BoxLayout.X_AXIS));			
		toFound = new JLabel(foundText1 + remaining + foundPairs);
		toFound.setForeground(Color.WHITE);
		found = new JLabel(foundText2 + 0 + foundPairs);
		found.setForeground(Color.WHITE);
		
		showStats.add(Box.createHorizontalGlue());
		showStats.add(toFound);
		showStats.add(Box.createHorizontalGlue());
		showStats.add(found);
		showStats.add(Box.createHorizontalGlue());
		
		showStats.setBackground(Color.GRAY);
		
		showStats.setLocation(7,490);
		showStats.setSize(310,66);
		
		studentsPanel.add(showStats);
		
		goBack.addActionListener(new onGoListener());
		goBack.setLocation(327,490);
		goBack.setSize(150,66);
		studentsPanel.add(goBack);
		
	}
	public void setupTeachers(){
		
		
		frame.setSize(495, 600);
		
		teachersPanel.removeAll();
		teachersPanel.setLayout(null);
		
		
		getValues.setEditable(false);
		getValues.setLocation(250, 7);
		getValues.setSize(235,535);
		
		
		input.setLayout(new GridLayout(0,1));
		
		

		input.add(new JLabel("First member of pair:"));
		input.add(firstPair);
		input.add(new JLabel("Second member of pair:"));
		input.add(secondPair);
		input.add(new JPanel());
		input.add(new JPanel());
		input.add(new JPanel());
		
		okButton.addActionListener(new onAddListener());
		input.add(okButton);
		input.add(goBack);
		
		input.setSize(235, 535);
		input.setLocation(7,7);
		
		
		
		
		teachersPanel.add(input);
		teachersPanel.add(getValues);
		
		
		goBack.addActionListener(new onGoListener());
		
		
		
	}
	
	public static void timer(){
		interval = 0;
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			public void run() {
				frame.setTitle("Memory game - Time elapsed: "+ Integer.toString(interval++)+ " s" );
			}
		}, 1000, 1000);
	}
	

	public static void switchContent(String text) {
		JPanel panel = null;
		MainClass mc = new MainClass();
		Dimension dim;
		switch(text){
		case "I'm a teacher": 
			
			panel = teachersPanel; 
			mc.setupTeachers();
			frame.setSize(495, 600);
			
			dim = Toolkit.getDefaultToolkit().getScreenSize();
			frame.setLocation(dim.width/2-(frame.getSize().width/2), dim.height/2-frame.getSize().height/2);
			break;
			
		case "I'm a student":
			
			Object answ = JOptionPane.showInputDialog(null, "Please choose the name of the file you want to open!", "Choose", JOptionPane.DEFAULT_OPTION, null, sm.getList().toArray(), null);
			if(answ != null){
				if(new File(answ.toString()+".memory").exists()){
					mc.setupStudents(sm.readList(answ.toString()));
					panel = studentsPanel;
					frame.setSize(495, 600);
					dim = Toolkit.getDefaultToolkit().getScreenSize();
					frame.setLocation(dim.width/2-(frame.getSize().width/2), dim.height/2-frame.getSize().height/2);
					} 
				} else { 
					panel = mainPanel;
					frame.setSize(300, 300);
					dim = Toolkit.getDefaultToolkit().getScreenSize();
					frame.setLocation(dim.width/2-(frame.getSize().width/2), dim.height/2-frame.getSize().height/2);					
				}
			break;
			
		case "Back":
			MainClass.timer();
			MainClass.timer.cancel();
			frame.setTitle("Memory game");
			
			pairsList.clear();
			getValues.setText("");
			entryCount = 0;
			
			mc.setupMain();
			panel = mainPanel;
			frame.setSize(300, 300); 
			dim = Toolkit.getDefaultToolkit().getScreenSize();
			frame.setLocation(dim.width/2-(frame.getSize().width/2), dim.height/2-frame.getSize().height/2);
			break;
		}
		
		frame.setContentPane(panel);
		frame.validate();
		frame.repaint();
		
	}
	
	
	class onAddListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			entryCount++;
			if(entryCount <= 18){
			
				
			getValues.setText(getValues.getText() + "\n" + " " + firstPair.getText() + "\t" + secondPair.getText());
			pairsList.add(new model.Pair(firstPair.getText(), secondPair.getText(), entryCount-1));
			
			firstPair.setText("");
			secondPair.setText("");
			firstPair.requestFocus();
			}
			if(entryCount == 18){
				String answ = JOptionPane.showInputDialog("Please write a filename!");
				if(answ != null){
					sm.saveList(answ, pairsList);
					pairsList.clear();
					
				}
				
			}
			
		}
		
	}
	
	class onGoListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			MainClass.switchContent(e.getActionCommand());
		}
		
	}
	
	class onMessageListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String shown = null;
			
			switch(e.getActionCommand()){
			case "About": 
				shown = aboutDescription; break;		
			}
			
			
			JOptionPane.showMessageDialog(null, shown, e.getActionCommand(), JOptionPane.INFORMATION_MESSAGE);
			
		}
		
	}
	
	class TileListener implements MouseListener{
		
		public void mouseClicked(MouseEvent e){
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
			if(first){			
				((model.Tile)e.getSource()).setBackground(Color.YELLOW);
				firstTile = (model.Tile)e.getSource();
				first = false;
		

			} else if(firstTile.getId() == ((model.Tile)e.getSource()).getId()&&firstTile.getI() != ((model.Tile)e.getSource()).getI()){ 
				
				((model.Tile)e.getSource()).setBackground(Color.GRAY);
				((model.Tile)e.getSource()).found();
				((model.Tile)e.getSource()).removeMouseListener(this);
		
				
				firstTile.setBackground(Color.GRAY);
				firstTile.found();
				firstTile.removeMouseListener(this);
				first = true;
				
				pairFound();
				
			} else {				
				firstTile.setBackground(Color.WHITE);
				first = true;
				
				
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}
		
	}

	public void pairFound(){
		
		remaining--;
		toFound.setText(foundText1 + remaining + foundPairs);
		int k = initial-remaining;
		found.setText(foundText2 + k + foundPairs);
		

		if(remaining==0){
			timer.cancel();
			JOptionPane.showConfirmDialog(null, "Congatulations, you found all pairs! Time elapsed: " + interval,"Congratulations",JOptionPane.DEFAULT_OPTION);
			switchContent("Back");
			}
		
	}
	
	

}
