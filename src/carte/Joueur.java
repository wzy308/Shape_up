package carte;

/**
 * A class representing the joueur in the game Shape up.
 * 
 * <p> Class {@code Joueur} mainly contains the following functions: setting the {@code carteNow} currently in the player's hand, setting player's name, setting player's victory card, getting the player's score, printing out who wins, etc.
 * 
 * For exemple:
 * <pre>
 *     Joueur joueur = new Joueur();
 *     joueur.getScore()();
 * </pre>
 * 
 * @author WU Zhongyang
 * @author GUAN Mingjun
 * @version %I%
 * @see Carte
 * @since 13 January 2021
 */
public class Joueur {
	
	private String nom;
	
	private Carte victoryCarte;
	private Carte carteNow;
	private int score=0;
	
	/**
	 * This is the constructor of the class {@code Joueur} which defines the player's name.
	 * @param nom The name of the joueur
	 */
	public Joueur(String nom) {
		this.nom = nom;
	}
	
	/**
	 * This method is used for the player to get a card and this card is {@code carteNow} which is the card currently in the player's hand.
	 * 
	 * @param carte The card that the player gets.
	 */
	public void prendreCarte(Carte carte) {
		this.carteNow = carte;
	}
	
	/**
	 * This method gets the card currently in the player's hand.
	 * @return The card currently in the player's hand.
	 */
	public Carte getCarteNow() {
		return this.carteNow;
	}
	
	/**
	 * This method gets the victory card of the player.
	 * @return The victory card of the player
	 */
	public Carte getVictoryCarte() {
		return this.victoryCarte;
	}
	
	/**
	 * This method sets up the victory card of the player.
	 * @param c The card to be set as the player's victory card
	 */
	public void setVictoryCarte(Carte c) {
		this.victoryCarte=c;
	}
	
	/**
	 * This method is used to print out the winner
	 * @return the {@code String} who wins
	 */
	public String aGagne() {
		String temp = nom+" Win!";
		System.out.println(nom+" Win!");
		return temp;
	}
	
	/**
	 * This method is used to print out the lose player(s)
	 * @return the {@code String} who loses
	 */
	public String aPerdu() {
		System.out.println(nom+" Lose!");
		String temp = nom+"Lose!";
		return temp;
	}
	
	/**
	 * This method gets the score of the player.
	 * @return the score of the player
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * This method sets the score of the player.
	 * @param s The score to be set as the player's score
	 */
	public void setScore(int s) {
		this.score=s;
	}
	
	/**
	 * This method is used for the sum of the scores.
	 * @param s The score to be added
	 */
	public void addScore(int s) {
		this.score=this.score+s;
	}
	
	/**
	 * This method is used to set the player's score to zero.
	 */
	public void ClearScore() {
		this.score=0;
	}
	
	/**
	 * This method gets the name of the player.
	 * @return the name of the player
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * This method sets the name of the player.
	 * @param nom The name to be set for the player
	 */
	public void setNom(String nom) {
		this.nom=nom;
	}
}