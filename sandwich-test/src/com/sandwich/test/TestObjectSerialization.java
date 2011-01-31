package com.sandwich.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sandwich.testclasses.LiteVO;

public class TestObjectSerialization {

	@SuppressWarnings("serial")
	public static void main(String[] args) {
		new TestFrame(){
			@Override
			JPanel getPanel() {
				JPanel jPanel = new JPanel();
				final JList list = new JList();
				jPanel.add(new JScrollPane(list));
				JPanel vPanel = new JPanel();
				vPanel.setLayout(new BoxLayout(vPanel,BoxLayout.Y_AXIS));
				JButton add25MoreButton = new JButton("Add 25 more");
				JButton add5000MoreButton = new JButton("Add 5000 more");
				vPanel.add(add25MoreButton);
				vPanel.add(add5000MoreButton);
				final String rowCountText = "Rows: "; 
				final JLabel recordCountLabel = new JLabel(rowCountText+0);
				final JLabel timeTakenLabel = new JLabel();
				vPanel.add(recordCountLabel);
				vPanel.add(timeTakenLabel);
				add25MoreButton.addActionListener(new AddMoreActionListener(
						25,
						list,
						recordCountLabel,
						timeTakenLabel,
						rowCountText));
				add5000MoreButton.addActionListener(new AddMoreActionListener(
						5000,
						list,
						recordCountLabel,
						timeTakenLabel,
						rowCountText));
				
				
				jPanel.add(vPanel);
				return jPanel;
			}
		};
	}
	
	static class AddMoreActionListener implements ActionListener {
		
		int numberToAdd;
		JList list;
		JLabel recordCountLabel;
		JLabel timeTakenLabel;
		String rowCountText;
		
		public AddMoreActionListener(	int numberToAdd, 
										JList list, 
										JLabel recordCountLabel,
										JLabel timeTakenLabel,
										String rowCountText){
			this.numberToAdd = numberToAdd;
			this.list = list;
			this.recordCountLabel = recordCountLabel;
			this.timeTakenLabel = timeTakenLabel;
			this.rowCountText = rowCountText;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			long time = TestUtils.time(new Runnable(){
				@Override
				public void run() {
					Vector<LiteVO> liteTos = new Vector<LiteVO>();
					for (int i = 0; i < list.getModel().getSize(); i++) {
						liteTos.add((LiteVO) list.getModel().getElementAt(i));
					}
					for (int i = 0; i < numberToAdd; i++) {
						liteTos.add(new LiteVO("", TestUtils.getRandomText(50)));
					}
					list.setListData(liteTos);
					recordCountLabel.setText(rowCountText + liteTos.size());
				}
			});
			timeTakenLabel.setText("Took "+time+"ms to redraw list");
		}
	}
}
