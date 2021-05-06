package designPattern;

/**
 * This interface has a method {@code playCard} to implement.
 * 
 * @author WU Zhongyang
 * @author GUAN Mingjun
 * @version %I%
 * @since 13 January 2021
 */
public interface PlayerStrategy {
	/**
	 * This method is used to be implemented by other classes
	 * @param tapis The chessboard where the virtual player play cards.
	 * @return The array which contains the coordinates of the position where the virtual player wants to play card
	 */
	public int[] playCard(RectangleTapisVisitor tapis);
}
