package SmallApp.BaseUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import static SmallApp.BaseUtil.publicMethod.requestvo;

/**
 * @author cuixiaohui
 */
public class HttpUtils {
    static String appVersionNumber = "100690009";

    public static ResponseVo login(String mobile, String passwd, String enterprise_account, String url) {
        RequestVo rv = new RequestVo();
        HashMap params = new HashMap();
        params.put("EnterpriseAccount", enterprise_account);
        params.put("UserAccount", mobile);
        params.put("Password", passwd);
        params.put("PersistenceHint", Boolean.valueOf(true));
        System.out.println(params);
        HashMap headers = new HashMap();
        headers.put("Content-Type", "application/json");
        rv.setRequestUrl(url);
        rv.setRquestHeaders(headers);
        rv.setRequestParams(params);
        return httpPostRaw(rv);
    }

    public static ResponseVo loginMobile(String mobile, String passwd, String enterprise_account, String url) {
        String publicKey = getLoginPubicKey();
        String passwordByEncryption = encryptPassword(passwd, publicKey);
        String requestUrl = "https://www.fxiaoke.com/FHE/EM0AUL/Authorize/EnterpriseAccountLogin" + "/iOS.100690009?_vn=100690009";
        RequestVo rv = new RequestVo();
        HashMap params = new HashMap();
        params.put("M1", enterprise_account);
        params.put("M2", mobile);
        params.put("M3", passwordByEncryption);  //加密后的密码
        params.put("M4", publicKey);  //公钥
        params.put("M7", "DAC9D258-5DBC-47D6-B3D1-1ED43FFDA608");  //设备信息
        params.put("M8", "iPhone6,2");  //设备型号
//        params.put("Password", passwd);
//        params.put("PersistenceHint", Boolean.valueOf(true));
        System.out.println(params);
        HashMap headers = new HashMap();
        headers.put("Content-Type", "application/json");
        rv.setRequestUrl(requestUrl);
        rv.setRquestHeaders(headers);
        rv.setRequestParams(params);
        return httpPostRaw(rv);
    }

