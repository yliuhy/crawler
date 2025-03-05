package URLManager;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class URLManagerBook {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/crawler";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public void addSeedURL(String url) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT IGNORE INTO seed_urls (url) VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, url);
            pstmt.executeUpdate();
            System.out.println("Inserting URL into seed_urls: " + url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCrawlingURL(String url) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT IGNORE INTO crawling_urls (url, status) VALUES (?, 0)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, url);
            pstmt.executeUpdate();
            System.out.println("Inserting URL into crawling_urls: " + url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getSeedURLs() {
        List<String> urls = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT url FROM seed_urls";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                urls.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urls;
    }

    public static List<String> getCrawlingURLs() {
        List<String> urls = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT url FROM crawling_urls WHERE status = 0";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                urls.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urls;
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    public void updateUrlStatus(String url) {
        try (Connection conn = getConnection()){
            String sql = "UPDATE crawling_urls SET status = 1 WHERE url = ?";
            PreparedStatement updateStatement = conn.prepareStatement(sql);
            updateStatement.setString(1, url);
            updateStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        URLManagerBook manager = new URLManagerBook();
        // 添加种子 URL
        manager.addSeedURL("https://book.douban.com/tag/");
        // 获取种子 URL
        List<String> seedURLs = manager.getSeedURLs();
        System.out.println("Seed URLs: " + seedURLs);
//         添加待爬取 URL
        manager.addCrawlingURL("https://book.douban.com/tag/小说");
        manager.addCrawlingURL("https://book.douban.com/tag/文学");
        // 获取待爬取 URL
        List<String> crawlingURLs = manager.getCrawlingURLs();
        System.out.println("Crawling URLs: " + crawlingURLs);
    }
}
