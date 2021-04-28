package pojo;

/**
 * @Author LuLu
 * @Description: TODO(描述)
 * @Date: Create 2020/2/11 19:54
 * @Version 1.0
 */
public class JsonPathValidate {
    // 断言预期jsonPath的值
    private String value;
    // 断言预期jsonPath的表达式
    private String expression;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "JsonPathVaildate [value=" + value + ", expression=" + expression + "]";
    }

}
