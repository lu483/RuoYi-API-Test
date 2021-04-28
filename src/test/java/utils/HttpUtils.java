package utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * @Author LuLu
 * @Description: TODO(不同的http请求)
 * @Date: Create 2021/4/28 16:18
 * @Version 1.0
 */
public class HttpUtils {

    public static BasicHeader lemonHeader = new BasicHeader("X-Lemonban-Media-Type", "lemonban.v2");
    private static Logger log = Logger.getLogger(HttpUtils.class);

    /**
     * 调用httpClient发送http请求，返回字符串类型的响应体
     * @param url				接口地址
     * @param params			接口参数（json格式）
     * @param type				接口method
     * @param contentType		接口的参数类型
     * @return
     */
    public static String call(String url,String params,String type,String contentType) {
        if("json".equalsIgnoreCase(contentType)) {
            //如果 type = post
            if("post".equalsIgnoreCase(type)) {
                return HttpUtils.testPostJson(url, params);
                //如果 type = get
            }else if("get".equalsIgnoreCase(type)) {
                return HttpUtils.testGetJson(url, params);
            }else if("patch".equalsIgnoreCase(type)) {
                //HttpUtils.testPatchJson(url, params);
                return "patch";
            }else if("delete".equalsIgnoreCase(type)) {
                //HttpUtils.testDeleteJson(url, params);
                return "delete";
            }
            //如果 contentType = form
        }else if("form".equalsIgnoreCase(contentType)) {
            String keyValueParams = json2KeyValue(params);
            System.out.println(keyValueParams);
            return HttpUtils.testPostForm(url, params);
        }
        return "";
    }

    /**
     * params是json格式，可以通过fastJson转成Map对象（key，value），
     * 再把Map对象转成需要字符串
     * @param params
     * @return
     */
    private static String json2KeyValue(String params) {

        //1、parmas转成Map对象
        HashMap<String,String> map = JSONObject.parseObject(params, HashMap.class);
        //2、Map对象转成对应字符串mobilephone=13877788811&pwd=12345678&
        String keyValueParams = "";
        Set<String> keySet = map.keySet();
        //2、Map对象转成对应字符串mobilephone=13877788811&pwd=12345678&age=19
        for (String key : keySet) {
            String value = map.get(key);
            if(keyValueParams.length() > 0) {
                keyValueParams +=  "&";
            }
            keyValueParams += key + "=" + value;
        }
        return keyValueParams;
    }



    // 静态方法，直接用类名就可以调用。
    // 在工具类里面把方法定义成静态的就是为了偷懒，偷不用创建对象的懒
    public static String testGetJson(String url,String params) {
        try {
            // 1、创建一个reqeust，携带了method和url ? /
            // 1.1 处理参数（目前这个步骤为空）
            HttpGet get = new HttpGet(url);
            // 1.2、添加请求头
            get.addHeader(lemonHeader);
            // 2、创建发送请求的客户端，HttpClients是HttpClient的工具类
            CloseableHttpClient client = HttpClients.createDefault();
            // 3、客户端调用发送get请求,立即返回http响应
            CloseableHttpResponse response = client.execute(get);
            // 4、response整个响应对象（body、header、statuscode）
            // 4.1、获取所有的响应头
            Header[] allHeaders = response.getAllHeaders();
            // 4.2、获取状态码
            int statusCode = response.getStatusLine().getStatusCode();
            // 4.3、获取响应体
            HttpEntity entity = response.getEntity();
            // 4.4、格式化响应体
            String body = EntityUtils.toString(entity);
            // 5、输出响应
//			for (Header header : allHeaders) {
//				System.out.print("Headers：" + header);
//			}
            log.info("Headers:" + Arrays.toString(allHeaders));
            log.info("statusCode:" + statusCode);
            log.info("body:" + body);
            return body;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String testPostJson(String url,String params) {
        try {
            HttpPost post = new HttpPost(url);
            //1.1、添加请求头
            post.addHeader(lemonHeader);
            post.addHeader("Content-Type", "application/json");
            //1.1.1、添加鉴权请求头。
            AuthenticationUtils.addToken(post);
            //1.2、添加请求体（参数）
            StringEntity requestEntity = new StringEntity(params,"utf-8");
            post.setEntity(requestEntity);
            //2、创建发送请求的客户端，HttpClients是HttpClient的工具类
            CloseableHttpClient client = HttpClients.createDefault();
            //3、客户端调用发送get请求,立即返回http响应
            CloseableHttpResponse response = client.execute(post);
            //4、response整个响应对象（body、header、statuscode）
            //4.1、获取所有的响应头
            Header[] allHeaders = response.getAllHeaders();
            //4.2、获取状态码
            int statusCode = response.getStatusLine().getStatusCode();
            //4.3、获取响应体
            HttpEntity entity = response.getEntity();
            //4.4、格式化响应体
            String body = EntityUtils.toString(entity);
            //5、输出响应l'l
            log.info("Headers:" + Arrays.toString(allHeaders));
            log.info("statusCode:" + statusCode);
            log.info("body:" + body);
            //6、token存储
            AuthenticationUtils.storeToken(body);
            return body;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String testPostForm(String url,String params) {
        try {
            HttpPost post = new HttpPost(url);
            //1.1、添加请求头
            post.addHeader("Content-Type", "application/x-www-form-urlencoded");
            //1.2、添加请求体（参数）
            StringEntity requestEntity = new StringEntity(params,"utf-8");
            post.setEntity(requestEntity);
            //2、创建发送请求的客户端，HttpClients是HttpClient的工具类
            CloseableHttpClient client = HttpClients.createDefault();
            //3、客户端调用发送get请求,立即返回http响应
            CloseableHttpResponse response = client.execute(post);
            //4、response整个响应对象（body、header、statuscode）
            //4.1、获取所有的响应头
            Header[] allHeaders = response.getAllHeaders();
            //4.2、获取状态码
            int statusCode = response.getStatusLine().getStatusCode();
            //4.3、获取响应体
            HttpEntity entity = response.getEntity();
            //4.4、格式化响应体
            String body = EntityUtils.toString(entity);
            //5、输出响应
            log.info("Headers:" + Arrays.toString(allHeaders));
            log.info("statusCode:" + statusCode);
            log.info("body:" + body);
            return body;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
