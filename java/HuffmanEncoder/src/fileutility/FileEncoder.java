package fileutility;





import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.lang.String;
import java.nio.channels.*;
import java.io.File;

import binarytree.heap.*;
import binarytree.huffmantree.*;

import java.awt.FlowLayout;


import javax.swing.JFrame;







public class FileEncoder  extends JFrame implements Runnable{

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
	
	private HuffTree CodeTree;
	private long longFileSize;
	private long longSizeTemp;
	
	private ProcessBar bar;
	//static FileEncoder tmpb;
	
	//Constructors
	public FileEncoder()
	{
		// Properties
		super();
		FileInput = null;
		FileOutput = null;
		
		fic = null;
		foc = null;
				
		InputFile = null;
		
		inbuff = ByteBuffer.allocate(1024);
		outbuff = ByteBuffer.allocate(4096);
		longFileSize = 0L;
		
		// JFrame
		setSize(515, 70);
		setLocation(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Time Process");
		setLayout(new FlowLayout());
		bar = new ProcessBar();
		setVisible(false);
		
		
		
	}
	
	public FileEncoder(String strFilePath)
	{
		this();
		try
		{
			//Input
			InputFile = new File(strFilePath);
			FileInput = new FileInputStream(InputFile);
			fic = FileInput.getChannel();
			//Output
			FileOutput = new FileOutputStream(InputFile.getAbsolutePath()+".huff");
			foc = FileOutput.getChannel();
			
			longFileSize = InputFile.length();
			longSizeTemp = 0L;
			
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
		
	}
	
	
	
	/* Convertion functions */
	private int byteToInt(byte bt) {
		assert (int)bt + 128 >=0;
		
		int inttem = 0;
		inttem = (int)(bt & 0x7F);
		if((int)bt < 0)
			inttem = inttem + 128;
		return inttem;	
	}
	
//	private String byteToString(byte bt) {
//		return intToString(byteToInt(bt));
//	}
//	
//	private byte stringToByte(String str) {
//		return (byte)(new BigInteger(str,2).intValue());
//	}
//	
//	private int stringToInt(String str) {
//		return new BigInteger(str,2).intValue();
//	}
//
//	private long stringToLong(String str) {
//		return new BigInteger(str, 2).longValue();
//	}
	
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
	
	private void complementStr(StringBuffer original) {
		int tempint = original.length()%8;
		for(int i=8; i>tempint; i--)
			original.append("0");
	}
	
	// Destructor
	@Override
	protected void finalize()
	{
		try
		{
			fic.close();
			foc.close();
			FileInput.close();
			FileOutput.close();
		}
		catch(IOException e)
		{
			System.out.println(e.toString());
			return;
		}
	}
	
	public boolean fileEncode() {
		
		long filelength = 0L;
		HuffNode[] frequency = new HuffNode[256];
		for(int i=0; i<256; i++) {
			frequency[i] = new HuffNode(i, 0);
		}
		MinHeap CodeHeap;
		
		
		// Count the Frequency
		while(true)	{
			int tempint = 0;
			try {
				inbuff.clear();
				tempint = fic.read(inbuff);
				if(tempint <= 0)
					break;
				else
					filelength += tempint;
				
			}
			catch(IOException e) {
				break;
			}
			
			if(inbuff.hasArray()) {		// buff is not null
				byte[] ByteArray = inbuff.array();	//get the byte[]
				
				for(int i=0; i<tempint; i++) {
									
//					byte bytetem = ByteArray[i];
//					assert (int)bytetem + 128 >=0;
//					
//					int inttem = 0;
//					inttem = (int)(bytetem & 0x7F);
//					if((int)bytetem < 0)
//						inttem = inttem + 128;
						
					HuffNode temp = frequency[byteToInt(ByteArray[i])];
					temp.setFreq(temp.freq() +1 );
					
				}
			}
			else
				break;
		}

		try{
			fic.close();
			FileInput.close();
		}
		catch(IOException e) {
			System.out.println(e.toString());
		}
			
		
		// Every HuffNode is a HuffTree
		HuffTree[] TreeArray = new HuffTree[256];
		int num = 0;
		int j=0;
		for(int i=0; i<256; i++) {
			
			if(frequency[i].freq() != 0) {
				TreeArray[j++] = new HuffTree(frequency[i]);
				num ++;
			}
		}
		
		// Make the TreeArray into a MinHeap
		CodeHeap = new MinHeap(TreeArray, num, 256);
		
		// Build the HuffTree
		while(CodeHeap.heapSize() > 1) {
			CodeTree = new HuffTree(CodeHeap.removeMin(), CodeHeap.removeMin());
			CodeHeap.insert(CodeTree);
		}
			
		// Get the Huffman tree
		CodeTree = CodeHeap.removeMin();
		
		// Encode the Source code
		CodeTree.beginCode();
			
		// Output Strings
		StringBuffer outStr = new StringBuffer();
		StringBuffer tempCodeStr = new StringBuffer();
		
		/* Begin Output */
		outbuff.clear();
		
		/* Numbers of codes */
		outStr.append(intToString(num%256));
		
		// CodeKey and length of value
		for(int i=0; i<256; i++) {
			if(CodeTree.CodeMap.containsKey((byte)i)) {
				outStr.append( intToString(i) );
				
				
				// Get the Huffman code
				tempCodeStr.append( CodeTree.CodeMap.get((byte)i) );
				
				// Get the length of encoded code
				outStr.append( intToString(CodeTree.CodeMap.get((byte)i).length()) );
			}
		}
			
		/* Code value string */
		
		// Complement the encoded code string 
		if(tempCodeStr.length()%8 != 0)
			complementStr(tempCodeStr);
		
		// Add huffman code string
		outStr.append(tempCodeStr);
		
		// add length of source file
		int needbytes = 0;	// needed bytes to present the length
		tempCodeStr = new StringBuffer();	// store the length
		
		
		if(filelength == 0) {				// when file is 0B
			needbytes = 1;
		}
		
		// Format the file length
		while(filelength > 0) {
			int temp = (int)filelength%256;
			tempCodeStr.insert(0,intToString(temp));
			filelength = filelength/256;
			needbytes ++;
		}
		
		outStr.append(intToString(needbytes));
		outStr.append(tempCodeStr);
		// Output the needed information
		
		for(int i=0; i<outStr.length(); i=i+8) {
			byte outbyte = (byte)(new BigInteger(outStr.substring(i, i+8),2).intValue());
			outbuff.put(outbyte);
		}
		
		try{
			outbuff.flip();
			foc.write(outbuff);
		}
		catch(IOException e) {
			System.out.println(e.toString());
		}
		
		// Transfer the source file
		
		
		
		try {
			FileInput = new FileInputStream(InputFile);
			fic = FileInput.getChannel();
		}
		catch(IOException e) {
			System.out.println(e.toString());
		}
		
		filelength = longFileSize;
		
		String lastLeave = "";
		this.setVisible(true);
		int tempcount = 1;
		while(true)	{
//			
//			if(longSizeTemp/tempcount == longFileSize/10) {
//				bar.setCurrentValue(tempcount * 10);
//				add(bar);
//				tempcount ++ ;
//			}
			
			int readlength = 0;						
			try {
				inbuff.clear();
				readlength = fic.read(inbuff);
				if(readlength <= 0)
					break;
				else{
					filelength -= readlength;
					longSizeTemp += readlength;
				}
			}
			catch(IOException e) {
				break;
			}
			
			if(inbuff.hasArray()) {		// buff is not null
			
				byte[] ByteArray = inbuff.array();	//get the byte[]
				
				outStr = new StringBuffer();
				outStr.append(lastLeave);
				
				for(int i=0; i<readlength; i++) {				
					outStr.append( CodeTree.CodeMap.get(ByteArray[i]) );
				}
				outbuff.clear();
				
				if(readlength < 1024 || filelength <= 0) {
					complementStr(outStr);
				}
				
				int temp2 = outStr.length()%8;
				if(temp2 > 0) {
					lastLeave = outStr.substring(outStr.length()-temp2);
					outStr = outStr.delete(outStr.length() - temp2, outStr.length());
				}
				else {
					lastLeave = "";
				}
					
			
				for(int i=0; i<outStr.length(); i=i+8) {
					byte outbyte = (byte)(new BigInteger(outStr.substring(i, i+8),2)).byteValue();
					outbuff.put(outbyte);
				}
				
				
				
				try {
					outbuff.flip();
					foc.write(outbuff);
				}
				catch(IOException e) {
					System.out.println(e.toString());
				}
			}
			else
				break;
		
			double tem2 = ((double)longSizeTemp) / ((double)longFileSize) *100 % 10 - tempcount;
			
			
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
		
		fileEncode();
		
	}

	
	
	
	
}
			
		
		
			
			
			
			
			
			
			
			
