/*
 *  File name: SeaPortProgram.java
 *  Date: April 20, 2017
 *  Class: CMSC-335
 *  Author: Behrooz Babazadeh
 *  Purpose: To construct a GUI that will read the proper data from a file, given the projects requirements.
 *  	This program leveraged the working cave example given for the project. 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;


public class SeaPortProgram extends JFrame {
	static final long serialVersionUID = 123L;

	public World world;
	public Job job;
	static JTextArea displayArea, jobArea;
	private Scanner inputScanner;
	private JScrollPane scrollPane;
	private JButton readButton;
	private JButton cancelButton;
	private JButton searchButton;
	private JLabel searchTarget;
	static JPanel displayPanel, jobConsole;
	private JFileChooser fileChooser;
	private JButton sortButton;
	String[] sortingStrings = { "weight", "length", "width", "draft" };
	public static HashMap<String, Integer> skill = new HashMap<>();
	static DefaultMutableTreeNode root = new DefaultMutableTreeNode("World");//Creating a Tree node based on project 3 requirements
    public static JTree tree = new JTree(root);
    static JTable table = new JTable();
    public static DefaultTableModel dtm = new DefaultTableModel(0, 0);
    String[] header = new String[] {"Id", "Name"};
	
	
	/*********************************************************************
	 * The sea port program method below will create the needed GUI 
	 **********************************************************************/
	public SeaPortProgram() {
		setTitle("Sea Ports Program");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		displayArea = new JTextArea(10, 10);
		displayArea.setBackground(Color.BLACK);
		displayArea.setForeground(Color.WHITE);
		displayArea.setFont(new java.awt.Font ("Monospaced", 0, 12));

		
		scrollPane = new JScrollPane(displayArea);
		scrollPane.setSize(300, 100);
		JPanel textArea = new JPanel(new BorderLayout());
		textArea.add(scrollPane, BorderLayout.CENTER);
		
		
		
		// ---------------------------------	Below is the job panel -----------
		jobConsole = new JPanel();
		jobArea = new JTextArea(10,10);
		jobArea.setText(toString());
		add(new JScrollPane (jobArea), BorderLayout.CENTER);
		jobConsole.setLayout (new GridLayout (0, 5, 2, 5));
		add(new JScrollPane (jobConsole));
		//-----------	End
		
		
		//	---------------------------------	Tree Panel		---------------------------------
		JScrollPane treeP = new JScrollPane(tree);
		treeP.setPreferredSize(new Dimension(100, 30));
		//-----------	End
		
		
		JTabbedPane tabbedOutput = new JTabbedPane();
		tabbedOutput.add("Console Output", textArea);
		tabbedOutput.add("Tree View", treeP);
		tabbedOutput.add("Job Processing", jobConsole);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setForeground(Color.BLUE);
		
		readButton = new JButton("Read");
		searchButton = new JButton("Search");
		searchTarget = new JLabel("Search Box");
		sortButton = new JButton("Sort");

		final JTextField searchBox = new JTextField(10);
		final JComboBox<String> searchingCriteria = new JComboBox<String>();
		searchingCriteria.addItem("Index");
		searchingCriteria.addItem("Skill");
		searchingCriteria.addItem("Name");
		final JComboBox<String> jcbsort = new JComboBox<String>(sortingStrings);
		
		displayPanel = new JPanel();
		displayPanel.add(cancelButton);
		displayPanel.add(readButton);
		displayPanel.add(searchTarget);
		displayPanel.add(searchBox);
		displayPanel.add(searchingCriteria);
		displayPanel.add(searchButton);
		displayPanel.add(jcbsort);
		displayPanel.add(sortButton);
		
		add(displayPanel, BorderLayout.PAGE_START);
		add(tabbedOutput, BorderLayout.CENTER);
		
		pack();
		readFileData();
		validate();
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(EXIT_ON_CLOSE);
			}
		});
		readButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				readFileData();
			}
		});

		sortButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayArea
						.append("\n\n*********************** Sorted Data ***********************\n");
				String type = jcbsort.getSelectedItem().toString();
				String resultText = sortType(type);
				displayArea.append(resultText);
			}
		});
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayArea.append("\n\n\t<<<<<<<	Result >>>>>>> \n\n");
				String searchString = searchBox.getText();
				String typeOFSearch = (String) searchingCriteria
						.getSelectedItem();
				
				displayArea.append(" " + world.search(typeOFSearch, searchString));
				displayArea.append("\n\n\t<<<<<<<	END of Result >>>>>>> \n");
			}
		});
	}
	

	/*********************************************************************
	 * Read file data method below will allow the user to select one file 
	 * 	from the working directory and load the data to the program.
	 **********************************************************************/
	public void readFileData() {
		fileChooser = new JFileChooser(".");//Start from the directory, when selecting a file
		int result = fileChooser.showOpenDialog(new JFrame());
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				inputScanner = new Scanner(new FileReader(
				fileChooser.getSelectedFile()));
				} 
				catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "File Not Found.");
			}//End of the try / catch block
		}//End of the conditional statement
		world = new World(inputScanner, jobConsole);
		displayArea.setText(world.toString());
	}//***********************************	END of read file data method ******************************************
	

	/*********************************************************************
	 * sortType method below receives an argument and will sort the user given values needed.
	 * @param type
	 * @return
	 **********************************************************************/
	private String sortType(String type) {
		String result = "";
		ArrayList<Ship> ships = new ArrayList<>();
		
		for (SeaPort msp : world.ports.values()) {
			for (Ship ms : msp.getShips()) {
				ships.add(ms);
			}//End of the nested for loop statement here
		}//End of the first for loop statement here

		if (type.equals("weight")) {
			Collections.sort(ships, new ShipComparator("weight"));
		} 
		else if (type.equals("width")) {
			Collections.sort(ships, new ShipComparator("X"));
		} 
		else if (type.equals("length")) {
			Collections.sort(ships, new ShipComparator("length"));
		} 
		else if (type.equals("draft")) {
			Collections.sort(ships, new ShipComparator("draft"));
		}//End of the if statement here

		for (Ship ship : ships) {
			result += ship.toString() + "\n";
		}//End of for loop here
		return result;
	}//********************** END of sortType method **********************

	
	public World getWorld() {
		return this.world;
	}
	
	/*********************************************************************
	 * Main Function
	 * 
	 * @param args
	 **********************************************************************/
	public static void main(String[] args) {
		new SeaPortProgram();
	}//End of the main method here

	
}

























		

	 	   	



