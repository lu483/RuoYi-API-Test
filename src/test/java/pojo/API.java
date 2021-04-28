package pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import javax.validation.constraints.NotNull;

/**
 * @Author LuLu
 * @Description: TODO(描述)
 * @Date: Create 2020/2/11 19:53
 * @Version 1.0
 */
public class API {
    //缺少API和excel的映射关系（API字段和Excel列的关系）。
    //加上@Excel注解之后，那么easypoi就知道了映射关系。

    // 接口名称  {"name":"ls","type":"post"}
    @Excel(name = "接口名称")
    @NotNull
    private String name;
    // 接口提交方式
    @Excel(name = "接口提交方式")
    @NotNull
    private String type;
    // 接口地址
    @Excel(name = "接口地址")
    @NotNull
    private String url;
    // 参数类型
    @Excel(name = "参数类型")
    @NotNull
    private String contentType;
    // 接口编号
    @Excel(name = "接口编号")
    @NotNull
    private String id;

//	List<Case> list2 = new ArrayList<Case>();

    public API(String id, String name, String type, String url, String contentType) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.url = url;
        this.contentType = contentType;
    }

    public API() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "API [id=" + id + ", name=" + name + ", type=" + type + ", url=" + url + ", contentType=" + contentType
                + "]";
    }

}
