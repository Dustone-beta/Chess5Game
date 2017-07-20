import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class ControllerMenu extends JMenuBar{
	JMenu control=new JMenu("游戏控制");
	JMenu select=new JMenu("设置");
		JMenu color=new JMenu("颜色");
	JMenuItem regretItem=new JMenuItem("悔棋");
	JMenuItem retryItem=new JMenuItem("重新开始");
	JMenuItem loseItem=new JMenuItem("认输");
	private Controller controller;
	public ControllerMenu(Controller controller){
		this.controller=controller;
		init(controller);
	}
	public void init(Controller controller){
		control.add(regretItem);
		control.add(retryItem);
		control.add(loseItem);
		select.add(color);
		this.add(control);
		this.add(select);
		MyEvent();
	}
	private void MyEvent(){
		regretItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				controller.regretChess();
			}
		});
		retryItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				controller.restart();
			}
		});
	}
}
