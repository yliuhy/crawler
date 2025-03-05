package URLManager;


import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.ArrayList;
import java.util.List;

public class URLSourceBook implements SourceFunction<String> {

    private volatile boolean running = true;

    @Override
    public void run(SourceContext<String> runtimeContext) throws Exception {
        while (running) {
//             从数据库中读取待爬取的URL
            List<String> urls = new ArrayList<>();
            // 这里可以调用URLManager中的方法获取待爬取的URL
             urls = URLManagerBook.getCrawlingURLs();
            for (String url : urls) {
                synchronized (this) {
                    collect(runtimeContext, url);
                }
                // 模拟处理间隔
                Thread.sleep(1000);
            }


        }
    }

    @Override
    public void cancel() {
        running = false;
    }

    private void collect(SourceContext<String> ctx, String url) {
        // 将URL发送到下游
        ctx.collect(url); // 使用ctx.collect()发送数据到下游
        System.out.println("收集到URL: " + url);
    }
}