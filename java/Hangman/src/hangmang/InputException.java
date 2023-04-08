package hangmang;

public class InputException extends Exception {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//Property
	private int nSize;
	
//Constructor
	public InputException(){
		nSize = 0;
	}
	
	public InputException(int nSize){
		this.nSize = nSize;
	}

	@Override
	public String toString() {
		return "InputException! you have input" + nSize + "words";
	}	
}
