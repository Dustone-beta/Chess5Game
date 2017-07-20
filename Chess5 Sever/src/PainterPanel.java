import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.format.FormatStyle;

import javax.swing.JPanel;


public class PainterPanel extends JPanel{
	private static final Color BLACK=new Color(0,0,0);
	private static final Color WHITE=new Color(255,255,255);
	private int k = 40; //方格宽（高）度	
	private int r = 15; //棋子（圆）半径
	private int x0=30, y0=30; //棋盘左上角的位置	
	private int N;
	private Point theOtherChoose;		//对手下棋标记
	private String cMessage;				//游戏控制器消息
	private Font messageFont=new Font("微软雅黑",Font.PLAIN,33 );			//消息字体设置
	
	private MouseListener mouseListener;
	public Point pointchoose;
	private ChessBoard chessboard;
	private Controller controller;
	private static PainterPanel painterpanel;
	private PainterPanel(Controller controller,int boardsize){
		this.controller=controller;
		cMessage=new String("empty");
		pointchoose=new Point();
		theOtherChoose=new Point(500,500);
		chessboard=ChessBoard.getBoard(boardsize);
		N=ChessBoard.BOARD_SIZE-1;
		setMouseListener();
		this.addMouseListener(mouseListener);
		setPreferredSize( new Dimension(700, 700) );
	}
	public static PainterPanel getPainter(Controller controller,int boardsize){
		if(painterpanel==null){
			painterpanel=new PainterPanel(controller,boardsize);
		}
		return painterpanel;
	}
	public void paintComponent(Graphics g){
		g.setColor(WHITE);
		g.fillRect(0, 0, 700, 700);
		g.setColor(BLACK);
		int[][] board=chessboard.getBoard();
		for(int i=0; i<=N; i++) {
			g.drawLine(x0, y0+i*k, x0+N*k, y0+i*k);	
		}
		for(int i=0; i<=N; i++) {
			g.drawLine(x0+i*k, y0, x0+i*k, y0+N*k);
		}
		
		for(int i=0; i<=N; i++){
			for(int j=0; j<=N; j++){
				drawChess(i, j,board[i][j], g);
			}
		}
		if(!cMessage.equals("empty")){
			g.setFont(messageFont);
			g.setColor(BLACK);
			g.drawString(cMessage,320, 320);
		}
		drawTip(theOtherChoose.x,theOtherChoose.y,g);
	}
	/** 
	*  在指定棋盘坐标绘制指定颜色的棋子（圆）
	 * @param x 棋盘横坐标
	 * @param y 棋盘纵坐标
	 * @param color 棋子颜色，1为黑子，2为白子
	 * @param g 图形设备上下文
	 */
	private void drawChess(int x, int y, int color, Graphics g){
		if(color==1)
			g.fillOval(x0+x*k-r, y0+y*k-r, 2*r, 2*r);
		else if(color==2){
			g.fillOval(x0+x*k-r, y0+y*k-r, 2*r, 2*r);
			g.setColor(WHITE);
			r=r-3;
			g.fillOval(x0+x*k-r, y0+y*k-r, 2*r, 2*r);
			r+=3;
			g.setColor(BLACK);
		}
	}
	private void drawTip(int x, int y, Graphics g){
		g.setColor(new Color(255,0,0));
		int r=4;
		g.fillOval(x0+x*k-r, y0+y*k-r, 2*r, 2*r);
		g.setColor(BLACK);
	}
	public void repaintBoard(){
		repaint();
	}
	//最后一步标记的设置和获取类
	public void tipSetter(Point p){
		theOtherChoose=p;
	}
	public ChessBoard getBoard(){
		return chessboard;
	}
	
	private void setMouseListener(){
		mouseListener=new MouseAdapter(){
						//松开鼠标
						public void mouseReleased(MouseEvent e) {
						}
						// 按下鼠标
						public void mousePressed(MouseEvent e) {
						}
						// 点击鼠标
						public void mouseClicked(MouseEvent e) {
							int x = Math.round( (e.getX()-x0)/(k+0.0f) );
							int y = Math.round( (e.getY()-y0)/(k+0.0f) );
							if(chessboard.isEmpty(x, y)){
								pointchoose=new Point(x,y);
								controller.thisChess();
								repaint();
							}
						}
		};
	}
	public void printString(String string) {
		cMessage=string;
	}
}
	
