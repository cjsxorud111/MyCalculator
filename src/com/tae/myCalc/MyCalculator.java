package com.tae.myCalc;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MyCalculator extends JFrame {

	private final String names[] = { "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "*", ".", "0", "=", "/" };
	private JTextField inputText;

	public MyCalculator() {
		super("계산기");

		BorderLayout layout = new BorderLayout();
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
	private ArrayList<String> inputTextList = new ArrayList<String>(); //입력값 저장
	private ArrayList<String> numberText = new ArrayList<String>(); //숫자 저장
	private ArrayList<String> operatorText = new ArrayList<String>(); //연산식 저장
	private String temp = null; //연산자 두개연속인지 확인위한 변수
	private Stack<String> forCheckStack = new Stack<String>(); 
	private class PadInput implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			String eventText = event.getActionCommand();

			if (Arrays.asList(names).contains(eventText) && eventText != "=") { //일반 텍스트 입력시
				inputTextList.add(eventText); //기본 입력값 저장
				forCheckStack.push(eventText); //연산자 연속입력 확인위해 입력받는대로 연속해서 넣어줌
				String forCheckOperator[] = { "+", "-", "*", "/", "="};  //연산자 연속입력 확인위한 배열
			
				//첫문자로 연산자 입력시 삭제
				if (Arrays.asList(forCheckOperator).contains(inputTextList.get(0))) {
					inputTextList.remove(0); 
				}
				
				//스택에서 하나빼고 남은게 연산자면 그대로 아니면 뺀걸다시추가
				if (Arrays.asList(forCheckOperator).contains(eventText)) {
					String temp = forCheckStack.pop();
					if (Arrays.asList(forCheckOperator).contains(forCheckStack.peek())) {
						inputTextList.remove(inputTextList.size()-1);
					}else {
						forCheckStack.push(temp);
					}
				}
			
				output = inputTextList.get(0); 
				
				//출력위한 문자열 만듦
				for (int i = 1; i < inputTextList.size(); i++) {
					output = output + inputTextList.get(i);
				}
				
				System.out.println(output);
				//입력값 출력
				inputText.setText(output);

			} else if (eventText.equals("=")) { //"="입력받았을때
				inputText.setText(""); //우선 출력창을 비움
				inputTextList.toArray(); 
				String[] inputTextArray = inputTextList.toArray(new String[inputTextList.size()]); //inputTextList배열변환

				System.out.println(Arrays.toString(inputTextArray));

				int temp = 0;
				
				//출력위한 문자열 만듦
				for (int i = 0; i < inputTextArray.length; i++) {
					if (inputTextArray[i] == "+" || inputTextArray[i] == "-" || inputTextArray[i] == "*" || inputTextArray[i] == "/" || inputTextArray[i] == ".") {
						numberText.add(output.substring(temp, i));
						System.out.println(temp + "  " + i);
						operatorText.add(inputTextArray[i]); 
						temp = i + 1;
					} else if (i == inputTextArray.length - 1) {
						numberText.add(output.substring(temp, i + 1));
					}
				}

				System.out.println(numberText.toString()+"??1");
				System.out.println(operatorText.toString()+"??2");
				//"."입력받았을때 소수점추가해 문자열만듦
				for (int i = 0; i < operatorText.size(); i++) {
					if (operatorText.get(i) == ".") {
						String newAddDot = numberText.get(i) + "." + numberText.get(i + 1);
						numberText.remove(i);
						numberText.remove(i);
						operatorText.remove(i);
						numberText.add(i, newAddDot);
						System.out.println(operatorText.toString());
						System.out.println(numberText.toString());
					}
				}
				
				int[] tempp = new int[operatorText.size()];
				int index = 0;
				//실제연산 연산자 만나면 연산자 앞숫자와 뒷숫자 연산후 연산자 앞숫자,연산자,연산자 뒷숫자 지우고 그자리에 넣음
				for (int i = 0; i < operatorText.size(); i++) {
					if (operatorText.get(i) == "*") {
						tempp[i] = 1;
						Double doubleText = Double.parseDouble(numberText.get(i - index)) * Double.parseDouble(numberText.get(i - index + 1));
						System.out.println(doubleText);

						numberText.remove(i - index);
						numberText.remove(i - index);
						System.out.println(index);
						String dDouble = Double.toString(doubleText);

						numberText.add(i - index, dDouble);
						System.out.println(operatorText.toString());
						System.out.println(numberText.toString());
						index = index + 1;
						
					} else if (operatorText.get(i) == "/") {
						tempp[i] = 1;
						Double dd = Double.parseDouble(numberText.get(i - index)) / Double.parseDouble(numberText.get(i - index + 1));
						System.out.println(dd);

						numberText.remove(i - index);
						numberText.remove(i - index);
						System.out.println(index);
						String ddd = Double.toString(dd);

						numberText.add(i - index, ddd);
						System.out.println(operatorText.toString());
						System.out.println(numberText.toString());
						index = index + 1;
					}
				}
				
				int num = 0;
				ArrayList<String> textTemp = new ArrayList<String>();
				for (int i = 0; i < operatorText.size(); i++) {
					if (operatorText.get(i) == "+" || operatorText.get(i) == "-") {
						textTemp.add(numberText.remove(0));
						textTemp.add(operatorText.get(i));
					}
				}
				
				textTemp.add(numberText.remove(0));
				double result = Double.parseDouble(textTemp.get(0));
				for (int i = 0; i < textTemp.size(); i++) {
					if (textTemp.get(i) == "+") { //+연산
						result = result + Double.parseDouble(textTemp.get(i+1));
					} else if (textTemp.get(i) == "-") { //-연산
						result = result - Double.parseDouble(textTemp.get(i+1));
					}
				}
				
				//소수점 체크후 결과출력  + 결과에 다시 연산하기위한 초기화
				if (result - (long)result == 0) {
					inputText.setText(String.valueOf((long)result));
					inputTextList.clear();
					numberText.clear();
					operatorText.clear();
					output = null;
					String reResult = String.valueOf((long)result);
					String[] reesult = reResult.split("");
			        for (String wo : reesult ){
			        	inputTextList.add(wo);
			        }
					
				}else {
					inputText.setText(String.valueOf(result));
					inputTextList.clear();
					numberText.clear();
					operatorText.clear();
					output = null;
					String reResult = String.valueOf(result);
					String[] reesult = reResult.split("");
			        for (String wo : reesult ){
			        	inputTextList.add(wo);
			        }
				}
			}
		}
	}
	
	// 클리어 버튼 시 모든 입력값 초기화
	private class ClearButton implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
			inputText.setText("");
			inputTextList.clear();
			numberText.clear();
			operatorText.clear();
			output = null;
		}
	}

	public static void main(String[] args) {
		MyCalculator aa = new MyCalculator();
		aa.setSize(280, 360);
		aa.setResizable(false);
		aa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aa.setVisible(true);
	}
}
