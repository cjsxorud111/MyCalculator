package com.tae.myCalc;
import javax.swing.*;
import java.awt.*;

public class MyCalculator extends JFrame {
	
	private String chkInput; 
	private int numOne, sum, total, sosu; 
	private int count = 0, inputChk = 0; 
	private final String names[] = { "7", "8", "9", "-", "4", "5", "6", "+", "1", "2", "3", "*", "0", ".", "=", "/" }; 
	private JTextField inputText;
	  
	public MyCalculator() {
		super("°è»ê±â");
		
		BorderLayout layout = new BorderLayout(2, 2); 
		setLayout(layout); 
		
		JPanel groupPanel = new JPanel(new BorderLayout(5, 3));
		inputText = new JTextField("", SwingConstants.CENTER); 
		inputText.setHorizontalAlignment(JTextField.RIGHT); 
		groupPanel.add(inputText, BorderLayout.CENTER);
		
		JPanel padPanel = new JPanel(new GridLayout(4, 4)); 
		JButton buttons[] = new JButton[names.length]; 
		
		for (int count = 0; count < names.length; count++) {
			buttons[count] = new JButton(names[count]);
			padPanel.add(buttons[count]);
		}
		
		JPanel infoView = new JPanel(new BorderLayout(2, 4));
		JButton clear = new JButton("Clear");
		
		infoView.add(clear, BorderLayout.NORTH);
		add(groupPanel, BorderLayout.NORTH);
		add(padPanel, BorderLayout.CENTER);
		add(infoView, BorderLayout.SOUTH);
	}
	public static void main(String[] args) {
		MyCalculator aa = new MyCalculator();
		aa.setSize(210, 350); 
		aa.setResizable(true);
		aa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		aa.setVisible(true);
	}
}
