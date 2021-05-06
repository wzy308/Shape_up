package vue;

import java.awt.*;



import javax.swing.*;

import carte.Carte;
import carte.Game;
import carte.Joueur;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controleur.ControleurGame;

/**
 * A class of Vue which offers game interface for users and communicates with Controller {@code ControleurGame}
 * 
 * @author WU Zhongyang
 * @author GUAN Mingjun
 * @version %I%
 * @see GameInterface
 * @see Game
 * @since 13 January 2021
 */
public class GameInterface implements Observer{

	private JFrame frmShapeUp;
	private JPanel contentPane;
	private JPanel start;
	private JPanel mode;
	private JPanel content;
	private JPanel panel;
	private JPanel panel_2;
	private CardLayout card;
	
	private JLabel vic_card1;
	private JLabel vic_card2;
	private JLabel carteNow;
	private JLabel score1;
	private JLabel score2;
	
	private JButton btnOk;
	private JButton btnPlayCard;
	private JButton btnMoveCard;
	private JLabel lbText;
	
	private Thread t;
	
	private ArrayList<ArrayList<JLabel>> jbList;
	
	private Game game;
	private JButton btnMode1;
	private JButton btnMode2;

	private JButton btnFrom;
	private JButton btnTo;
	private JButton btnPlayAgain;
	private JButton btnMode3;
	private JButton btnMode4;
	private JButton btnMode5;
	private JPanel triangle;
	private JPanel triangletapis;
	private JLabel lb21;
	private JLabel lb12;
	private JLabel lb03;
	private JLabel lb31;
	private JLabel lb32;
	private JLabel lb33;
	private JLabel lb34;
	private JLabel lb35;
	private JLabel lb36;
	private JLabel lb22;
	private JLabel lb23;
	private JLabel lb24;
	private JLabel lb25;
	private JLabel lb13;
	private JLabel lb14;

	/**
	 * Function Main for initializing everything needed, including new object of Game,
	 * ConsoleVue, GameInterface, and the cycle of the game.
	 * Launch the application.
	 * @param args any arguments
	 */
	public static void main(String[] args) {
		Game g = new Game();
		ConsoleVue console = new ConsoleVue(g);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameInterface window = new GameInterface(g);
					window.frmShapeUp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	 	do {
	 		g.setGameSet(false);
	 		g.setModeChosed(false);
	 		g.setMode(0);
	 		g.waitForChooseMode();
			System.out.println("waitForChooseMode over");
			g.chooseModeRectangle(g.getMode());
		}while (g.isAgain()== true);

	}

	
    /**
     * This method is used for update the images of cards and hints showed in the interface graphic
     *  when this observer is notified by the Observable Game.
     * @param  o     the observable object.
     * @param  arg   an argument passed to the {@code notifyObservers}
     */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(game.isModeChosed()) {
			card.show(frmShapeUp.getContentPane(), "Content");
		}
		
		this.lbText.setText(game.getTexte());
		
