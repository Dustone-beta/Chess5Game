import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;


public class ApplyDialog extends JDialog{
	private JDialog apply;
	private JButton yesButton;
	private JButton noButton;
	private static ApplyDialog applydialog;
	public int result;
	public static ApplyDialog applydialog(String message,JFrame frame){
		if(applydialog==null){
			applydialog=new ApplyDialog(message,frame);
		}
		return applydialog;
	}
	private ApplyDialog(String message,JFrame frame){
		init(message,frame);
	}
	private void init(String message,JFrame frame){
		yesButton=new JButton("同意");
		noButton=new JButton("拒绝");
		apply=new JDialog(frame,message,true);
		apply.setTitle(message);
		apply.setLayout(new BorderLayout());
		apply.add("North",yesButton);
		apply.add("South",noButton);
		apply.setBounds(30, 30,108, 192);
		myEvent();
		apply.setVisible(false);
	}
	public void setON(){
		apply.setVisible(true);
	}
	public void setOFF(){
		apply.setVisible(false);
	}
	private void myEvent(){
		yesButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				result=1;
				apply.setVisible(false);
			}
		});
		noButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				result=2;
				apply.setVisible(false);
			}
		});
	}
}