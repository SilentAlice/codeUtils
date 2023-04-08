package fileutility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.lang.String;
import java.nio.channels.*;
import java.io.File;

public class FileSplitter
{
	//Properties
	private FileInputStream FileInput;
	private FileOutputStream FileOutput;
	private int nFileNumber;
	private long lSingleFileSize;
	private ByteBuffer buff;
	
	private FileChannel fic;
	private FileChannel foc;
	private File InputFile;
	
	//Constructors
	public FileSplitter()
	{
		super();
		FileInput = null;
		FileOutput = null;
		nFileNumber = 0;
		lSingleFileSize = 0L;
		buff = ByteBuffer.allocate(1024);
		fic = null;
		foc = null;
		
		InputFile = null;
	}
	
	public FileSplitter(String strFilePath, int nFileNumber, long lSingleFileSize)
	{
		this();
		try
		{
			InputFile = new File(strFilePath);
			FileInput = new FileInputStream(InputFile);
			fic = FileInput.getChannel();
			
			this.nFileNumber = nFileNumber;
			this.lSingleFileSize = lSingleFileSize;
			
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
	
	
	
	protected void finalize()
	{
		try
		{
			FileInput.close();
		}
		catch(IOException e)
		{
			return;
		}
	}
	
	public boolean fileSplitter()
	{
		
		
		for(int i = 0; i < nFileNumber; i++)
		{
			int TotalSize = 0;
			String TempOPath = InputFile.getAbsolutePath() + ".part" + i;
			try
			{
				FileOutput = new FileOutputStream(TempOPath);
				foc = FileOutput.getChannel();
			}
			catch (FileNotFoundException e1) 
			{
				
				e1.printStackTrace();
				return false;
			}
			
			while(true)
			{
				int temp;
				try
				{
					buff.clear();
					temp = fic.read(buff);
					TotalSize = TotalSize + temp;
					buff.flip();
					foc.write(buff);
				}
				catch(IOException e)
				{
					break;
				}
				if(temp <= 0)
					break;
				
				if(TotalSize >= lSingleFileSize)
					break;
				
				
				
			}
			try
			{
				FileOutput.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				return false;
			}
		}
		try{
			fic.close();
			FileInput.close();
		}
		catch(IOException e2)
		{
			;
		}
		return true;
	}
}
