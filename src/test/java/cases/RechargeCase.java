package cases;

import com.alibaba.fastjson.JSONPath;
import constants.Constant;
import org.apache.commons.lang3.StringUtils;
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
 * @Date: Create 2020/2/11 19:51
 * @Version 1.0
 */
public class RechargeCase extends BaseCase {

    @Test(dataProvider = "datas",description = "充值接口")
    public void testRecharge(API api, Case c) {
        //0、参数化替换
        String params = paramReplace(c.getParams());
        String sql = paramReplace(c.getSql());
        c.setParams(params);
        c.setSql(sql);
        //1、数据库前置查询结果(数据断言必须在接口执行前后都查询)
        Object beforeSQLResult = SQLUtils.query(sql);  //1053200
        //2、调用接口
        String content = call(api, c);
        //3、添加接口响应回写内容
        addWriteBackData(c.getId(), Constant.ACTUAL_RESPONSE_DATA_CELLNUM, content);
        //4、断言响应结果
        boolean assertResponseData = assertResponseData(c, content);
        System.out.println("断言结果：" + assertResponseData);
        //5、数据库后置查询结果
        Object afterSQLResult = SQLUtils.query(sql);	//1053200 + 6300
        //6、数据库断言
        boolean assertSQL = true;
        if(StringUtils.isNoneBlank(sql)) {
//			afterSQLResult - beforeSQLResult == c.getParams().($.amount)
            assertSQL = assertSQL(beforeSQLResult, afterSQLResult,c);
            System.out.println("数据库断言结果：" + assertSQL);
        }
        String passContent = (assertResponseData && assertSQL) ? "Pass" : "Fail";
        //7、添加断言回写内容
        addWriteBackData(c.getId(), Constant.ASSERT_CELLNUM, passContent);
        //报表断言
        Assert.assertEquals(passContent, "Pass");
    }

    public boolean assertSQL(Object beforeSQLResult,Object afterSQLResult,Case c) {
        System.out.println("beforeSQLResult:" + beforeSQLResult.getClass());
        String moneyStr = JSONPath.read(c.getParams(), "$.amount").toString();
        String beforeMoneyStr = beforeSQLResult.toString();
        String afterMoneyStr = afterSQLResult.toString();
        double beforeMoney = Double.parseDouble(beforeMoneyStr);
        double afterMoney = Double.parseDouble(afterMoneyStr);
        double money = Double.parseDouble(moneyStr);
        return afterMoney - beforeMoney == money;

    }

    @DataProvider(name="datas")
    public Object[][] datas() {
        Object[][] datas = DataUtils.getAPIAndCaseByAPIId("3");
        //{{API,Case},{API,Case},{API,Case},{API,Case}}
        return datas;
    }



}
