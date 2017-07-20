import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GameFrame {
	private JFrame frame;
	public GameFrame(){
		frame = new JFrame("五子棋（客户端执黑）");
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(frame);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Controller controller=Controller.getController();
		controller.setFrame(frame);
		ControllerMenu controllermenu=new ControllerMenu(controller);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setJMenuBar(controllermenu);
		frame.getContentPane().add(controller.getPainter());
		frame.pack();
		frame.setVisible(true);
	}
}

