package DataBaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManagerBook {
    private static final String URL = "jdbc:mysql://localhost:3306/crawler"; // 替换为你的数据库 URL
    private static final String USER = "root"; // 替换为你的数据库用户名
    private static final String PASSWORD = ""; // 替换为你的数据库密码

    public static void main(String[] args) {
        DatabaseManagerBook manager = new DatabaseManagerBook();
        manager.resetDatabase();
    }

    public void resetDatabase() {
        dropTables();
        createSeedUrlsTable();
        createCrawlingUrlsTable();
        createBooksTable();
    }

    private void dropTables() {
        String dropSeedUrlsSQL = "DROP TABLE IF EXISTS seed_urls;";
        String dropCrawlingUrlsSQL = "DROP TABLE IF EXISTS crawling_urls;";
        String dropBooksSQL = "DROP TABLE IF EXISTS books;";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            // Execute SQL statements to drop tables
            statement.executeUpdate(dropSeedUrlsSQL);
            statement.executeUpdate(dropCrawlingUrlsSQL);
            statement.executeUpdate(dropBooksSQL); // Drop books table
            System.out.println("Tables 'seed_urls', 'crawling_urls', and 'books' dropped successfully.");

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

            // 执行 SQL 语句
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

            // 执行 SQL 语句
            statement.executeUpdate(createTableSQL);
            System.out.println("Table 'seed_urls' created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createBooksTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS books (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "isbn VARCHAR(20) NOT NULL UNIQUE, " +  // Unique ISBN
                "book_name VARCHAR(255) NOT NULL, " +
                "author VARCHAR(255), " +
                "publisher VARCHAR(255), " +
                "publication_year VARCHAR(255), " +
                "page_count VARCHAR(255), " +
                "price VARCHAR(255), " +
                "rating VARCHAR(255)" +
                ");";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            // Execute SQL statement
            statement.executeUpdate(createTableSQL);
            System.out.println("Table 'books' created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}