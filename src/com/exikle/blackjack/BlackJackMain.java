package com.exikle.blackjack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

public class BlackJackMain extends JFrame implements ActionListener {

	int[][] cardspicked;

	int[][] deck;

	int[] check;

	int card, suit, count = 0, turn = 0, playercounter = 0,
			dealercounter = 0, ptot = 0, dtot = 0;

	String c, s, out = "Status";

	Border brdr1, brdr2, brdr3;

	JPanel pnl1, pnl2;

	JButton btnHit, btnStand, btnShuffle, test;

	JLabel lblTop;

	ClassLoader cl = BlackJackGUI1.class.getClassLoader();

	URL imageURL = cl.getResource("cards.png");

	Image image;

	MyDrawPanel p1;

	Font f1 = new Font("Book Antiqua", Font.BOLD, 40), f2 = new Font(
			"Comic Sans", Font.BOLD, 20);

	Color c1 = new Color(0, 133, 0), c2 = new Color(82, 0, 0),
			c3 = new Color(152, 0, 0), c4 = new Color(62, 0, 0),
			c5 = new Color(0, 92, 0);

	public static void main(String[] args) {
		new BlackJackMain();
	}

	public BlackJackMain() {
		super("BlackJack");
		this.setResizable(false);
		brdr1 = new CompoundBorder(LineBorder.createGrayLineBorder(),
				BorderFactory.createLineBorder(Color.white));
		brdr3 = new CompoundBorder(LineBorder.createGrayLineBorder(),
				BorderFactory.createLineBorder(Color.black));

		brdr2 = new CompoundBorder(brdr1,
				BorderFactory.createLoweredBevelBorder());
		p1 = new MyDrawPanel();
		pnl1 = new JPanel();
		pnl2 = new JPanel();
		cardspicked = new int[14][5];
		deck = new int[52][4];

		btnShuffle = new RoundedCornerButton("Deal");
		btnShuffle.addActionListener(this);
		btnShuffle.setOpaque(false);
		btnShuffle.setContentAreaFilled(false);

		btnHit = new RoundedCornerButton("Hit");
		btnHit.addActionListener(this);
		btnHit.setEnabled(false);

		btnStand = new RoundedCornerButton("Stand");
		btnStand.addActionListener(this);
		btnStand.setEnabled(false);

		lblTop = new JLabel("Blackjack");
		lblTop.setFont(f1);
		Icon ic1 = new ImageIcon("btnPress.png");

		if (imageURL != null) {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			image = toolkit.createImage(imageURL);
		}
		pnl1.setLayout(new GridLayout(1, 3));

		btnShuffle.setPressedIcon(ic1);

		p1.add(btnShuffle);
		btnShuffle.setBounds(150, 435, 75, 75);

		p1.add(btnHit);
		btnHit.setBounds(50, 435, 75, 75);
		btnHit.setOpaque(false);
		p1.add(btnStand);
		btnStand.setBounds(250, 435, 75, 75);
		btnStand.setOpaque(false);
		p1.setLayout(null);

		pnl2.add(lblTop);
		pnl2.setBackground(c1);

		this.setLayout(new BorderLayout());
		this.add(p1, BorderLayout.CENTER);
		this.add(pnl2, BorderLayout.NORTH);
		this.setSize(400, 600);
		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnShuffle) {
			for (int x = 0; x < 52; x++) {
				for (int z = 0; z < 4; z++) {
					deck[x][z] = 0;
				}
			}
			for (int x = 0; x < 14; x++) {
				for (int z = 0; z < 5; z++) {
					cardspicked[x][z] = 0;
				}
			}
			turn = 1;
			btnHit.setEnabled(true);
			btnStand.setEnabled(true);
			playercounter = 2;
			dealercounter = 1;
			ptot = 0;

			shuffle();
			out = "Players Turn";
			Checkwin();
			Checkwin();
			// p1.remove(btnShuffle);
			repaint();
		} else if (e.getSource() == btnHit) {
			if (turn == 1) {
				if (playercounter != 5) {
					playercounter++;
					repaint();
					ptot = 0;
					for (int z = 0; z < playercounter; z++)
						ptot += deck[z + 5][2];
					if (ptot > 21) {
						for (int z = 0; z < playercounter; z++) {
							if (deck[z + 5][2] == 11) {
								deck[z + 5][2] = 1;
							}
						}
					}
					ptot = 0;
					for (int z = 0; z < playercounter; z++) {
						ptot += deck[z + 5][2];
					}
				}
			}
			Checkwin();
			Checkwin();
		} else if (e.getSource() == btnStand) {
			if (turn == 1) {
				turn = 2;
			}
			do {
				if (turn == 2) {
					btnHit.setEnabled(false);
					btnStand.setEnabled(false);
					if (dealercounter != 5) {
						dealercounter++;
						repaint();
						dtot = 0;
						for (int z = 0; z < dealercounter; z++)
							dtot += deck[z][2];
						if (dtot > 21) {
							for (int z = 0; z < dealercounter; z++) {
								if (deck[z][2] == 11) {
									deck[z][2] = 1;
								}
							}
						}
						dtot = 0;
						for (int z = 0; z < dealercounter; z++) {
							dtot += deck[z + 5][2];
						}
						System.out.println(dtot);
					}
				}
			} while (dtot < 17);
			Checkwin();
			Checkwin();
		}
	}

	class MyDrawPanel extends JPanel {

		public void paintComponent(Graphics g) {
			Graphics2D g7 = (Graphics2D) g;
			g7.setColor(c1);
			g7.fillRect(0, 0, 400, 600);
			g7.setColor(c5);

			g7.fillRect(0, 400, 400, 200);

			g7.setColor(Color.BLACK);
			g7.drawRect(125, 25, 80, 125);
			g7.drawImage(image, 150, 25, 230, 150, 2 * 144, 4 * 194,
					(2 * 144) + 144, (4 * 194) + 194, this);
			g7.drawRect(125, 200, 80, 125);
			g7.drawImage(image, 125, 200, 205, 325, 2 * 144, 4 * 194,
					(2 * 144) + 144, (4 * 194) + 194, this);

			Graphics2D g9 = (Graphics2D) g;
			g9.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g9.setColor(Color.BLACK);
			g9.fillArc(-50, 295, 500, 150, 0, -180);
			g9.setColor(c2);
			g9.fillArc(-50, 300, 500, 145, 0, -180);
			g9.setColor(c4);
			g9.fillArc(-50, 300, 500, 130, 0, -180);
			g9.setColor(c3);
			g9.fillArc(-50, 300, 500, 125, 0, -180);

			g7.setColor(Color.BLACK);
			g9.fillArc(0, 350, 400, 30, 0, -180);
			g9.setColor(c1);
			g9.fillArc(0, 350, 400, 25, 0, -180);

			Graphics2D g4 = (Graphics2D) g;
			// This line takes away the jaggedness and smooths out the lines
			g4.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			// drawGrid Codes
			//
			// g4.setColor(Color.BLACK);
			// for(int x=0;x<=1000;x+=25)
			// {
			// g4.drawLine(x,0,x,800);
			// g4.drawString(""+x,x,20);
			// }
			//
			// for(int y=0;y<=800;y+=25)
			// {
			// g4.drawLine(0,y,1000,y);
			// g4.drawString(""+y,0,y+20);
			// }

			Graphics2D g2 = (Graphics2D) g;
			for (int z = 0; z < dealercounter; z++) {
				g2.drawImage(image, (150 + z * 25), 25 + 25 * z,
						(230 + z * 25), 150 + 25 * z, deck[z][0],
						deck[z][1], deck[z][0] + 144,
						deck[z][1] + 194, this);
				// g2.drawImage(image, (125+(z+1)*25), 25, (205+(z+1)*25), 150, 2*144, 4*194, (2*144)+144, (4*194)+194, this);
			}
			for (int z = 0; z < playercounter; z++) {
				g2.drawImage(image, (125 + z * 25), 200 + 25 * z,
						(205 + z * 25), 325 + 25 * z, deck[z + 5][0],
						deck[z + 5][1], deck[z + 5][0] + 144,
						deck[z + 5][1] + 194, this);
				// g2.drawImage(image, (125+(z+1)*25), 200, (205+(z+1)*25), 325, 2*144, 4*194, (2*144)+144, (4*194)+194, this);
			}

			if ((turn == 1) || (turn == 2) || (turn == 3)) {
				Graphics2D g3 = (Graphics2D) g;
				g3.setColor(Color.BLACK);
				g3.setFont(f2);
				g3.drawString("(" + dtot + ")", 150, 175);
				g3.drawString("(" + ptot + ")", 150, 350);
				g3.setFont(f1);
				g3.setColor(Color.white);
				g3.drawString(out, 75, 425);
			}
			// g9.fillArc(0,300, 400,100, 0, -180);
		}
	}

	public void Checkwin() {
		if (turn == 1) {
			if (ptot < 21) {
				// out=("Go Again?");
			} else if (ptot > 21) {
				out = ("Lose");
				turn = 3;
				btnHit.setEnabled(false);
				btnStand.setEnabled(false);
				p1.add(btnShuffle);
			} else if (ptot == 21) {
				// out=("Maybe win, Dealer Turn ");
				if (turn == 1) {
					turn = 2;
				}
				do {
					if (turn == 2) {
						btnHit.setEnabled(false);
						btnStand.setEnabled(false);
						if (dealercounter != 5) {
							dealercounter++;
							repaint();
							dtot = 0;
							for (int z = 0; z < dealercounter; z++)
								dtot += deck[z][2];
							System.out.println(dtot);
						}
					}
				} while (dtot < 17);
				// repaint();
			}
		} else if (turn == 2) {
			if (dtot == 17) {
				if (dtot < ptot) {
					out = "Player Wins";
					btnHit.setEnabled(false);
					btnStand.setEnabled(false);
					p1.add(btnShuffle);
				} else if (dtot > ptot) {
					out = "Dealer Wins";
					btnHit.setEnabled(false);
					btnStand.setEnabled(false);
					p1.add(btnShuffle);
				}
			} else if (dtot > 21) {
				out = "Player Wins";
				btnHit.setEnabled(false);
				btnStand.setEnabled(false);
				p1.add(btnShuffle);
			}

			else if (dtot < ptot) {
				out = "Player Wins";
				btnHit.setEnabled(false);
				btnStand.setEnabled(false);
				p1.add(btnShuffle);
			} else if (ptot == dtot) {
				out = ("Tie");
				btnHit.setEnabled(false);
				btnStand.setEnabled(false);
				p1.add(btnShuffle);
			}
			if ((dtot > ptot) && (dtot <= 21)) {
				out = "Dealer Wins";
				btnHit.setEnabled(false);
				btnStand.setEnabled(false);
				p1.add(btnShuffle);
			}
		}
		repaint();
	}

	public void Ace() {

	}

	public void shuffle() {
		for (int x = 0; x < 52; x++) {
			do {
				card = (int) (Math.random() * 13);
				suit = (int) (Math.random() * 4);
			} while (cardspicked[card][suit] == 1);
			cardspicked[card][suit]++;
			deck[x][0] = card * 144;
			deck[x][1] = suit * 194;

			if (card == 0) {
				deck[x][2] = 11;
			} else if (card == 10) {
				deck[x][2] = 10;
			} else if (card == 11) {
				deck[x][2] = 10;
			} else if (card == 12) {
				deck[x][2] = 10;
			} else if (card == 13) {
				deck[x][2] = 10;
			} else {
				deck[x][2] = card + 1;
			}
			deck[x][3] = suit;
		}

		for (int z = 0; z < playercounter; z++) {
			ptot += deck[z + 5][2];
		}
		if (ptot > 21) {
			for (int z = 0; z < playercounter; z++) {
				if (deck[z + 5][2] == 11) {
					deck[z + 5][2] = 1;
				}
			}
		}
		ptot = 0;
		for (int z = 0; z < playercounter; z++) {
			ptot += deck[z + 5][2];
		}

		for (int z = 0; z < dealercounter; z++) {
			dtot += deck[z][2];
		}
		if (dtot > 21) {
			for (int z = 0; z < dealercounter; z++) {
				if (deck[z][2] == 11) {
					deck[z][2] = 1;
				}
			}
		}
		dtot = 0;
		for (int z = 0; z < dealercounter; z++) {
			dtot += deck[z][2];
		}
	}
}