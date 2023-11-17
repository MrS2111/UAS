package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;

public class GameListScreen {
    public GameListScreen(){
        JFrame frame = new JFrame("Game List");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JButton transaction = new JButton("Transaction");
        transaction.setBounds(100, 10, 300, 30);
        transaction.addActionListener(e -> {
            new TransactionScreen();
            frame.dispose();
        });

        String[] gameList = new controller.Controller().gameList();
        JComboBox<String> gameListBox = new JComboBox<String>(gameList);
        gameListBox.setBounds(100, 50, 300, 30);
        gameListBox.setSelectedIndex(0);

        JLabel nameGameLabel = new JLabel("Name Game : ");
        nameGameLabel.setBounds(100, 100, 300, 30);
        JLabel nameGame = new JLabel(gameList[gameListBox.getSelectedIndex()]);
        nameGame.setBounds(100, 130, 300, 30);
        
        JLabel genreLabel = new JLabel("Genre : ");
        genreLabel.setBounds(100, 160, 300, 30);
        JLabel genre = new JLabel(new Controller().getGenre(gameList[gameListBox.getSelectedIndex()]));
        genre.setBounds(100, 190, 300, 30);
        
        JLabel priceLabel = new JLabel("Price : ");
        priceLabel.setBounds(100, 220, 300, 30);
        JLabel price = new JLabel(new Controller().getPrice(gameList[gameListBox.getSelectedIndex()]) + "");
        price.setBounds(100, 250, 300, 30);
        
        JButton buyButton = new JButton("Buy");
        buyButton.setBounds(100, 280, 300, 30);
        
        gameListBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nameGame.setText(gameList[gameListBox.getSelectedIndex()]);
                genre.setText(new Controller().getGenre(gameList[gameListBox.getSelectedIndex()]));
                price.setText(new Controller().getPrice(gameList[gameListBox.getSelectedIndex()]) + "");
            }
        });

        buyButton.addActionListener(e -> {
            int check = new Controller().buyGame(gameList[gameListBox.getSelectedIndex()]);
            if (check == -1) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error occured when buy game");
            }else if(check == -2){
                javax.swing.JOptionPane.showMessageDialog(null, "Game already bought");
            }else if(check == -3){
                javax.swing.JOptionPane.showMessageDialog(null, "Error occured when buy game");
            }else{
                javax.swing.JOptionPane.showMessageDialog(null, "Buy game success");
                new GameListScreen();
                frame.dispose();
            }
        });

        panel.add(gameListBox);
        panel.add(nameGameLabel);
        panel.add(nameGame);
        panel.add(genreLabel);
        panel.add(genre);
        panel.add(priceLabel);
        panel.add(price);
        panel.add(buyButton);

        panel.add(transaction);

        frame.add(panel);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    //test area
    public static void main(String[] args) {
        new GameListScreen();
    }
}
