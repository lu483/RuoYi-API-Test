package utils;

import com.alibaba.fastjson.JSONPath;
import constants.Constant;
import org.apache.http.client.methods.HttpRequestBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author LuLu
 * @Description: TODO(鉴权)
 * @Date: Create 2020/2/11 19:55
 * @Version 1.0
 */
public class AuthenticationUtils {

    //声明一个变量---环境变量
    public static Map<String,String> env = new HashMap<String, String>();

    /**
     * 从响应体中获取token信息存储到缓存（postman环境变量）中。
     * 先要得到所有的响应responseBody，然后在里面抓取token
     * @param responseBody
     */
    public static void storeToken(String responseBody) {
        //1、取出token,如果找不到token返回null，用JSONPath来抓取到token
        Object token = JSONPath.read(responseBody, "$.data.token_info.token");
        //2、存储
        if(token != null) {
            env.put("token",token.toString());
            Object member_id = JSONPath.read(responseBody, "$.data.id");
            if(member_id != null) {
                env.put(Constant.MEMBER_ID,member_id.toString());
            }
        }
    }

    /**
     * 添加token到请求头，在HttpUtils类里面
     * @param request
     */
    public static void addToken(HttpRequestBase request) {
        //如果通过键找不到值，返回null
        String token = env.get("token");
        if(token != null) {
            request.addHeader("Authorization","Bearer " + token);
        }
    }
}
