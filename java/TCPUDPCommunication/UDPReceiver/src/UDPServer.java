import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {

	private final static int port = 3334;
	public static void main(String[] args) {
		DatagramSocket socket = null;
		FileOutputStream output = null;
		File newFile = null;
		byte[] buffer = new byte[1024];
		try {
			newFile = new File("V:/getUDPFile.mp4");
			output = new FileOutputStream(newFile);
			socket = new DatagramSocket(port);
			DatagramPacket data = new DatagramPacket(buffer, buffer.length);
			System.out.println("Server:New File Name:getUDPFile.m4a");
			System.out.println("Server:Begin Receive");
			socket.receive(data);
			byte[] info = data.getData();
			String tem = new String(info).trim();
			int num = new Integer(tem).intValue();
			
			for(int i=0; i<num; i++) {
				socket.receive(data);
				output.write(data.getData());
			}
			
			long endTime = System.currentTimeMillis();
			System.out.println("Server:Finished, time: "+endTime);
			
			socket.close();
			output.close();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
