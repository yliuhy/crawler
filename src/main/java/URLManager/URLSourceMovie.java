package URLManager;



import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.ArrayList;
import java.util.List;

public class URLSourceMovie implements SourceFunction<String> {

    private volatile boolean running = true;

    @Override
    public void run(SourceContext<String> runtimeContext) throws Exception {
        while (running) {
            // Retrieve URLs to crawl from the database
            List<String> urls = new ArrayList<>();
            // Here you can call the URLManagerMovie method to get URLs
            urls = URLManagerMovie.getCrawlingURLs(); // Ensure this method exists

            for (String url : urls) {
                synchronized (this) {
                    collect(runtimeContext, url);
                }
                // Simulate processing interval
                Thread.sleep(1000);
            }
        }
    }

    @Override
    public void cancel() {
        running = false;
    }

    private void collect(SourceContext<String> ctx, String url) {
        // Send the URL to downstream
        ctx.collect(url); // Use ctx.collect() to send data downstream
        System.out.println("收集到URL: " + url);
    }
}