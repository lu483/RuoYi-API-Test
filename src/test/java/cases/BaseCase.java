package cases;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import pojo.API;
import pojo.Case;
import pojo.JsonPathValidate;
import pojo.WriteBackData;
import utils.AuthenticationUtils;
import utils.ExcelUtils;
import utils.HttpUtils;

import java.util.List;
import java.util.Set;

/**
 * @Author LuLu
 * @Description: TODO(描述)
 * @Date: Create 2020/2/11 19:48
 * @Version 1.0
 */
public class BaseCase {
    //	public void addWriteBackData(Case c,String content) {
//		String caseId = c.getId();
//		//把字符串类型"10"转成int类型的10
//		int rowNum = Integer.parseInt(caseId);
//		//double d = Double.parseDouble("0.0")
//		int cellNum = Constant.ACTUAL_RESPONSE_DATA_CELLNUM;
//		addWriteBackData(rowNum, cellNum, content);
//	}

    /**
     * 参数化替换方法
     * @param params
     * @return
     */
    @Step("参数化替换 {params}")
    public String paramReplace(String params) {
        if(StringUtils.isBlank(params)) {
            return "";
        }
        Set<String> keySet = AuthenticationUtils.env.keySet();
        for (String key : keySet) {
            String value = AuthenticationUtils.env.get(key);
            params = params.replace(key, value);
        }
        return params;
    }

    /**
     * 添加回写对象到回写集合中
     * @param rowNum
     * @param cellNum
     * @param content
     */
    @Step("回写excel")
    public void addWriteBackData(int rowNum,int cellNum,String content) {
        WriteBackData wbd = new WriteBackData(rowNum, cellNum, content);
        //添加到回写集合中
        ExcelUtils.wbdList.add(wbd);
    }

    /**
     * 传入用例信息调用HTTP接口，并且返回响应体
     * @param api
     * @param c
     * @return
     */
    @Step("接口调用 {api.url}/{c.params}")
    public String call(API api, Case c) {
        String url = api.getUrl();
        String params = c.getParams();
        String type = api.getType();
        String contentType = api.getContentType();
        //测试注册接口
        //面向对象：高内聚（当前方法核心功能）    低耦合（依赖其他类完成的功能）
        return HttpUtils.call(url, params, type, contentType);
    }

    /**
     * 断言接口响应内容,第一种判断采用json数组的多关键字匹配，第二种采用json对象的等值匹配
     * @param c
     * @param content
     * @return
     */
    @Step("断言接口响应内容 {content}")
    public boolean assertResponseData(Case c,String content) {
        //获取期望数据
        String exepctData = c.getExpectData();
        Object parse = JSONObject.parse(exepctData);
        //判断parse是json的对象类型（JSONObject）还是数组类型（JSONArray）
        if(parse instanceof JSONArray) {
            //如果是数组类型，就采用多关键字匹配
            //通过期望数据转成list集合
            List<JsonPathValidate> list = JSONObject.parseArray(exepctData, JsonPathValidate.class);
            //循环集合
            for (JsonPathValidate jpv : list) {
                //通过jsonpath表达式获取到实际响应中的实际值
                Object actualValue = JSONPath.read(content, jpv.getExpression());
                //获取期望值
                String exepctValue = jpv.getValue();
                //拿期望值和实际值比较
                boolean flag = exepctValue.equals(actualValue.toString());
                //!false 其中的某一个断言已经失败。 && || !flase == true  !true == false
                if(flag == false) {
                    return false;
                }
            }
            //在循环的过程中flag都为true
            return true;
        }else if(parse instanceof JSONObject) {
            //如果是对象类型，就采用等值匹配
            boolean flag = exepctData.equals(content);
            return flag;
        }
        return false;
    }

}
