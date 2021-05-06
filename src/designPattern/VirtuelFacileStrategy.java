package designPattern;

/**
 * This class implements PlayerStrategy.This class implements the functions {@code playCard} in the interface.
 * 
 * <p>This class defines the playing card strategy of simple virtual players.
 * @author WU Zhongyang
 * @author GUAN Mingjun
 * @version %I%
 * @since 13 January 2021
 * @see RectangleTapisVisitor
 */
public class VirtuelFacileStrategy implements PlayerStrategy{
	
	/**
	 * This method is a simple virtual player's playing card strategy.
	 * 
	 * <p>By traversing the chessboard, this method finds out whether there is a vacancy in the upper, lower, left and right positions of each existing card. If there is, it returns the coordinates of the empty position.
	 * 
	 * @return returns the array which contains the coordinates of the empty position
	 * @see TriangleTapisVisitor#isLegalPlay(int, int)
	 */
	public int[] playCard(RectangleTapisVisitor tapis) {
		int x=0, y=0;
		for(int i=0; i<tapis.getTapis().size();i++) {
			for(int j=0; j<tapis.getTapis().get(i).size();j++) {
				if(tapis.getTapis().get(i).get(j)!=null) {
					int[] a1 = new int[2];
					int[] a2 = new int[2];
					int[] a3 = new int[2];
					int[] a4 = new int[2];
					a1[0] = i-1;
					a1[1] = j;
					a2[0] = i;
					a2[1] = j-1;
					a3[0] = i;
					a3[1] = j+1;
					a4[0] = i+1;
					a4[1] = j;
					
					if(tapis.isLegalPlay(a1[0],a1[1])) {
						x = i-1;
						y = j;
					}
					else if(tapis.isLegalPlay(a2[0],a2[1])) {
						x = i;
						y = j-1;
					}
					else if(tapis.isLegalPlay(a3[0],a3[1])) {
						x = i;
						y = j+1;
					}
					else if(tapis.isLegalPlay(a4[0],a4[1])) {
						x = i+1;
						y = j;
					}
				}
			}
		}
		return new int[] {x,y};
	}
}
