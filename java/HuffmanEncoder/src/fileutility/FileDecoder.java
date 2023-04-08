package fileutility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.lang.String;
import java.nio.channels.*;
import java.util.HashMap;
import java.io.File;




import java.awt.FlowLayout;


import javax.swing.JFrame;

public class FileDecoder extends JFrame implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Properties
	private FileInputStream FileInput;
	private FileOutputStream FileOutput;
	
	private FileChannel fic;
	private FileChannel foc;
	
	private File InputFile;
	
	private ByteBuffer inbuff;
	private ByteBuffer outbuff;
	
	private HashMap<String, Byte> CodeMap;
	private int CodeNum;
	private long filelength;
	private long longFileSize;
	
	private ProcessBar bar;
	
	//Constructors
	public FileDecoder()
	{
		super();
		FileInput = null;
		FileOutput = null;
		
		inbuff = ByteBuffer.allocate(1024);
		outbuff = ByteBuffer.allocate(4096);
		fic = null;
		foc = null;
		
		InputFile = null;
		
		CodeMap = null;
		
		CodeNum = -1;
		filelength = -1;
		longFileSize = -1;
		
		// JFrame
		setSize(515, 70);
		setLocation(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Time Process");
		setLayout(new FlowLayout());
		bar = new ProcessBar();
		setVisible(false);
		
	}
	
	public FileDecoder(String strFilePath)
	{
		this();
		try
		{
			InputFile = new File(strFilePath);
			FileInput = new FileInputStream(InputFile);
			fic = FileInput.getChannel();
			
			FileOutput = new FileOutputStream(strFilePath.substring(0, strFilePath.length()-5));
			foc = FileOutput.getChannel();
			
			longFileSize = InputFile.length();
		}
		catch(FileNotFoundException e1)
		{
			return;
		}
		catch(NullPointerException e2)
		{
			System.out.println(strFilePath + "is not found, Please check!");
			return;
		}
		
		CodeMap = new HashMap<String, Byte>();
		CodeNum = 0;
		filelength = 0L;
		
	}
	
	/* Destructor */
	protected void finalize()
	{
		try
		{
			fic.close();
			FileInput.close();
			foc.close();
			FileOutput.close();
		}
		catch(IOException e)
		{
			System.out.println("Not found file to close");
			return;
		}
	}
	
	/* Convertion functions */
	private String intToString(int inte) {
		StringBuffer c = new StringBuffer(Integer.toBinaryString(inte));
		if(c.length() >= 8)
			return c.toString();
		char[] temp = new char[8-c.length()];
		for(int i=0; i<temp.length; i++) {
			temp[i] = '0';
		}
		c.insert(0, temp);
		return c.toString();
	}
		
	private int byteToInt(byte bt) {
		assert (int)bt + 128 >=0;
		
		int inttem = 0;
		inttem = (int)(bt & 0x7F);
		if((int)bt < 0)
			inttem = inttem + 128;
		return inttem;	
	}
	
	private String byteToString(byte bt) {
		return intToString(byteToInt(bt));
	}
	
//	private byte stringToByte(String str) {
//		return (byte)(new BigInteger(str,2).intValue());
//	}
	
//	private int stringToInt(String str) {
//		return new BigInteger(str,2).intValue();
//	}

	private long stringToLong(String str) {
		return new BigInteger(str, 2).longValue();
	}
	
//	private void complementStr(StringBuffer original) {
//		int tempint = original.length()%8;
//		for(int i=8; i>tempint; i--)
//			original.append("0");
//	}
	
	private int init(byte[] byteArray) {
		CodeNum = byteToInt(byteArray[0]);
		if(CodeNum == 0)
			CodeNum = 256;
		
		int pos = 2*CodeNum + 1;
		int HuffLength = 0;
		
		// Get the length of huffcode;
		for(int i=1; i<CodeNum*2; i+=2) {
			HuffLength += byteToInt(byteArray[i+1]);
		}
		
		// Get the needed counts to achieve the huffcode
		if(HuffLength % 8 > 0) {
			HuffLength = HuffLength / 8 + 1;
		}
		else
			HuffLength = HuffLength / 8;
		
		// To store the huffcode
		StringBuffer strTemp = new StringBuffer();
		for(int i=0; i<HuffLength; i++) {
			strTemp.append(byteToString(byteArray[pos + i]));
		}				

		// Finish the CodeMap
		pos = 0;
		int tempInt = 0;
		for(int i=1; i<CodeNum*2; i+=2) {
			
			tempInt = byteToInt(byteArray[i+1]);
			
			CodeMap.put(strTemp.substring(pos, pos + tempInt),byteArray[i]);
			pos = pos + tempInt;
		}
		
		// Set the pos at the needed bytes to present the file length
		pos = 2*CodeNum + 1 + HuffLength;
		
		tempInt = byteToInt(byteArray[pos]);
		pos ++;

		// Get the file length
		strTemp = new StringBuffer();
		for(int i=0; i<tempInt; i++) {
			strTemp.append(byteToString(byteArray[pos]));
			pos++;
		}
		
		// Set the filelength
		filelength = stringToLong(strTemp.toString());
		
		return pos; 
	}
	
	public boolean filedecoder() {
		
		
		StringBuffer tempOutStr;
				
		String lastLeave = "";
		
		long fileleft = longFileSize;
		int tempcount = 1;
		setVisible(true);
		for(int count=0; ;count++)	{
			
			int readlength = 0;
			int pos = 0;
			
			try {
				inbuff.clear();
				readlength = fic.read(inbuff);
				if(readlength <= 0)
					break;
				else
					fileleft -= readlength;
			}
			catch(IOException e) {
				break;
			}
			
			if(inbuff.hasArray()) {		// buff is not null
			
				byte[] ByteArray = inbuff.array();	//get the byte[]
				if(count == 0) {
					pos = init(ByteArray);
				}
				
				tempOutStr = new StringBuffer();
				tempOutStr.append(lastLeave);
				
				for(int i=pos; i<readlength; i++) {
					tempOutStr.append(byteToString(ByteArray[i]));
				}
				
				int pos1 = 0;
				int pos2 = 2;
				
				outbuff.clear();
				String strTemp;
				byte bOut;
				if(readlength < 1024) {
					do {
						strTemp = tempOutStr.substring(pos1, pos2);
						if(CodeMap.containsKey(strTemp)) {
							bOut = CodeMap.get(strTemp);
							outbuff.put(bOut);
							pos1 = pos2;
							pos2 += 2;
							filelength --;
							if(filelength <= 0)
								break;
						}
						else {
							pos2 ++;
						}
					}while(pos2 <= tempOutStr.length());
				}
				else {
					do {
						strTemp = tempOutStr.substring(pos1, pos2);
						if(CodeMap.containsKey(strTemp)) {
							bOut = CodeMap.get(strTemp);
							outbuff.put(bOut);
							pos1 = pos2;
							pos2 += 2;
							filelength --;
						}
						else {
							pos2 ++;
						}
					}while(pos2 <= tempOutStr.length());
					
					lastLeave = tempOutStr.substring(pos1);
				}
				
				try{
					outbuff.flip();
					foc.write(outbuff);
				}
				catch(IOException e) {
					System.out.println(e.toString());
				}
				
			}
			else
				break;
			double tem2 = (double)(longFileSize - fileleft) / ((double)longFileSize) *100 % 10 - tempcount;
			
			
			if(tem2 >0 && tem2 < 1 ) {
				bar.setCurrentValue(tempcount*10);
				add(bar);
				tempcount++;
			}
			
			
		}
		return true;
	}

	@Override
	public void run() {
		filedecoder();
		
	}
}
