package hangmang;

import java.util.Scanner; //Use Scanner to read data from console
import java.lang.String;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class Vocabulary {
//properties
	private ArrayList<String> listVocabulary;
	private String strFilePath;
	private Scanner FileInput;
	
//methods
	//Constructor
	public Vocabulary(){
		super();
		listVocabulary = new ArrayList<String>();
		strFilePath = "Vocabulary.txt";
		File WordsFile = new File(strFilePath);
		try{
			FileInput = new Scanner(WordsFile);
			//use a list to store the vocabulary
			listVocabulary.clear();
			while(FileInput.hasNext()){
				listVocabulary.add(FileInput.next());
			}
			FileInput.close();
		}
		catch(FileNotFoundException e){
			System.out.println(e.toString());
			return;
		}
	}
	
	public Vocabulary(String strFilePath){
		this();
		if(!gainWords(strFilePath)){
			System.out.println("File not found! use the default file\n");
			return;
		}
		this.strFilePath = strFilePath;
	}
	
	//fun used to read vocabulary from file
	private boolean gainWords(String strFilePath){
		File WordsFile = new File(strFilePath);
		try{		
			FileInput = new Scanner(WordsFile);
			//use a list to store the vocabulary
			listVocabulary.clear();
			while(FileInput.hasNext()){
				listVocabulary.add(FileInput.next());
			}
			FileInput.close();
		}
		catch(FileNotFoundException e){
			return false;
		}
		return true;
	}
	
	//fun to return a word at random
	public String getRandomWord(){
		//create a random integer based on time
		java.util.Date date = new java.util.Date();
		java.util.Random randomInt = new java.util.Random(date.getTime());
		return listVocabulary.get(randomInt.nextInt(listVocabulary.size()));
	}
	
	//fun to return the size of vocabulary
	public int sizeOfVocabulary(){
		return listVocabulary.size();
	}
	
	//fun to return the word chose by client
	public String chooseWord(int Index){
		try{
			return listVocabulary.get(Index);
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("Your Index is beyond the bound!");
			return null;
		}
	}
	
	public boolean setFilePath(String strFilePath){
		
		if(!gainWords(strFilePath)){
			System.out.println("File not found! Please check the directory\n and the strFilePath will not be changed\n");
			return false;
		}
		
		this.strFilePath = strFilePath;
		return true;
	}
	
	public String getNowFilePath(){
		return strFilePath;
	}
	/*
	 //fun to return the word inputed by client
	 public String inputWord()
	 
	{
		Scanner ConsoleInput = new Scanner(System.in);
		ConsoleInput.close();
		return ConsoleInput.next();
	}
	*/
}