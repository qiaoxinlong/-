package util;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

public class UrlUtil {

    // 	public static String BASE_URL = GetConfigUtil.getTestProperty("config","test_env");
    public static String TOTAL_URL;


    public static String getTotalUrl(String endpoint, List<BasicNameValuePair> params) {

        if (params == null) {
            TOTAL_URL = endpoint;
        } else {
            String param = URLEncodedUtils.format(params, "UTF-8");
            TOTAL_URL = endpoint + "?" + param;
        }

        return TOTAL_URL;
    }
}
