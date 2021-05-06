package controleur;

import carte.Game;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * A class of Controller which communicates between Model and different View from users
 * 
 * @author WU Zhongyang
 * @author GUAN Mingjun
 * @version %I%
 * @see Game
 * @since 13 January 2021
 */
public class ControleurGame {
	private Game game;
	private JFrame frmShapeUp;
	private CardLayout card;
	private JButton btnMode1;
	private JButton btnMode2;
	private JButton btnPlayCard;
	private JButton btnMoveCard;
	private JButton btnFrom;
	private JButton btnTo;
	private JButton btnOk;
	private JLabel lbText;
	private JButton btnPlayAgain;
	
    /**
     * This is the constructor of the class {@code ControleurGame}.
     * @param g The Object of the class Game from package carte.
     * @param frmShapeUp Frame of interface graphic.
     * @param card CardLayout from interface graphic.
     * @param btnMode1 Button for choosing mode 1 from interface graphic.
     * @param btnMode2 Button for choosing mode 2 from interface graphic.
     * @param btnPlayCard Button for placing the card in interface graphic.
     * @param btnMoveCard Button for moving the card in interface graphic.
     * @param btnFrom Button for confirming the departure of the card you want to move in interface graphic.
     * @param btnTo Button for confirming the arrival of the card you want to move to in interface graphic.
     * @param btnOk Button for confirming the end of your round.
     * @param lbText Label for hints.
     * @param btnPlayAgain Button for playing again.
     */
	public ControleurGame(Game g, JFrame frmShapeUp, CardLayout card, JButton btnMode1, 
			JButton btnMode2, JButton btnPlayCard, JButton btnMoveCard, JButton btnFrom,
			JButton btnTo, JButton btnOk ,JLabel lbText ,JButton btnPlayAgain) {
		this.game=g;
		btnMode1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(game.isModeChosed()==false) {
					card.show(frmShapeUp.getContentPane(), "Content");
					game.setMode(1);
					game.setModeChosed(true);
					System.out.println("Button: mode chosed set true");
				}else {
					card.show(frmShapeUp.getContentPane(), "Content");
				}
			}
		});
		btnMode2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(game.isModeChosed()==false) {
					card.show(frmShapeUp.getContentPane(), "Content");
					game.setMode(2);
					game.setModeChosed(true);
				}else {
					card.show(frmShapeUp.getContentPane(), "Content");
				}
			}
		});
		btnPlayCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println(g.playCard(game.getxChosen(), game.getyChosen(), g.getJoueurNow()));
				
			}
		});
		btnMoveCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 btnMoveCard.setVisible(false);
				 btnFrom.setVisible(true);
				 btnTo.setVisible(true);
			}
		});
		btnFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.setxFrom(game.getxChosen());
				game.setyFrom(game.getyChosen());
			}
		});
		btnTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.setxTo(game.getxChosen());
				game.setyTo(game.getyChosen());
				System.out.println(game.moveCard(game.getxFrom(), game.getyFrom(), game.getxTo(), game.getyTo()));
				 btnMoveCard.setVisible(true);
				 btnFrom.setVisible(false);
				 btnTo.setVisible(false);
			}
		});
		btnPlayAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(game.isAgainSet()==false) {
					card.show(frmShapeUp.getContentPane(), "Start");
					game.setAgain(true);
					game.setAgainSet(true);
					btnPlayAgain.setVisible(false);
					btnPlayCard.setVisible(true);
					btnMoveCard.setVisible(true);
				}else {
					card.show(frmShapeUp.getContentPane(), "Start");
					btnPlayAgain.setVisible(false);
					btnPlayCard.setVisible(true);
					btnMoveCard.setVisible(true);
				}
			}
		});
	}
}