    //手机端根据公钥加密密码
    private static String encryptPassword(String password, String publicKey) {
        String key = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
            key = Base64.encodeBase64String(cipher.doFinal(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    //获取公钥
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    //手机端获取登录密码公钥
    public static String getLoginPubicKey() {
        String requestUrl = "https://www.fxiaoke.com/FHE/EM0AUL/Authorize/GetInitLoginData"
                + "/iOS." + appVersionNumber + "?_vn=" + appVersionNumber;
        HashMap headers = new HashMap();
        headers.clear();
        headers.put("Content-Type", "application/xml");
        HashMap params = new HashMap();
        params.clear();
        params.put("", "");
        requestvo.setRequestVo(headers, params, requestUrl);
        ResponseVo responsevo = httpPostRaw(requestvo);
        JSONObject json = JSONObject.parseObject(XmlUtils.getElementText(responsevo.getResponseStr(), "Data"));
        return json.getString("M1");
    }

    /**
     * get request
     *
     * @param //RequestVo 通过hashmap赋值获得参数键值对，以及请求头信息
     * @param //headers   http请求头信息
     * @return InterfaceReturnVo 接口调用返回结果数据对象，包括cookie信息及json串
     * @throws Exception
     */

    @SuppressWarnings({"deprecation", "null"})
    public static ResponseVo httpGet(RequestVo requestvo) throws Exception {

        ResponseVo responsevo = new ResponseVo();
        List<Cookie> responsecookie = null;
        JSONObject json = null;
        String responseStr = null;
        String rUrl = requestvo.getRequestUrl() + "?";
        @SuppressWarnings({"resource"}) HttpClient client = new DefaultHttpClient();
        CookieStore cookieStore = new BasicCookieStore();
        HttpContext localContext = new BasicHttpContext();
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        rUrl = setGetRequestParam(requestvo, rUrl);
        System.out.println("request url is : " + rUrl);
        System.out.println("Resuest method : GET");
        try {
            HttpGet httpget = new HttpGet(requestvo.getRequestUrl());
            if (!requestvo.getRequestHeaders().isEmpty()) {
                Set<?> entrySet = requestvo.getRequestHeaders().entrySet();
                for (Iterator<?> itor = entrySet.iterator(); itor.hasNext(); ) {
                    Entry<?, ?> entry = (Entry<?, ?>) itor.next();
                    httpget.addHeader(entry.getKey().toString(), entry.getValue().toString());
                }
            }

            long startTime = System.currentTimeMillis();
            HttpResponse httpResponse = client.execute(httpget, localContext);
            long endTime = System.currentTimeMillis();
            System.out.println("Response time is :" + (endTime - startTime) + "ms");
            responsecookie = cookieStore.getCookies();
            responsevo.setCookies(responsecookie);
            int responseCode = httpResponse.getStatusLine().getStatusCode();
            responsevo.setHttpCode(responseCode);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                //			        org.apache.http.Header[] headers= httpResponse.getAllHeaders();
                //		            // 打印所有响应头
                //		            for(org.apache.http.Header h:headers){
                //		                System.out.println(h.getName()+":"+h.getValue());
                //		            }
                //System.out.println(head.getName()+"**********"+head.getValue());
                org.apache.http.Header head = httpResponse.getFirstHeader("Content-Type");
                if (head.getValue().contains("application/json")) {
                    json = JSON.parseObject(EntityUtils.toString(entity));
                    System.err.println("Response is :" + json);
                    responsevo.setJson(json);
                } else {
                    responseStr = EntityUtils.toString(entity);
                    System.err.println("Response is :" + responseStr.length());
                    responsevo.setResponseStr(responseStr);
                }
            } else {
                System.err.println("http response is null");
            }
            httpget.abort();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responsevo;
    }

    public static ResponseVo httpGetURL(RequestVo requestvo) {
        // TODO Auto-generated method stub

        ResponseVo interfacereturnvo = new ResponseVo();
        JSONObject json = new JSONObject();
        try {
            URL url = new URL(requestvo.getRequestUrl());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            // urlConnection.setRequestProperty("Content-type",
            // "application/x-www-form-urlencoded");
            // urlConnection.setRequestProperty("Cookie",cookieinfo+";");

            if (!requestvo.getRequestHeaders().isEmpty()) {

                Set<?> entrySet = requestvo.getRequestHeaders().entrySet();

                for (Iterator<?> itor = entrySet.iterator(); itor.hasNext(); ) {
                    Entry<?, ?> entry = (Entry<?, ?>) itor.next();
                    urlConnection.setRequestProperty(entry.getKey().toString(), entry.getValue().toString());
                }

            }
            System.out.println("Resuest url is : " + requestvo.getRequestUrl());

            System.out.println("Resuest method : DELETE");

            InputStream in = urlConnection.getInputStream();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(in));
            StringBuffer temp = new StringBuffer();

            String line = bufferedreader.readLine();
            while (line != null) {
                temp.append(line).append("");
                line = bufferedreader.readLine();
            }

            json = JSONObject.parseObject(temp.toString());

            System.err.println("Response is :" + json);

            interfacereturnvo.setJson(json);

            // System.out.println("json is : "+json);

            urlConnection.disconnect();

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return interfacereturnvo;
    }

    /**
     * Post 请求 通过raw方式提交
     */
    public static ResponseVo httpPostRaw(RequestVo requestvo) {
        //需要在url后面增加?_fs_token=PZ9ZE3CuDMKjOpXbOYqqOsCmBM8qEMCjOMKuDZ4uC3PaPJKp
        if (requestvo.getRequestHeaders() != null) {
            String cookie = requestvo.getRequestHeaders().get("cookie");
            if (cookie != null) {
                String[] cookieList = cookie.split(";");
                String fs_token = "";
                for (int i = 0; i < cookieList.length; i++) {
                    String temp = cookieList[i].toString().trim();
                    if (temp.contains("fs_token=")) {
                        fs_token = temp.substring("fs_token=".length());
                        break;
                    }
                }
//        System.out.println("fs_token is "+ fs_token);
                String url = requestvo.getRequestUrl() + "?_fs_token=" + fs_token;
                //更新url
                requestvo.setRequestUrl(url);
            }
        }

        ResponseVo responsevo = new ResponseVo();
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        JSONObject json = new JSONObject();
        HttpContext localContext = new BasicHttpContext();
        try {
            httpClient = HttpClients.createDefault();

            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();
            httpPost = new HttpPost(requestvo.getRequestUrl());

            HashMap<String, String> newHeaders = requestvo.getRequestHeaders();
            if (requestvo.getRequestUrl().contains("FHE")) {
                newHeaders.put("Content-Type", "application/xml");
                newHeaders.put("Accept", "application/xml");
            }
            addAllHeaders(httpPost, newHeaders);
            httpPost.setConfig(requestConfig);
            setAllBody(httpPost, requestvo);
            long startTime = System.currentTimeMillis();
            CloseableHttpResponse response = httpClient.execute(httpPost, localContext);
            long endTime = System.currentTimeMillis();
            System.out.println("Response time is :" + (endTime - startTime) + "ms");
            @SuppressWarnings("deprecation") CookieStore cookieStore = (CookieStore) localContext.getAttribute(ClientContext.COOKIE_STORE);
            responsevo.setCookies(cookieStore.getCookies());
            int responseCode = response.getStatusLine().getStatusCode();
            responsevo.setHttpCode(responseCode);

            if (responseCode == 200 || responseCode == 201) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    if (requestvo.getRequestUrl().contains("FHE")) {
                        String responseStr = EntityUtils.toString(entity);
                        responsevo.setResponseStr(responseStr);
                        String data = XmlUtils.getElementTextByXpath("FHE/Data", responseStr);
                        System.out.println(">>>>>>>>" + data);
                        responsevo.setJson(JSON.parseObject(data));
                        System.out.println("responseStr is:" + responseStr);
                    } else {
                        json = JSON.parseObject(EntityUtils.toString(entity));
                        responsevo.setJson(json);
                        System.out.println("responseStr is:" + json.toJSONString());
                    }
                } else {
                    System.out.println("entity is null");
                }
            } else {
                System.out.println(EntityUtils.toString(response.getEntity()));
                System.out.println("http POST request return error, error code is " + responseCode);
            }
            httpPost.abort();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responsevo;
    }

    /**
     * 上传文件的类型
     */
    public static ResponseVo httpPostMultipart(RequestVo requestvo, ArrayList<UploadFileItem> filesToBeUploaded) {
        ResponseVo result = new ResponseVo();
        JSONObject json = new JSONObject();
        String BOUNDARY = "----------HV2ymHFg03ehbqgZCaKO6jyH";
        URL url;
        try {
            url = new URL(requestvo.getRequestUrl());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            if (!requestvo.getRequestHeaders().isEmpty()) {
                Set<?> entrySet = requestvo.getRequestHeaders().entrySet();
                for (Iterator<?> itor = entrySet.iterator(); itor.hasNext(); ) {
                    Entry<?, ?> entry = (Entry<?, ?>) itor.next();
                    urlConnection.setRequestProperty(entry.getKey().toString(), entry.getValue().toString());
                }
            }
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            StringBuffer contentBody = new StringBuffer("--" + BOUNDARY);
            String boundary = BOUNDARY;
            String endBoundary = "\r\n--" + boundary + "--\r\n";
            OutputStream out = urlConnection.getOutputStream();
            if (filesToBeUploaded != null) {
                for (UploadFileItem ufi : filesToBeUploaded) {
                    contentBody = new StringBuffer();
                    //					contentBody.append("\r\n")
                    //
                    //							.append("Content-Disposition:form-data; name=\"")
                    //
                    //							.append(ufi.getFormFieldName() + "\"; ") // form中field的名称
                    //
                    //							.append("filename=\"")
                    //
                    //							.append(ufi.getFileName() + "\"") // 上传文件的文件名，包括目录
                    //
                    //							.append("\r\n")
                    //
                    //							.append("Content-Type:application/octet-stream")
                    //
                    //							.append("\r\n\r\n");
                    //
                    //					String boundaryMessage2 = contentBody.toString();
                    //
                    //					out.write(boundaryMessage2.getBytes("utf-8"));

                    // 开始真正向服务器写文件

                    File file = new File(ufi.getFileName());

                    DataInputStream dis = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[(int) file.length()];
                    bytes = dis.read(bufferOut);
                    out.write(bufferOut, 0, bytes);
                    dis.close();
                    contentBody.append("------------HV2ymHFg03ehbqgZCaKO6jyH");
                    String boundaryMessage = contentBody.toString();
                    out.write(boundaryMessage.getBytes("utf-8"));
                    // System.out.println(boundaryMessage);
                    out.flush();
                    out.close();
                    // 4. 从服务器获得回答的内容
                    String strLine = "";
                    String strResponse = "";
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    while ((strLine = reader.readLine()) != null) {
                        strResponse += strLine + "\n";
                        json = JSON.parseObject(strResponse);
                    }
                    result.setJson(json);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


    public static ResponseVo httpPostURL(String requesturl, byte[] body, HashMap<String, String> headers) {
        // TODO Auto-generated method stub
        int HTTP_TIMEOUT = 30 * 1000;
        boolean useCache = false;
        int BUFFER_SIZE = 4096;
        URL url;
        ResponseVo responseVo = new ResponseVo();
        try {
            url = new URL(requesturl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (headers != null) {
                Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, String> entry = (Entry<String, String>) iterator.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    conn.setRequestProperty(key, value);
                }
            }

            conn.setRequestMethod("POST");
            conn.setInstanceFollowRedirects(false);
            conn.setDoOutput(true);
            conn.setConnectTimeout(HTTP_TIMEOUT);
            conn.setUseCaches(useCache);
            if (body != null) {
                OutputStream outStream = conn.getOutputStream();
                outStream.write(body);
                outStream.flush();
                outStream.close();
            } else {
                conn.connect();
            }

            int resCode = conn.getResponseCode();
            Map<String, List<String>> headerFields = conn.getHeaderFields();
            InputStream resInputStream = conn.getInputStream();

            byte[] resByte = null;
            String resString = null;
            if (resInputStream != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] data = new byte[BUFFER_SIZE];
                int count = -1;
                while ((count = resInputStream.read(data, 0, BUFFER_SIZE)) != -1) {
                    baos.write(data, 0, count);
                }
                data = null;
                resByte = baos.toByteArray();
                resString = new String(resByte);
            }
            System.out.println("response is :" + resString);
            responseVo.setResponseStr(resString);
            responseVo.setHttpCode(resCode);
            conn.disconnect();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return responseVo;
    }

    /**
     * post request 通过表单方式提交
     *
     * @param requestvo
     * @param //cookiename 本次接口调用需用到的其他接口cookie名
     * @return InterfaceReturnVo 接口调用返回结果数据对象，包括cookie信息及json串
     */
    @SuppressWarnings("deprecation")
    public static ResponseVo httpPost(RequestVo requestvo) {

        ResponseVo responsevo = new ResponseVo();

        @SuppressWarnings({"resource"}) HttpClient client = new DefaultHttpClient();

        CookieStore cookieStore = new BasicCookieStore();

        HttpContext localContext = new BasicHttpContext();

        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

        List<Cookie> responsecookie = null;

        JSONObject json = new JSONObject();

        try {
            HttpPost httppost = new HttpPost(requestvo.getRequestUrl());

            if (!requestvo.getRequestHeaders().isEmpty() || null != requestvo.getRequestHeaders()) {

                Set<?> entrySet = requestvo.getRequestHeaders().entrySet();

                for (Iterator<?> itor = entrySet.iterator(); itor.hasNext(); ) {
                    Entry<?, ?> entry = (Entry<?, ?>) itor.next();
                    httppost.addHeader(entry.getKey().toString(), entry.getValue().toString());
                }

            }
            if (!requestvo.getRequestParams().isEmpty() || null != requestvo.getRequestParams()) {

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                Set<?> set = requestvo.getRequestParams().entrySet();

                Iterator<?> iterator = set.iterator();

                while (iterator.hasNext()) {
                    @SuppressWarnings("rawtypes") Entry mapentry = (Entry) iterator.next();
                    parameters.add(new BasicNameValuePair(mapentry.getKey().toString(), mapentry.getValue().toString()));
                }

                System.out.println("Request url: " + requestvo.getRequestUrl());

                System.err.println("Request par: " + parameters);

                System.out.println("Resuest method : POST");

                UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(parameters, "UTF-8");

                httppost.setEntity(formEntiry);
            }

            long startTime = System.currentTimeMillis();
            HttpResponse httpResponse = client.execute(httppost, localContext);
            long endTime = System.currentTimeMillis();
            System.out.println("Response time is :" + (endTime - startTime) + "ms");

            responsecookie = cookieStore.getCookies();

            responsevo.setCookies(responsecookie);

            int responseCode = httpResponse.getStatusLine().getStatusCode();
            responsevo.setHttpCode(responseCode);
            if (responseCode == 200 || responseCode == 201) {
                HttpEntity entity = httpResponse.getEntity();

                if (entity != null) {

                    json = JSON.parseObject(EntityUtils.toString(entity));

                    responsevo.setJson(json);

                    System.err.println("Response is :" + json);
                } else {
                    System.out.println("entity is null");
                }
            } else {
                System.out.println(EntityUtils.toString(httpResponse.getEntity()));
                System.out.println("http POST request return error, error code is " + responseCode);
            }
            httppost.abort();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return responsevo;
    }

    /**
     * delete request httpurlconnect
     *
     * @param requestvo
     * @return InterfaceReturnVo 接口调用返回结果数据对象，包括cookie信息及json串
     * @param// header
     * 需要设置的header头信息
     */
    public static ResponseVo httpdelete(RequestVo requestvo) {
        // TODO Auto-generated method stub

        ResponseVo interfacereturnvo = new ResponseVo();
        JSONObject json = new JSONObject();

        try {
            URL url = new URL(requestvo.getRequestUrl());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            if (!requestvo.getRequestHeaders().isEmpty()) {
                Set<?> entrySet = requestvo.getRequestHeaders().entrySet();
                for (Iterator<?> itor = entrySet.iterator(); itor.hasNext(); ) {
                    Entry<?, ?> entry = (Entry<?, ?>) itor.next();
                    urlConnection.setRequestProperty(entry.getKey().toString(), entry.getValue().toString());
                }
            }
            System.out.println("Resuest url is : " + requestvo.getRequestUrl());
            System.out.println("Resuest method : DELETE");
            InputStream in = urlConnection.getInputStream();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(in));
            StringBuffer temp = new StringBuffer();
            String line = bufferedreader.readLine();
            while (line != null) {
                temp.append(line).append("");
                line = bufferedreader.readLine();
            }
            json = JSONObject.parseObject(temp.toString());
            System.err.println("Response is :" + json);
            interfacereturnvo.setJson(json);
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return interfacereturnvo;
    }

    /**
     * delete request 使用httpclient
     *
     * @param requestvo
     * @param //header  需要设置的header头信息
     * @return InterfaceReturnVo 接口调用返回结果数据对象，包括cookie信息及json串
     */
    @SuppressWarnings("deprecation")
    public static ResponseVo httpDeleteWithBody(RequestVo requestvo) {
        // 接口返回信息类
        ResponseVo interfacereturnvo = new ResponseVo();

        CookieStore cookieStore = new BasicCookieStore();

        HttpContext localContext = new BasicHttpContext();

        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

        List<Cookie> responsecookie = null;

        JSONObject json = new JSONObject();

        @SuppressWarnings("resource") HttpClient httpClient = new DefaultHttpClient();

        try {

            MyHttpDelete httpdelete = new MyHttpDelete(requestvo.getRequestUrl());

            if (!requestvo.getRequestHeaders().isEmpty()) {

                Set<?> entrySet = requestvo.getRequestHeaders().entrySet();

                for (Iterator<?> itor = entrySet.iterator(); itor.hasNext(); ) {
                    Entry<?, ?> entry = (Entry<?, ?>) itor.next();

                    httpdelete.addHeader(entry.getKey().toString(), entry.getValue().toString());
                }
            }
            if (requestvo != null) {
                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                HashMap<String, Object> hashmap = requestvo.getRequestParams();
                Set<?> set = hashmap.entrySet();
                Iterator<?> iterator = set.iterator();
                while (iterator.hasNext()) {
                    @SuppressWarnings("rawtypes") Entry mapentry = (Entry) iterator.next();
                    parameters.add(new BasicNameValuePair(mapentry.getKey().toString(), mapentry.getValue().toString()));
                }
                System.out.println("Request url: " + requestvo.getRequestUrl());
                System.err.println("Request par: " + parameters);
                System.out.println("Resuest method : DELETE");
                httpdelete.setEntity(new UrlEncodedFormEntity(parameters));
            }

            long startTime = System.currentTimeMillis();
            HttpResponse response = httpClient.execute(httpdelete, localContext);
            long endTime = System.currentTimeMillis();
            System.out.println("Response time is :" + (endTime - startTime) + "ms");
            responsecookie = cookieStore.getCookies();
            interfacereturnvo.setCookies(responsecookie);
            int responseCode = response.getStatusLine().getStatusCode();
            interfacereturnvo.setHttpCode(responseCode);
            if (responseCode == 200 || responseCode == 201) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    json = JSON.parseObject(EntityUtils.toString(entity));
                    System.err.println("Response is :" + json);
                    interfacereturnvo.setJson(json);
                } else {
                    System.out.println("entity is null");
                }
            } else {
                System.out.println(EntityUtils.toString(response.getEntity()));
                System.out.println("http POST request return error, error code is " + responseCode);
            }
            httpdelete.abort();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return interfacereturnvo;
    }

    /**
     * put request 使用httpurlconnect
     *
     * @param requestvo
     * @param //        header
     *                  需要设置的header头信息
     * @return InterfaceReturnVo 接口调用返回结果数据对象，包括cookie信息及json串
     */
    public static ResponseVo httpPut(RequestVo requestvo) {
        // TODO Auto-generated method stub

        ResponseVo interfacereturnvo = new ResponseVo();
        JSONObject json = new JSONObject();

        try {
            URL url = new URL(requestvo.getRequestUrl());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            if (!requestvo.getRequestHeaders().isEmpty()) {
                Set<?> entrySet = requestvo.getRequestHeaders().entrySet();
                for (Iterator<?> itor = entrySet.iterator(); itor.hasNext(); ) {
                    Entry<?, ?> entry = (Entry<?, ?>) itor.next();
                    urlConnection.setRequestProperty(entry.getKey().toString(), entry.getValue().toString());
                }
            }
            String strUpdateParam = "";
            if (requestvo != null) {
                HashMap<String, Object> hashmap = requestvo.getRequestParams();
                Set<?> set = hashmap.entrySet();
                Iterator<?> iterator = set.iterator();
                while (iterator.hasNext()) {
                    @SuppressWarnings("rawtypes") Entry mapentry = (Entry) iterator.next();
                    strUpdateParam += mapentry.getKey().toString() + "=" + mapentry.getValue().toString() + "&";
                }
                strUpdateParam = strUpdateParam.substring(0, strUpdateParam.length() - 1);
                System.out.println("Resuest url is : " + requestvo.getRequestUrl());
                System.err.println("Request params is : " + strUpdateParam);
                System.out.println("Resuest method : PUT");
            }
            PrintWriter out = new PrintWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "utf-8"));
            long startTime = System.currentTimeMillis();
            out.write(strUpdateParam);
            out.flush();
            out.close();
            InputStream in = urlConnection.getInputStream();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(in));
            StringBuffer temp = new StringBuffer();
            String line = bufferedreader.readLine();
            long endTime = System.currentTimeMillis();
            System.out.println("Response time is :" + (endTime - startTime) + "ms");
            while (line != null && (endTime = System.currentTimeMillis()) != 0) {
                temp.append(line).append("");
                line = bufferedreader.readLine();
            }
            json = JSONObject.parseObject(temp.toString());
            System.err.println("Response is :" + json);
            interfacereturnvo.setJson(json);
            // System.out.println("json is : "+json);
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return interfacereturnvo;
    }

    /**
     * get cookiestore value
     *
     * @param cookies    list
     * @param cookiename
     * @return cookie value of cookiename
     */

    public static String getCookieValue(List<Cookie> cookies, String cookiename) {
        String cookievalue = "";
        for (Iterator<Cookie> i = cookies.iterator(); i.hasNext(); ) {
            String cookiestr = i.next().toString();
            if (cookiestr.contains(cookiename)) {
                String[] strarray = cookiestr.split("\\]\\[");
                for (int j = 0; j < strarray.length; j++) {
                    if (strarray[j].contains("value")) {
                        String[] tmp = strarray[j].split(":");
                        cookievalue = tmp[1].trim();
                    }
                }
            }

        }
        return cookievalue;
    }

    /**
     * 获取参数列表 （已不用）
     *
     * @param //cookies    list
     * @param //cookiename
     * @return cookie value of cookiename
     */

    public static List<NameValuePair> getParamList(Object object) throws Exception {
        Class<?> objClass = object.getClass();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();

        while (objClass != null && !objClass.equals(Object.class)) {
            Field fields[] = objClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    if (null == field.get(object)) {
                        continue;
                    }
                } catch (Exception e) {
                    continue;
                }
                String value = "";
                value = field.get(object).toString();
                parameters.add(new BasicNameValuePair(field.getName(), value));
            }
            objClass = objClass.getSuperclass();
        }
        return parameters;
    }

    /**
     * 获取对象field（暂时不用）
     *
     * @return String URI
     * @throws Exception
     * @param//Object object
     */
    public static String get(Object object) throws Exception {
        Class<?> objClass = object.getClass();
        StringBuffer sb = new StringBuffer();
        while (objClass != null && !objClass.equals(Object.class)) {
            Field fields[] = objClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    if (null == field.get(object)) {
                        continue;
                    }
                } catch (Exception e) {
                    continue;
                }
                String value = "";
                value = field.get(object).toString();
                sb.append(field.getName() + "=" + value + "&");
            }
            objClass = objClass.getSuperclass();
        }
        String s = sb.toString();
        // System.out.println(s);
        s = s.substring(0, s.length() - 1);
        return s;
    }

