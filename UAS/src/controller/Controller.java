package controller;

import java.beans.Statement;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.DatabaseHandler;

public class Controller {
    DatabaseHandler conn = new DatabaseHandler();

    public int login(String email, String password){
        if (email == null) {
            return -1;
        }
        if (password == null) {
            return -2;
        }

    try {
        conn.open();

        String query = "SELECT * FROM `users` WHERE `email`=? AND `password`=?";
        PreparedStatement statement = conn.connection.prepareStatement(query);
        statement.setString(1, email);
        statement.setString(2, sha256(password));

        ResultSet result = statement.executeQuery();

        if (!result.isBeforeFirst()) {
            return -3;
        }
        result.next();
        conn.close();
        return 1;
        } catch (SQLException e) {
            System.out.println("Error occurred when login: " + e.getMessage());
            return -4;
        }
    }
    
    public int insertToSingelton(String email){
        try {
            conn.open();
            String query = "SELECT * FROM `users` WHERE `email`=?";
            PreparedStatement statement = conn.connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            result.next();
            UserSingleton user = UserSingleton.getInstance();
            user.setId(result.getInt("id"));
            user.setName(result.getString("name"));
            user.setEmail(result.getString("email"));
            user.setPassword(result.getString("password"));
            conn.close();
            return 1;
        } catch (SQLException e) {
            System.out.println("Error occurred when insert to singleton: " + e.getMessage());
            return -1;
        }
    }

    public String[] gameList(){
        try {
            conn.open();
            String query = "SELECT * FROM `games`";
            PreparedStatement statement = conn.connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            String[] gameList = new String[100];
            int i = 0;
            while (result.next()) {
                gameList[i] = result.getString("name");
                i++;
            }
            conn.close();
            return gameList;
        } catch (SQLException e) {
            System.out.println("Error occurred when get game list: " + e.getMessage());
            return null;
        }
    }
    public int getPrice(String name){
        try {
            conn.open();
            String query = "SELECT * FROM `games` WHERE `name`=?";
            PreparedStatement statement = conn.connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            result.next();
            int price = result.getInt("price");
            conn.close();
            return price;
        } catch (SQLException e) {
            System.out.println("Error occurred when get price: " + e.getMessage());
            return -1;
        }
    }
    public String getGenre(String name){
        try {
            conn.open();
            String query = "SELECT * FROM `games` WHERE `name`=?";
            PreparedStatement statement = conn.connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            result.next();
            String genre = result.getString("genre");
            conn.close();
            return genre;
        } catch (SQLException e) {
            System.out.println("Error occurred when get genre: " + e.getMessage());
            return null;
        }
    }
    public int buyGame(String name){
        try {
            conn.open();
            String query = "SELECT * FROM `games` WHERE `name`=?";
            PreparedStatement statement = conn.connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            result.next();
            int idGame = result.getInt("id");
            int idUser = UserSingleton.getInstance().getId();
            query = "SELECT * FROM `transactions` WHERE `game_id`=? AND `user_id`=?";
            statement = conn.connection.prepareStatement(query);
            statement.setInt(1, idGame);
            statement.setInt(2, idUser);
            result = statement.executeQuery();
            if (result.isBeforeFirst()) {
                return -2;
            }
            query = "INSERT INTO `transactions`(`game_id`, `user_id`) VALUES (?,?)";
            statement = conn.connection.prepareStatement(query);
            statement.setInt(1, idGame);
            statement.setInt(2, idUser);
            statement.executeUpdate();
            conn.close();
            return 1;
        } catch (SQLException e) {
            System.out.println("Error occurred when buy game: " + e.getMessage());
            return -3;
        }
    }

    // hashing password
    public String sha256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}