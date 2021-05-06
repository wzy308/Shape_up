package designPattern;

/**
 * This class extends the abstract class {@code Regle}.This class is used to visit.
 * <p>This class is about the game mode (@code RegleClassicForThree}
 * @author WU Zhongyang
 * @author GUAN Mingjun
 * @version %I%
 * @see Regle
 * @since 13 January 2021
 */
public class RegleClassicForThree extends Regle{

	/**
	 * This method is used to accept visitor's visit.
	 * @param visitor Visitor to visit
	 */
	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}

}