    /**
     * 设置参数URI
     *
     * @param //Object object
     * @return String URI
     * @throws Exception
     */
    public static String prepareParam(HashMap<String, Object> hashMap) {
        StringBuffer result = new StringBuffer();
        if (hashMap.isEmpty()) {
            return "";
        } else {
            for (String key : hashMap.keySet()) {
                String value = (String) hashMap.get(key);
                if (result.length() < 1) {
                    result.append(key).append("=").append(value);
                } else {
                    result.append("&").append(key).append("=").append(value);
                }
            }
        }
        return result.toString();
    }

    /**
     * get randomSting
     *
     * @return Random String
     * @throws Exception
     */

    public static String randomString() {
        String randomstring = "";
        Random rand = new Random();
        int randnum = rand.nextInt(100);
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
        Date nowTime = new Date();
        randomstring = format.format(nowTime).toString() + String.valueOf(randnum);
        return randomstring;
    }

    public static boolean validateJson(JSONObject json) {
        // TODO Auto-generated method stub
        if (json.containsKey("errorCode") && json.containsKey("errorMsg")) {
            return true;
        }
        return false;
    }

    public static boolean isSSL(String requesturl) {

        if (requesturl.split(":")[0].equals("https")) {
            return true;
        } else {
            return false;
        }

    }

