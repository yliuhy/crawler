package URLManager;
import Crawler.OkHttpCrawler;
import Dispatcher.Dispatcher;
import Parser.DoubanParser; // Update import if necessary
import Parser.MovieParser;  // Assume you have a MovieParser class
import okhttp3.OkHttpClient;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLStreamingManager {
    private static final Logger logger = LoggerFactory.getLogger(URLStreamingManager.class);
    private final String type; // Parameter to determine if it's for books or movies
    private final String dispatcherType; // Parameter for dispatcher type
    private final int dispatcherCount; // Parameter for number of dispatchers

    public URLStreamingManager(String type, String dispatcherType, int dispatcherCount) {
        this.type = type.toLowerCase(); // Normalize to lower case
        this.dispatcherType = dispatcherType;
        this.dispatcherCount = dispatcherCount;
    }

    public void start() {
        try {
            // Create the execution environment
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


            // Create the Dispatcher with a fixed number of workers (e.g., 3)
            Dispatcher dispatcher = new Dispatcher(3);


            // Create the Dispatcher based on the type

            if ("random".equalsIgnoreCase(dispatcherType)) {
                // Submit job to fetch URLs from the database
                ((Dispatcher) dispatcher).submitJob("urlJob", "random");
            } else {
                // Submit job to fetch URLs from the database
                ((Dispatcher) dispatcher).submitJob("urlJob", "roundRobin");
            }
            // Create a data source
            DataStreamSource<String> urlStream = env.addSource(new URLSourceMovie());

            // Process the URLs
            urlStream
                    .map(new MapFunction<String, String>() {
                        private transient OkHttpCrawler crawler; // Declare as transient
                        private transient Object parser; // Declare as transient, to be initialized based on type
                        private transient Object urlManager; // Declare as transient

                        @Override
                        public String map(String url) {
                            logger.debug("Processing URL: {}", url);
                            // Create crawler instance
                            crawler = new OkHttpCrawler(new OkHttpClient());
                            parser = new MovieParser();
                            urlManager = new URLManagerMovie();
                            // Call the crawler to fetch content
                            String content = crawler.crawl(url);
                            // Call the parser to parse content
                            ((MovieParser) parser).parse(content);
                            ((URLManagerMovie) urlManager).updateUrlStatus(url);
                            return url;
                        }
                    })
                    .print();
            // Execute the task
            env.execute("URL Streaming Manager");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

//            if(type.equals("book")){
//                // Create a data source
//                DataStreamSource<String> urlStream = env.addSource(new URLSourceBook());
//
//                // Process the URLs
//                urlStream
//                        .map(new MapFunction<String, String>() {
//                                 private transient OkHttpCrawler crawler; // Declare as transient
//                                 private transient Object parser; // Declare as transient, to be initialized based on type
//                                 private transient Object urlManager; // Declare as transient
//
//                                 @Override
//                                 public String map(String url) {
//                                     logger.debug("Processing URL: {}", url);
//                                     // Create crawler instance
//                                     crawler = new OkHttpCrawler(new OkHttpClient());
//                                     parser = new DoubanParser();
//                                     urlManager = new URLManagerBook();
//                                     // Call the crawler to fetch content
//                                     String content = crawler.crawl(url);
//                                     // Call the parser to parse content
//                                     ((DoubanParser) parser).parse(content);
//                                     ((URLManagerBook) urlManager).updateUrlStatus(url);
//                                     return url;
//                                 }
//                        })
//                        .print();
//                // Execute the task
//                env.execute("URL Streaming Manager");
//
//            }
//            else if("movie".equals(type)){
//                // Create a data source
//                DataStreamSource<String> urlStream = env.addSource(new URLSourceMovie());
//
//                // Process the URLs
//                urlStream
//                        .map(new MapFunction<String, String>() {
//                            private transient OkHttpCrawler crawler; // Declare as transient
//                            private transient Object parser; // Declare as transient, to be initialized based on type
//                            private transient Object urlManager; // Declare as transient
//
//                            @Override
//                            public String map(String url) {
//                                logger.debug("Processing URL: {}", url);
//                                // Create crawler instance
//                                crawler = new OkHttpCrawler(new OkHttpClient());
//                                parser = new MovieParser();
//                                urlManager = new URLManagerMovie();
//                                // Call the crawler to fetch content
//                                String content = crawler.crawl(url);
//                                // Call the parser to parse content
//                                ((MovieParser) parser).parse(content);
//                                ((URLManagerMovie) urlManager).updateUrlStatus(url);
//                                return url;
//                            }
//                        })
//                        .print();
//                // Execute the task
//                env.execute("URL Streaming Manager");
//
//            }
//            } catch (Exception ex) {
//            throw new RuntimeException(ex);




