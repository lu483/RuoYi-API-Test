package pojo;

/**
 * @Author LuLu
 * @Description: TODO(描述)
 * @Date: Create 2020/2/11 19:54
 * @Version 1.0
 */
public class WriteBackData {
    //回写excel的行号
    private int rowNum;
    //回写excel的列号
    private int cellNum;
    //回写excel的内容
    private String content;

    public WriteBackData() {
        super();
    }

    public WriteBackData(int rowNum, int cellNum, String content) {
        super();
        this.rowNum = rowNum;
        this.cellNum = cellNum;
        this.content = content;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getCellNum() {
        return cellNum;
    }

    public void setCellNum(int cellNum) {
        this.cellNum = cellNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "WriteBackData [rowNum=" + rowNum + ", cellNum=" + cellNum + ", content=" + content + "]";
    }

}

