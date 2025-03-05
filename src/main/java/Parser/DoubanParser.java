package Parser;

import Crawler.OkHttpCrawler;
import POJO.Book;
import URLManager.URLManagerBook;
import URLManager.BookManager;
import okhttp3.OkHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoubanParser implements Parser {
    @Override
    public void parse(String content) {
        if (content == null || content.isEmpty()) {
            System.out.println("内容为空，无法解析。");
            return;
        }
        if (content.contains("豆瓣图书标签:") ) {
            parseList(content);
        } else if (content.contains("豆瓣图书标签")) {
            parseTag(content);
        }
        else if (content.contains("v:itemreviewed")) {
            parseBookDetail(content);
        }



    }
    private void parseTag(String content) {
        Document doc = Jsoup.parseBodyFragment(content);
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String href = link.attr("href");
            // Use regex to filter for hrefs that start with "/tag/"
            if (href.matches("^/tag/.+")) {
                String fullUrl = "https://book.douban.com" + href;
                System.out.println("Tag Link: " + fullUrl);
                URLManagerBook urlManager = new URLManagerBook();
                urlManager.addCrawlingURL(href);
            }
        }
    }
    private void parseList(String content) {
        Document doc = Jsoup.parseBodyFragment(content);
        Elements links = doc.select("a[href]");
        // Compile the regex pattern to match book URLs
        Pattern pattern = Pattern.compile("https://book\\.douban\\.com/subject/(\\d+)/");

        // Iterate through each link and match against the pattern
        for (Element link : links) {
            String href = link.attr("href");
            Matcher matcher = pattern.matcher(href); // Match href against the regex pattern

            if (matcher.matches()) {
                System.out.println("Book URL: " + href);
                URLManagerBook urlManager = new URLManagerBook();
                urlManager.addCrawlingURL(href);
            }
        }
    }
    private static final Pattern bookNameRe = Pattern.compile("<span property=\"v:itemreviewed\">([^<]+)</span>");
    private static final Pattern authorRe = Pattern.compile("<span class=\"pl\"> 作者</span>:[\\d\\D]*?<a.*?>([^<]+)</a>");
    private static final Pattern publicRe = Pattern.compile("<span class=\"pl\">出版社:</span>[\\d\\D]*?<a.*?>([^<]+)</a>");
    private static final Pattern pageRe = Pattern.compile("<span class=\"pl\">页数:</span> ([^<]+)<br/>");
    private static final Pattern priceRe = Pattern.compile("<span class=\"pl\">定价:</span>([^<]+)<br/>");
    private static final Pattern yearRe = Pattern.compile("<span class=\"pl\">出版年:</span>([^<]+)<br/>");
    private static final Pattern ISBNRe = Pattern.compile("<span class=\"pl\">ISBN:</span>([^<]+)<br/>");
    private static final Pattern ratingRe = Pattern.compile("<div class=\"rating_self clearfix\" typeof=\"v:Rating\">[\\d\\D]*?<strong class=\"ll rating_num \" property=\"v:average\">\\s*([^<]+)\\s*</strong>");
    private void parseBookDetail(String content) {

        String bookName = extraString(content, bookNameRe);
        String author = extraString(content, authorRe);
        String publisher = extraString(content, publicRe);
        String publicationYear = extraString(content, yearRe);
        String pageCountString = extraString(content, pageRe);
        String priceString = extraString(content, priceRe);
        String isbn = extraString(content, ISBNRe);
        String ratingString = extraString(content,ratingRe);
//        int pageCount = Integer.parseInt(pageCountString); // Convert to int
//        double price = Double.parseDouble(priceString);    // Convert to double
//        double rating = Double.parseDouble(ratingString);  // Convert to double
        System.out.println("书名: " + bookName);
        System.out.println("作者: " + author);
        System.out.println("出版社: " + publisher);
        System.out.println("出版年: " + publicationYear);
        System.out.println("页数: " + pageCountString);
        System.out.println("定价: " + priceString);
        System.out.println("ISBN: " + isbn);
        System.out.println("评分: " + ratingString);
        BookManager bookManager = new BookManager();
        bookManager.addBook(new Book(bookName,author,publisher,publicationYear,pageCountString,priceString,isbn,ratingString));

    }

    private String extraString(String content, Pattern pattern) {
        Matcher matcher = pattern.matcher(content);
        if (matcher.find() && matcher.groupCount() >= 1) {
            return matcher.group(1).trim();
        }
        return "";
    }


    public static void main(String[] args) {
        DoubanParser parser = new DoubanParser();
        OkHttpClient client = new OkHttpClient();
        OkHttpCrawler crawler = new OkHttpCrawler(client);
//        String url = "https://book.douban.com/tag";
//        String content = crawler.crawl(url);
//        parser.parse(content);
//        String url2 = "https://book.douban.com/tag/%E5%B0%8F%E8%AF%B4";
//        String content2 = crawler.crawl(url2);
//        parser.parse(content2);
//        System.out.println(content2);
        String url3 = "https://book.douban.com/subject/36104107/";
        String content3 = crawler.crawl(url3);
        parser.parse(content3);

    }
}
