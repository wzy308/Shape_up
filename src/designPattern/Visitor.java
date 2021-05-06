package designPattern;

/**
 * This is a visitor interface.This interface contains 5 functions that need to be implemented, including {@code visit} {@code RegleClassicForTwo} mode, {@code RegleAdvancedForTwo} mode, {@code RegleSimpleForTwo} mode, {@code RegleClassicForThree} mode, {@code RegleComputerForTwo} mode.
 * @author WU Zhongyang
 * @author GUAN Mingjun
 * @version %I%
 * @since 13 January 2021
 */
public interface Visitor {
	/**
	 * This method is used to be implemented by other classes
	 * @param regleclassicfortwo Game mode to visit
	 */
	void visit(RegleClassicForTwo regleclassicfortwo);
	/**
	 * This method is used to be implemented by other classes
	 * @param regleadvancedfortwo Game mode to visit
	 */
	void visit(RegleAdvancedForTwo regleadvancedfortwo);
	/**
	 * This method is used to be implemented by other classes
	 * @param reglesimplefortwo Game mode to visit
	 */
	void visit(RegleSimpleForTwo reglesimplefortwo);
	/**
	 * This method is used to be implemented by other classes
	 * @param regleclassicforthree Game mode to visit
	 */
	void visit(RegleClassicForThree regleclassicforthree);
	/**
	 * This method is used to be implemented by other classes
	 * @param reglecomputerfortwo Game mode to visit
	 */
	void visit(RegleComputerForTwo reglecomputerfortwo);
}
