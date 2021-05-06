package designPattern;

import java.util.ArrayList;

import carte.Carte;
import carte.Color;
import carte.Fill;
import carte.Joueur;
import carte.Shape;

/**
 * A class representing the triangle chessboard in the game Shape up. This class implements Visitor.
 * 
 * <p> Class {@code TriangleTapisVisitor} mainly contains the following functions: calculate the score, judge whether the placed card position is legal, judge whether it meets the adjacency rules, and visit each game mode,etc.
 * 
 * For exemple:
 * <pre>
 *     TriangleTapisVisitor tapis = new TriangleTapisVisitor();
 *     tapis.calculerScore(joueur);
 * </pre>
 * 
 * @author WU Zhongyang
 * @author GUAN Mingjun
 * @version %I%
 * @since 13 January 2021
 */
public class TriangleTapisVisitor implements Visitor{
	
	private ArrayList<ArrayList<Carte>> co;
	
	/**
	 * This is the constructor of the class {@code TriangleTapisVisitor}.
	 * 
	 * <p>This method initializes a triangle chessboard which contains 16 grids, where the {@code ArrayList} contains {@code ArrayList}, and initializes each element to null.
	 */
	public TriangleTapisVisitor() {
		co= new ArrayList<>();
		for(int i=0;i<4;i++){
			co.add(new ArrayList<Carte>());
			for(int j=0;j<7;j++){
				co.get(i).add(null);
			}
		}
	}
	
	/**
	 * This method creates a new chessboard and initializes the chessboard to null.
	 * @return Newly created chessboard
	 */
	public ArrayList<ArrayList<Carte>> newCo() {
		ArrayList<ArrayList<Carte>> c= new ArrayList<>();
		for(int i=0;i<4;i++){
			c.add(new ArrayList<Carte>());
			for(int j=0;j<7;j++){
				c.get(i).add(null);
			}
		}
		return c;
	}
	
	/**
	 * This method gets the chessboard of the object.
	 * @return The chessboard to get
	 */
	public ArrayList<ArrayList<Carte>> getTapis(){
		return this.co;
	}
	
	/**
	 * This method removes all of the cards from this {@code ArrayList}. The list will be empty after this call returns.
	 * @see ArrayList#clear()
	 */
	public void clear(){
		co.clear();
	}
	
	/**
	 * This method places the specified card on the specified position on the board.
	 * 
	 * @param x The abscissa of the specified position on the chessboard
	 * @param y The ordinate of the specified position of the chessboard
	 * @param c Card to be placed
	 * @see ArrayList#get(int)
	 * @see ArrayList#set(int, Object)
	 */
	public void ajouterCarte(int x,int y,Carte c) {//ajouter les deux dans Map
		co.get(x).set(y,c);
	}

	/**
	 * This method determines whether there is a card at the specified position on the board.
	 * 
	 * @param x The abscissa of the location to be judged		 
	 * @param y The ordinate of the location to be judged
	 * @return If there is a card in that position, return {@code true}, else {@code false}
	 */
	public boolean hasCarte(int x, int y) {
		int count = 0;
		for (int i = 0; i < co.size(); i++) {
			for (int j = 0; j < co.get(i).size(); j++) {
				if(co.get(i).get(j)!=null){
					if(x == i && y == j) {
						count++;
					}
				}
			}
		}
        if (count > 0) {
        	return true;
        }
        else {
        	return false;
        }
	}

	/**
	 * This method determines whether the position of the card to be placed meets the adjacent rules.
	 * 
	 * <p> If there are no cards on the board, the adjacent rules are met. If there are cards on the board, judge whether the position of the card to be placed is up, down, left, and right of the existing cards.
	 * 
	 * @param x The abscissa of the card to be placed
	 * @param y The ordinate of the card to be placed
	 * @return If the adjacency rules are met, then return {@code true}, else {@code false}
	 */
	public boolean isAdjacent(int x,int y) {            //juger si'il suffit la regle de Adjacent
		int count = 0;
		int countnotnull = 0;
		for (int i = 0; i < co.size(); i++) {
			for (int j = 0; j < co.get(i).size(); j++) {
				if(co.get(i).get(j)!=null){
					countnotnull++;
					if (Math.hypot((i - x), (j - y)) == 1) {
						count++;
					}
				}
			}
		}
		if(countnotnull==0) return true;
		else return count > 0;
	}

