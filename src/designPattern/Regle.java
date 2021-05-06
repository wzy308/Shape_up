package designPattern;

import carte.Color;
import carte.Fill;
import carte.Shape;

/**
 * This class is an abstract class and contains an abstract function {@code accept}.This class is used to visit.
 * 
 * @author WU Zhongyang
 * @author GUAN Mingjun
 * @version %I%
 * @since 13 January 2021
 * @see Visitor
 */
public abstract class Regle {
	/**
	 * This method is an abstract function which is used to accept visitor's visit.
	 * @param visitor Visitor to visit
	 */
	public abstract void accept(Visitor visitor);
}
