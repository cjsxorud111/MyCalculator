package com.tae.myCalc;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class MyCalculator extends JFrame {

	private final String names[] = { "7", "8", "9", "-", "4", "5", "6", "+", "1", "2", "3", "*", "0", ".", "=", "/" };
	private JTextField inputText;
	private String input;

	public MyCalculator() {
		super("계산기");

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

			PadInput handler = new PadInput(); // 키패드 이벤트를 발생 시키기 위해 handler 생성
			buttons[count].addActionListener(handler);
		}

		JPanel infoView = new JPanel(new BorderLayout(2, 4));
		JButton clear = new JButton("Clear");

		infoView.add(clear, BorderLayout.NORTH);
		add(groupPanel, BorderLayout.NORTH);
		add(padPanel, BorderLayout.CENTER);
		add(infoView, BorderLayout.SOUTH);

		ClearButton handler = new ClearButton();
		clear.addActionListener(handler);
	}

	private String output = null;
	private ArrayList<String> li = new ArrayList<String>();
	private ArrayList<String> lo = new ArrayList<String>(); //입력값 저장
	private ArrayList<String> vo = new ArrayList<String>(); //연산식 저장

	private class PadInput implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			String eventText = event.getActionCommand();

			if (Arrays.asList(names).contains(eventText) && eventText != "=") {
				li.add(eventText);

				output = li.get(0);

				for (int i = 1; i < li.size(); i++) {
					output = output + li.get(i);
				}
				System.out.println(output);

				inputText.setText(output);

			} else if (eventText.equals("=")) {
				inputText.setText("");
				li.toArray();
				String[] arr = li.toArray(new String[li.size()]);
				String str;

				System.out.println(Arrays.toString(arr));

				int temp = 0;
				for (int i = 0; i < arr.length; i++) {
					if (arr[i] == "+" || arr[i] == "-" || arr[i] == "*" || arr[i] == "/" || arr[i] == ".") {
						lo.add(output.substring(temp, i));
						vo.add(arr[i]);
						temp = i + 1;
					} else if (i == arr.length - 1) {
						String a = output.substring(temp, i + 1);
						lo.add(a);
					}
				}

				System.out.println(lo.toString());
				System.out.println(vo.toString());

				for (int i = 0; i < vo.size(); i++) {
					if (vo.get(i) == ".") {

						String ss = lo.get(i) + "." + lo.get(i + 1);
						double dd = Double.parseDouble(ss);
						lo.remove(i);
						lo.remove(i);
						vo.remove(i);
						lo.add(i, ss);
						System.out.println(vo.toString());
						System.out.println(lo.toString());
					}
				}
				int[] tempp = new int[vo.size()];
				int aa = 0;
				for (int i = 0; i < vo.size(); i++) {
					if (vo.get(i) == "*") {
						tempp[i] = 1;
						Double dd = Double.parseDouble(lo.get(i - aa)) * Double.parseDouble(lo.get(i - aa + 1));
						System.out.println(dd);

						lo.remove(i - aa);
						lo.remove(i - aa);
						System.out.println(aa);
						String ddd = Double.toString(dd);

						lo.add(i - aa, ddd);
						System.out.println(vo.toString());
						System.out.println(lo.toString());
						aa = aa + 1;
					} else if (vo.get(i) == "/") {
						tempp[i] = 1;
						Double dd = Double.parseDouble(lo.get(i - aa)) / Double.parseDouble(lo.get(i - aa + 1));
						System.out.println(dd);

						lo.remove(i - aa);
						lo.remove(i - aa);
						System.out.println(aa);
						String ddd = Double.toString(dd);

						lo.add(i - aa, ddd);
						System.out.println(vo.toString());
						System.out.println(lo.toString());
						aa = aa + 1;
					}
				}
				int num = 0;
				ArrayList<String> vov = new ArrayList<String>();
				for (int i = 0; i < vo.size(); i++) {
					if (vo.get(i) == "+" || vo.get(i) == "-") {
						vov.add(lo.remove(0));
						vov.add(vo.get(i));
					}
				}
				vov.add(lo.remove(0));
				double result = Double.parseDouble(vov.get(0));
				for (int i = 0; i < vov.size(); i++) {
					if (vov.get(i) == "+") {
						result = result + Double.parseDouble(vov.get(i+1));
					} else if (vov.get(i) == "-") {
						result = result - Double.parseDouble(vov.get(i+1));
					}
				}
				
				BigDecimal result2 = new BigDecimal(result);
				if (result - (long)result == 0) {
					inputText.setText(String.valueOf((long)result));
				}else {
					inputText.setText(String.valueOf(result));
				}
				
				
			}
		}
	}

	private class ClearButton implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// 클리어 버튼 시 모든 계산식 함수 초기화
			
			inputText.setText("");
			li.clear();
			lo.clear();
			vo.clear();
			output = null;
		
		}
	}

	public static void main(String[] args) {
		MyCalculator aa = new MyCalculator();
		aa.setSize(210, 350);
		aa.setResizable(true);
		aa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aa.setVisible(true);
	}
}
