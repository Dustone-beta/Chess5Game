import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JFrame;

public class GameFrame {
	private JFrame frame;
	public GameFrame(){
		
		frame = new JFrame("五子棋（服务器执白）");
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
	/*Point point1 =new Point(14,14);
	Point point2 =new Point(13,13);
	Point point3 =new Point(12,12);
	Point point4 =new Point(11,11);
	Point point5 =new Point(1,1);
	Point point6 =new Point(2,2);
	Point point7 =new Point(3,3);

	Player p1=Player.getPlayer("chenyan", 1);
	p1.handmark=1;
	p1.setChess(point1);
	p1.handmark=1;
	p1.setChess(point2);
	p1.handmark=1;
	p1.setChess(point3);
	p1.handmark=1;
	p1.setChess(point4);

	Player p2=Player.getPlayer("chenyan", 2);
	p2.handmark=1;
	p2.setChess(point5);
	p2.handmark=1;
	p2.setChess(point6);
	p2.handmark=1;
	p2.setChess(point7);
	p2.handmark=1;
	p2.regretChess();
*/
}

