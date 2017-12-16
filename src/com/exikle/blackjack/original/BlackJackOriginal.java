package com.exikle.blackjack.original;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BlackJackOriginal extends JFrame implements
	ActionListener {

	int[][] cardspicked;

	int[][] deck;

	int[] check;

	int card, suit, count = 0, turn = 0, playerCount = 0,
	                dealCount = 0, playerTotal = 0, dealerTotal = 0;

	String c, s, out = "Status";

	MyDrawPanel dPnl1 = new MyDrawPanel();

	JPanel pnl1 = new JPanel(), pnl2 = new JPanel();

	JButton btnHit, btnStand, btnShuffle;

	JLabel lblTop;

	ClassLoader cl = BlackJackOriginal.class.getClassLoader();

	String picPath = "pictures/";

	URL imageURL = cl.getResource(picPath + "cards.png");

	Image image;

	Font f1 = new Font("Book Antiqua", Font.BOLD, 40), f2 = new Font(
	    "Comic Sans", Font.BOLD, 20);

	Color c1 = new Color(0, 133, 0), c2 = new Color(82, 0, 0),
	c3 = new Color(152, 0, 0), c4 = new Color(62, 0, 0),
	c5 = new Color(0, 92, 0);

	public static void main(String[] args) {
		new BlackJackOriginal();
	}

	public BlackJackOriginal() {
		super("BlackJack");
		setResizable(false);

		cardspicked = new int[14][5];
		deck = new int[52][4];

		btnShuffle = new JButton("Deal");
		btnShuffle.addActionListener(this);
		btnShuffle.setOpaque(false);
		btnShuffle.setContentAreaFilled(false);

		btnHit = new JButton("Hit");
		btnHit.addActionListener(this);
		btnHit.setEnabled(false);

		btnStand = new JButton("Stand");
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

		dPnl1.add(btnShuffle);
		btnShuffle.setBounds(150, 435, 75, 75);

		dPnl1.add(btnHit);
		btnHit.setBounds(50, 435, 75, 75);
		btnHit.setOpaque(false);
		dPnl1.add(btnStand);
		btnStand.setBounds(250, 435, 75, 75);
		btnStand.setOpaque(false);
		dPnl1.setLayout(null);

		pnl2.add(lblTop);
		pnl2.setBackground(c1);

		this.setLayout(new BorderLayout());
		this.add(dPnl1, BorderLayout.CENTER);
		this.add(pnl2, BorderLayout.NORTH);
		this.setSize(400, 600);
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnShuffle) {
			for (int x = 0; x < 52; x++) {
				for (int z = 0; z < 4; z++)
					deck[x][z] = 0;
			}
			for (int x = 0; x < 14; x++) {
				for (int z = 0; z < 5; z++)
					cardspicked[x][z] = 0;
			}
			turn = 1;
			btnHit.setEnabled(true);
			btnStand.setEnabled(true);
			playerCount = 2;
			dealCount = 1;
			playerTotal = 0;

			shuffle();
			out = "Players Turn";
			Checkwin();
			Checkwin();
			// p1.remove(btnShuffle);
			repaint();
		} else if (e.getSource() == btnHit) {
			if (turn == 1) {
				if (playerCount != 5) {
					playerCount++;
					repaint();
					playerTotal = 0;
					for (int z = 0; z < playerCount; z++)
						playerTotal += deck[z + 5][2];
					if (playerTotal > 21) {
						for (int z = 0; z < playerCount; z++) {
							if (deck[z + 5][2] == 11)
								deck[z + 5][2] = 1;
						}
					}
					playerTotal = 0;
					for (int z = 0; z < playerCount; z++)
						playerTotal += deck[z + 5][2];
				}
			}
			Checkwin();
			Checkwin();
		} else if (e.getSource() == btnStand) {
			if (turn == 1)
				turn = 2;
			do {
				if (turn == 2) {
					btnHit.setEnabled(false);
					btnStand.setEnabled(false);
					if (dealCount != 5) {
						dealCount++;
						repaint();
						dealerTotal = 0;
						for (int z = 0; z < dealCount; z++)
							dealerTotal += deck[z][2];
						if (dealerTotal > 21) {
							for (int z = 0; z < dealCount; z++) {
								if (deck[z][2] == 11)
									deck[z][2] = 1;
							}
						}
						dealerTotal = 0;
						for (int z = 0; z < dealCount; z++)
							dealerTotal += deck[z + 5][2];
						System.out.println(dealerTotal);
					}
				}
			} while (dealerTotal < 17);
			Checkwin();
			Checkwin();
		}
	}

	class MyDrawPanel extends JPanel {

		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(image, 150, 25, 230, 150, 2 * 144, 4 * 194,
			             (2 * 144) + 144, (4 * 194) + 194, this);
			g2.drawImage(image, 125, 200, 205, 325, 2 * 144, 4 * 194,
			             (2 * 144) + 144, (4 * 194) + 194, this);
			for (int z = 0; z < dealCount; z++)
				g2.drawImage(image, (125 + (z + 1) * 25), 25,
				             (205 + (z + 1) * 25), 150, 2 * 144, 4 * 194,
				             (2 * 144) + 144, (4 * 194) + 194, this);
			for (int z = 0; z < playerCount; z++)
				g2.drawImage(image, (125 + (z + 1) * 25), 200,
				             (205 + (z + 1) * 25), 325, 2 * 144, 4 * 194,
				             (2 * 144) + 144, (4 * 194) + 194, this);

			if ((turn == 1) || (turn == 2) || (turn == 3)) {
				Graphics2D g3 = (Graphics2D) g;
				g3.setColor(Color.BLACK);
				g3.setFont(f2);
				g3.drawString("(" + dealerTotal + ")", 150, 175);
				g3.drawString("(" + playerTotal + ")", 150, 350);
				g3.setFont(f1);
				g3.setColor(Color.white);
				g3.drawString(out, 75, 425);
			}
		}
	}

	public void Checkwin() {
		if (turn == 1) {
			if (playerTotal < 21) { // out=("Go Again?");
			} else if (playerTotal > 21) {
				out = ("Lose");
				turn = 3;
				btnHit.setEnabled(false);
				btnStand.setEnabled(false);
				dPnl1.add(btnShuffle);
			} else if (playerTotal == 21) {
				// out=("Maybe win, Dealer Turn ");
				if (turn == 1)
					turn = 2;
				do {
					if (turn == 2) {
						btnHit.setEnabled(false);
						btnStand.setEnabled(false);
						if (dealCount != 5) {
							dealCount++;
							repaint();
							dealerTotal = 0;
							for (int z = 0; z < dealCount; z++)
								dealerTotal += deck[z][2];
							System.out.println(dealerTotal);
						}
					}
				} while (dealerTotal < 17);
				// repaint();
			}
		} else if (turn == 2) {
			if (dealerTotal == 17) {
				if (dealerTotal < playerTotal) {
					out = "Player Wins";
					btnHit.setEnabled(false);
					btnStand.setEnabled(false);
					dPnl1.add(btnShuffle);
				} else if (dealerTotal > playerTotal) {
					out = "Dealer Wins";
					btnHit.setEnabled(false);
					btnStand.setEnabled(false);
					dPnl1.add(btnShuffle);
				}
			} else if (dealerTotal > 21) {
				out = "Player Wins";
				btnHit.setEnabled(false);
				btnStand.setEnabled(false);
				dPnl1.add(btnShuffle);
			}

			else if (dealerTotal < playerTotal) {
				out = "Player Wins";
				btnHit.setEnabled(false);
				btnStand.setEnabled(false);
				dPnl1.add(btnShuffle);
			} else if (playerTotal == dealerTotal) {
				out = ("Tie");
				btnHit.setEnabled(false);
				btnStand.setEnabled(false);
				dPnl1.add(btnShuffle);
			}
			if ((dealerTotal > playerTotal) && (dealerTotal <= 21)) {
				out = "Dealer Wins";
				btnHit.setEnabled(false);
				btnStand.setEnabled(false);
				dPnl1.add(btnShuffle);
			}
		}
		repaint();
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

			if (card == 0)
				deck[x][2] = 11;
			else if (card == 10)
				deck[x][2] = 10;
			else if (card == 11)
				deck[x][2] = 10;
			else if (card == 12)
				deck[x][2] = 10;
			else if (card == 13)
				deck[x][2] = 10;
			else
				deck[x][2] = card + 1;
			deck[x][3] = suit;
		}

		for (int z = 0; z < playerCount; z++)
			playerTotal += deck[z + 5][2];
		if (playerTotal > 21) {
			for (int z = 0; z < playerCount; z++) {
				if (deck[z + 5][2] == 11)
					deck[z + 5][2] = 1;
			}
		}
		playerTotal = 0;
		for (int z = 0; z < playerCount; z++)
			playerTotal += deck[z + 5][2];
		for (int z = 0; z < dealCount; z++)
			dealerTotal += deck[z][2];
		if (dealerTotal > 21) {
			for (int z = 0; z < dealCount; z++) {
				if (deck[z][2] == 11)
					deck[z][2] = 1;
			}
		}
		dealerTotal = 0;
		for (int z = 0; z < dealCount; z++)
			dealerTotal += deck[z][2];
	}
}