		Iterator<Joueur> it = this.game.getJoueurs().iterator();
		while(it.hasNext()) {
			Joueur w =(Joueur)it.next();
			ImageIcon icon = new ImageIcon(GameInterface.class.getResource("/vue/"+w.getVictoryCarte().getPath()));
			this.setCardImage(icon, this.vic_card1);
			this.score1.setText("Score: "+w.getScore());
			
			w =(Joueur)it.next();
			icon = new ImageIcon(GameInterface.class.getResource("/vue/"+w.getVictoryCarte().getPath()));
			this.setCardImage(icon, this.vic_card2);
			this.score2.setText("Score: "+w.getScore());
		}
		ImageIcon icon = new ImageIcon(GameInterface.class.getResource("/vue/"+this.game.getJoueurNow().getCarteNow().getPath()));
		this.setCardImage(icon, this.carteNow);
		
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				Carte c = this.game.getTapis().getTapis().get(i).get(j);
				if(c==null) {
					this.jbList.get(i).get(j).setIcon(null);
				}else {
					ImageIcon ic = new ImageIcon(GameInterface.class.getResource("/vue/"+c.getPath()));
					this.setCardImage(ic, this.jbList.get(i).get(j));
				}
			}
		}
		
		if(game.isGameSet()==true ) {
			this.btnPlayAgain.setVisible(true);
			this.btnPlayCard.setVisible(false);
			this.btnMoveCard.setVisible(false);
		}else {
			this.btnPlayAgain.setVisible(false);
		}
		
	}


    /**
     * This is the constructor of the class {@code GameInterface}.
     * starts the initialization of frames, layouts , buttons , labels, etc.
     * adding this class to the observer list of the Observable {@code Game}
     * also create an object of {@code ControleurGame} and forms the MVC.
     * @param g The object of the class Game.
     */
	public GameInterface(Game g) {
		this.game=g;
		this.game.addObserver(this);
		initialize();
		ControleurGame cg = new ControleurGame(g, frmShapeUp, card, btnMode1, btnMode2, btnPlayCard, btnMoveCard, btnFrom, btnTo, btnOk, lbText, btnPlayAgain);
	}

    /**
     * This method gets the attribute frmShapeUp
     * @return frmShapeUp type JFrame, the main frame of the interface graphic.
     */
	public JFrame getMainFrame() {
		return this.frmShapeUp;
	}
	
    /**
     * This method sets up the image of the JLabel
     * @param i type ImageIcon leads to the file of the image.
     * @param jl type JLabel which is the label whose image needs to be changed      
     */
	public void setCardImage(ImageIcon i,JLabel jl) {
		i.setImage(i.getImage().getScaledInstance(jl.getWidth(), jl.getHeight(),i.getImage().SCALE_SMOOTH));
		jl.setIcon(i);
	}
	
	/**
	 * This method initialize the contents of the frame.
	 */
	private void initialize() {
		frmShapeUp = new JFrame();
		frmShapeUp.setTitle("Shape Up");
		frmShapeUp.setBounds(100, 100, 823, 900);
		frmShapeUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		frmShapeUp.setContentPane(contentPane);
		contentPane.setLayout(card);
		
		start = new JPanel();
		start.setBounds(0, 0, 630, 730);
		
		mode = new JPanel();
		mode.setBounds(0, 0, 630, 730);
		
		content = new JPanel();
		content.setBounds(0, 740, 708, 123);
		content.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(26, 7, 396, 441);
		panel.setLayout(new GridLayout(7, 7, 1, 1));
		content.add(panel);
		
		panel_2 = new JPanel();
		panel_2.setBounds(0, 453, 632, 217);
		content.add(panel_2);
		panel_2.setLayout(null);
		
		lbText = new JLabel("");
		lbText.setBackground(Color.WHITE);
		lbText.setVerticalAlignment(SwingConstants.TOP);
		lbText.setFont(new Font("Calibri", Font.PLAIN, 20));
		lbText.setBounds(10, 10, 237, 176);
		panel_2.add(lbText);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Calibri", Font.PLAIN, 20));
		btnExit.setBounds(471, 123, 150, 40);
		panel_2.add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		btnOk = new JButton("OK!");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(game.ok());
			}
		});
		btnOk.setFont(new Font("Calibri", Font.PLAIN, 20));
		btnOk.setBounds(471, 77, 150, 40);
		panel_2.add(btnOk);
		
		btnMoveCard = new JButton("Move Card");
		btnMoveCard.setFont(new Font("Calibri", Font.PLAIN, 20));
		btnMoveCard.setBounds(303, 77, 150, 40);
		panel_2.add(btnMoveCard);
		
		btnPlayCard = new JButton("Play Card");
		btnPlayCard.setFont(new Font("Calibri", Font.PLAIN, 20));
		btnPlayCard.setBounds(303, 27, 150, 40);
		panel_2.add(btnPlayCard);
		
		btnFrom = new JButton("From");
		btnFrom.setFont(new Font("Calibri", Font.PLAIN, 22));
		btnFrom.setBounds(257, 127, 93, 33);
		panel_2.add(btnFrom);
		btnFrom.setVisible(false);
		
		
		btnTo = new JButton("To");
		btnTo.setFont(new Font("Calibri", Font.PLAIN, 22));
		btnTo.setBounds(360, 127, 93, 33);
		panel_2.add(btnTo);
		btnTo.setVisible(false);
		
		card = new CardLayout(0, 0);
		frmShapeUp.getContentPane().setLayout(card);
		frmShapeUp.getContentPane().add(start, "Start");
		
		JPanel tapis = new JPanel();
		contentPane.add(tapis, "name_53753586430600");
		tapis.setLayout(null);
		
		JButton btnTapis1 = new JButton("Rectangle");
		btnTapis1.setFont(new Font("Calibri", Font.PLAIN, 22));
		btnTapis1.setBounds(299, 259, 254, 58);
		tapis.add(btnTapis1);
		
		JButton btnTapis2 = new JButton("Triangle");
		btnTapis2.setFont(new Font("Calibri", Font.PLAIN, 22));
		btnTapis2.setBounds(299, 384, 254, 58);
		tapis.add(btnTapis2);
		
		triangle = new JPanel();
		contentPane.add(triangle, "name_8679784657500");
		triangle.setLayout(null);
		
		triangletapis = new JPanel();
		triangletapis.setBounds(41, 20, 486, 400);
		triangle.add(triangletapis);
		triangletapis.setLayout(null);
		
		JLabel lb30 = new JLabel("");
		lb30.setOpaque(true);
		lb30.setBackground(Color.LIGHT_GRAY);
		lb30.setBounds(0, 286, 66, 73);
		triangletapis.add(lb30);
		
		lb21 = new JLabel("");
		lb21.setOpaque(true);
		lb21.setBackground(Color.LIGHT_GRAY);
		lb21.setBounds(68, 211, 66, 73);
		triangletapis.add(lb21);
		
		lb12 = new JLabel("");
		lb12.setOpaque(true);
		lb12.setBackground(Color.LIGHT_GRAY);
		lb12.setBounds(136, 136, 66, 73);
		triangletapis.add(lb12);
		
		lb03 = new JLabel("");
		lb03.setOpaque(true);
		lb03.setBackground(Color.LIGHT_GRAY);
		lb03.setBounds(204, 61, 66, 73);
		triangletapis.add(lb03);
		
		lb31 = new JLabel("");
		lb31.setOpaque(true);
		lb31.setBackground(Color.LIGHT_GRAY);
		lb31.setBounds(68, 286, 66, 73);
		triangletapis.add(lb31);
		
		lb32 = new JLabel("");
		lb32.setOpaque(true);
		lb32.setBackground(Color.LIGHT_GRAY);
		lb32.setBounds(136, 286, 66, 73);
		triangletapis.add(lb32);
		
		lb33 = new JLabel("");
		lb33.setOpaque(true);
		lb33.setBackground(Color.LIGHT_GRAY);
		lb33.setBounds(204, 286, 66, 73);
		triangletapis.add(lb33);
		
		lb34 = new JLabel("");
		lb34.setOpaque(true);
		lb34.setBackground(Color.LIGHT_GRAY);
		lb34.setBounds(272, 286, 66, 73);
		triangletapis.add(lb34);
		
		lb35 = new JLabel("");
		lb35.setOpaque(true);
		lb35.setBackground(Color.LIGHT_GRAY);
		lb35.setBounds(340, 286, 66, 73);
		triangletapis.add(lb35);
		
		lb36 = new JLabel("");
		lb36.setOpaque(true);
		lb36.setBackground(Color.LIGHT_GRAY);
		lb36.setBounds(408, 286, 66, 73);
		triangletapis.add(lb36);
		
		lb22 = new JLabel("");
		lb22.setOpaque(true);
		lb22.setBackground(Color.LIGHT_GRAY);
		lb22.setBounds(136, 211, 66, 73);
		triangletapis.add(lb22);
		
		lb23 = new JLabel("");
		lb23.setOpaque(true);
		lb23.setBackground(Color.LIGHT_GRAY);
		lb23.setBounds(204, 211, 66, 73);
		triangletapis.add(lb23);
		
		lb24 = new JLabel("");
		lb24.setOpaque(true);
		lb24.setBackground(Color.LIGHT_GRAY);
		lb24.setBounds(272, 211, 66, 73);
		triangletapis.add(lb24);
		
		lb25 = new JLabel("");
		lb25.setOpaque(true);
		lb25.setBackground(Color.LIGHT_GRAY);
		lb25.setBounds(340, 211, 66, 73);
		triangletapis.add(lb25);
		
		lb13 = new JLabel("");
		lb13.setOpaque(true);
		lb13.setBackground(Color.LIGHT_GRAY);
		lb13.setBounds(204, 136, 66, 73);
		triangletapis.add(lb13);
		
		lb14 = new JLabel("");
		lb14.setOpaque(true);
		lb14.setBackground(Color.LIGHT_GRAY);
		lb14.setBounds(272, 136, 66, 73);
		triangletapis.add(lb14);
		frmShapeUp.getContentPane().add(mode, "Mode");
		mode.setLayout(null);
		
		btnMode1 = new JButton("Two people classic");
		btnMode1.setBounds(271, 142, 274, 61);
		btnMode1.setFont(new Font("Calibri", Font.PLAIN, 22));
		mode.add(btnMode1);
		
		btnMode2 = new JButton("One people VS CPU");
		btnMode2.setBounds(271, 396, 274, 61);
		btnMode2.setFont(new Font("Calibri", Font.PLAIN, 22));
		mode.add(btnMode2);
		frmShapeUp.getContentPane().add(content,"Content");
		
		btnMode3 = new JButton("Two people advanced");
		btnMode3.setBounds(271, 224, 274, 61);
		btnMode3.setFont(new Font("Calibri", Font.PLAIN, 22));
		mode.add(btnMode3);
		
		btnMode4 = new JButton("Two people simple");
		btnMode4.setBounds(271, 309, 274, 61);
		btnMode4.setFont(new Font("Calibri", Font.PLAIN, 22));
		mode.add(btnMode4);
		
		btnMode5 = new JButton("Three people classic");
		btnMode5.setBounds(271, 481, 274, 61);
		btnMode5.setFont(new Font("Calibri", Font.PLAIN, 22));
		mode.add(btnMode5);
		
		JButton startButton = new JButton("Start Game");
		startButton.setBounds(264, 302, 243, 59);
		startButton.setFont(new Font("Calibri", Font.PLAIN, 29));
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(frmShapeUp.getContentPane(), "Mode");
				btnPlayAgain.setVisible(false);
				btnPlayCard.setVisible(true);
				btnMoveCard.setVisible(true);
			}
		});
		start.setLayout(null);
		start.add(startButton);
		
		btnPlayAgain = new JButton("Play Again");
		btnPlayAgain.setFont(new Font("Calibri", Font.PLAIN, 20));
		btnPlayAgain.setBounds(471, 27, 150, 40);
		panel_2.add(btnPlayAgain);
		btnPlayAgain.setVisible(false);
		
		jbList = new ArrayList<>();
		for(int i = 0; i < 7; i++){
			jbList.add(new ArrayList<JLabel>());
			for(int j = 0; j < 7; j++){
				jbList.get(i).add(new JLabel());
				JLabel temp = jbList.get(i).get(j);
				temp.setBackground(Color.LIGHT_GRAY);
				temp.setOpaque(true);
				panel.add(temp);
				temp.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						for(int i = 0; i < 7; i++){
							for(int j = 0; j < 7; j++){
								jbList.get(i).get(j).setBorder(null);
							}
						}
						temp.setBorder(BorderFactory.createLineBorder(Color.red, 3));
						for(int i = 0; i < 7; i++){
							for(int j = 0; j < 7; j++){
								if(jbList.get(i).get(j)==temp) {
									game.setxChosen(i);
									game.setyChosen(j);
								}
							}
						}
					}
				});
			}
		}
		
		JLabel player1 = new JLabel("Your Victory Card");
		player1.setFont(new Font("Calibri", Font.PLAIN, 20));
		player1.setBounds(651, 38, 158, 47);
		content.add(player1);
		
		vic_card1 = new JLabel("");
		vic_card1.setBackground(Color.WHITE);
		vic_card1.setBounds(651, 81, 83, 104);
		vic_card1.setOpaque(true);
		content.add(vic_card1);
		
		JLabel player2 = new JLabel("player2");
		player2.setFont(new Font("Calibri", Font.PLAIN, 20));
		player2.setBounds(660, 230, 114, 34);
		content.add(player2);
		
		vic_card2 = new JLabel("");
		vic_card2.setFont(new Font("Calibri", Font.PLAIN, 20));
		vic_card2.setBackground(Color.WHITE);
		vic_card2.setBounds(651, 274, 83, 104);
		vic_card2.setOpaque(true);
		content.add(vic_card2);
		
		score1 = new JLabel("score1");
		score1.setFont(new Font("Calibri", Font.PLAIN, 20));
		score1.setBounds(661, 195, 83, 25);
		content.add(score1);
		
		score2 = new JLabel("score2");
		score2.setFont(new Font("Calibri", Font.PLAIN, 20));
		score2.setBounds(660, 388, 83, 25);
		content.add(score2);
		
		carteNow = new JLabel("");
		carteNow.setBackground(Color.WHITE);
		carteNow.setBounds(651, 423, 83, 104);
		carteNow.setOpaque(true);
		content.add(carteNow);
		
		JLabel lblNewLabel_1 = new JLabel("Your Card Now");
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(656, 537, 143, 34);
		content.add(lblNewLabel_1);
		

	}
}
