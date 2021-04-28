package pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import javax.validation.constraints.NotNull;

/**
 * @Author LuLu
 * @Description: TODO(描述)
 * @Date: Create 2020/2/11 19:53
 * @Version 1.0
 */
public class Case {
    // 用例编号
    @Excel(name = "用例编号")
    @NotNull
    private int id;
    // 用例描述
    @Excel(name = "用例描述")
    @NotNull
    private String desc;
    // 参数
    @Excel(name = "参数")
    @NotNull
    private String params;
    // 接口编号 关联API类
    @Excel(name = "接口编号")
    @NotNull
    private String apiId;
    // 期望响应数据
    @Excel(name = "期望响应数据")
    @NotNull
    private String expectData;
    // 期望响应数据
    @Excel(name = "检验SQL")
    private String sql;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getExpectData() {
        return expectData;
    }

    public void setExpectData(String expectData) {
        this.expectData = expectData;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "Case [id=" + id + ", desc=" + desc + ", params=" + params + ", apiId=" + apiId + ", expectData="
                + expectData + ", sql=" + sql + "]";
    }

}
