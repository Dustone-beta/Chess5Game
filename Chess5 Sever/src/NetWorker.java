import java.awt.Point;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class NetWorker extends Thread {
	private static NetWorker networker;
	private OutputStream output;
	private InputStream input;
	private Controller controller;
	private int mode;//1为客户端，2为服务器
	private String ipAddress;//另一位玩家的IP地址
	
	public void run(){
		Point p = null;
		int cs=0;
		do {
			p = readPoint();
			if(p.x<50){
				controller.thatChess(p);
			}else{
				controller.answerCode(p.x);
			}
		}while(p != null);//游戏结束控制代码
	}
	public static NetWorker getNetWorker(Controller controller,int mode,String ipAddress){
		if(networker==null){
			networker=new NetWorker(controller,mode,ipAddress);
		}
		return networker;
	}
	private NetWorker(Controller controller,int mode,String ipAddress){
		try{
			this.controller=controller;
			this.mode=mode;
			this.ipAddress=ipAddress;
			//mode1客户机模式 mode2服务器模式
			if(mode==1){
				int port = 8848;
				Socket socket = new Socket(ipAddress, port);
				output = socket.getOutputStream();	
				input = socket.getInputStream();
			}else{
				int port = 8848;			
				ServerSocket serverSocket = new ServerSocket(port);
				InetAddress address = serverSocket.getInetAddress();
				InetAddress serverName = address.getLocalHost();
				System.out.printf("我在 %s:%d 等你...\r\n", serverName, port);
				Socket socket = serverSocket.accept();
				output = socket.getOutputStream();	
				input = socket.getInputStream();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public Point readPoint(){
		try{
			int x = input.read();
			int y = input.read();
			return new Point(x, y);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public void writePoint(int x, int y){
		try{
			output.write(x);
			output.write(y);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/*
	public int readCode(){
		try{
			int cs = input.read();
			return cs;
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	public void writeCode(int cs){
		try{
			output.write(cs);
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
}
