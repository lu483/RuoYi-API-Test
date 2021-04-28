package utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import org.apache.poi.ss.usermodel.*;
import pojo.WriteBackData;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author LuLu
 * @Description: TODO(描述)
 * @Date: Create 2020/2/11 19:56
 * @Version 1.0
 */
public class ExcelUtils {

    //存储回写数据的List集合。
    public static List<WriteBackData> wbdList = new ArrayList<WriteBackData>();

    public static<T> List<T> read(String path, int sheetIndex, Class<T> clazz) {
        FileInputStream fis = null;
        try {
            // 1、加载excel的流对象
            fis = new FileInputStream(path);
            // 2、导入参数对象（默认值：读取第一个Sheet且只读取第一个，表头是Sheet的第一行且只有一行）
            ImportParams params = new ImportParams();
            params.setNeedVerify(true);
            params.setStartSheetIndex(sheetIndex);
            // 3、工具解析excel封装成List对象
            List<T> list = ExcelImportUtil.importExcel(fis, clazz, params);
            return list;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(fis);
        }
        return null;
    }

    public static void write(String path,int sheetIndex) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            //1、找到excel文件 修改
            fis = new FileInputStream(path);
            //2、打开
            Workbook workbook = WorkbookFactory.create(fis);
            //3、获取Sheet
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            //4、获取要写的行和列定位到单元格（cell）
            //4.1、获取第三行
            //循环 wbdList 集合，取出 wbd对象，获取到行号、列号、内容。
            for (WriteBackData wbd : wbdList) {
                Row row = sheet.getRow(wbd.getRowNum());
                //4.2、获取第三列
                Cell cell = row.getCell(wbd.getCellNum(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                //5、修改内容
                cell.setCellValue(wbd.getContent());
            }
            //到目前为止所有的操作都是在java里面
            fos = new FileOutputStream(path);
            workbook.write(fos);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            //6、关流
            close(fis);
            close(fos);
        }

    }

    private static void close(Closeable stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
