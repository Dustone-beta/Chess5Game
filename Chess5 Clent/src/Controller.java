import java.awt.Point;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

//游戏控制器
public class Controller {
	private PainterPanel painterpanel;
	private Player blackplayer;
	private Player whiteplayer;
	private NetWorker networker;
	private ApplyDialog applydialog;
	public static int roleplay;
	private final int WIN_COUNT=5;
	private int boardsize;
	private String ipAddress;
	private JFrame frame;
	//单例
	private static Controller controller;
	private Controller(){
		roleplay=1;//代表先手黑子
		boardsize=15;
		setAddress("192.168.3.44");//默认IP地址
		painterpanel=PainterPanel.getPainter(this,boardsize);
		blackplayer=Player.getPlayer("chenyan", 1);
		whiteplayer=Player.getPlayer("chenwei", 2);
		blackplayer.handmark=1;
		whiteplayer.handmark=0;
		networker=NetWorker.getNetWorker(this,roleplay,ipAddress);
		networker.start();
	}
	public static Controller getController(){
		if(controller==null){
			controller=new Controller();
		}
		return controller;
	}
	public void setFrame(JFrame frame){
		this.frame=frame;
	}
	//获取棋盘
	public JPanel getPainter(){
		return painterpanel;
	}
	
	//设置服务器端IP地址
	public void setAddress(String ipAddress){
			this.ipAddress=ipAddress;
	}
	//选手下棋动作（另一方）
	public void thatChess(Point p){
		if(roleplay==1){
			whiteplayer.setChess(p);
			showWinner(p,2);
			whiteplayer.handmark=0;
			blackplayer.handmark=1;
			painterpanel.tipSetter(p);
			painterpanel.repaintBoard();
		}else{
			blackplayer.setChess(p);
			showWinner(p,1);
			blackplayer.handmark=0;
			whiteplayer.handmark=1;
			painterpanel.tipSetter(p);
			painterpanel.repaintBoard();
		}
	}
	//选手下棋动作（自己）
	public void thisChess(){
		if(	roleplay==1){
			while(blackplayer.handmark==1){
				blackplayer.setChess(painterpanel.pointchoose);
				blackplayer.handmark=0;
				whiteplayer.handmark=1;
				networker.writePoint(painterpanel.pointchoose.x, painterpanel.pointchoose.y);
				showWinner(painterpanel.pointchoose,1);
			}
		}else{
			while(whiteplayer.handmark==1){
				whiteplayer.setChess(painterpanel.pointchoose);
				whiteplayer.handmark=0;
				blackplayer.handmark=1;
				networker.writePoint(painterpanel.pointchoose.x, painterpanel.pointchoose.y);
				showWinner(painterpanel.pointchoose,2);
			}
		}
	}
	//局势发布器
	public void showWinner(Point p,int ico){
		if(isWon(p,ico)){
			if(ico==1){
				painterpanel.printString("黑方获胜！");
			}else{
				painterpanel.printString("白方获胜！");
			}
			blackplayer.handmark=0;
			whiteplayer.handmark=0;
			painterpanel.repaint();
		}
	}
	
	//局势判断模块
	public boolean isWon(Point p,int ico){
		int posX=p.x;
		int posY=p.y;
		// 直线起点的X坐标
		int startX = 0;
		// 直线起点Y坐标
		int startY = 0;
		// 直线结束X坐标
		int endX =boardsize-1;
		// 直线结束Y坐标
		int endY = endX;
		// 同条直线上相邻棋子累积数
		int sameCount = 0;
		int temp = 0;
		// 计算起点的最小X坐标与Y坐标
		temp = posX - WIN_COUNT+1;
		startX = temp < 0 ? 0 : temp;
		temp = posY - WIN_COUNT+1;
		startY = temp < 0 ? 0 : temp;
		// 计算终点的最大X坐标与Y坐标
		temp = posX + WIN_COUNT-1;
		endX = temp > boardsize-1 ? boardsize - 1
						: temp;
		temp = posY + WIN_COUNT - 1;
		endY = temp > boardsize-1 ? boardsize - 1
						: temp;
				// 从左到右方向计算相同相邻棋子的数目
		int[][] board = (painterpanel.getBoard()).getBoard();
		for (int i = startY; i < endY; i++) {
			if (board[posX][i] == ico && board[posX][i + 1] == ico) {
				sameCount++;
			} else if (sameCount != WIN_COUNT - 1) {
				sameCount = 0;
			}
		}
		if (sameCount == 0) {
			// 从上到下计算相同相邻棋子的数目
			for (int i = startX; i < endX; i++) {
				if (board[i][posY] == ico && board[i + 1][posY] == ico) {
					sameCount++;
				} else if (sameCount != WIN_COUNT - 1) {
					sameCount = 0;
				}
			}
		}
		if (sameCount == 0) {
			// 从左上到右下计算相同相邻棋子的数目
			int j = startY;
			for (int i = startX; i < endX; i++) {
				if (j < endY) {
					if (board[i][j] == ico && board[i + 1][j + 1] == ico) {
						sameCount++;
					} else if (sameCount != WIN_COUNT - 1) {
						sameCount = 0;
					}
					j++;
				}
			}
		}
		return sameCount == WIN_COUNT - 1 ? true : false;
	}
	
	
	//菜单动作控制器
	public void answerCode(int cs){
		if(cs!=0){//如果游戏没有正常进行 代码1
			switch(cs){
			//重开动作
			case 99:
				whiteplayer.resetChess();
				blackplayer.resetChess();
				whiteplayer.handmark=0;
				blackplayer.handmark=1;
				painterpanel.printString("empty");
				painterpanel.repaintBoard();
				break;
			case 111:
			{
				applydialog=ApplyDialog.applydialog("对手（黑色方）申请悔棋",frame);
				applydialog.setON();
				if(applydialog.result==1){
					blackplayer.regretChess();
					networker.writePoint(112,0);
				}
				painterpanel.repaintBoard();
				//调用对话框并获得返回值
			}break;
			case 121:
			{
				applydialog=ApplyDialog.applydialog("对手（白色方）申请悔棋",frame);
				applydialog.setON();
				if(applydialog.result==1){
					whiteplayer.regretChess();
					networker.writePoint(122,0);
				}
				painterpanel.repaintBoard();
			}break;
			case 112:
			{
				blackplayer.regretChess();
				painterpanel.repaintBoard();
			}break;
			case 122:
			{
				whiteplayer.regretChess();
				painterpanel.repaintBoard();
			}break;
			default:
			}
		}
	}
	//悔棋动作
	public void regretChess(){
		if(roleplay==1){
			networker.writePoint(111,0);
		}else{
			networker.writePoint(121,0);
		}
	}
	//重新开局动作
	public void restart(){
		whiteplayer.resetChess();
		blackplayer.resetChess();
		whiteplayer.handmark=0;
		blackplayer.handmark=1;
		painterpanel.printString("empty");
		painterpanel.repaintBoard();
		networker.writePoint(99, 0);
	}
}
