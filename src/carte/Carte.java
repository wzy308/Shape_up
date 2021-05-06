package carte;

/**
 * A class representing the cards in the game Shape up.
 * 
 * <p>Class {@code Carte} mainly contains the following functions: obtaining card attributes, set up card attributs,  etc.
 * 
 * For exemple:
 * <pre>
 *     Carte carte = new Carte(c);
 *     carte.toString();
 * </pre>
 * 
 * @author WU Zhongyang
 * @author GUAN Mingjun
 * @version %I%
 * @see Color
 * @see Fill
 * @see Shape
 * @since 13 January 2021
 */
public class Carte {

    private Color c;
    private Fill f;
    private Shape s;
    
    /**
     * This is the constructor of the class {@code Carte}.
     */
    public Carte() {
    	
    }
    
    /**
     * This is the constructor of the class {@code Carte}.
     * 
     * @param c The card to use.
     *        
     */
    public Carte (Carte c){
    	this.c=c.c;
    	this.f=c.f;
    	this.s=c.s;
	}

    /**
     * This is the constructor of the class {@code Carte}.
     * 
     * @param s The shape of the card to set.
     * @param f The fill of the card to set.
     * @param c The color of the card to set.
     */
    public Carte (Shape s, Fill f, Color c) {
    	this.setShape(s);
	    this.setColor(c);
	    this.setFill(f);
    }

	/**
	 * This method gets the shape of the card.
	 * @return the shape of the card
	 */
	public Shape getShape() {
    	return this.s;
    }
	
    /**
     * This method sets up the shape of the card.
     * @param s The shape of the card to set
     */
    public void setShape(Shape s) {
    	this.s=s;
    }

    /**
     * This method gets the color of the card.
     * @return the color of the card
     */
    public Color getColor() {
	return this.c;
    }

    /**
     * This method sets up the color of the card.
     * @param c The color of the card to set
     */
    public void setColor(Color c) {
	    this.c = c;
    }

    /**
     * This method gets the fill or not of the card.
     * @return the fill of the card
     */
    public Fill getFill() {
	return this.f;
    }

    /**
     * This method sets up the fill of the card.
     * @param f The fill of the card to set
     */
    public void setFill(Fill f) {
	    this.f = f;
    }
    
    /**
     * This method is used to get the file name of the corresponding card when implementing graphical interfaces.
     * @return The file name of the corresponding card represented by the string argument.
     */
    public String getPath() {
    	StringBuffer sb = new StringBuffer();
    	sb.append(this.s);
    	sb.append("-");
    	sb.append(this.f);
    	sb.append("-");
    	sb.append(this.c);
    	sb.append(".JPG");
    	return sb.toString();
    }
    
    /**
     * This method defines how the card is printed.
     * @return The output form of the card represented by the string argument.
     */
    public String toString() {
	StringBuffer sb = new StringBuffer();
	sb.append(this.s);
	sb.append("-");
	sb.append(this.f);
	sb.append("-");
	sb.append(this.c);
	return sb.toString();
    }    
}
