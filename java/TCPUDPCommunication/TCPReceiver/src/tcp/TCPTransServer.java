package tcp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPTransServer {
	
	private static final int port = 3333;
	
	private Socket socket;
	private ServerSocket serverSocket;
	private byte[] buffer;
	private InputStream input;
	private FileOutputStream fout;
	private File newFile;
	
	public TCPTransServer() {
		try {
			serverSocket = new ServerSocket(port);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public long getNewFile(String newfilepath) {
		
		try {
			
			// Initialize file output
			newFile = new File(newfilepath);
			fout = new FileOutputStream(newFile);
			buffer = new byte[1024];
			
			// Indicate the file path
			System.out.println("Server:NewFilePath:"+newfilepath);
			
			
			// Initialize socket input
			// Waiting connection
			System.out.println("Server:waiting for connection(port:"+port+")");
			socket = serverSocket.accept();
			System.out.println("Server:Connection Setup:"+socket.getInetAddress().toString());
			input = socket.getInputStream();				
			
			// Begin Transfer
			System.out.println("Server:Receiving File");
				while(input.read(buffer) != -1) {
					fout.write(buffer);
				}
			fout.close();
			serverSocket.close();
			
			long endTime = System.currentTimeMillis();
			System.out.println("Server:Finish Transfer at "+endTime+" ms, total size is "+newFile.length()+" B");
			return endTime;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	
	}

}
