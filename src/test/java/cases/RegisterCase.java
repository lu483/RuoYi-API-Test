package cases;

import constants.Constant;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.API;
import pojo.Case;
import utils.DataUtils;
import utils.SQLUtils;

/**
 * @Author LuLu
 * @Description: TODO(描述)
 * @Date: Create 2020/2/11 19:52
 * @Version 1.0
 */
public class RegisterCase extends BaseCase {
    private Logger log = Logger.getLogger(RegisterCase.class);

    @Test(dataProvider = "datas",description = "注册接口")
    public void testRegister(API api, Case c) {
        //0、参数化替换
        String params = paramReplace(c.getParams());
        String sql = paramReplace(c.getSql());
        c.setParams(params);
        c.setSql(sql);
        //1、数据库前置查询结果(数据断言必须在接口执行前后都查询)
        Object beforeSQLResult = SQLUtils.query(sql);
        //2、调用接口
        String content = call(api, c);
        //3、添加回写内容
        addWriteBackData(c.getId(), Constant.ACTUAL_RESPONSE_DATA_CELLNUM, content);
        //4、断言响应结果
        boolean assertResponseData = assertResponseData(c, content);
        System.out.println("响应断言结果：" + assertResponseData);
        //5、数据库后置查询结果
        Object afterSQLResult = SQLUtils.query(sql);
        //6、数据库断言==》beforeSQLResult == 0 && afterSQLResult == 1
        boolean assertSQL = true;
        if(StringUtils.isNoneBlank(sql)) {
            assertSQL = assertSQL(beforeSQLResult, afterSQLResult);
            System.out.println("数据库断言结果：" + assertSQL);
        }
        String passContent = (assertResponseData && assertSQL) ? "Pass" : "Fail";
        //7、添加断言回写内容
        addWriteBackData(c.getId(),Constant.ASSERT_CELLNUM, passContent);
        //报表断言
        Assert.assertEquals(passContent, "Pass");
    }

    @Step("执行sql断言")
    public boolean assertSQL(Object beforeSQLResult,Object afterSQLResult) {
        System.out.println("beforeSQLResult:" + beforeSQLResult.getClass());
        if((Long)beforeSQLResult == 0 && (Long)afterSQLResult == 1) {
            return true;
        }else {
            return false;
        }
    }


    @DataProvider(name="datas")
    public Object[][] datas() {
        Object[][] datas = DataUtils.getAPIAndCaseByAPIId("1");
        //{{API,Case},{API,Case},{API,Case},{API,Case}}
        return datas;
    }



}
