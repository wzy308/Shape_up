package carte;
import java.util.*;
import designPattern.*;

/**
 * A class representing the game core of the game Shape up.
 * 
 * <p>Class {@code Game} mainly contains the following functions: 
 * 
 * <p>The realization of a total of six game modes, which are {@code RegleClassicForTwo}, {@code RegleComputerClassicForTwo}, {@code RegleAdvancedForTwo}, {@code RegleSimpleForTwo}, {@code RegleClassicForThree}, {@code Regleclassicfortwo} on {@code TriangleTapisVisitor}, 
 *    select game mode, play cards, move cards, allocate and set most of the parameters needed for the game, reset the parameters etc.
 *    
 * <p>This class uses the strategy pattern, observer pattern and visitor pattern. Among them, the strategy pattern is used to realize the virtual player. The visitor pattern is to tell the players that the game mode is to calculate the score under the rectangular or triangular chessboard.
 * 
 * For exemple:
 * <pre>
 *     Game game = new Game();
 *     game.chooseModeRectangle(1);
 * </pre>
 * 
 * @author WU Zhongyang
 * @author GUAN Mingjun
 * @version %I%
 * @see Regle
 * @see Context
 * @see TriangleTapisVisitor
 * @see RectangleTapisVisitor
 * @see PlayerStrategy
 * @since 13 January 2021
 */
public class Game extends Observable{
	
	public ArrayList<Joueur> joueurs;//Initialization
	public Deck deck;
	public RectangleTapisVisitor tapis;
	public TriangleTapisVisitor triangle;
	public int rounds;
	private boolean inRound;
	private Carte hiddenCard;
	private int i=0;
	private boolean again = false;
	private Context context;
	
	private int mode;
	private boolean modeChosed=false;
	private boolean roundInPlay=false;
	private boolean cardPlayed=false;
	private boolean cardMoved=false;
	private boolean isOk=false;
	private boolean isGameSet=false;
	private boolean isAgainSet=false;
	private int xChosen;
	private int yChosen;
	private int xFrom;
	private int yFrom;
	private int xTo;
	private int yTo;
	private String texte;
	
	/**
	 * This is the constructor of the class {@code Game}.
	 * 
	 * <p>This method initializes the {@code ArrayList} of the players, initializes the game settings.
	 */
	public Game() {
		joueurs = new ArrayList<Joueur>();
		this.resetRound();
		hiddenCard= new Carte();
		inRound = false;
		context = new Context(new VirtuelFacileStrategy());
	}
	
	/**
	 * This method gets the rectangle tapis of the game.
	 * @return the rectangle tapis of the game 
	 */
	public RectangleTapisVisitor getTapis() {
		return tapis;
	}

	/**
	 * This method adds {@code joueur} to the {@code ArrayList} {@code joueurs}.
	 * @param joueur New player added to the {@code ArrayList} {@code joueurs}
	 */
	public void ajouterJoueur(Joueur joueur) { 
		if(inRound==false) {
			joueurs.add(joueur);
		}
	}
	
	/**
	 * Removes the specified {@code joueur} from this list {@code joueurs}, if it is present. If the list does not contain the player, it is unchanged.
	 * @param joueur The player that needs to be removed
	 */
	public void retirerJoueur(Joueur joueur) { 
		joueurs.remove(joueur);
	}
	
	/**
	 * This method initializes each round of the game. It distributes hidden card and victory card.
	 */
	public void startRound(){
		this.distribuerHiddenCarte();
		this.distribuerVictoryCarte();
	}
	
	/**
	 * This method initializes each round of the game of the advanced mode. It distributes hidden card. 
	 */
	public void startAdvancedRound() {
		this.distribuerHiddenCarte();
	}
	
	/**
	 * This method resets game's parameters.
	 * 
	 * @see Deck
	 * @see Context
	 */
	public void resetAll() {
		deck =new Deck();
		deck.melanger();
		this.rounds=0;
		tapis=new RectangleTapisVisitor();
		this.joueurs = new ArrayList<Joueur>();
		this.isOk=false;
		this.cardPlayed=false;
		this.cardMoved=false;
		this.i=0;
		this.mode=0;
		this.modeChosed=false;
		this.roundInPlay=false;
		this.cardPlayed=false;
		this.cardMoved=false;
		this.isOk=false;
		this.isGameSet=false;
		this.isAgainSet=false;
		this.texte=null;
		hiddenCard= new Carte();
		inRound = false;
		context = new Context(new VirtuelFacileStrategy());
	}
	
	/**
	 * This method resets each round of the game.
	 * 
	 * @see Deck
	 * @see RectangleTapisVisitor
	 */
	public void resetRound() {
		deck =new Deck();
		deck.melanger();
		tapis=new RectangleTapisVisitor();
		this.isOk=false;
		this.cardPlayed=false;
		this.cardMoved=false;
		this.i=0;
	}
	
	/**
	 * This method resets each round of the game on the trangle tapis.
	 * 
	 * @see Deck
	 * @see TriangleTapisVisitor
	 */
	public void resetRoundTriangle() {
		deck =new Deck();
		deck.melanger();
		triangle=new TriangleTapisVisitor();
		this.isOk=false;
		this.cardPlayed=false;
		this.cardMoved=false;
		this.i=0;
	}
	
	/**
	 * This method increases the round of the game by one.
	 */
	public void roundsPlus() {
		this.rounds++;
	}
	
	/**
	 * This method gets the index of the current player dans {@code ArrayList} {@code joueurs}
	 * @return The integer index of the current player dans {@code ArrayList} {@code joueurs}
	 */
	public int getI() {
		return this.i;
	}
	
	/**
	 * This method gets the texte.
	 * @return The texte
	 */
	public String getTexte() {
		return texte;
	}

