package URLManager;

import POJO.Movie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MovieManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/crawler"; // Database URL
    private static final String DB_USER = "root"; // Database username
    private static final String DB_PASSWORD = ""; // Database password

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public void addMovie(Movie movie) {
        try (Connection conn = getConnection()) {
            String insertSQL = "INSERT INTO movies (title, year, category, stars) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertSQL);
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getYear());
            preparedStatement.setString(3, movie.getCategory());
            preparedStatement.setString(4, movie.getStars());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Movie inserted successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}