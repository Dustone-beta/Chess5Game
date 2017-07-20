import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


public class Player {
	private String name;
	private int color;
	private List<Point> chesslist;
	//private boolean[][] chessboard;
	private ChessBoard chessboard;
	private static Player blackplayer;
	private static Player whiteplayer;
	public int handmark;
	private TimeZone usedtime;
	//双例实现模块
	private Player(String name,int color){
		this.color=color;
		this.name=name;
		chessboard=ChessBoard.getBoard(ChessBoard.BOARD_SIZE);
		//chessboard=new boolean[ChessBoard.BOARD_SIZE][ChessBoard.BOARD_SIZE];
		chesslist=new ArrayList<>();
	}
	public static Player getPlayer(String name,int color){
		if(color==1){
			if(blackplayer==null){
				blackplayer=new Player(name,color);
			}
			return blackplayer;
		}else if(color==2){
			if(whiteplayer==null){
				whiteplayer=new Player(name,color);
			}
			return whiteplayer;
		}
		return null;
	}
	//下棋
	public void setChess(Point p){
		if(handmark==1){
			chessboard.setChess(p,color);
			chesslist.add(p);
			handmark=0;
		}
	}
	//悔棋
	public void regretChess(){
		if(this==blackplayer){
			if(handmark==1){
				whiteplayer.deleteChess();
				blackplayer.deleteChess();
			}else{
				blackplayer.deleteChess();
				handmark=1;
			}
		}else{
			if(handmark==1){
				blackplayer.deleteChess();
				whiteplayer.deleteChess();
			}else{
				whiteplayer.deleteChess();
				handmark=1;
			}
		}
	}
	//删棋（私有方法，用于处理悔棋）
	private void deleteChess(){
		Point dp=chesslist.remove(chesslist.size()-1);
		chessboard.delChess(dp, color);
	}
	//重新开始
	public void resetChess(){
		chessboard.clearChess();
		chesslist.clear();
	}
}