	/**
	 * This method sets the attribute texte of the game by {@code texte}.
	 * @param texte The texte to set
	 */
	public void setTexte(String texte) {
		this.texte = texte;
	}

	/**
	 * This method judges if the game is set. 
	 * 
	 * <p> If the game is set, the result is {@code true}, {@code false} otherwise.
	 * 
	 * @return if the game is set or not
	 */
	public boolean isGameSet() {
		return isGameSet;
	}

	/**
	 * This method sets the game setted or not.
	 * @param isGameSet the {@code isGameSet} to set
	 */
	public void setGameSet(boolean isGameSet) {
		this.isGameSet = isGameSet;
	}

	/**
	 * This method judges if the game is set again.
	 * 
	 * <p>If the game is set again, the result is {@code true}, {@code false} otherwise.
	 * 
	 * @return if the game is set again or not
	 */
	public boolean isAgainSet() {
		return isAgainSet;
	}

	/**
	 * This method sets the game setted again or not.
	 * @param isAgainSet the {@code isAgainSet} to set
	 */
	public void setAgainSet(boolean isAgainSet) {
		this.isAgainSet = isAgainSet;
	}

	/**
	 * This method gets the mode the player chooses.
	 * @return the integer mode the player chooses
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * This method sets the mode of the game by {@code mode}.
	 * @param mode The mode to set
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * This method gets the x-coordinate chosen by the player.
	 * @return the x-coordinate chosen by the player
	 */
	public int getxChosen() {
		return xChosen;
	}

	/**
	 * This method gets the y-coordinate chosen by the player.
	 * @return the y-coordinate chosen by the player
	 */
	public int getyChosen() {
		return yChosen;
	}

	/**
	 * This method sets the x-coordinate by {@code xChosen}
	 * @param xChosen the x-coordinate to set
	 */
	public void setxChosen(int xChosen) {
		this.xChosen = xChosen;
	}

	/**
	 * This method sets the y-coordinate by {@code yChosen}
	 * @param yChosen the y-coordinate to set
	 */
	public void setyChosen(int yChosen) {
		this.yChosen = yChosen;
	}
	
	/**
	 * This method gets the x-coordinate of the card to move.
	 * @return the x-coordinate of the card to move
	 */
	public int getxFrom() {
		return xFrom;
	}

	/**
	 * This method sets the x-coordinate of the card to move.
	 * @param xFrom the x-coordinate to set of the card to move
	 */
	public void setxFrom(int xFrom) {
		this.xFrom = xFrom;
	}

	/**
	 * This method gets the y-coordinate of the card to move.
	 * @return the y-coordinate of the card to move
	 */
	public int getyFrom() {
		return yFrom;
	}

	/**
	 * This method sets the y-coordinate of the card to move.
	 * @param yFrom the y-coordinate to set of the card to move
	 */
	public void setyFrom(int yFrom) {
		this.yFrom = yFrom;
	}

	/**
	 * This method gets the x-coordinate of the position to be moved to.
	 * @return  the x-coordinate of the position to be moved to
	 */
	public int getxTo() {
		return xTo;
	}

	/**
	 * This method sets the x-coordinate of the position to be moved to by {@code xTo}.
	 * @param xTo the x-coordinate of the position to set
	 */
	public void setxTo(int xTo) {
		this.xTo = xTo;
	}

	/**
	 * This method gets the y-coordinate of the position to be moved to.
	 * @return  the y-coordinate of the position to be moved to
	 */
	public int getyTo() {
		return yTo;
	}

	/**
	 * This method sets the y-coordinate of the position to be moved to by {@code yTo}.
	 * @param yTo the y-coordinate of the position to set
	 */
	public void setyTo(int yTo) {
		this.yTo = yTo;
	}

	/**
	 * This method distributes the first card of the deck to the hidden card.
	 * 
	 * @see Deck#tirerCarteDessus()
	 */
	public void distribuerHiddenCarte() {
		this.hiddenCard=deck.tirerCarteDessus();
	}
	
	/**
	 * This method distributes the victory card to every player in the {@code ArrayList} {@code joueurs}.
	 * 
	 * @see Iterator
	 * @see Joueur#setVictoryCarte(Carte)
	 * @see Deck#tirerCarteDessus()
	 */
	public void distribuerVictoryCarte() {
		Iterator<Joueur> it = joueurs.iterator();
		while(it.hasNext()) {
		    Joueur j = (Joueur) it.next();
			j.setVictoryCarte(deck.tirerCarteDessus());
		}	
	}
	
	/**
	 * This method takes turns distributing the card for the player and informs the observers that the state has changed.
	 * 
	 * @see ArrayList#get(int)
	 * @see Joueur#prendreCarte(Carte)
	 */
	public void distribuerCartesNow() {
		if(i==joueurs.size()) {
			this.i=0;
		}
		Joueur j = (Joueur)joueurs.get(i);
		j.prendreCarte(deck.tirerCarteDessus());
		setChanged();
		notifyObservers();	
	}

	/**
	 * {@code i} represents the index of the player in the {@code ArrayList} {@code joueurs}. This method is used to change the current player one after another.
	 */
	public void iPlus(){
		this.i=this.i+1;
	}
	
	/**
	 * This method gets the player which is at the number {@code i} position in the {@code ArrayList} {@code joueurs}.{@code i} represents the index of the player in the {@code ArrayList} {@code joueurs}.
	 * @return the player which is at the number {@code i} position in the {@code ArrayList} {@code joueurs}
	 */
	public Joueur getJoueurNow() {
		return this.joueurs.get(this.getI());
	}
	
	/**
	 * This method gets the {@code ArrayList} {@code joueurs} where there are all the players in this game.
	 * @return the {@code ArrayList} {@code joueurs}
	 */
	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	/**
	 * This method judges whether the mode is chosed.
	 * 
	 * <p> If the mode is chosed, the result is {@code true}, {@code false} otherwise.
	 * @return The mode is chosed or not
	 */
	public boolean isModeChosed() {
		return modeChosed;
	}

