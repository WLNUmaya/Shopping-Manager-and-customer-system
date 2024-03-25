public class User {

    // Instance variables for storing the username and password
    private String username;
    private String password;

    // Constructor to initialize a User with a username and password
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters for retrieving the values of username and password

    // Getter for the username
    public String getUsername() {
        return username;
    }

    // Getter for the password
    public String getPassword() {
        return password;
    }
}
