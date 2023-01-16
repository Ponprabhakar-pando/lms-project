package com.library;
import java.sql.SQLOutput;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        int userType = -1, userChoice = -1;
        Librarian librarian = new Librarian();
        Student student = new Student();
        Scanner scan = new Scanner(System.in);
        while (userType != 0) {
            System.out.println("1. Librarian\n2. Student\n0.Exit\n\nEnter your choice: ");
            userType = scan.nextInt();
            if (userType == 1) {
                while (userChoice != 0) {
                    System.out.println("1.Book\n2.Student\n0.Back\nEnter your choice: ");
                    userChoice = scan.nextInt();
                    if (userChoice == 1) {
                        while (userChoice != 0) {
                            System.out.println("1.Add Book\n2.Delete Book\n3.Update Book\n4.Show all books\n0.Back\nEnter your choice: ");
                            userChoice = scan.nextInt();
                            if (userChoice == 1) {
                                System.out.println("\nEnter the book details\n");
                                System.out.println("\nEnter book name:");
                                String book_name = scan.nextLine();
                                System.out.println("\nEnter book author: ");
                                String book_author = scan.nextLine();
                                System.out.println("\nEnter book price: ");
                                Double price = scan.nextDouble();
                                System.out.println("\nEnter book available count:");
                                Integer count = scan.nextInt();
                                librarian.addBook(book_name, book_author, price, count);
                            }
                            else if (userChoice == 2) {
                                System.out.println("\nEnter book ID to delete\n");
                                Integer book_id = scan.nextInt();
                                librarian.removeBook(book_id);
                            }
                            else if (userChoice == 3) {
                                System.out.println("\nEnter book ID to update count\n");
                                Integer book_id = scan.nextInt();
                                System.out.println("\nEnter book count to be updated\n");
                                Integer book_count = scan.nextInt();
                                librarian.updateBook(book_id, book_count, true);

                            } else if (userChoice == 4) {
                                librarian.showAllBook();
                            }
                        }
                    } else if (userChoice == 2) {
                        while(userChoice != 0){
                            System.out.println("1.Add User\n2.Remove User\n3.Back\n\nEnter your choice: ");
                            userChoice = scan.nextInt();
                            if(userChoice == 1){
                                System.out.println("enter user name: ");
                                String user_name = scan.nextLine();
                                System.out.println("enter password: ");
                                String password = scan.nextLine();
                                librarian.addUser(user_name, password);
                            }
                            else if(userChoice == 2){
                                System.out.println("Enter User ID to remove: ");
                                String user_name = scan.nextLine();
                                librarian.removeUser(user_name);
                            }
                        }
                    }
                }
            } else if (userType == 2) {
                userChoice = -1;
                while(userChoice != 0){
                    System.out.println("1.Login\n0.back\n\nEnter your choice: ");
                    userChoice = scan.nextInt();
                    if(userChoice == 1){
                        System.out.println("\nEnter user name:\n");
                        String user_name = scan.nextLine();
                        System.out.println("\nEnter password\n");
                        String user_password = scan.nextLine();
                        if(student.login(user_name, user_password)) {
                            while(userChoice!= 0){
                                System.out.println("1.Fetch Book\n2.Return Book\n3.Show Issued books\n4.Show All Books\n5.Exit\n\nEnter you choice: ");
                                userChoice = scan.nextInt();
                                if(userChoice == 1){
                                    System.out.println("Enter Book Name:");
                                    Integer book_id = scan.nextInt();
                                    student.fetchBook(book_id);
                                }
                                else if(userChoice == 2){
                                    System.out.println("Enter Book Id to return: ");
                                    Integer book_id = scan.nextInt();
                                    student.returnBook(book_id);
                                }
                                else if(userChoice == 3){
                                    student.showIssuedBooks();
                                }
                                else if(userChoice == 4){
                                    student.showAllBooks();
                                }
                                else if(userChoice == 5){
                                    exit(0);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}


