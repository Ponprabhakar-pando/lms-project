package com.library;

import java.sql.*;

public class Student {
    public Connection conn = null;
    Librarian libr = new Librarian();
    public int current_user_id;
    Student() {
        conn = new JdbcConnector().connect();
    }

    boolean login(String user_name, String user_password) {
        boolean valid = false;
        try {
            String sql = "SELECT * FROM users WHERE user_name=? AND user_password=?";

            PreparedStatement statement = this.conn.prepareStatement(sql);
            statement.setString(1, user_name);
            statement.setString(2, user_password);
            ResultSet result = statement.executeQuery(sql);

            while (result.next()){
                this.current_user_id = result.getInt("user_id");
                valid = true;
            }
        } catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return valid;
    }

    void fetchBook(int book_id) {
        try {
            String sql2 = "SELECT * FROM books WHERE book_id=?";

            PreparedStatement statement2 = this.conn.prepareStatement(sql2);
            statement2.setInt(1, book_id);
            ResultSet result2 = statement2.executeQuery(sql2);
            int book_count = 0;
            while (result2.next()) {
                book_count = result2.getInt("book_count");
            }
            if(book_count == 0) {
                System.out.println("No Books Available!");
                return;
            }
            long millis=System.currentTimeMillis();
            Date currentDate=new Date(millis);
            int entry_type = 0; // 0 -> Fetched Book, 1 -> Returned Book
            String sql = "INSERT INTO student_book (user_id, book_id, entry_type, last_updated_date) VALUES (?, ?, ?, ?)";

            PreparedStatement statement = this.conn.prepareStatement(sql);
            statement.setInt(1, this.current_user_id);
            statement.setInt(2, book_id);
            statement.setInt(3, entry_type);
            statement.setDate(4, currentDate);

            this.libr.updateBook(book_id, book_count-1, false);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Book returned successfully!");
            }
        } catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    void returnBook(int book_id) {
        try {
            long millis=System.currentTimeMillis();
            Date currentDate=new Date(millis);
            int entry_type = 1; // 0 -> Fetched Book, 1 -> Returned Book
            String sql = "INSERT INTO student_book (user_id, book_id, entry_type, last_updated_date) VALUES (?, ?, ?, ?)";

            PreparedStatement statement = this.conn.prepareStatement(sql);
            statement.setInt(1, this.current_user_id);
            statement.setInt(2, book_id);
            statement.setInt(3, entry_type);
            statement.setDate(4, currentDate);

            String sql2 = "SELECT * FROM books WHERE book_id=?";

            PreparedStatement statement2 = this.conn.prepareStatement(sql2);
            statement2.setInt(1, book_id);
            ResultSet result2 = statement2.executeQuery(sql2);
            int book_count = 0;
            while (result2.next()) {
                book_count = result2.getInt("book_count");
            }
            this.libr.updateBook(book_id, book_count+1, false);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Book returned successfully!");
            }

        } catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    void showIssuedBooks() {
        try {
            String sql = "SELECT * FROM student_book WHERE user_id=? AND entry_type=?";

            PreparedStatement statement = this.conn.prepareStatement(sql);
            statement.setInt(1, this.current_user_id);
            statement.setInt(2, 0);
            ResultSet result = statement.executeQuery(sql);
            System.out.println("<---------------Books Fetched--------------->");
            while (result.next()) {
                int book_id = result.getInt("book_id");
                Date last_date = result.getDate("last_updated_date");

                String output = "Details:  Book_Id Id -> %d | Last updated Date -> %s";
                System.out.println(String.format(output, book_id, last_date.toString()));
            }
            System.out.println("<----------------------------------------------------->");
        } catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    void showAllBooks() {
        this.libr.showAllBook();
    }
}
