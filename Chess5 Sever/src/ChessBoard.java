import java.awt.Point;


public class ChessBoard {
	//创建单例棋盘类
	private static ChessBoard chessboard;
	public static int BOARD_SIZE;
	//保存棋盘内容的数组
	private int[][] board;
	
	//单例实现模块
	private ChessBoard(int BOARD_SIZE){
		this.BOARD_SIZE=BOARD_SIZE;
		board=new int[BOARD_SIZE][BOARD_SIZE];
	}
	public static ChessBoard getBoard(int BOARD_SIZE){
		if(chessboard==null){
			chessboard=new ChessBoard(BOARD_SIZE);
		}
		return chessboard;
	}
	
	//棋盘保护模块
	private boolean isLegal(Point p,int color){
		if(!(p.x<BOARD_SIZE&&p.x>=0&&p.y<BOARD_SIZE&&p.y>=0)){
			return false;
		}
		if(color!=1&&color!=2&&color!=0){
			return false;
		}
		return true;
	}
	//增加棋子模块
	public boolean setChess(Point p,int color){
		try{
			//棋盘入口
			if(isLegal(p,color)&&board[p.x][p.y]==0){
				board[p.x][p.y]=color;
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	//减少棋子模块
	public boolean delChess(Point p,int color){
		try{
			//棋盘入口
			if(isLegal(p,color)&&board[p.x][p.y]==color){
				board[p.x][p.y]=0;
				return true;
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	//棋盘重置
	public void clearChess(){
		for(int i=0;i<BOARD_SIZE;i++){
			for(int j=0;j<BOARD_SIZE;j++){
				board[i][j]=0;
			}
		}
	}
	//查询指定位置是否为空
		public boolean isEmpty(int x,int y){
			if(board[x][y]==0){
				return true;
			}
			return false;
		}
	//获得棋盘
	public int[][] getBoard(){
		return board;
	}
}