	/**
	 * This method determines whether the position of the card to be placed is legal or not.
	 * 
	 * <p> If the position of the card to be placed is empty and the adjacent rules are met, the position of the card to be placed is legal.
	 * 
	 * @param x The abscissa of the card to be placed
	 * @param y The ordinate of the card to be placed
	 * @return If the coordinates are legal, then return {@code true}, else {@code false}
	 */
	public boolean isLegalPlay(int x, int y){
		boolean b=false;

		if(hasCarte(x,y)==false && isAdjacent(x,y)==true){
			b=true;
		}
		else{
			b=false;
		}

		return b;
	}

	/**
	 * This method determines whether the position of the card to be placed is legal or not When given the chessboard {@code co}.
	 * @param x The abscissa of the card to be placed
	 * @param y The ordinate of the card to be placed
	 * @param co The chessboard when judging whether the placed card position is legal
	 * @return If the coordinates are legal, then return {@code true}, else {@code false}
	 */
	//@Overload
	public boolean isLegalPlay(int x, int y,ArrayList<ArrayList<Carte>> co){
		boolean b=false;

		if(hasCarte(x,y)==false && isAdjacent(x,y)==true){
			b = true;
		}
		else{
			b=false;
		}

		return b;
	}

	/**
	 * This method determines whether the step moving card is legal.
	 * 
	 * <p>This method first creates a new chessboard to assume that the cards are moved as required.
	 * <p>And then, set the initial position of the card to be moved to null.
	 * <p>And then, determine whether the location to be moved is legal.
	 * 
	 * @param x The initial abscissa of the card to be moved
	 * @param y The initial ordinate of the card to be moved
	 * @param a The abscissa of the position to move to
	 * @param b The ordinate of the position to move to
	 * @return If the step moving card is legal, returns {@code true}, otherwise {@code false}
	 */
	public boolean isLegalMove(int x, int y,int a, int b){//x,y point depart a,b point arrivee
		boolean bool=false;
		ArrayList<ArrayList<Carte>> temp = new ArrayList<>();
		for (int i = 0; i < co.size(); i++) {
			temp.add(i,new ArrayList<Carte>());
			for (int j = 0; j < co.get(i).size(); j++) {
				temp.get(i).add(j,co.get(i).get(j));
			}
		}
		temp.get(x).set(y,null);
		if(this.isLegalPlay(a,b,temp)){
			bool=true;
		}
		
		return bool;
	}

	/**
	 * This method places the original card at (x, y) at the position (a, b) and delete the card at position (x,y).
	 * @param x The initial abscissa of the card being moved
	 * @param y The initial ordinate of the card being moved
	 * @param a Abscissa after moving
	 * @param b Ordinate after moving
	 */
	public void replaceCarte(int x,int y,int a,int b) {      //chercher et remplacer la valeur Coordonnee par la cle Carte
		co.get(a).set(b,co.get(x).get(y));
		co.get(x).set(y,null);
	}
	