	/**
	 * This method sets whether the mode is chosed or not by {@code modeChosed}.
	 * @param modeChosed the {@code modeChosed} to set
	 */
	public void setModeChosed(boolean modeChosed) {
		this.modeChosed = modeChosed;
	}

	/**
	 * This method judges if the round of the game is in play.
	 * 
	 * <p> If the round is in play, the result is {@code true}, {@code false} otherwise.
	 * @return the round of the game is in play or not
	 */
	public boolean isRoundInPlay() {
		return roundInPlay;
	}

	/**
	 * This method sets whether the round is in play or not by {@code roundInPlay}.
	 * @param roundInPlay the {@code roundInPlay} to set
	 */
	public void setRoundInPlay(boolean roundInPlay) {
		this.roundInPlay = roundInPlay;
	}

	/**
	 * This method judges if the card has been played by the player.
	 * 
	 * <p> If the card has been played, the result is {@code true}, {@code false} otherwise.
	 * @return the card has been played or not
	 */
	public boolean getCardPlayed() {
		return this.cardPlayed;
	}
	
	/**
	 * This method is used when the player has played card and it sets the {@code cardPlayed} in {@code true}.
	 */
	public void setCardPlayed() {
		this.cardPlayed=true;
	}
	
	/**
	 * This method judges if the card has been moved by the player.
	 * 
	 * <p> If the card has been moved, the result is {@code true}, {@code false} otherwise.
	 * @return the card has been moved or not
	 */
	public boolean getCardMoved() {
		return this.cardMoved;
	}
	
	/**
	 * This method is used when the player has moved card and it sets the {@code cardMoved} in {@code true}.
	 */
	public void setCardMoved() {
		this.cardMoved=true;
	}
	
	/**
	 * This method judges whether player has clicked the button Ok.
	 * 
	 * <p> If the player has clicked the button ok, the result is {@code true}, {@code false} otherwise.
	 * @return the player has clicked the button Ok or not
	 */
	public boolean isOk() {
		return isOk;
	}

	/**
	 * This method sets whether the "play card" step is complete by {@code isOk}.
	 * @param isOk the {@code isOk} or not to set
	 */
	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * This method judges if the player wants to play again.
	 * 
	 * <p> If the player wants to play again, the result is {@code true}, {@code false} otherwise.
	 * @return the player wants to play again or not
	 */
	public boolean isAgain() {
		return again;
	}

	/**
	 * This method is synchronized method. It sets {@code again} {@code isAgainSet} when the player decides to play again or not. It wakes up the wating thread for the current object when the method is finished.
	 * @param again The player wants to play again or not
	 */
	public synchronized void setAgain(boolean again) {
		this.again = again;
		this.isAgainSet = true;
		notifyAll();
	}

