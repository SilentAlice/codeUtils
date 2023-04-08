package hangmang;

import java.lang.String;
import java.util.Scanner;
public class Hangman {
//properties
	private char[] arrWord;		//record the word by a char list
	private boolean[] bFlagWord;//used to judge if the letter has been guessed
	private int nWordSize;		//record the length of the word
	private String Word;		//record what the word is
	private Vocabulary vocabulary;	//Create a new vocabulary
	private Scanner temInput;
	
//Methods
	//Constructor
	public Hangman(){
		vocabulary = new Vocabulary();
		Word = null;
		nWordSize = 0;
		bFlagWord = null;
		arrWord = null;
		temInput = new Scanner(System.in);
	}
	
	//Deconstructor
	public void finalize(){
		this.temInput.close();
	}
	//Fun used to change the vocabulary
	public boolean changeVocabulary(String strFilePath){
		if(vocabulary.setFilePath(strFilePath))
			return true;
		return false;
	}
	
	//Fun used to get a letter 
	public char getOneLetter() throws InputException{
		String strTemp;
		strTemp = temInput.next();
		
		if(strTemp.length() != 1){
			throw new InputException(strTemp.length());
		}
		
		return strTemp.charAt(0);
	}
	
	//Fun used to start the game
	public void newGame(){
		Word = vocabulary.getRandomWord();
		arrWord = Word.toCharArray();
		nWordSize = Word.length();
		bFlagWord = new boolean[nWordSize];
		int Flag = 0;					//used to judge if the word is finished
		boolean GuessFlag = false;		//used to judge if the client guess a letter this time
		char cTempWord;
		int nCounter = 0;
			
		do{
			System.out.print("(Guess) Enter a letter in word ");
			GuessFlag = false;//suppose this time client will not guess the letter
			for(int i = 0; i < nWordSize; i++){
				if(bFlagWord[i])
					System.out.print(arrWord[i]);
				else
					System.out.print("*");
				
			}
						
			System.out.print(" > ");
			try{
				cTempWord = getOneLetter();
			}
			catch(InputException e){
				System.out.println("Wrong Input! Please check!");
				continue;
			}
			
			for(int i = 0; i < nWordSize; i++){
				if(cTempWord == arrWord[i]){
					GuessFlag = true;
					if(bFlagWord[i]){
						System.out.println(cTempWord + " is already in the word");
						break;
					}
					else					
						bFlagWord[i] = true;	
				}
			}
			
			if(GuessFlag == false){
				System.out.println(cTempWord + " is not in the word");
				nCounter++;
			}
			
			Flag = 0;
			for(int i = 0; i < nWordSize; i++)
			{
				if(bFlagWord[i] == true)
					Flag++;
			}
		}while(Flag < nWordSize);
		System.out.println();
		System.out.println("Congratulations! The word is " + Word + "\nYou missed " + nCounter + "times");		
	}
};