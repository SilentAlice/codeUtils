package test;

import java.util.Date;

import fileutility.*;
public class Test {

	public Test() {
		super();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String strFilePath;	// To store the file path
		
		// Test the command(argument1)
		if(args.length < 3) {
			System.out.println("Please Full Command!");
			return;
		}
		
		if(args[0].equalsIgnoreCase("Huffman")) {
			strFilePath = args[2];
			
			// Test the argument2
			if(args[1].equalsIgnoreCase("-encode")) {
				FileValidator validator = new FileValidator(strFilePath); 
				long longTemp = validator.validate();
				
				// Test the file
				if(longTemp < 0) {
					System.out.println("File Invalid!");
					return;
				}
				
				// File is directory
				if(longTemp == 0) {
					System.out.println("This Is A Directory!");
					return;
				}
				
				// File can be use
				FileEncoder encoder = new FileEncoder(strFilePath);
				Date date1 = new Date();
				encoder.run();
				Date date2 = new Date();
				System.out.println("Successful");
				long time = date2.getTime() - date1.getTime();
				
				System.out.println("Total Time:" + time +"ms");
				System.exit(0);
			}
			else {
				// Test the argument2
				if(args[1].equalsIgnoreCase("-decode")) {
					
					// Whether the file is a huff file
					if(strFilePath.endsWith(".huff")) {
						
						FileValidator validator = new FileValidator(strFilePath);
						long longTemp = validator.validate();

						// Test if the  huff file is a normal file 
						if(longTemp < 0) {
							System.out.println("File Invalid!");
							return ;
						}
						
						// Is a directory
						if(longTemp == 0) {
							System.out.println("This Is A Directory!");
							return ;
						}
						
						// File is valid
						FileDecoder decoder = new FileDecoder(strFilePath);
						Date date1 = new Date();
						decoder.run();
						Date date2 = new Date();
						
						System.out.println("Successful");
						long time = date2.getTime() - date1.getTime();
						
						System.out.println("Total Time:" + time + "ms");
						System.exit(0);
						return ;
					}
					else {
						System.out.println("This Is Not A Huff File!");
						return ;
					}
				}
				else {
					System.out.println("Command Error!(-encode or -decode)");
				}
			}
		}
		else {
			System.out.println("Command Error!(Huffman)");
		}
		
		
	}
	

}
