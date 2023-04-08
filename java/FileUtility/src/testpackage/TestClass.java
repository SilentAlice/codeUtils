package testpackage;
import fileutility.*;
import java.lang.String;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class TestClass {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner Input = new Scanner(System.in);
		outer:while(true)
		{
			String strFilePath = null;
			File InputFile = null;
			long nFileNumber = 0;
			long lSingleFileSize  = 0;
			System.out.println("Please choose the mode S(split)/C(combine)");
			strFilePath = Input.next();
			if(strFilePath.equalsIgnoreCase("S"))
			{
				while(true)
				{
					
					System.out.println("Please input the file path");
					strFilePath = Input.next();
					//Check if the file is existed
					try
					{	
						new java.io.FileReader(strFilePath);
						
					}
					catch(FileNotFoundException e2)
					{
						System.out.println("File doesn't exist! Please try again.");
						continue;
					}
					//use the File to get the length of the file
					InputFile = new File(strFilePath);
					break;
				}
				
				//obtain the number of file parts
				System.out.println("Please input the number of file parts (0 to skip)");
				nFileNumber = Input.nextInt();
				
				if(nFileNumber <= 0L)
				{
					System.out.println("Please input the single file size");
					lSingleFileSize = Input.nextLong();
					
					if(lSingleFileSize <= 0L)
					{
						System.out.println("Invalidate Input !");
						continue;
					}
					else
					{
						if(InputFile.length() <= lSingleFileSize)
						{
							System.out.println("The file is smaller than the single file size!");
							continue;
						}
						
						//calculate the file number
						nFileNumber = InputFile.length() / lSingleFileSize;
						if(InputFile.length() % lSingleFileSize != 0)
							nFileNumber++;
					}
				}
				else
				{
					//calculate the single file size
					lSingleFileSize = InputFile.length() / nFileNumber;
					if(InputFile.length() % nFileNumber != 0)
						lSingleFileSize ++;
				}
				
				FileSplitter ob1 = new FileSplitter(strFilePath, (int)nFileNumber, lSingleFileSize);
				ob1.fileSplitter();
			}
			else
				if(strFilePath.equalsIgnoreCase("C"))
				{
					while(true)
					{
						System.out.println("Please input the file path(not include the '.part')");
						strFilePath = Input.next();
						
						//Check if the file is existed
						String TempStr = strFilePath + ".part0";
						try
						{	
							new java.io.FileReader(TempStr);
						}
						catch(FileNotFoundException e2)
						{
							System.out.println("File doesn't exist! Please try again.");
							continue;
						}
						
						break;
					}
					FileCombiner ob2 = new FileCombiner(strFilePath);
					if(ob2.fileCombiner())
						System.out.println("Files Combined successfully!");
				}
				else
					 if(strFilePath.equalsIgnoreCase("exit"))
						 break outer;
					 else
					 {
						 System.out.println("Wront mode chosen! Please check!");
						 continue;
					 }
		}
		
					
		Input.close();
	}

}
