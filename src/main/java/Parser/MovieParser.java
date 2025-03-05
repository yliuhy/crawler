package Parser;

import Crawler.OkHttpCrawler;
import POJO.Book;
import POJO.Movie;
import URLManager.BookManager;
import URLManager.MovieManager;
import okhttp3.OkHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieParser implements Parser{
    @Override
    public void parse(String content) {
        if (content == null || content.isEmpty()) {
            System.out.println("内容为空，无法解析。");
            return;
        }
        else if(content.contains("<h1>TV Shows</h1>") ){
            parseTag(content);
        }
        else if (content.contains("<h1>TV Dramas</h1>") ) {
            parseList(content);
        }
        else{
            parseMovieDetail(content);
        }
    }
    private void parseTag(String content) {
        Document document = Jsoup.parse(content);

        // Select all <div> elements with the class 'movie show'
        Elements movieRowElements = document.select("h2.nm-collections-row-name a");

        int limit = 100; // Set the limit to 10
        int count = 0;  // Initialize a counter

        for (Element movieRowElement : movieRowElements) {
            if (count >= limit) {
                break; // Stop if we've reached the limit
            }

            // Get the <a> element for the movie link
            Element movieLinkElement = movieRowElement.selectFirst("a[href]");
            if (movieLinkElement != null) {
                String href = movieLinkElement.attr("href");
                href = "" + href;

                // Print or store the href
                System.out.println("电影分类: " + href);

                count++; // Increment the counter
            }
        }
    }

    private void parseList(String content) {
        Document document = Jsoup.parse(content);

        // Select all <div> elements with the class 'movie show'
        Elements movieRowElements = document.select("a.nm-collections-title.nm-collections-link");

        int limit = 100; // Set the limit to 10
        int count = 0;  // Initialize a counter

        for (Element movieRowElement : movieRowElements) {
            if (count >= limit) {
                break; // Stop if we've reached the limit
            }

            // Get the <a> element for the movie link
            Element movieLinkElement = movieRowElement.selectFirst("a[href]");
            if (movieLinkElement != null) {
                String href = movieLinkElement.attr("href");
                href = "" + href;

                // Print or store the href
                System.out.println("电影链接: " + href);

                count++; // Increment the counter
            }
        }
    }
    private static final Pattern starringRe = Pattern.compile("<span class=\"title-data-info-item-list\"[^>]*>(.*?)<\\/span>");
    private static final Pattern catRe = Pattern.compile("<a[^>]*class=\"title-info-metadata-item item-genre\"[^>]*>(.*?)<\\/a>");

    private static final Pattern yearRe = Pattern.compile("<span[^>]*class=\"title-info-metadata-item item-year\"[^>]*>(.*?)<\\/span>");
    private static final Pattern titleRe = Pattern.compile("<h1[^>]*class=\"title-title\"[^>]*>(.*?)<\\/h1>");

    private void parseMovieDetail(String content) {

        String title = extraString(content, titleRe);
        String year = extraString(content, yearRe);
        String cat = extraString(content, catRe);
        String star = extraString(content, starringRe);


        System.out.println("标题: " + title);
        System.out.println("年份: " + year);
        System.out.println("类别: " + cat);
        System.out.println("演员: " + star);
        MovieManager bookManager = new MovieManager();
        bookManager.addMovie(new Movie(title,year,cat,star));


    }

    private String extraString(String content, Pattern pattern) {
        Matcher matcher = pattern.matcher(content);
        if (matcher.find() && matcher.groupCount() >= 1) {
            return matcher.group(1).trim();
        }
        return "";
    }


    public static void main(String[] args) {
        MovieParser parser = new MovieParser();
        OkHttpClient client = new OkHttpClient();
        OkHttpCrawler crawler = new OkHttpCrawler(client);
//        String url = "https://book.douban.com/tag";
//        String content = crawler.crawl(url);
//        parser.parse(content);
//        String url2 = "https://book.douban.com/tag/%E5%B0%8F%E8%AF%B4";
//        String content2 = crawler.crawl(url2);
//        parser.parse(content2);
//        System.out.println(content2);
        String url3 = "https://www.netflix.com/hk-en/title/81714054";
        String content3 = crawler.crawl(url3);
//        System.out.println(content3);
        parser.parse(content3);

    }
}
