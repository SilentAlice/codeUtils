package fileutility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.lang.String;
import java.nio.channels.*;
import java.io.File;

public class FileCombiner {

	//Properties
	private FileInputStream FileInput;
	private FileOutputStream FileOutput;
	
	private ByteBuffer buff;
	
	private FileChannel fic;
	private FileChannel foc;
	private File OutputFile;
	
	//Constructors
	public FileCombiner()
	{
		super();
		FileInput = null;
		FileOutput = null;

		buff = ByteBuffer.allocate(1024);
		fic = null;
		foc = null;
		
		OutputFile = null;
		
	}
	
	public FileCombiner(String strFilePath)
	{
		
		this();
		try
		{
			OutputFile = new File(strFilePath);
			FileOutput = new FileOutputStream(OutputFile);
			foc = FileOutput.getChannel();
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
	@Override
	protected void finalize()
	{
		try
		{
			foc.close();
			FileOutput.close();
		}
		catch(IOException e)
		{
			return;
		}
	}
	
	public boolean fileCombiner()
	{
		
		File tempInputF;
		for(int i = 0; ; i++)
		{
			long TotalSize = 0L;
			
			String TempOPath = OutputFile.getAbsolutePath() + ".part" + i;
			try
			{
				FileInput = new FileInputStream(TempOPath);
				tempInputF = new File(TempOPath);
				fic = FileInput.getChannel();
			}
			catch (FileNotFoundException e1) 
			{
				break;
			}
			
			while(true)
			{
				try
				{
					buff.clear();
					TotalSize += fic.read(buff);
					buff.flip();
					foc.write(buff);
				}
				catch(IOException e)
				{
					break;
				}
				
				if(TotalSize >= tempInputF.length() )
					break;
				
			}
			try
			{
				FileInput.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				return false;
			}
		}
		try
		{
			foc.close();
			FileOutput.close();
		}
		catch(IOException e)
		{
			;
		}
		return true;
		
	}
}
