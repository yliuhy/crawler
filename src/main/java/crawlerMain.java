import DataBaseManager.DatabaseManagerBook;
import DataBaseManager.DatabaseManagerMovie;
import Parser.DoubanParser;
import Parser.Parser;      // Import the Parser interface
import URLManager.URLManagerMovie;
import URLManager.URLStreamingManager;

import URLManager.URLManagerBook;

import java.util.List;

public class crawlerMain {
    public static void main(String[] args) {
        // 选择分发策略
        String dispatcherType = "roundRobin"; // "random"
        // 选择解析器
        String Type = "movie"; // book
        boolean isBook = true;


        if (Type == "movie") {
            DatabaseManagerMovie dbmanager = new DatabaseManagerMovie();
            dbmanager.resetDatabase();
            URLManagerMovie manager = new URLManagerMovie();
            // Add seed URL
            manager.addSeedURL("https://www.netflix.com/hk-en/browse/genre/83");
            // Get seed URLs
            List<String> seedURLs = manager.getSeedURLs();
            System.out.println("Seed URLs: " + seedURLs);
            // Add crawling URLs
            manager.addCrawlingURL("https://www.netflix.com/hk-en/browse/genre/11714");
            // Get crawling URLs
            List<String> crawlingURLs = manager.getCrawlingURLs();
            System.out.println("Crawling URLs: " + crawlingURLs);

            URLStreamingManager urlStreamingManager = new URLStreamingManager(Type, dispatcherType,3);
            urlStreamingManager.start();
        } else if (Type == "book") {
            DatabaseManagerBook manager = new DatabaseManagerBook();
            manager.resetDatabase();
            URLManagerBook URLmanager = new URLManagerBook();
            // 添加种子 URL
            URLmanager.addSeedURL("https://book.douban.com/tag/");
            // 获取种子 URL
            List<String> seedURLs = URLmanager.getSeedURLs();
            System.out.println("Seed URLs: " + seedURLs);
//         添加待爬取 URL
            URLmanager.addCrawlingURL("https://book.douban.com/tag/童话");
            URLmanager.addCrawlingURL("https://book.douban.com/tag/散文");
            // 获取待爬取 URL
            List<String> crawlingURLs = URLmanager.getCrawlingURLs();
            System.out.println("Crawling URLs: " + crawlingURLs);

            URLStreamingManager urlStreamingManager = new URLStreamingManager(Type, dispatcherType,3);
            urlStreamingManager.start();
        }

        // 启动 URL 流管理器，并传入所需的解析器

    }
}
