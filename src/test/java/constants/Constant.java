package constants;

/**
 * @Author LuLu
 * @Description: TODO(常量类，用来保存全局变量)
 * @Date: Create 2020/2/11 19:52
 * @Version 1.0
 */
//常量类，充当配置文件的作用。
public class Constant {
    // final 修饰类类不能被继承。
    // 修饰方法，方法不能被重写。
    // 修饰字段（变量），变量变成常量。（只能被赋值一次）
    // 常量命名规则：所有英文大写，多个英文用下划线分割 LINUX WINDOW \\
    public static final String EXCEL_PATH = "src/test/resources/cases_v8.xlsx";
    // excel中实际响应数据列号
    public static final int ACTUAL_RESPONSE_DATA_CELLNUM = 5;
    // excel断言列号
    public static final int ASSERT_CELLNUM = 6;

    // 数据库连接url
    public static final String JDBC_URL = "jdbc:mysql://api.lemonban.com:3306/futureloan?useUnicode=true&characterEncoding=utf-8";
    // 数据库连接用户名
    public static final String JDBC_USER = "future";
    // 数据库连接密码
    public static final String JDBC_PASSWORD = "123456";
    // 参数化替换字段
    public static final String REGISTER_MOBILEPHONE_TEXT = "${toBeRegisterMobilephone}";
    public static final String REGISTER_PASSWORD_TEXT = "${toBeRegisterPassword}";
    public static final String LOGIN_MOBILEPHONE_TEXT = "${toBeLoginMobilephone}";
    public static final String LOGIN_PASSWORD_TEXT = "${toBeLoginPassword}";
    public static final String MEMBER_ID = "${member_id}";
    // 参数化替换值
    public static final String REGISTER_MOBILEPHONE_VALUE = getTel();
    public static final String REGISTER_PASSWORD_VALUE = "12345678";
    public static final String LOGIN_MOBILEPHONE_VALUE = REGISTER_MOBILEPHONE_VALUE;
    public static final String LOGIN_PASSWORD_VALUE = "12345678";

    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    /**
     * 随机的手机号
     * @return
     */
    private static String getTel() {
        String[] telFirst = { "134", "135", "136", "137", "138", "139", "150", "151", "152", "157", "158", "159", "130",
                "131", "132", "155", "156", "133", "153" };
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String third = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + second + third;
    }


}

