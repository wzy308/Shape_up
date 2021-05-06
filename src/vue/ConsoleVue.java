package vue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import carte.Game;

/**
 * A class of Vue which offers Console View for users
 * 
 * @author WU Zhongyang
 * @author GUAN Mingjun
 * @version %I%
 * @since 13 January 2021
 */
public class ConsoleVue implements Observer, Runnable {
    public static String PROMPT = ">";
    public static String QUITTER = "quit";
    public static String MODETWOPEOPLE ="hvh";
    public static String MODECPU ="hvc";
    public static String PLAYCARD ="playcard";
    public static String MOVECARD ="movecard";
    public static String OK ="ok";
    public static String AGAIN ="again";
    public static String EXIT ="exit";
    
    private String saisie = null;
	private Game game;
	
    /**
     * This is the constructor of the class {@code ConsoleVue}.
     * 
     * @param g The object of the class Game.
     *        
     */
	public ConsoleVue(Game g) {
		this.game=g;
		this.game.addObserver(this);
		Thread t = new Thread(this);
		t.start();
	}
	
    /**
     * This method allow Console to read characters typed by user.
     * @return resultat Characters typed by user
     */
    private String lireChaine() {
	BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
	String resultat = null;
	try {
	    System.out.print(ConsoleVue.PROMPT);
	    resultat = br.readLine();
	} catch (IOException e) {
	    System.err.println(e.getMessage());
	}
	return resultat;	
    }
	
  
    /**
     * This method is used for getting the coordinate typed by user.
     * @return temp An array[2] consists of the x and y of coordinate.
     */
    public int[] getCoordonnee() {
    	Scanner input = new Scanner(System.in);  
    	int[] temp = new int[2];
    	temp[0]=input.nextInt();
    	temp[1]=input.nextInt();
    	return temp;
    }
    
    
    /**
     * This method will be run when the constructor starts the Thread t.
     * Mainly contains different reactions and hints that should be done or showed after the user type in his commands.
     */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String saisie = null;
		boolean quitter = false;
	 	do {
	 		game.setGameSet(false);
			System.out.println("Taper " + ConsoleVue.QUITTER + " pour quitter.");
			System.out.println("Choose players : two people[Type: "+ConsoleVue.MODETWOPEOPLE+"] \n one people and one computer[Type: "+ConsoleVue.MODECPU+"]");
			
			do {
			    saisie = this.lireChaine();
			    if (saisie.equals(ConsoleVue.MODETWOPEOPLE) && game.isModeChosed()==false) {
					game.setMode(1);
					game.setModeChosed(true);
				} else if (saisie.equals(ConsoleVue.MODECPU) && game.isModeChosed()==false) {
					game.setMode(2);
					game.setModeChosed(true);
				}else if(saisie.equals(ConsoleVue.QUITTER)) {
					break;
			    } else {
				    System.out.println("Commande non reconnue...");
				}		
			} while (game.isModeChosed() == false);
			    
			do {  
			    saisie = this.lireChaine();	
				if (saisie.equals(ConsoleVue.PLAYCARD) && game.getCardPlayed()==false) {
					System.out.println("Enter Coordonnee comme 1  1");
					int[] temp = this.getCoordonnee();
					System.out.println(game.playCard(temp[0], temp[1], game.getJoueurNow()));
					System.out.println("Current tapis: " +game.getTapis().toString());
				} else if (saisie.equals(ConsoleVue.MOVECARD) && game.getCardMoved()==false) {
					System.out.println("Enter the coordonnee of the card you want to move");
					int[] temp = this.getCoordonnee();
					game.setxFrom(temp[0]);
					game.setyFrom(temp[1]);
					System.out.println(" Move to Which Position ? x y");
					temp = this.getCoordonnee();
					game.setxTo(temp[0]);
					game.setyTo(temp[1]);
					System.out.println(game.moveCard(game.getxFrom(), game.getyFrom(), game.getxTo(), game.getyTo()));
					System.out.println("Current tapis: " +game.getTapis().toString());
				} else if (saisie.equals(ConsoleVue.OK)) {
					System.out.println(game.ok());
				} else if (saisie.equals(ConsoleVue.AGAIN)) {
					game.setAgain(true);
					quitter = true;
				} else if (saisie.equals(ConsoleVue.EXIT)) {
					System.exit(0);
				} else if(saisie.equals(ConsoleVue.QUITTER)) {
					quitter = true;
			    } else {
				    System.out.println("Commande non reconnue...");
				}		
			    
			} while (quitter == false);
//			System.exit(0);
		}while (game.isAgain()== true);

		
		
	}
	
    /**
     * This method is used for update the hints showed in the console, when this observer is notified by the Observable Game.
     * @param  o     the observable object.
     * @param  arg   an argument passed to the {@code notifyObservers}
     */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(game.isModeChosed()==true && game.isOk()==false && game.isGameSet()==false) {
			System.out.println("You choose to\nPlay Card[Type: " + ConsoleVue.PLAYCARD + "]\nMove Card[Type: " + ConsoleVue.MOVECARD + "]\n"
					+"(You MUST at least Play Card for once)\n"+"Once you finished, Type:ok");
		}
	}
	
	
}