	/**
	 * This method gets the {@code context} of the game which invokes the strategy pattern for virtual players playing the cards.
	 * 
	 * @return the attribut which invokes the strategy pattern for virtual players playing the cards
	 * @see Context
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * This method sets the context of the game by {@code context}
	 * @param context the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * This method encapsulates two methods which is used in the class which extends the class observable.
	 */
	public void notifyObv() {
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * This method is synchornized method which defines the step playing card by the player.
	 * 
	 * <p>When the player does not get the card or the position of the card does not conform to the adjacent rule, the method prompts the player that the coordinates are illegal.
	 * <p>If the coordinates are legal, this method places the card on the chessboard, sets the card to be placed, refreshes the chessboard, informs the player that the card has been placed, and notifys the observers that the state is changed.
	 *    
	 * @param x The x-coordinate of the position to place
	 * @param y The y-coordinate of the position to place
	 * @param j The player who is playing card
	 * @return Returns (@code text} whether the card is placed successfully
	 * @see RectangleTapisVisitor#ajouterCarte(int, int, Carte)
	 * @see RectangleTapisVisitor#refresh()
	 */
	public synchronized String playCard(int x, int y,Joueur j) {
		String text="default";
		if(this.getCardPlayed()==false) {
				if(this.tapis.isLegalPlay(x, y)==false) {
					text="Illegal Coordonnee, please reenter Coordonne";
					this.texte=text;
					setChanged();
					notifyObservers();	
				}else {
					this.tapis.ajouterCarte(x,y,j.getCarteNow());
					this.setCardPlayed();
					this.tapis.refresh();
					text="Card Played Success";
					this.texte=text;
					setChanged();
					notifyObservers();	
					}
		}else {
			text="Card already played at your turn!";
			this.texte=text;
			setChanged();
			notifyObservers();	
		}
		return text;
	}
	
	/**
	 * This method defines the step moving card by the player.
	 * 
	 * <p>If the card is not moved and there is the card to be moved on the chessboard {@code tapis}, perform the following procedure. 
	 * <p>If the card movement is illegal, the player will be informed. 
	 * <p>If the card movement is legal, move the card and notify the observers that the state has been changed. 
	 * <p>If there is no corresponding card to move on the chessboard, inform the player.
	 * 
	 * @param x The x-coordinate of the initial position of the card to be moved
	 * @param y The y-coordinate of the initial position of the card to be moved
	 * @param a The x-coordinate of the position to be moved to
	 * @param b The y-coordinate of the position to be moved to
	 * @return Returns (@code text} whether the card is moved successfully
	 * @see RectangleTapisVisitor#isLegalMove(int, int, int, int)
	 */
	public String moveCard(int x, int y, int a, int b) {
		String text="default";
		if(this.getCardMoved()==false) {
				if(this.tapis.hasCarte(x,y)){
						if (this.tapis.isLegalMove(x, y, a, b) == false) {
							text="Illegal Arrival, reenter arrival position";
							this.texte=text;
							setChanged();
							notifyObservers();	
							} else {
								this.tapis.replaceCarte(x, y, a, b);
								this.setCardMoved();
								this.tapis.refresh();
								text="Card Moved Success";
								this.texte=text;
								setChanged();
								notifyObservers();	
								}
					}else{
						text="No such carte exists,please reenter the postion";
						this.texte=text;
						setChanged();
						notifyObservers();	
						}
				
		}else {
			text="Card already moved at your turn!";
			this.texte=text;
			setChanged();
			notifyObservers();	
		}
		return text;
	}
	
	/**
	 * This method is synchronized method which defines whether the player's round is finished.
	 * 
	 * <p>If the card has not been placed, let the player know. 
	 * <p>Otherwise, it tells the player that this round has been completed, and gives the right to draw cards to the next player, resets the parameters, notifys the observers that the state has been changed.
	 * <p>At the end of the method, it wakes up the waiting thread on the current object.
	 *    
	 * @return If the player's turn is over
	 */
	public synchronized String ok() {
		String text="default";
		if(this.cardPlayed==false) {
			text = "You must at least play your card!";
			this.texte=text;
			setChanged();
			notifyObservers();	
		}else {
			text = "Your round is finished";
			this.texte=text;
			setChanged();
			notifyObservers();	
			this.iPlus();
			this.cardMoved=false;
			this.cardPlayed=false;
			this.isOk=true;
			notifyAll();
		}
		return text;
	}
	
	/**
	 * This method waits the player to choose the mode of the game when the mode has not been choosed yet and tells the player that the mode is choosed when the player has choosed the mode.
	 * 
	 * <p> If the mode has not been choosed yet, the thread will sleep for 1000 millisecond.
	 * @see InterruptedException#printStackTrace()
	 */
	public void waitForChooseMode() {
		while(this.isModeChosed()==false) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("mode chosen!");
	}
	
	/**
	 * This method is used to select the mode of the game.
	 *  
	 * <p>It provides a total of six choices, which are {@code RegleClassicForTwo}, {@code RegleComputerClassicForTwo}, {@code RegleAdvancedForTwo}, {@code RegleSimpleForTwo}, {@code RegleClassicForThree}, {@code regleclassicfortwo} on {@code TriangleTapisVisitor}.
	 * <p>It implements the mechanism of each game mode. 
	 * <p>And at the end of the mode chosen, all relevant parameters are reset.
	 * <p>This method is applied the visitor pattern to tell the players that the game mode is to calculate the score under the rectangular or triangular chessboard.
	 *    
	 * @param choice The mode to choose
	 */
	public synchronized void chooseModeRectangle(int choice) {
			Scanner input = new Scanner(System.in); 
			switch(choice) {
			case 1://RegleClassicForTwo

		    this.rounds=1;
		    RegleClassicForTwo regleclassicfortwo = new RegleClassicForTwo();
			Joueur marcel = new Joueur("Marcel");
			Joueur raymond = new Joueur("Raymond");
			
			this.ajouterJoueur(marcel);
			this.ajouterJoueur(raymond);
			
			Joueur j1 = (Joueur)this.joueurs.get(0);
			Joueur j2 = (Joueur)this.joueurs.get(1);
			Joueur j;
			
			while(this.rounds<=4) {
				System.out.println("Game Start !");
				System.out.println("----------------------------------------------------------------------------");
				System.out.println("Round "+this.rounds+"\n");
				this.deck.melanger();
				this.startRound();

				System.out.println("A hidden card has been already faced down beside the play area."+"\n");
				System.out.println("Your victory card is: " + j1.getVictoryCarte().toString());
				System.out.println("His/Her victory card is: " + j2.getVictoryCarte().toString()+"\n");
				while(this.deck.estVide()==false) {
					System.out.println("----------------------------------------------------------------------------");
					this.distribuerCartesNow();//now i=1
					j = this.joueurs.get(this.getI());
					this.setChanged();
					this.notifyObservers();
					System.out.println("Your Carte now is: "+j.getCarteNow().toString());
					System.out.println("Current tapis: " +this.tapis.toString());
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				regleclassicfortwo.accept(new RectangleTapisVisitor());
				Iterator<Joueur> it = this.joueurs.iterator();
				while(it.hasNext()) {
					Joueur w =(Joueur)it.next();
						w.addScore(this.tapis.calculerScore(w));// ajouter au totale des scores chaque partie
						System.out.println("score cumule:"+w.getScore());
				}
				
				this.resetRound();
				this.roundsPlus();
				
			}
			
			Iterator<Joueur> it = this.joueurs.iterator();
			int s=0;
			
			while(it.hasNext()) {                 //calculer le plus haute points
				Joueur w =(Joueur)it.next();
				System.out.println(w.getNom()+" Score:"+w.getScore());
					if(w.getScore()>s) {
						s=w.getScore();
					}
			}
			
			while(it.hasNext()) {
				Joueur w =(Joueur)it.next(); //donner qui a gagne
				if(w.getScore()==s) {
					System.out.println(w.aGagne());
					this.setTexte(w.aGagne());
				}else {
					System.out.println(w.aPerdu());
				}
			}
			this.isGameSet=true;
			this.setChanged();
			this.notifyObservers();
			System.out.println("Do you want to play again?\n[again].yes\n[exit].no\n");
			if(this.isAgainSet==false) {
				System.out.println(">");
				try {
					this.wait();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			this.modeChosed=true;
			break;
			
			case 2://RegleComputerClassicForTwo
				this.rounds=1;
				RegleComputerForTwo reglecomputerfortwo = new RegleComputerForTwo();
				Joueur you = new Joueur("you");
				Joueur computer = new Joueur("computer");
				
				this.ajouterJoueur(you);
				this.ajouterJoueur(computer);
				
				Joueur a = (Joueur)this.joueurs.get(0);
				Joueur b = (Joueur)this.joueurs.get(1);
				
				while(this.rounds<=4) {
					System.out.println("Game Start !");
					System.out.println("----------------------------------------------------------------------------");
					System.out.println("Round "+this.rounds+"\n");
					this.deck.melanger();
					this.startRound();
					System.out.println("A hidden card has been already faced down beside the play area."+"\n");
					System.out.println("Your victory card is: " + you.getVictoryCarte().toString());
					System.out.println("His victory card is: " + computer.getVictoryCarte().toString()+"\n");
					while(this.deck.estVide()==false) {
						System.out.println("----------------------------------------------------------------------------");
						this.distribuerCartesNow();//now i=1
						this.setChanged();
						this.notifyObservers();
						System.out.println("Your Carte now is: "+you.getCarteNow().toString());
						System.out.println("Current tapis: " +this.tapis.toString());
						

								try {
									this.wait();
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

						
						if(this.deck.estVide()==false){
							this.distribuerCartesNow();
							try {
								Thread.sleep(1000);
								System.out.println("Your oppenent's turn, please wait.");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("His Carte now is: "+computer.getCarteNow().toString());
							System.out.println("Current tapis: " +this.tapis.toString());
							System.out.println("He chooses to [1].just place the card "+"\n");
							System.out.println("He put his card at : "+this.context.playCard(this.tapis)[0]+" "+this.context.playCard(this.tapis)[1]);
							this.tapis.ajouterCarte(this.context.playCard(this.tapis)[0],this.context.playCard(this.tapis)[1],computer.getCarteNow());
							this.tapis.refresh();
							System.out.println("Current tapis: " +this.tapis.toString());
							this.setChanged();
							this.notifyObservers();
							this.iPlus();
							}
						}
					reglecomputerfortwo.accept(new RectangleTapisVisitor());
					Iterator<Joueur> it1 = this.joueurs.iterator();
					while(it1.hasNext()) {
						Joueur w =(Joueur)it1.next();
						w.addScore(this.tapis.calculerScore(w));// ajouter au totale des scores chaque partie
						}
					System.out.println("Your score this round is:"+a.getScore());
					System.out.println("His score this round is:"+b.getScore());
					this.resetRound();
					this.roundsPlus();		
					}

				System.out.println("Your score total is:"+a.getScore());
				System.out.println("His score total is:"+b.getScore());
				if(a.getScore()>b.getScore()){
					System.out.println(a.aGagne());
					this.setTexte(a.aGagne());
				}else if(a.getScore()==b.getScore()){
					System.out.println("Round draw!");
					this.setTexte("Round draw!");
				}else{
					System.out.println(b.aGagne());
					this.setTexte(b.aGagne());
				}
				this.isGameSet=true;
				this.setChanged();
				this.notifyObservers();
				System.out.println("Do you want to play again?\n[again].yes\n[exit].no\n");
				if(this.isAgainSet==false) {
					System.out.println(">");
					try {
						this.wait();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

		break;
		
			
			case 3://RegleAdvancedForTwo
				this.rounds=1;
				RegleAdvancedForTwo regleadvancedfortwo = new RegleAdvancedForTwo();
				Joueur theo = new Joueur("theo");
				Joueur yann = new Joueur("yann");
				
				this.ajouterJoueur(theo);
				this.ajouterJoueur(yann);
				
				Joueur j3 = (Joueur)this.joueurs.get(0);
				Joueur j4 = (Joueur)this.joueurs.get(1);
				
				while(this.rounds<=4) {
					System.out.println("Game Start !");
					System.out.println("----------------------------------------------------------------------------");
					System.out.println("Round "+this.rounds+"\n");
					this.deck.melanger();
					this.startAdvancedRound();
					System.out.println("A hidden card has been already faced down beside the play area."+"\n");
					
					this.distribuerCartesNow();
					j = this.joueurs.get(this.getI());
					Carte c1 = j.getCarteNow();
					System.out.println("Your first Card is: [1]."+j.getCarteNow().toString());
					this.distribuerCartesNow();
					Carte c2 = j.getCarteNow();
					System.out.println("Your second Card is: [2]."+j.getCarteNow().toString());
					this.distribuerCartesNow();
					Carte c3 = j.getCarteNow();
					System.out.println("Your third Card is: [3]."+j.getCarteNow().toString()+"\n");
					
					this.iPlus();
					
					this.distribuerCartesNow();
					j = this.joueurs.get(this.getI());
					Carte c4 = j.getCarteNow();
					System.out.println("His/Her first Card is: [1]."+j.getCarteNow().toString());
					this.distribuerCartesNow();
					Carte c5 = j.getCarteNow();
					System.out.println("His/Her second Card is: [2]."+j.getCarteNow().toString());
					this.distribuerCartesNow();
					Carte c6 = j.getCarteNow();
					System.out.println("His/Her third Card is: [3]."+j.getCarteNow().toString()+"\n");
					
					
					int fois = 0;

					while(fois <= 4) {
						System.out.println("----------------------------------------------------------------------------");
						System.out.println("Your Card now:\n[1]."+c1.toString()+"\n[2]."+c2.toString()+"\n[3]."+c3.toString());
						System.out.println("Which card do you choose to play?");
						if(input.nextInt()==1) {
							System.out.println("You choose to play "+c1.toString());
							System.out.println("Current tapis: " +this.tapis.toString());


							this.distribuerCartesNow();
							c1 = j.getCarteNow();
						}
						
						else if(input.nextInt()==2) {
							System.out.println("You choose to play "+c2.toString());
							System.out.println("Current tapis: " +this.tapis.toString());

							
							this.distribuerCartesNow();
							c2 = j.getCarteNow();
						}
						else if(input.nextInt()==3) {
							System.out.println("You choose to play "+c3.toString());
							System.out.println("Current tapis: " +this.tapis.toString());

							this.distribuerCartesNow();
							c3 = j.getCarteNow();
						}
						
						//The second player
						System.out.println("His/Her Card now:\n[1]."+c4.toString()+"\n[2]."+c5.toString()+"\n[3]."+c6.toString());
						System.out.println("Which card do you choose to play?");
						if(input.nextInt()==1) {
							System.out.println("You choose to play "+c4.toString());
							System.out.println("Current tapis: " +this.tapis.toString());
							this.distribuerCartesNow();
							c4 = j.getCarteNow();
						}
						
						else if(input.nextInt()==2) {
							System.out.println("You choose to play "+c5.toString());
							System.out.println("Current tapis: " +this.tapis.toString());

							this.distribuerCartesNow();
							c5 = j.getCarteNow();
						}
						else if(input.nextInt()==3) {
							System.out.println("You choose to play "+c6.toString());
							System.out.println("Current tapis: " +this.tapis.toString());

							this.distribuerCartesNow();
							c6 = j.getCarteNow();
						}
						fois++;
					}
					
					// when 1 card left in the deck
		
					System.out.println("Your Card now:\n[1]."+c1.toString()+"\n[2]."+c2.toString()+"\n[3]."+c3.toString());
					System.out.println("Which card do you choose to play?");
					if(input.nextInt()==1) {
						System.out.println("You choose to play "+c1.toString());
						System.out.println("Current tapis: " +this.tapis.toString());

						this.distribuerCartesNow();
						c1 = j.getCarteNow();
					}
					
					else if(input.nextInt()==2) {
						System.out.println("You choose to play "+c2.toString());
						System.out.println("Current tapis: " +this.tapis.toString());

						this.distribuerCartesNow();
						c2 = j.getCarteNow();
					}
					else if(input.nextInt()==3) {
						System.out.println("You choose to play "+c3.toString());
						System.out.println("Current tapis: " +this.tapis.toString());

						this.distribuerCartesNow();
						c3 = j.getCarteNow();
					}
					
					//second player
					System.out.println("His/Her Card now:\n[1]."+c4.toString()+"\n[2]."+c5.toString()+"\n[3]."+c6.toString());
					System.out.println("Which card do you choose to play?");
					if(input.nextInt()==1) {
						System.out.println("You choose to play "+c4.toString());
						System.out.println("Current tapis: " +this.tapis.toString());

						c4 = null;
					}
					
					else if(input.nextInt()==2) {
						System.out.println("You choose to play "+c5.toString());
						System.out.println("Current tapis: " +this.tapis.toString());

						c5 = null;
					}
					else if(input.nextInt()==3) {
						System.out.println("You choose to play "+c6.toString());
						System.out.println("Current tapis: " +this.tapis.toString());

						c6 = null;
					}
					
					//player 1 has 3 cards left, player 2 has 2 cards left.
					
					//player 1
					System.out.println("Your Card now:\n[1]."+c1.toString()+"\n[2]."+c2.toString()+"\n[3]."+c3.toString());
					System.out.println("Which card do you choose to play?");
					if(input.nextInt()==1) {
						System.out.println("You choose to play "+c1.toString());
						System.out.println("Current tapis: " +this.tapis.toString());

						c1 = null;
					}
					
					else if(input.nextInt()==2) {
						System.out.println("You choose to play "+c2.toString());
						System.out.println("Current tapis: " +this.tapis.toString());

						c2 = null;
					}
					else if(input.nextInt()==3) {
						System.out.println("You choose to play "+c3.toString());
						System.out.println("Current tapis: " +this.tapis.toString());

						c3 = null;
					}
					
					//player 2
					Carte cartes[] = new Carte[3];
					cartes[0]=c4;
					cartes[1]=c5;
					cartes[2]=c6;
					for(int i=0; i<cartes.length; i++) {
						if(cartes[i]!=null) {
							System.out.println("His card ["+(i+1)+"] now : "+cartes[i].toString());
						}
					}
					System.out.println("Which card do you choose to play?");
					switch(input.nextInt()) {
					case 1 :
						System.out.println("You choose to play "+c4.toString());
						System.out.println("Current tapis: " +this.tapis.toString());

						c4 = null;
						
						break;
						
					case 2 :
						System.out.println("You choose to play "+c5.toString());
						System.out.println("Current tapis: " +this.tapis.toString());

					
						c5 = null;
						break;


					case 3 : 
						System.out.println("You choose to play "+c6.toString());
						System.out.println("Current tapis: " +this.tapis.toString());

						c6 = null;
						break;
					}

					
					//player 1 has 2 cards left.
					Carte carte[] = new Carte[3];
					carte[0]=c1;
					carte[1]=c2;
					carte[2]=c3;
					for(int i=0; i<cartes.length; i++) {
						if(carte[i]!=null) {
							System.out.println("Your card ["+(i+1)+"] now : "+cartes[i].toString()+"\n");
						}
					}
					
					System.out.println("Which card do you choose to play?");
//					do {
					switch(input.nextInt()) {
					case 1 :

						System.out.println("You choose to play "+c1.toString());
						System.out.println("Current tapis: " +this.tapis.toString());

						c1 = null;
						break;

					
					case 2 :

						System.out.println("You choose to play "+c2.toString());
						System.out.println("Current tapis: " +this.tapis.toString());

						c2 = null;
						break;


						
					case 3 : 
						System.out.println("You choose to play "+c3.toString());
						System.out.println("Current tapis: " +this.tapis.toString());


						c3 = null;
						break;
					}

					
					//determine victory card
					
					if(c1!=null) {
						j3.setVictoryCarte(c1);
						System.out.println("Your victory card is: " + c1.toString());
					}
					if(c2!=null) {
						j3.setVictoryCarte(c2);
						System.out.println("Your victory card is: " + c2.toString());
					}
					if(c3!=null) {
						j3.setVictoryCarte(c3);
						System.out.println("Your victory card is: " + c3.toString());
					}
					if(c4!=null) {
						j4.setVictoryCarte(c4);
						System.out.println("Your victory card is: " + c4.toString());
					}
					if(c5!=null) {
						j4.setVictoryCarte(c5);
						System.out.println("Your victory card is: " + c5.toString());
					}
					if(c6!=null) {
						j4.setVictoryCarte(c6);
						System.out.println("Your victory card is: " + c6.toString());
					}
					
					regleadvancedfortwo.accept(new RectangleTapisVisitor());
					Iterator<Joueur> iter = this.joueurs.iterator();
					while(iter.hasNext()) {
						Joueur w =(Joueur)iter.next();
							w.addScore(this.tapis.calculerScore(w));// ajouter au totale des scores chaque partie
							System.out.println("score cumule:"+w.getScore());
					}
					System.out.println("Your score this round is:"+j3.getScore());
					System.out.println("His score this round is:"+j4.getScore());
					i = 2;
					this.resetRound();
					this.roundsPlus();
					
				}
				Iterator<Joueur> it2 = this.joueurs.iterator();
				s=0;
				
				while(it2.hasNext()) {                 //calculer le plus haute points
					Joueur w =(Joueur)it2.next();
					System.out.println(w.getNom()+" Score:"+w.getScore());
						if(w.getScore()>s) {
							s=w.getScore();
						}
				}
				
				while(it2.hasNext()) {
					Joueur w =(Joueur)it2.next(); //donner qui a gagne
					if(w.getScore()==s) {
						System.out.println(w.aGagne());
						this.setTexte(w.aGagne());
					}else {
						System.out.println(w.aPerdu());
					}
				}
				this.isGameSet=true;
				this.setChanged();
				this.notifyObservers();
				System.out.println("Do you want to play again?\n[again].yes\n[exit].no\n");
				if(this.isAgainSet==false) {
					System.out.println(">");
					try {
						this.wait();
					} catch (InterruptedException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

				}

				this.modeChosed=true;
				
			break;
			
			case 4://RegleSimpleForTwo
				this.rounds=1;
				RegleSimpleForTwo reglesimplefortwo = new RegleSimpleForTwo();
				Joueur vincent = new Joueur("vincent");
				Joueur emma = new Joueur("emma");
				
				this.ajouterJoueur(vincent);
				this.ajouterJoueur(emma);
				
				Joueur j6 = (Joueur)this.joueurs.get(0);
				Joueur j7 = (Joueur)this.joueurs.get(1);

				
				while(this.rounds<=4) {
					System.out.println("Game Start !");
					System.out.println("----------------------------------------------------------------------------");
					System.out.println("Round "+this.rounds+"\n");
					this.deck.melanger();
					this.startRound();
					System.out.println("A hidden card has been already faced down beside the play area."+"\n");
					System.out.println("Your victory card is: " + j6.getVictoryCarte().toString());
					System.out.println("His/Her victory card is: " + j7.getVictoryCarte().toString()+"\n");
					while(this.deck.estVide()==false) {
						System.out.println("----------------------------------------------------------------------------");
						this.distribuerCartesNow();//now i=1
						j = this.joueurs.get(this.getI());
						System.out.println("Your Carte now is: "+j.getCarteNow().toString());
						System.out.println("Please put your card.");
						System.out.println("Current tapis: " +this.tapis.toString());
						
					}
					
					reglesimplefortwo.accept(new RectangleTapisVisitor());
					Iterator<Joueur> it3 = this.joueurs.iterator();
					while(it3.hasNext()) {
						Joueur w =(Joueur)it3.next();
							w.addScore(this.tapis.calculerScore(w));// ajouter au totale des scores chaque partie
							System.out.println("score cumule:"+w.getScore());
					}
					System.out.println("Your score this round is:"+j6.getScore());
					System.out.println("His score this round is:"+j7.getScore());
					this.resetRound();
					this.roundsPlus();
					
				}
				Iterator<Joueur> it3 = this.joueurs.iterator();
				s=0;
				
				while(it3.hasNext()) {                 //calculer le plus haute points
					Joueur w =(Joueur)it3.next();
					System.out.println(w.getNom()+" Score:"+w.getScore());
						if(w.getScore()>s) {
							s=w.getScore();
						}
				}
				
				while(it3.hasNext()) {
					Joueur w =(Joueur)it3.next(); //donner qui a gagne
					if(w.getScore()==s) {
						System.out.println(w.aGagne());
						this.setTexte(w.aGagne());
					}else {
						System.out.println(w.aPerdu());
					}
				}
				this.isGameSet=true;
				this.setChanged();
				this.notifyObservers();
				System.out.println("Do you want to play again?\n[again].yes\n[exit].no\n");
				if(this.isAgainSet==false) {
					System.out.println(">");
					try {
						this.wait();
					} catch (InterruptedException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}

				}

				this.modeChosed=true;
				
				System.out.println("Do you want to play again?\n[1].yes\n[2].no\n");
				if(input.nextInt()==1) {
					again = true;
				}
			break;	
			
			case 5://RegleClassicForThree
				this.rounds=1;
				RegleClassicForThree regleclassicforthree = new RegleClassicForThree();
				Joueur alex = new Joueur("alex");
				Joueur jean = new Joueur("jean");
				Joueur tom = new Joueur("tom");
					
				this.ajouterJoueur(alex);
				this.ajouterJoueur(jean);
				this.ajouterJoueur(tom);
					
				Joueur j8 = (Joueur)this.joueurs.get(0);
				Joueur j9 = (Joueur)this.joueurs.get(1);
				Joueur j10 = (Joueur)this.joueurs.get(2);
				
				while(this.rounds<=4) {
					System.out.println("Game Start !");
					System.out.println("----------------------------------------------------------------------------");
					System.out.println("Round "+this.rounds+"\n");
					this.deck.melanger();
					this.startRound();
					System.out.println("A hidden card has been already faced down beside the play area."+"\n");
					System.out.println("Player1's victory card is: " + j8.getVictoryCarte().toString());
					System.out.println("Player2's victory card is: " + j9.getVictoryCarte().toString());
					System.out.println("Player3's victory card is: " + j10.getVictoryCarte().toString()+"\n");
					while(this.deck.estVide()==false) {
						System.out.println("----------------------------------------------------------------------------");
						this.distribuerCartesNow();
						j = this.joueurs.get(this.getI());
						System.out.println("Your Carte now is: "+j.getCarteNow().toString());
						System.out.println("Current tapis: " +this.tapis.toString());

						
					}
					regleclassicforthree.accept(new RectangleTapisVisitor());
					Iterator<Joueur> it4 = this.joueurs.iterator();
					while(it4.hasNext()) {
						Joueur w =(Joueur)it4.next();
							w.addScore(this.tapis.calculerScore(w));// ajouter au totale des scores chaque partie
							System.out.println("score cumule:"+w.getScore());
					}
					System.out.println("Player1's score this round is:"+j8.getScore());
					System.out.println("Player2's score this round is:"+j9.getScore());
					System.out.println("Player3's score this round is:"+j10.getScore());
					this.resetRound();
					this.roundsPlus();
					
				}//4 rounds end
				Iterator<Joueur> it4 = this.joueurs.iterator();
				s=0;
				
				while(it4.hasNext()) {                 //calculer le plus haute points
					Joueur w =(Joueur)it4.next();
					System.out.println(w.getNom()+" Score:"+w.getScore());
						if(w.getScore()>s) {
							s=w.getScore();
						}
				}
				
				while(it4.hasNext()) {
					Joueur w =(Joueur)it4.next(); //donner qui a gagne
					if(w.getScore()==s) {
						System.out.println(w.aGagne());
						this.setTexte(w.aGagne());
					}else {
						System.out.println(w.aPerdu());
					}
				}
				this.isGameSet=true;
				this.setChanged();
				this.notifyObservers();
				System.out.println("Do you want to play again?\n[again].yes\n[exit].no\n");
				if(this.isAgainSet==false) {
					System.out.println(">");
					try {
						this.wait();
					} catch (InterruptedException e4) {
						e4.printStackTrace();
					}

				}

				this.modeChosed=true;
				
				System.out.println("Do you want to play again?\n[1].yes\n[2].no\n");
				if(input.nextInt()==1) {
					again = true;
				}
			break;
			
			case 6://regleclassicfortwo on triangletapis
				this.rounds=1;
			    RegleClassicForTwo regleclassicfortwo1 = new RegleClassicForTwo();
				Joueur jerry = new Joueur("jerry");
				Joueur tommy = new Joueur("tommy");
				
				this.ajouterJoueur(jerry);
				this.ajouterJoueur(tommy);
				
				Joueur j11 = (Joueur)this.joueurs.get(0);
				Joueur j12 = (Joueur)this.joueurs.get(1);
				
				while(this.rounds<=4) {
					System.out.println("Game Start !");
					System.out.println("----------------------------------------------------------------------------");
					System.out.println("Round "+this.rounds+"\n");
					this.deck.melanger();
					this.startRound();

					System.out.println("A hidden card has been already faced down beside the play area."+"\n");
					System.out.println("Your victory card is: " + j11.getVictoryCarte().toString());
					System.out.println("His/Her victory card is: " + j12.getVictoryCarte().toString()+"\n");
					while(this.deck.estVide()==false) {
						System.out.println("----------------------------------------------------------------------------");
						this.distribuerCartesNow();
						j = this.joueurs.get(this.getI());
						this.setChanged();
						this.notifyObservers();
						System.out.println("Your Carte now is: "+j.getCarteNow().toString());
						System.out.println("Current tapis: " +this.triangle.toString());
						try {
							this.wait();
						} catch (InterruptedException e5) {
							// TODO Auto-generated catch block
							e5.printStackTrace();
						}

					}
					regleclassicfortwo1.accept(new TriangleTapisVisitor());
					Iterator<Joueur> it5 = this.joueurs.iterator();
					while(it5.hasNext()) {
						Joueur w =(Joueur)it5.next();
							w.addScore(this.triangle.calculerScore(w));// ajouter au totale des scores chaque partie
							System.out.println("score cumule:"+w.getScore());
					}
					
					this.resetRoundTriangle();
					this.roundsPlus();
					
				}
				
				Iterator<Joueur> it5 = this.joueurs.iterator();
				s=0;
				
				while(it5.hasNext()) {                 //calculer le plus haute points
					Joueur w =(Joueur)it5.next();
					System.out.println(w.getNom()+" Score:"+w.getScore());
						if(w.getScore()>s) {
							s=w.getScore();
						}
				}
				
				while(it5.hasNext()) {
					Joueur w =(Joueur)it5.next(); //donner qui a gagne
					if(w.getScore()==s) {
						System.out.println(w.aGagne());
						this.setTexte(w.aGagne());
					}else {
						System.out.println(w.aPerdu());
					}
				}
				this.isGameSet=true;
				this.setChanged();
				this.notifyObservers();
				System.out.println("Do you want to play again?\n[again].yes\n[exit].no\n");
				if(this.isAgainSet==false) {
					System.out.println(">");
					try {
						this.wait();
					} catch (InterruptedException e5) {
						e5.printStackTrace();
					}

				}

				this.modeChosed=true;
			break;
		}
			this.resetAll();
	}
}
