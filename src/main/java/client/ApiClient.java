package client;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import util.UrlUtil;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class ApiClient {
    private static int httpCode = -1;
    private static HttpPost post;
    private static HttpGet get;
    private static HttpResponse response;
    private static HttpEntity entity;
    static String result = "";
    private static String token="156935a1-e893-46ba-a2b2-f6e26829d81b";
    private static String uid="220000017";


    private final static ThreadLocal<CloseableHttpClient> CLIENT_CACHE = new InheritableThreadLocal<CloseableHttpClient>() {
        @Override
        protected CloseableHttpClient initialValue() {

            SSLContext sslContext = null;
            try {
                sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        return true;
                    }
                }).build();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(HttpConstants.Connection_Timeout)
                    .setConnectTimeout(HttpConstants.Connection_Timeout)
                    .setConnectionRequestTimeout(HttpConstants.Connection_Timeout).build();

            CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig)
                    .setSSLSocketFactory(sslsf).build();

            return client;

        }
    };

    private static Header JSON_TYPE_HEADER = new BasicHeader(HttpConstants.Content_Type, HttpConstants.JSON_MEDIA_TYPE);

    public static String Get(String url) {
        get = new HttpGet(UrlUtil.getTotalUrl(url, null));
        get.setHeader(JSON_TYPE_HEADER);
        get.setHeader("token",token);
        get.setHeader("uid",uid);
        try {
            response = CLIENT_CACHE.get().execute(get);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        entity = response.getEntity();
        httpCode = response.getStatusLine().getStatusCode();

        try {
            result = EntityUtils.toString(entity);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String Post(String url, String jsonBody) {
        post = new HttpPost(UrlUtil.getTotalUrl(url, null));
        post.setHeader(JSON_TYPE_HEADER);
        post.setHeader("token",token);
        post.setHeader("uid",uid);

        StringEntity s;
        s = new StringEntity(jsonBody, "utf-8");
        s.setContentType("application/json");
        post.setEntity(s);
        try {
            response = CLIENT_CACHE.get().execute(post);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        entity = response.getEntity();
        httpCode = response.getStatusLine().getStatusCode();
        try {
            result = EntityUtils.toString(entity);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
