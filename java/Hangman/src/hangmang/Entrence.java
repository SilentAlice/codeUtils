package hangmang;

public class Entrence {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Hangman Game = new Hangman();
		char cInput = '\u0000';
		do{
			Game.newGame();
			System.out.println("To continue input y");
			try{
				cInput = Game.getOneLetter();
			}
			catch(InputException e){
				System.out.println("Wrong Input! Please Check!");
				continue;
			}
		
		}while(cInput == 'y');
	}
}