    public static String setGetRequestParam(RequestVo requestvo, String result) {
        if (requestvo != null) {

            if (!requestvo.getRequestParams().isEmpty()) {
                result += prepareParam(requestvo.getRequestParams());
                requestvo.setRequestUrl(result);
            } else {
                result = result.substring(0, result.length() - 1);
            }
        }
        return result;
    }

    public static HttpGet setGetRequestHeader(RequestVo requestvo, HttpGet httpget) {

        if (!requestvo.getRequestHeaders().isEmpty()) {

            Set<?> entrySet = requestvo.getRequestHeaders().entrySet();

            for (Iterator<?> itor = entrySet.iterator(); itor.hasNext(); ) {
                Entry<?, ?> entry = (Entry<?, ?>) itor.next();
                httpget.addHeader(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        return httpget;
    }

    public static String getMapValue(HashMap<String, String> map, String paramkey) {
        String result = "";
        if (map.isEmpty()) {
            result = "";
            return result;
        }
        Iterator<Entry<String, String>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, String> entry = iter.next();
            if (entry.getKey().equals(paramkey)) {
                result = entry.getValue();
                return result;
            }
        }
        return result;
    }

    public static JSONObject transformToJson(HashMap<String, Object> hashMap) {
        String jsonString = JSONObject.toJSONString(hashMap);
        return JSON.parseObject(jsonString);
    }

    //	public static JSONObject transformToJson(HashMap<String, Object> hashMap) {
    //		JSONObject result = null;
    //		StringBuffer sb = new StringBuffer();
    //		Set<?> entrySet = hashMap.entrySet();
    //		for (Iterator<?> itor = entrySet.iterator(); itor.hasNext();) {
    //			Entry<?, ?> entry = (Entry<?, ?>) itor.next();
    //			//string 非 list
    //			if(entry.getValue() instanceof String&&!(entry.getValue().toString().contains(","))){
    //				sb.append("\"" + entry.getKey().toString() + "\"" + ":" + "\"" + entry.getValue().toString() + "\"" + ",");
    //			}
    //			// list
    //			//if(!(entry.getValue() instanceof List)&&entry.getValue().toString().contains(",")&&!(entry.getValue().toString().contains("{")))
    //			else if(!(entry.getValue() instanceof List)&&entry.getValue().toString().contains(",")&&!(entry.getValue().toString().contains("{")))
    //			{
    //				sb.append("\"" + entry.getKey().toString() + "\"" + ":" +"["+ entry.getValue().toString() + "]"+",");
    //			}
    //			else if(entry.getValue() instanceof List)
    //			{
    //				sb.append("\"" + entry.getKey().toString() + "\"" + ":" +entry.getValue()+",");
    //			}
    //			// boolean
    //			else if(entry.getValue() instanceof Boolean)
    //			{
    //				sb.append(sb.append("\"" + entry.getKey().toString() + "\"" + ":" + entry.getValue() + ","));
    //			}
    //			else if (entry.getValue() instanceof Integer)
    //			{
    //				sb.append(sb.append("\"" + entry.getKey().toString() + "\"" + ":" + entry.getValue() + ","));
    //			}
    //			else if(entry.getValue() instanceof com.alibaba.fastjson.JSONObject)
    //			{
    //				sb.append("\"" + entry.getKey().toString() + "\"" + ":" + entry.getValue().toString() + ",");
    //			}else if((entry.getValue().toString().startsWith("["))&&(entry.getValue().toString().endsWith("]")))
    //			{
    //				sb.append("\"" + entry.getKey().toString() + "\"" + ":" + entry.getValue().toString() + ",");
    //			}else{
    //				sb.append("\"" + entry.getKey().toString() + "\"" + ":" + entry.getValue().toString() + ",");
    //			}
    //			//System.err.println("\r\n\r\ntransform is: " + entry.getKey().toString()+"  :  "+entry.getValue()+"\r\n\r\n");
    //		}
    //		String str = sb.toString().substring(0, sb.length() - 1);
    //		str = "{" + str + "}";
    //		System.out.println("\r\n\r\ntransform is: " + str+"\r\n\r\n");
    //		result = JSON.parseObject(str);
    //		return result;
    //	}

    private static void addAllHeaders(HttpPost httppost, HashMap<String, String> header) {
        if (!header.isEmpty() || null != header) {
            Set<?> entrySet = header.entrySet();
            for (Iterator<?> itor = entrySet.iterator(); itor.hasNext(); ) {
                Entry<?, ?> entry = (Entry<?, ?>) itor.next();
                httppost.addHeader(entry.getKey().toString(), entry.getValue().toString());
            }
        }
    }

    private static void setAllBody(HttpPost httpPost, RequestVo requestvo) {
        // TODO Auto-generated method stub
        System.out.println("Request url: " + requestvo.getRequestUrl());
        System.out.println("Request method : POST");
        System.out.println("Request Params : " + requestvo.getRequestParams());
        System.out.println("Request JsonStr : " + requestvo.getRequestJsonStr());
        if (!requestvo.getRequestParams().isEmpty() || null != requestvo.getRequestParams()) {
            //			try {
            JSONObject httpbody;
            if (requestvo.getRequestParams().size() == 0) {
                if (requestvo.getRequestJsonStr().length() == 0) {
                    httpbody = new JSONObject();
                } else {
                    httpbody = JSONObject.parseObject(requestvo.getRequestJsonStr());
                }
            } else {
                httpbody = transformToJson(requestvo.getRequestParams());
            }
            if (requestvo.getRequestUrl().contains("FHE")) {
                String requestxml = XmlUtils.getMobileRequestData(httpbody);
                httpPost.setEntity(new StringEntity(requestxml, "utf-8"));
                System.err.println("Request par: " + requestxml);
            } else if (requestvo.getRequestUrl().contains("FHH")) {
                httpPost.setEntity(new StringEntity(httpbody.toJSONString(), "utf-8"));
                System.err.println("Request par: " + httpbody);
            } else {
                httpPost.setEntity(new StringEntity(httpbody.toJSONString(), "utf-8"));
                System.err.println("Request par: " + httpbody);
            }
            //			} catch (UnsupportedEncodingException e) {
            //				// TODO Auto-generated catch block
            //				e.printStackTrace();
            //			}
        }
    }

    /**
     * @formFiledName 可以设置为null，在上次文件时候无用处
     * @fileName为上传文件路径 eg：D:\\picture\\1.jpg
     */
    public static class UploadFileItem implements Serializable {
        private static final long serialVersionUID = 1L;
        // The form field name in a form used foruploading a file,

        // such as "upload1" in "<inputtype="file" name="upload1"/>"
        public String formFieldName;
        public String fileName;

        public UploadFileItem(String formFieldNameformFieldName, String fileName) {
            this.formFieldName = formFieldName;

            this.fileName = fileName;
        }

        public String getFormFieldName() {
            return formFieldName;
        }

        public void setFormFieldName(String formFieldName) {
            this.formFieldName = formFieldName;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }


//    /**
//     * 返回byte[]类型的post方法
//     */
//
//    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
//
//    public static byte[] postHTTPRequest(String requestURL, byte[] content, Map<String, String> headers) {
//        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
//        final HttpPost post = new HttpPost(requestURL);
//        Iterator it = headers.keySet().iterator();
//        while (it.hasNext()) {
//            String key = (String) it.next();
//            post.setHeader(key, headers.get(key));
//        }
//        //			post.setEntity(new ByteArrayEntity(content));
//        byte[] result = null;
//        try {
//            HttpResponse response = httpclient.execute(post);
//            result = IOUtils.toByteArray(response.getEntity().getContent());
//            System.out.println("result is:" + response.getEntity().getContent().toString());
//        } catch (Exception e) {
//            logger.error("postHTTPRequest", e);
//            throw new RuntimeException(e);
//        }
//        return result;
//    }
//

//    public static ResponseVo postHTTPRequestForResponseJson(String requestURL,
//                                                            byte[] content,
//                                                            Map<String, String> headers) {
//        System.out.println("Request url: " + requestURL);
//        System.out.println("headers is:" + headers.toString());
//        ResponseVo responsevo = new ResponseVo();
//        JSONObject json = new JSONObject();
//        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
//        final HttpPost post = new HttpPost(requestURL);
//        Iterator it = headers.keySet().iterator();
//        while (it.hasNext()) {
//            String key = (String) it.next();
//            post.setHeader(key, headers.get(key));
//        }
//        post.setEntity(new ByteArrayEntity(content));
//        try {
//            HttpResponse response = httpclient.execute(post);
//            //				result = IOUtils.toByteArray(response.getEntity().getContent());
//
//            int responseCode = response.getStatusLine().getStatusCode();
//            responsevo.setHttpCode(responseCode);
//            if (responseCode == 200 || responseCode == 201) {
//                HttpEntity entity = response.getEntity();
//                if (entity != null) {
//                    json = JSON.parseObject(EntityUtils.toString(entity));
//                    responsevo.setJson(json);
//                    System.err.println("Response is :" + json);
//                } else {
//                    System.out.println("entity is null");
//                }
//            } else {
//                System.out.println("Response is :" + EntityUtils.toString(response.getEntity()));
//                System.out.println("http POST request return error, error code is " + responseCode);
//            }
//        } catch (Exception e) {
//            logger.error("postHTTPRequest", e);
//            throw new RuntimeException(e);
//        }
//        return responsevo;
//    }

}
