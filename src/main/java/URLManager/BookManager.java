package URLManager;

import POJO.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class BookManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/crawler";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }



    public void addBook(Book book) {

        try (Connection conn = getConnection()){
            String insertSQL = "INSERT INTO books (isbn, book_name, author, publisher, publication_year, page_count, price, rating) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertSQL);
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getBookName());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getPublisher());
            preparedStatement.setString(5, book.getPublicationYear());
            preparedStatement.setString(6, book.getPageCount());
            preparedStatement.setString(7, book.getPrice());
            preparedStatement.setString(8, book.getRating());


            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Book inserted successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}