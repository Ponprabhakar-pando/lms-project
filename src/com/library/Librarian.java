package com.library;

import java.sql.*;

public class Librarian {
    public Connection conn = null;
    Librarian() {
        conn = new JdbcConnector().connect();
    }

    void addBook(String book_name, String book_author, double price, int count) {
        try {
            String sql = "INSERT INTO books (book_name, book_author, book_price, book_count) VALUES (?, ?, ?, ?)";

            PreparedStatement statement = this.conn.prepareStatement(sql);
            statement.setString(1, book_name);
            statement.setString(2, book_author);
            statement.setDouble(3, price);
            statement.setInt(4, count);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Book added successfully!");
            }
        } catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    void removeBook(int book_id) {
        try {
            String sql = "DELETE FROM books WHERE book_id=?";

            PreparedStatement statement = this.conn.prepareStatement(sql);
            statement.setInt(1, book_id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Book deleted successfully!");
            }
        } catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    void updateBook(int book_id, int book_count, boolean show_message) {
        try {
            String sql = "UPDATE books SET book_count=? WHERE book_id=?";
            PreparedStatement statement = this.conn.prepareStatement(sql);
            statement.setInt(1, book_count);
            statement.setInt(2, book_id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0 && show_message) {
                System.out.println("Book count updated successfully!");
            }
        } catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    void showAllBook() {
        try {
            String sql = "SELECT * FROM books";

            Statement statement = this.conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            System.out.println("<----------------------------------------------------->");
            while (result.next()) {
                int book_id = result.getInt("book_id");
                String book_name = result.getString("book_name");
                String book_author = result.getString("book_author");
                int book_price = result.getInt("book_price");
                int book_count = result.getInt("book_count");
                if (book_count > 0) {
                    String output = "Book Id -> %d | Name -> %s | Author -> %s | Price -> %d | Count Available -> %d";
                    System.out.println(String.format(output, book_id, book_name, book_author, book_price, book_count));
                }
            }
            System.out.println("<----------------------------------------------------->");
        } catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    void addUser(String user_name, String user_password) {
        try {
            int user_role = 1; // 1 -> Student, 0 -> Librarian
            String sql = "INSERT INTO users (user_name, user_passowrd, user_role) VALUES (?, ?, ?)";

            PreparedStatement statement = this.conn.prepareStatement(sql);
            statement.setString(1, user_name);
            statement.setString(2, user_password);
            statement.setInt(3, user_role);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Student added successfully!");
            }
        } catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    void removeUser(String user_name) {
        try {
            String sql = "DELETE FROM users WHERE user_name=?";

            PreparedStatement statement = this.conn.prepareStatement(sql);
            statement.setString(1, user_name);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User deleted successfully!");
            }
        } catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
