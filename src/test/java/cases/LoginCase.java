package cases;

import constants.Constant;
import io.qameta.allure.Description;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.API;
import pojo.Case;
import utils.AuthenticationUtils;
import utils.DataUtils;
import utils.ExcelUtils;

/**
 * @Author LuLu
 * @Description: TODO(登录测试用例)
 * @Date: Create 2020/2/11 19:47
 * @Version 1.0
 */
public class LoginCase extends BaseCase {
    private Logger log = Logger.getLogger(LoginCase.class);


    @BeforeSuite
    public void init() {
        log.info("======================项目启动=========================");
        //套件执初始化
        //配置参数化变量
        AuthenticationUtils.env.put(Constant.REGISTER_MOBILEPHONE_TEXT, Constant.REGISTER_MOBILEPHONE_VALUE);
        AuthenticationUtils.env.put(Constant.REGISTER_PASSWORD_TEXT, Constant.REGISTER_PASSWORD_VALUE);
        AuthenticationUtils.env.put(Constant.LOGIN_MOBILEPHONE_TEXT, Constant.LOGIN_MOBILEPHONE_VALUE);
        AuthenticationUtils.env.put(Constant.LOGIN_PASSWORD_TEXT, Constant.LOGIN_PASSWORD_VALUE);
    }

    @Test(dataProvider = "datas")
    @Description("登录测试")
    public void testLogin(API api, Case c) {
        log.info("======================参数化替换=========================" + api.getUrl());
        //0、参数化替换
        String params = paramReplace(c.getParams());
        String sql = paramReplace(c.getSql());
        c.setParams(params);
        c.setSql(sql);
        //1、调用接口
        log.info("======================调用接口=========================");
        String content = call(api, c);
        //2、添加回写内容 poi + list 循环
        log.info("======================添加回写内容=========================");
        addWriteBackData(c.getId(), Constant.ACTUAL_RESPONSE_DATA_CELLNUM, content);
        //[{"value":2,"expression":"$.code"}]
        //3、断言接口响应内容
        log.info("======================断言接口响应内容=========================");
        boolean assertResponseData = assertResponseData(c, content);
        System.out.println("断言结果：" + assertResponseData);
        log.info("断言结果：" + assertResponseData);
        String passContent = (assertResponseData) ? "Pass" : "Fail";
        //4、添加断言回写内容
        log.info("添加断言回写内容");
        addWriteBackData(c.getId(), Constant.ASSERT_CELLNUM, passContent);
        //报表断言
        Assert.assertEquals(passContent, "Pass");
    }

    @AfterSuite
    public void finish() {
        //套件执行完毕之后，执行批量回写。
        log.info("======================批量回写=========================");
        ExcelUtils.write(Constant.EXCEL_PATH, 1);
        //清楚垃圾数据
        log.info("======================项目结束=========================");
    }


    //getAPIAndCaseByAPIId("2")，这里的2是excel中接口对应的编号，登录接口的编号都是2，所以填2
    @DataProvider(name="datas")
    public Object[][] datas() {
        Object[][] datas = DataUtils.getAPIAndCaseByAPIId("2");
        //{{API,Case},{API,Case},{API,Case},{API,Case}}
        return datas;
    }



}