	/**
	 * This method is used to calculate the player's score.
	 * 
	 * <p>This method first creates the collection {@code ArrayList} of each column.
	 * <p>Add the shape, color and fill scores of each row and each column to get the final total score.
	 * @param joueur Player to calculate score
	 * @return The total score of the player
	 */
	public int calculerScore(Joueur joueur) {
		int score = 0;
		ArrayList<Carte> listy1 = new ArrayList<Carte>();
		ArrayList<Carte> listy2 = new ArrayList<Carte>();
		ArrayList<Carte> listy3 = new ArrayList<Carte>();
		ArrayList<Carte> listy4 = new ArrayList<Carte>();
		ArrayList<Carte> listy5 = new ArrayList<Carte>();
		ArrayList<Carte> listy6 = new ArrayList<Carte>();
		ArrayList<Carte> listy7 = new ArrayList<Carte>();

			listy1.add(co.get(3).get(0));
			listy2.add(co.get(2).get(1));
			listy2.add(co.get(3).get(1));
			listy3.add(co.get(1).get(2));
			listy3.add(co.get(2).get(2));
			listy3.add(co.get(3).get(2));
			listy4.add(co.get(0).get(3));
			listy4.add(co.get(1).get(3));
			listy4.add(co.get(2).get(3));
			listy4.add(co.get(3).get(3));
			listy5.add(co.get(1).get(4));
			listy5.add(co.get(2).get(4));
			listy5.add(co.get(3).get(4));
			listy6.add(co.get(2).get(5));
			listy6.add(co.get(3).get(5));
			listy7.add(co.get(3).get(6));

			//Shape
			Shape s = joueur.getVictoryCarte().getShape();
			score=score+this.calculer(co.get(0), s)+this.calculer(co.get(1), s)
					+this.calculer(co.get(2), s)+this.calculer(co.get(3), s)+this.calculer(listy1, s)+this.calculer(listy2, s)
					+this.calculer(listy3, s)+this.calculer(listy4, s)+this.calculer(listy5, s)+this.calculer(listy6, s)+this.calculer(listy7, s);

			//Fill
			Fill f = joueur.getVictoryCarte().getFill();
			score=score+this.calculer(co.get(0), f)+this.calculer(co.get(1), f)
			      +this.calculer(co.get(2), f)+this.calculer(co.get(3), f)+this.calculer(listy1, f)+this.calculer(listy2, f)
			      +this.calculer(listy3, f)+this.calculer(listy4, f)+this.calculer(listy5, f)+this.calculer(listy6, f)+this.calculer(listy7, f);

			//Color
			Color c = joueur.getVictoryCarte().getColor();
			score=score+this.calculer(co.get(0), c)+this.calculer(co.get(1), c)
			      +this.calculer(co.get(2), c)+this.calculer(co.get(3), c)+this.calculer(listy1, c)+this.calculer(listy2, c)
			      +this.calculer(listy3, c)+this.calculer(listy4, c)+this.calculer(listy5, c)+this.calculer(listy6, c)+this.calculer(listy7, c);

		return score;
	}
	
	/**
	 * This method is used to calculate the score. 
	 * <p>This method determines the shape score of each row or column.
	 * @param list The row or column of the board to be calculated
	 * @param s The shape of the victory card to be calculated
	 * @return Returns the score for the shape of the row or column
	 */
	public int calculer(ArrayList<Carte> list,Shape s) {
		int count=0;
		int x=0;
		for(int i=0; i<list.size(); i++) {
			if(list.get(i) != null) {
				if(list.get(i).getShape().equals(s)) {
					count++;
					System.out.println("true shape");
				}else if(count<=1){
					count=0;
					System.out.println("false shape");
				}
			}
		}
		switch(count) {
			case 0:
				x=0;
				break;
			case 1:
				x=0;
				break;
			case 2:
				x=1;
				break;
			case 3:
				x=2;
				break;
			case 4:
				x=3;
				break;
			case 5:
				x=4;
				break;
			case 6:
				x=5;
				break;
			case 7:
			    x=6;
			    break;
		}
		return x;
	}

	/**
	 * This method is used to calculate the score. 
	 * <p>This method determines the fill score of each row or column.
	 * @param list The row or column of the board to be calculated
	 * @param f The fill of the victory card to be calculated
	 * @return Returns the score for the fill of the row or column
	 */
	public int calculer(ArrayList<Carte> list,Fill f) {
		int count=0;
		int x=0;
		for(int i=0; i<list.size(); i++) {
			if(list.get(i) != null) {
				if(list.get(i).getFill().equals(f)) {
					count++;
					System.out.println("true fill");
				}else if(count<=2){
					count=0;
					System.out.println("false fill");
				}
			}
		}
		switch(count) {
			case 0:
				x=0;
				break;
			case 1:
				x=0;
				break;
			case 2:
				x=0;
				break;
			case 3:
				x=3;
				break;
			case 4:
				x=4;
				break;
			case 5:
				x=5;
				break;
			case 6:
				x=6;
				break;
			case 7:
				x=7;
				break;
		}
		return x;

	}

