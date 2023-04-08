package tcp;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class TCPTransClient {

	private int port;
	private Socket socket;
	private byte[] buffer;
	private FileInputStream fin;
	private File newFile;
	private String IP;
	private OutputStream output;
	
	public TCPTransClient() {
		IP="localhost";
		port = 3333;
	}
	
	public TCPTransClient(String IP, int port) {
		this.IP = IP;
		this.port = port;
	}
	
	public long sentNewFile(String newfilepath) {
		
		try {
			// Inform the file info
			newFile = new File(newfilepath);
			System.out.println("Client:FilePath:"+newfilepath);
			System.out.println("Client:FileSize:"+newFile.length()+" B");
			
			// Initialize file input
			fin = new FileInputStream(newFile);
			buffer = new byte[1024];
			
			// Initialize socket input
			socket = new Socket(IP, port);
			System.out.println("Client:Connection Setup:"+IP+":"+port);
			output = socket.getOutputStream();
					
			// Begin Transfer
			long beginTime = System.currentTimeMillis();
			System.out.println("Client:Sending File, begin at "+beginTime+" ms");
			
			while(fin.read(buffer) != -1) {
				output.write(buffer);
			}
			
			fin.close();
			socket.close();
			System.out.println("Client:Finished");
			return beginTime;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
