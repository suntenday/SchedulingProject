package com.suntenday.scheduling.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;

/**
 * 表格工具类
 *
 * @author zhengli08275
 * @date 2018/1/5 0005 16:00
 */
public class WorkbookUtils {

    /**
     * 读取表格（仅支持xls）
     * @param filePath
     * @param listener
     */
    public static void readFileData(String filePath, WorkBookReadListener listener) {
        try {
            //输入流
            InputStream inputStream =  new FileInputStream(filePath);
            if (inputStream == null) {
                listener.recodeError("文件读取失败");
                return;
            }
            readFileData(inputStream, listener);
        } catch (Exception e) {
            System.out.println("出错异常" + e);
            listener.recodeError("文件读取失败");
        }
    }

    private static void readFileData(InputStream inputStream, WorkBookReadListener listener) throws Exception {
        //Excel工作簿对象
        Workbook book = null;
        //用读取到的表格文件来实例化工作簿对象（符合常理，我们所希望操作的就是Excel工作簿文件）
        book = Workbook.getWorkbook(inputStream);
        //得到所有的工作表
        Sheet[] sheets = book.getSheets();
        for (int m = 0; m < sheets.length; m++) {
            Sheet sheet = book.getSheet(m);
            //得到当前工作表的行数
            int Rows = sheet.getRows();
            //得到当前工作表的列数
            int Cols = sheet.getColumns();
            // 注意：这里是按列读取的！！！
            for (int i = 0; i < Cols; i++) {
                for (int j = 0; j < Rows; j++) {
                    //结果提供给监听方法
                    listener.recordData(sheet.getCell(i, j).getContents(), i, j);
                }
            }
        }
    }

    /**
     * 监听
     */
    public interface WorkBookReadListener {
        void recordData(String content, int col, int row);

        void recodeError(String errorMsg);
    }
}
