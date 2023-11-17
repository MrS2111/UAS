package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginScreen {
    public LoginScreen(){
        JFrame frame = new JFrame("Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,2));
        JLabel label1 = new JLabel("Email");
        JTextField email = new JTextField();
        JLabel label2 = new JLabel("Password");
        JPasswordField password = new JPasswordField();
        JButton submit = new JButton("Submit");

        submit.addActionListener(e -> {
            String userEmail = email.getText();
            String pass = password.getText();
            int check = new controller.Controller().login(userEmail, pass);
            if (check == -1) {
                JOptionPane.showMessageDialog(null, "Email cannot be empty");
            }else if(check == -2){
                JOptionPane.showMessageDialog(null, "Password cannot be empty");
            }else if(check == -3){
                JOptionPane.showMessageDialog(null, "Email or password is incorrect");
            }else if(check == -4){
                JOptionPane.showMessageDialog(null, "Error occured when login");
            }else{
                JOptionPane.showMessageDialog(null, "Login success");
                new controller.Controller().insertToSingelton(userEmail);
                new GameListScreen();
                frame.dispose();
            }
        });

        panel.add(label1);
        panel.add(email);
        panel.add(label2);
        panel.add(password);
        panel.add(new JLabel(""));
        panel.add(submit);
        
        frame.add(panel);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    //test area
    public static void main(String[] args) {
        new LoginScreen();
    }
}
