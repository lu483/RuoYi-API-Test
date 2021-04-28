package utils;

import constants.Constant;
import pojo.API;
import pojo.Case;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author LuLu
 * @Description: TODO(描述)
 * @Date: Create 2020/2/11 19:56
 * @Version 1.0
 */
public class DataUtils {
    //成员变量
    public static List<API> apiList = ExcelUtils.read(Constant.EXCEL_PATH, 0, API.class);
    public static List<Case> caseList = ExcelUtils.read(Constant.EXCEL_PATH, 1, Case.class);

    /**
     * 根据传入的apiId获取一个API和多个Case
     * @param apiId
     * @return
     */
    public static Object[][] getAPIAndCaseByAPIId(String apiId) {
        API wantAPI = null;
        ArrayList<Case> wantCases = new ArrayList<Case>();
        //1、获取wantAPI
        for(API api : apiList) {
            //传入的apiId和遍历的apiId相等
            if(apiId.equals(api.getId())) {
                wantAPI = api;
                break;
            }
        }
        //2、获取wantCases
        for(Case c : caseList) {
            //传入的apiId和遍历的apiId相等
            if(apiId.equals(c.getApiId())) {
                wantCases.add(c);
            }
        }
        //3、把wantAPI和wantCases合并。
        //{{API,Case},{API,Case},{API,Case},{API,Case}}
        //4、把wantAPI和wantCase存入到二维数组中。
        Object[][] datas = new Object[wantCases.size()][2];
        for (int i = 0; i < wantCases.size(); i++) {
            //第一个参数
            datas[i][0] = wantAPI;
            //第二个参数
            datas[i][1] = wantCases.get(i);
        }
        return datas;
    }
}
