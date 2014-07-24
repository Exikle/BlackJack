package com.exikle.blackjack;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
public class BlackJackGUI1 extends JFrame implements ActionListener{
  int[][] cards;
  int card, suit, count=0, turn=1;
  String c,s;
  Border brdr1, brdr2, brdr3;
  String[]deck;
  JLabel[] dealer;
  JLabel[] player;
  JLabel lbl1, lbl2;
  JPanel pnl1, pnl2, pnl3, pnl4;
  JButton btn1,btn2;
  public static void main(String[] args)
  {
    new BlackJackGUI1();
  }
  
  public BlackJackGUI1(){
    super("BlackJack");
//    this.setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    brdr1 = new CompoundBorder(LineBorder.createGrayLineBorder(),
                               BorderFactory.createLineBorder(Color.white));
    brdr3 = new CompoundBorder(LineBorder.createGrayLineBorder(),
                               BorderFactory.createLineBorder(Color.black));
    
    brdr2 = new CompoundBorder(brdr1, BorderFactory
                                 .createLoweredBevelBorder());
    pnl1 = new JPanel();
    pnl2 = new JPanel();
    pnl3 = new JPanel();
    pnl4 = new JPanel();
    cards = new int[14][5];
    deck = new String[52];
    dealer = new JLabel[5];
    player = new JLabel[5];
    lbl1 = new JLabel("Dealer");
    lbl2 = new JLabel("Player");
    btn1 = new JButton("Hit");
    btn1.addActionListener(this);
    btn2 = new JButton("Stand");
    pnl1.add(lbl1);
    pnl2.add(lbl2);
    for (int z=0;z<5;z++){
      dealer[z] = new JLabel("");
      dealer[z].setBorder(brdr2);
      pnl1.add(dealer[z]);
      player[z] = new JLabel("");
      player[z].setBorder(brdr2);
      pnl2.add(player[z]);
    }
    for (int x=0;x<52;x++){
      do{
        card = (int)(Math.random()*13)+1;
        suit = (int)(Math.random()*4)+1;
      }while (cards[card][suit]==1);
      cards[card][suit]++;
      
      if (card==1){
        c=("Ace");
      }
      else if (card==11){
        c=("Jack"); 
      }
      else if (card==12){
        c=("Queen"); 
      }
      else if (card==13){
        c=("King"); 
      }
      else {
        c=(""+card);
      }
      
      if (suit==1){
        s=("Hearts");
      }
      else if (suit==2){
        s=("Diamonds");
      }
      else if (suit==3){
        s=("Spades");
      }
      else if (suit==4){
        s=("Clubs");
      }
      deck[x]=(c+" , "+s);
    }
//    player[count].setText(deck[count]);
//    count++;
//    
    pnl1.setLayout(new GridLayout(1,5));
    pnl2.setLayout(new GridLayout(1,5));
    pnl3.setLayout(new GridLayout(2,1));
    pnl4.setLayout(new GridLayout(1,2));
    
    
    pnl3.add(pnl1);
    pnl3.add(pnl2);
    pnl4.add(btn1);
    pnl4.add(btn2);
    
    this.setLayout(new BorderLayout());
    this.add(pnl3, BorderLayout.CENTER);
    this.add(pnl4, BorderLayout.SOUTH);
    this.setSize(600,350);
    this.setVisible(true);
    
  }
  
  public void actionPerformed(ActionEvent e){
    if (e. getSource()==btn1){
      player[count].setText(deck[count]);
      count++;
    }
  }
}