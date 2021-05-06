package designPattern;

/**
 * This class calls the method {@code playCard} of the strategy pattern.
 * 
 * For exemple:
 * <pre>
 *     int x = 0;
 *     Context context = new Context(playerStrategy);
 *     x = context.playCard(tapis)[1];
 * </pre>
 * 
 * @author WU Zhongyang
 * @author GUAN Mingjun
 * @version %I%
 * @see PlayerStrategy
 * @since 13 January 2021
 */
public class Context {
	
	private PlayerStrategy playerStrategy;
	
	/**
	 * This is the constructor of the class {@code Context}. It initializes the attribute {@code playerStrateg}.
	 * @param playerStrategy The playerStrategy to set 
	 */
	public Context(PlayerStrategy playerStrategy) {
		this.playerStrategy = playerStrategy;
	}

	/**
	 * This method calls the method {@code playCard} of the object {@code playerStrategy} which is of the type {@code  PlayerStrategy}.
	 * @param tapis The chessboard on which the virtual player is playing card.
	 * @return The method returns the array which contains the coordinates of the position where the virtual player wants to play card
	 */
	public int[] playCard(RectangleTapisVisitor tapis){
         return this.playerStrategy.playCard(tapis);
    }

}
