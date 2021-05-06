package designPattern;

import carte.*;
import java.util.*;

/**
 * A class representing the rectangular chessboard in the game Shape up. This class implements Visitor.
 * 
 * <p>Class {@code RectangleTapisVisitor} mainly contains the following functions: calculate the score, judge whether the placed card position is legal, judge whether it meets the adjacency rules, and visit each game mode,etc.
 * 
 * For exemple:
 * <pre>
 *     RectangleTapisVisitor tapis = new RectangleTapisVisitor();
 *     tapis.calculerScore(joueur);
 * </pre>
 * 
 * @author WU Zhongyang
 * @author GUAN Mingjun
 * @version %I%
 * @since 13 January 2021
 */
public class RectangleTapisVisitor implements Visitor{

		private ArrayList<ArrayList<Carte>> co;
		int top=1,bottom=1,left=1,right=1,length=1,width=1;

		/**
		 * This is the constructor of the class {@code RectangleTapisVisitor}.
		 * 
		 * <p>This method initializes a 12*12 chessboard, where the {@code ArrayList} contains {@code ArrayList}, and initializes each element to null.
		 */
		public RectangleTapisVisitor() {
			co= new ArrayList<>();
			for(int i=0;i<12;i++){
				co.add(new ArrayList<Carte>());
				for(int j=0;j<12;j++){
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
			for(int i=0;i<12;i++){
				c.add(new ArrayList<Carte>());
				for(int j=0;j<12;j++){
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
	        	//System.out.println("There is already a card here.");
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
		 * <p> The method first determines the length and width of the current chessboard.
		 * <p> Under the premise that the position of the card to be placed is empty and the adjacent rules are met, judge whether the coordinates exceed the 3*5 chessboard range.
		 * 
		 * @param x The abscissa of the card to be placed
		 * @param y The ordinate of the card to be placed
		 * @return If the coordinates are legal, then return {@code true}, else {@code false}
		 */
		public boolean isLegalPlay(int x, int y){
			boolean b=false;

			for (int i = 0; i < co.size(); i++) {
				for (int j = 0; j < co.get(i).size(); j++) {
					if(co.get(i).get(j)!=null){

						if(i<left){
							left=i;
						} else if(i>right){
							right=i;
						}
						if(j<bottom){
							bottom=j;
						}else if(j>top){
							top=j;
						}
					}
				}
			}
			length = right-left+1;
			width = top-bottom+1;

			if(hasCarte(x,y)==false && isAdjacent(x,y)==true){
				if(length == 4 && width == 3){
					if(y > top || y < bottom){
						b=false;
					}else{
						b=true;
					}
				}else if(length == 3 && width == 4){
					if(x>right||x<left){
						b=false;
					}else{
						b=true;
					}
				}else if(length == 5 && width == 3){
					if(y > top || y < bottom || x > right || x < left){
						b=false;
					}else{
						b=true;
					}
				}else if(length == 3 && width == 5){
					if (y > top || y < bottom || x > right || x < left){
						b=false;
					}else{
						b=true;
					}
				}else if(length ==5){
					if(x>right||x<left){
						b=false;
					}else{
						b=true;
					}
				}else if(width ==5) {
					if (y > top || y < bottom) {
						b = false;
					}else{
						b=true;
					}
				}else{
					b=true;
				}

			}else{
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

			for (int i = 0; i < co.size(); i++) {
				for (int j = 0; j < co.get(i).size(); j++) {
					if(co.get(i).get(j)!=null){

						if(i<this.left){
							this.left=i;
						} else if(i>this.right){
							this.right=i;
						}
						if(j<this.bottom){
							this.bottom=j;
						}else if(j>this.top){
							this.top=j;
						}
					}
				}
			}
			this.length = right-left+1;
			this.width = top-bottom+1;

			if(hasCarte(x,y)==false && isAdjacent(x,y)==true){
				if(length == 4 && width == 3){
					if(y > top || y < bottom){
						b=false;
					}else{
						b=true;
					}
				}else if(length == 3 && width == 4){
					if(x>right||x<left){
						b=false;
					}else{
						b=true;
					}
				}else if(length == 5 && width == 3){
					if(y > top || y < bottom || x > right || x < left){
						b=false;
					}else{
						b=true;
					}
				}else if(length == 3 && width == 5){
					if (y > top || y < bottom || x > right || x < left){
						b=false;
					}else{
						b=true;
					}
				}else if(length ==5){
					if(x>right||x<left){
						b=false;
					}else{
						b=true;
					}
				}else if(width ==5) {
					if (y > top || y < bottom) {
						b = false;
					}else{
						b=true;
					}
				}else{
					b=true;
				}

			}else{
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
//			temp=null;
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
		 * This method is used to determine the coordinates of the upper-left card on the board.
		 * 
		 * <p> This method is used to calculate the score.
		 * @return the array which contains the coordinates of the upper-left card on the board
		 */
		public int[] coinHautGauche() {
			int a = 0, b = 0;
			boolean flag = false;
			for(int i = 0; i < 12 && !flag; i++) {
				for(int j = 0; j < 12; j++) {
					if (co.get(i).get(j) != null) {
						a = i;
						b = j;
						flag = true;
						break;
					}
				}
			}
			return new int[] {a,b};
		}

		/**
		 * This method is used to determine whether the length of the board is 5 or 3.
		 * 
		 * <p> This method is used to calculate the score.
		 * @return the length of the board
		 */
		public int tapisLength() {
			int count = 0;
			int a = this.coinHautGauche()[0];
			for (int j=0; j<12; j++) {
				if(co.get(a).get(j) != null) {
					count ++;
				}
			}
			return count;
		}

		/**
		 * This method is used to calculate the player's score.
		 * 
		 * <p>This method first creates the collection {@code ArrayList} of each column.
		 * <p>This method is divided into two cases, one has a board with a length of 5 and the other with a length of 3. The scores are calculated separately.
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
			int a = this.coinHautGauche()[0];
			int b = this.coinHautGauche()[1];

			if(this.tapisLength() > 3) {
				listy1.add(co.get(a).get(b));
				listy1.add(co.get(a+1).get(b));
				listy1.add(co.get(a+2).get(b));
				listy2.add(co.get(a).get(b+1));
				listy2.add(co.get(a+1).get(b+1));
				listy2.add(co.get(a+2).get(b+1));
				listy3.add(co.get(a).get(b+2));
				listy3.add(co.get(a+1).get(b+2));
				listy3.add(co.get(a+2).get(b+2));
				listy4.add(co.get(a).get(b+3));
				listy4.add(co.get(a+1).get(b+3));
				listy4.add(co.get(a+2).get(b+3));
				listy5.add(co.get(a).get(b+4));
				listy5.add(co.get(a+1).get(b+4));
				listy5.add(co.get(a+2).get(b+4));

				//Shape
				Shape s = joueur.getVictoryCarte().getShape();
				score=score+this.calculer(co.get(a), s)+this.calculer(co.get(a+1), s)
						+this.calculer(co.get(a+2), s)+this.calculer(listy1, s)+this.calculer(listy2, s)
						+this.calculer(listy3, s)+this.calculer(listy4, s)+this.calculer(listy5, s);

				//Fill
				Fill f = joueur.getVictoryCarte().getFill();
				score=score+this.calculer(co.get(a), f)+this.calculer(co.get(a+1), f)
						+this.calculer(co.get(a+2), f)+this.calculer(listy1, f)+this.calculer(listy2, f)
						+this.calculer(listy3, f)+this.calculer(listy4, f)+this.calculer(listy5, f);

				//Color
				Color c = joueur.getVictoryCarte().getColor();
				score=score+this.calculer(co.get(a), c)+this.calculer(co.get(a+1), c)
						+this.calculer(co.get(a+2), c)+this.calculer(listy1, c)+this.calculer(listy2, c)
						+this.calculer(listy3, c)+this.calculer(listy4, c)+this.calculer(listy5, c);
			}

			else {
				listy1.add(co.get(a).get(b));
				listy1.add(co.get(a+1).get(b));
				listy1.add(co.get(a+2).get(b));
				listy1.add(co.get(a+3).get(b));
				listy1.add(co.get(a+4).get(b));
				listy2.add(co.get(a).get(b+1));
				listy2.add(co.get(a+1).get(b+1));
				listy2.add(co.get(a+2).get(b+1));
				listy2.add(co.get(a+3).get(b+1));
				listy2.add(co.get(a+4).get(b+1));
				listy3.add(co.get(a).get(b+2));
				listy3.add(co.get(a+1).get(b+2));
				listy3.add(co.get(a+2).get(b+2));
				listy3.add(co.get(a+3).get(b+2));
				listy3.add(co.get(a+4).get(b+2));

				//Shape
				Shape s = joueur.getVictoryCarte().getShape();
				score=score+this.calculer(co.get(a), s)+this.calculer(co.get(a+1), s)
						+this.calculer(co.get(a+2), s)+this.calculer(co.get(a+3), s)+this.calculer(co.get(a+4), s)
						+this.calculer(listy1, s)+this.calculer(listy2, s)+this.calculer(listy3, s);

				//Fill
				Fill f = joueur.getVictoryCarte().getFill();
				score=score+this.calculer(co.get(a), f)+this.calculer(co.get(a+1), f)
						+this.calculer(co.get(a+2), f)+this.calculer(co.get(a+3), f)+this.calculer(co.get(a+4), f)
						+this.calculer(listy1, f)+this.calculer(listy2, f)+this.calculer(listy3, f);

				//Color
				Color c = joueur.getVictoryCarte().getColor();
				score=score+this.calculer(co.get(a), c)+this.calculer(co.get(a+1), c)
						+this.calculer(co.get(a+2), c)+this.calculer(co.get(a+3), c)+this.calculer(co.get(a+4), c)
						+this.calculer(listy1, c)+this.calculer(listy2, c)+this.calculer(listy3, c);
			}
			return score;
		}

		/**
		 * This method is used to calculate the score. 
		 * <p>This method determines the shape score of each row or column.
		 * @param list The row or column of the board to be calculated
		 * @param s The shape of the victory card to be calculated
		 * @return Returns the score for the shape of the row or column
		 */
		public int calculer(ArrayList<Carte> list,Shape s) {//
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
			}
			return x;

		}


		/**
		 * This method is used in the graphical interface to refresh the board.
		 * <p> This method forces the card placed by the player to move to the corresponding position on the board with 1, 1 as the upper left corner.
		 */
		public void refresh() {
			int a = 1;
			int b = 1;
			int xmin = 0;
			int ymin = 0;
			boolean flag = false;
			ArrayList<ArrayList<Carte>> temp = this.newCo();
			for (int i = 0; i < co.size(); i++) {
				for (int j = 0; j < co.get(i).size(); j++) {
					if (co.get(i).get(j) != null) {
						if(flag == false) {
							xmin = i;
							ymin = j;
							flag = true;
						}else {
							if(i<xmin) {
								xmin=i;
							}
							if(j<ymin) {
								ymin=j;
							}
						}
					}
				}
			}
			for (int i = xmin; i < xmin+5; i++) {
				for (int j = ymin; j < ymin+5; j++) {
					temp.get(a).set(b, co.get(i).get(j));
					b++;
				}
				a++;
				b=1;
			}
			this.co=temp;
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
		 * This class is the visitor. This method is used to make {@code RectangleTapisVisitor} visit (@code RegleClassicForTwo}.
		 * <p>Used to tell the player to calculate the score of the {@code RegleClassicForTwo}  mode under the rectangular board.
		 */
		@Override
		public void visit(RegleClassicForTwo regleclassicfortwo) {
			// TODO Auto-generated method stub
			System.out.println("Calculate the score for RegleClassicForTwo when RectangleTapis");
		}

		/**
		 * This class is the visitor. This method is used to make {@code RectangleTapisVisitor} visit (@code RegleAdvancedForTwo}.
		 * <p>Used to tell the player to calculate the score of the {@code RegleAdvancedForTwo} mode under the rectangular board.
		 */
		@Override
		public void visit(RegleAdvancedForTwo regleadvancedfortwo) {
			// TODO Auto-generated method stub
			System.out.println("Calculate the score for RegleAdvancedForTwo when RectangleTapis");
		}

		/**
		 * This class is the visitor. This method is used to make {@code RectangleTapisVisitor} visit (@code RegleSimpleForTwo}.
		 * <p>Used to tell the player to calculate the score of the {@code RegleSimpleForTwo} mode under the rectangular board.
		 */
		@Override
		public void visit(RegleSimpleForTwo regleautrefortwo) {
			// TODO Auto-generated method stub
			System.out.println("Calculate the score for RegleAutreForTwo when RectangleTapis");
		}

		/**
		 * This class is the visitor. This method is used to make {@code RectangleTapisVisitor} visit (@code RegleClassicForThree}.
		 * <p>Used to tell the player to calculate the score of the {@code RegleClassicForThree} mode under the rectangular board.
		 */
		@Override
		public void visit(RegleClassicForThree regleclassicforthree) {
			// TODO Auto-generated method stub
			System.out.println("Calculate the score for RegleClassicForThree when RectangleTapis");
		}

		/**
		 * This class is the visitor. This method is used to make {@code RectangleTapisVisitor} visit (@code RegleComputerForTwo}.
		 * <p>Used to tell the player to calculate the score of the {@code RegleComputerForTwo} mode under the rectangular board.
		 */
		@Override
		public void visit(RegleComputerForTwo reglecomputerfortwo) {
			// TODO Auto-generated method stub
			System.out.println("Calculate the score for RegleComputerForTwo when RectangleTapis");
		}	
	}