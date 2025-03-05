package DataBaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManagerMovie {
    private static final String URL = "jdbc:mysql://localhost:3306/crawler"; // Replace with your database URL
    private static final String USER = "root"; // Replace with your database username
    private static final String PASSWORD = ""; // Replace with your database password

    public static void main(String[] args) {
        DatabaseManagerMovie manager = new DatabaseManagerMovie();
        manager.resetDatabase();
    }

    public void resetDatabase() {
        dropTables();
        createSeedUrlsTable();
        createCrawlingUrlsTable();
        createMoviesTable(); // Create the movies table
    }

    private void dropTables() {
        String dropSeedUrlsSQL = "DROP TABLE IF EXISTS seed_urls;";
        String dropCrawlingUrlsSQL = "DROP TABLE IF EXISTS crawling_urls;";
        String dropMoviesSQL = "DROP TABLE IF EXISTS movies;"; // Drop movies table

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            // Execute SQL statements to drop tables
            statement.executeUpdate(dropSeedUrlsSQL);
            statement.executeUpdate(dropCrawlingUrlsSQL);
            statement.executeUpdate(dropMoviesSQL); // Drop movies table
            System.out.println("Tables 'seed_urls', 'crawling_urls', and 'movies' dropped successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createSeedUrlsTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS seed_urls (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "url VARCHAR(512) NOT NULL UNIQUE, " +
                "create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            // Execute SQL statement
            statement.executeUpdate(createTableSQL);
            System.out.println("Table 'seed_urls' created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCrawlingUrlsTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS crawling_urls (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "url VARCHAR(512) NOT NULL UNIQUE, " +  // Unique constraint on url
                "status INT NOT NULL, " +  // 0: 未爬取, 1: 已爬取
                "create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            // Execute SQL statement
            statement.executeUpdate(createTableSQL);
            System.out.println("Table 'crawling_urls' created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createMoviesTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS movies (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "title VARCHAR(255) NOT NULL, " +  // Movie title
                "year VARCHAR(255), " +  // Release year
                "category VARCHAR(255), " +  // Movie category
                "stars VARCHAR(255)" +  // Actor names
                ");";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            // Execute SQL statement
            statement.executeUpdate(createTableSQL);
            System.out.println("Table 'movies' created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}