	/**
	 * This method is used to calculate the score. 
	 * <p>This method determines the color score of each row or column.
	 * @param list The row or column of the board to be calculated
	 * @param color The color of the victory card to be calculated
	 * @return Returns the score for the color of the row or column
	 */
	public int calculer(ArrayList<Carte> list,Color color) {
		int count=0;
		int x=0;
		for(int i=0; i<list.size(); i++) {
			if(list.get(i) != null) {
				if(list.get(i).getColor().equals(color)) {
					count++;
					System.out.println("true color");
				}else if(count<=2){
					count=0;
					System.out.println("false color");
				}
			}
		}
		switch(count) {
			case 0:
				x=0;
				break;
			case 1:
				x=0;
				break;
			case 2:
				x=0;
				break;
			case 3:
				x=4;
				break;
			case 4:
				x=5;
				break;
			case 5:
				x=6;
				break;
			case 6:
				x=7;
				break;
			case 7:
				x=8;
				break;
		}
		return x;

	}

	/**
	 * This method defines the method of outputting the chessboard.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < co.size(); i++) {
			for (int j = 0; j < co.get(i).size(); j++) {
				if (co.get(i).get(j) != null) {
					sb.append(" (");
					sb.append(i);
					sb.append(",");
					sb.append(j);
					sb.append(")  ");
					sb.append(co.get(i).get(j).toString());
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * This class is the visitor. This method is used to make {@code TriangleTapisVisitor} visit (@code RegleClassicForTwo}.
	 * <p> Used to tell the player to calculate the score of the {@code RegleClassicForTwo} mode under the triangle board.
	 */
	@Override
	public void visit(RegleClassicForTwo regleclassicfortwo) {
		// TODO Auto-generated method stub
		System.out.println("Calculate the score for RegleClassicForTwo when TriangleTapis");
	}

	/**
	 * This class is the visitor. This method is used to make {@code TriangleTapisVisitor} visit (@code RegleAdvancedForTwo}.
	 * <p>Used to tell the player to calculate the score of the {@code RegleAdvancedForTwo} mode under the triangle board.
	 */
	@Override
	public void visit(RegleAdvancedForTwo regleadvancedfortwo) {
		// TODO Auto-generated method stub
		System.out.println("Calculate the score for RegleAdvancedForTwo when TriangleTapis");
	}

	/**
	 * This class is the visitor. This method is used to make {@code TriangleTapisVisitor} visit (@code RegleSimpleForTwo}.
	 * <p>Used to tell the player to calculate the score of the {@code RegleSimpleForTwo} mode under the Triangle board.
	 */
	@Override
	public void visit(RegleSimpleForTwo regleautrefortwo) {
		// TODO Auto-generated method stub
		System.out.println("Calculate the score for RegleAutreForTwo when TriangleTapis");
	}

	/**
	 * This class is the visitor. This method is used to make {@code TriangleTapisVisitor} visit (@code RegleClassicForThree}.
	 * <p>Used to tell the player to calculate the score of the {@code RegleClassicForThree} mode under the Triangle board.
	 */
	@Override
	public void visit(RegleClassicForThree regleclassicforthree) {
		// TODO Auto-generated method stub
		System.out.println("Calculate the score for RegleClassicForThree when TriangleTapis");
	}

	/**
	 * This class is the visitor. This method is used to make {@code TriangleTapisVisitor} visit (@code RegleComputerForTwo}.
	 * <p>Used to tell the player to calculate the score of the {@code RegleComputerForTwo} mode under the Triangle board.
	 */
	@Override
	public void visit(RegleComputerForTwo reglecomputerfortwo) {
		// TODO Auto-generated method stub
		System.out.println("Calculate the score for RegleComputerForTwo when TriangleTapis");
	}
	
}
