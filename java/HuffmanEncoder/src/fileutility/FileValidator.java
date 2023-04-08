package fileutility;

import java.io.File;

public class FileValidator {

	private String strFilePath;
	
	/* Constructors */
	public FileValidator() {
		super();
		strFilePath = null;
	}
	
	public FileValidator(String filepath) {
		super();
		strFilePath = filepath;
	}
	
	public long validate() {
		File InputFile = new File(strFilePath);
		if(InputFile.exists()) {
			if(InputFile.isFile())
				return InputFile.length();
			if(InputFile.isDirectory())
				return 0;
		}
		return -1;
	}
}
