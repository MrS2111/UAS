package controller;

public class UserSingleton {
    private static UserSingleton instance;

    private int id;
    private String name;
    private String email;
    private String password;

    private UserSingleton() {}
    
    private UserSingleton(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email= email;
        this.password = password;
    }
    public static UserSingleton getInstance() {
        if (instance == null) {
            instance = new UserSingleton();
        }
        return instance;
    }

    public static void setInstance(UserSingleton instance) {
        UserSingleton.instance = instance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
