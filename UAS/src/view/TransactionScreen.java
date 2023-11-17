package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.UserSingleton;

public class TransactionScreen extends JFrame {
    private JTable table;

    public TransactionScreen() {
        setTitle("Transaction Data");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        String[] columns = { "ID", "User ID", "User Name", "Game ID", "Game Name", "Total Price" };

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        try {
            String url = "jdbc:mysql://localhost:3306/game_shop";
            String username = "root";
            String password = "";
            Connection connection = DriverManager.getConnection(url, username, password);

            String query = "SELECT t.id, t.user_id, u.name, t.game_id, g.name, g.price " +
                    "FROM transactions t " +
                    "JOIN users u ON t.user_id = u.id " +
                    "JOIN games g ON t.game_id = g.id " +
                    "WHERE t.user_id = '" + UserSingleton.getInstance().getId() + "'";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                model.addRow(new Object[] {
                        resultSet.getInt("t.id"),
                        resultSet.getString("t.user_id"),
                        resultSet.getString("u.name"),
                        resultSet.getInt("t.game_id"),
                        resultSet.getString("g.name"),
                        resultSet.getDouble("g.price")
                });
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);

        setVisible(true);
    }

    // test area
    public static void main(String[] args) {
        new TransactionScreen();
    }
}
