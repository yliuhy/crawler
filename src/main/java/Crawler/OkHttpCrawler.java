package Crawler;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OkHttpCrawler implements Crawler {
    private OkHttpClient client;
    private static final List<Proxy> PROXIES;

// 自定义ip接口 目前这些已过期
    static {
        // Initialize the constant list of proxies
        PROXIES = new ArrayList<>();
        PROXIES.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("58.60.255.104", 8118)));
        PROXIES.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("219.135.164.245", 3128)));
        PROXIES.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("27.44.171.27", 9999)));
        PROXIES.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("58.252.6.165", 9000)));
    }

    public OkHttpCrawler(OkHttpClient client) {
        this.client = client;
    }

    @Override
    public String crawl(String url) {
        try {
//            Proxy proxy = selectRandomProxy();
//            OkHttpClient proxyClient;
//            proxyClient = client.newBuilder().proxy(proxy).build();

//            // create a OkHttpClient builder instance and configure it to use the proxy
//            OkHttpClient proxyClient = new OkHttpClient.Builder()
//                    .proxy(proxy)
//                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                System.out.println("抓取失败，状态码: " + response.code());
                return null;
            }
        } catch (Exception e) {
            System.out.println("抓取异常: " + e.getMessage());
            return null;
        }
    }
    private Proxy selectRandomProxy() {
        Random random = new Random();
        int index = random.nextInt(PROXIES.size());  // Select a random index
        System.out.println("ip " + PROXIES.get(index));
        return PROXIES.get(index);
    }

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        OkHttpCrawler crawler = new OkHttpCrawler(client);
        String url = "https://list.suning.com/";
        String content = crawler.crawl(url);
        System.out.println("抓取内容: " + content);
    }
}