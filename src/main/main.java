import Parser.DoubanParser;
import Parser.SuningParser;
import Parser.Parser;      // Import the Parser interface
import URLManager.URLStreamingManager;

public class MainApp {
    public static void main(String[] args) {
        DatabaseManager manager = new DatabaseManager();
        manager.resetDatabase();
        URLManager URLmanager = new URLManager();
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

        // 可以根据需要选择解析器
        Parser parser;

        // 选择解析器
        String parserType = "douban"; // 可以通过命令行参数或其他方式动态设置
        if ("suning".equalsIgnoreCase(parserType)) {
            parser = new SuningParser();
        } else {
            parser = new DoubanParser();
        }

        // 启动 URL 流管理器，并传入所需的解析器
        URLStreamingManager urlStreamingManager = new URLStreamingManager();
        urlStreamingManager.start(parser);
    }
}