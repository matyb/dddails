package com.sandwich.test;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public abstract class TestFrame extends JFrame{

	private static final long serialVersionUID = 7233470298457716752L;
	JPanel centerPanel;
	JPanel southPanel;
	JPanel buttonPanel;
	JPanel labelPanel;
	JLabel sizeFrameLabel;
	JLabel sizeRamLabel;
	String bytes 				= " bytes";
	
	
	public TestFrame(){
		JFrame frame = new JFrame();
		JPanel borderPanel = new JPanel(new BorderLayout());
		frame.getContentPane().add(borderPanel);
		borderPanel.add(centerPanel = getPanel(), BorderLayout.CENTER);
		borderPanel.add(getSouthPanel(), BorderLayout.SOUTH);
		setTitle(getClass()+" Test");
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private Component getSouthPanel() {
		if (southPanel == null) {
			southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			southPanel.add(getButtonPanel());
			southPanel.add(Box.createHorizontalStrut(120));
			southPanel.add(getLabelPanel());
			southPanel.setPreferredSize(new Dimension(950, southPanel
					.getPreferredSize().height));
		}
		return southPanel;
	}

	private Component getLabelPanel() {		
		if (labelPanel == null) {
			JPanel leftPanel = new JPanel();
			leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
			leftPanel.add(new JLabel("size of entire frame(and contents):"));
			leftPanel.add(new JLabel("approx RAM usage:"));
			JPanel rightPanel = new JPanel();
			rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
			rightPanel.add(sizeFrameLabel = new JLabel());
			rightPanel.add(sizeRamLabel = new JLabel());
			labelPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			labelPanel.add(leftPanel);
			labelPanel.add(rightPanel);
		}
		return labelPanel;
	}

	private Component getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			AbstractButton closeButton = new JButton("Close");
			closeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			buttonPanel.add(closeButton);
			AbstractButton sizeMeButton = new JButton("Size me");
			buttonPanel.add(sizeMeButton);
			sizeMeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					showSize();
				}

				private void showSize() {
					sizeFrameLabel.setText(TestUtils
							.sizeInBytes(TestFrame.this) + bytes);
					Runtime runtime = Runtime.getRuntime();
					sizeRamLabel.setText(runtime.totalMemory() - runtime.freeMemory()+ bytes);
				}
			});
		}
		return buttonPanel;
	}

	abstract JPanel getPanel();
	
}
