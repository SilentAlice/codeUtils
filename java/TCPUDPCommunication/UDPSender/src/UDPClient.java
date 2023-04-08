import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPClient {

	private final static int port = 3334;
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		FileInputStream input = null;
		File newFile = null;
		byte[] buffer = new byte[1024];
		try {
			InetAddress IP = InetAddress.getByName("localhost");
			newFile = new File("V:/MP4.mp4");
			input = new FileInputStream(newFile);
			socket = new DatagramSocket();
			System.out.println("Client:File Name is o.m4a, size is "+newFile.length() + " B");
			
			
			
			int num = (int)(newFile.length() / 1024);
			if(newFile.length() % 1024 != 0)
				num ++;
		
			byte[] numByte = (num + "").getBytes();
			
			long beginTime = System.currentTimeMillis();
			System.out.println("Client:Begin send at "+beginTime);
			socket.send(new DatagramPacket(numByte,numByte.length,IP,port));
			for(int i=0; i<num; i++) {
				input.read(buffer);
				socket.send(new DatagramPacket(buffer,buffer.length,IP,port));
			}
		
			socket.close();
			input.close();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
