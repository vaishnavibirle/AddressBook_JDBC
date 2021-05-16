package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*Connecting and retrieving all entries from addressBook
* USED TDD approach */

public class AddressBookConnection {
    public static final String URL = "jdbc:mysql://localhost:3306/addressbooksystem?useSSL=false";
    public static final String USER = "root";
    public static final String PASSWORD = "Vaishnavi@123";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
