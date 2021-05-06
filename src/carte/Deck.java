package carte;
import java.util.*;

/**
 * A class representing the deck in the game Shape up.
 * 
 * <p> Class {@code Deck} mainly contains the following functions: initializing the {@code LinkedList} of the cards in the deck, shuffling the cards in the deck, getting the first card in the deck, etc.
 * 
 * For exemple:
 * <pre>
 *     Deck deck = new Deck();
 *     deck.melanger();
 * </pre>
 * 
 * @author WU Zhongyang
 * @author GUAN Mingjun
 * @version %I%
 * @see Carte
 * @since 13 January 2021
 */
public class Deck {
	
	/**
	 * The constant NOMBRE_DE_CATRES specifies the number of all cards on the deck.
	 */
	public final static int NOMBRE_DE_CATRES =Shape.values().length * Fill.values().length * Color.values().length;
	
	private LinkedList<Carte> tas;
	
	/**
	 * This is the constructor of the class {@code Deck}.
	 * 
	 * <p>This method initializes the {@code LinkedList} of cards in the deck and puts all of the cards in the {@code LinkedList}.
	 */
	public Deck() {
		
		tas = new LinkedList<Carte>();
		
		for(Shape s : Shape.values()) {
			for(Fill f : Fill.values()) {
				for(Color c : Color.values()) {
					Carte carte = new Carte(s, f, c);
					carte.toString();
					tas.add(carte);
				}
			}
		}
	}
	
	/**
	 * This method arranges the cards in the {@code LinkedList} out of order.
	 */
	public void melanger() {
		for(int i = 0; i<Deck.NOMBRE_DE_CATRES;i++) {
			int position = (int) Math.round((Deck.NOMBRE_DE_CATRES-1)*Math.random());
			Carte carte = tas.pop();
			tas.add(position, carte);
		}	
	}
	
	/**
	 * This method returns the first card of the {@code LinkedList} of the cards in the deck.
	 * @return the first card of the {@code LinkedList} of the cards on the deck.
	 */
	public Carte tirerCarteDessus() {
		return tas.pop();
	}
	
	/**
	 * This method returns if the deck has cards or not.
	 * @return {@code true} if there is no card in the deck, {@code false} otherwise.
	 */
	public boolean estVide() {
		return tas.isEmpty();
	}
	
	/**
	 * This method returns a string representation of the {@code LinkedList} of the cards in the deck.
	 * 
	 * @return a string representation of the {@code LinkedList} of the cards in the deck
	 * @see Carte#toString()
	 */
	public String toString() {
		return tas.toString();
	}
}