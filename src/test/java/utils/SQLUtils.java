package utils;

import io.qameta.allure.Step;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author LuLu
 * @Description: TODO(描述)
 * @Date: Create 2020/2/11 19:58
 * @Version 1.0
 */
public class SQLUtils {

    /**
     * 调用DBUtils执行对应的查询sql语句，并返回查询结果。
     * @param sql
     * @return
     */
    @Step("执行sql {sql}")
    public static Object query(String sql) {
        if(StringUtils.isBlank(sql)) {
            return null;
        }
        //创建QueryRunner对象
        QueryRunner qr = new QueryRunner();
        //获取数据连接
        Connection conn = JDBCUtils.getConnection();
        try {
            //创建返回结果类型对象
            ScalarHandler rsh = new ScalarHandler();
            //执行sql查询语句
            Object result = qr.query(conn, sql, rsh);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭数据库连接
            JDBCUtils.close(conn);
        }
        return null;
    }
}

