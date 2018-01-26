//package com.suntenday.scheduling.utils;
//
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * com.suntenday.scheduling.utils
// *
// * @author zhengli08275
// * @date 2018/1/5 0005 17:23
// */
//public class PoiExcelUtils {
//    /**
//     * 适用于没有标题行的excel，例如
//     * 张三   25岁     男   175cm
//     * 李四   22岁     女   160cm
//     * 每一行构成一个map，key值是列标题，value是列值。没有值的单元格其value值为null
//     * 返回结果最外层的list对应一个excel文件，第二层的list对应一个sheet页，第三层的map对应sheet页中的一行
//     *
//     * @throws Exception
//     */
//    public static List<List<List<String>>> readExcelWithoutTitle(String filepath) throws Exception {
//        String fileType = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());
//        InputStream is = null;
//        Workbook wb = null;
//        try {
//            is = new FileInputStream(filepath);
//
//            if (fileType.equals("xls")) {
//                wb = new HSSFWorkbook(is);
//            } else if (fileType.equals("xlsx")) {
//                wb = new XSSFWorkbook(is);
//            } else {
//                throw new Exception("读取的不是excel文件");
//            }
//            //对应excel文件
//            List<List<List<String>>> result = new ArrayList<>();
//
//            int sheetSize = wb.getNumberOfSheets();
//            //遍历sheet页
//            for (int i = 0; i < sheetSize; i++) {
//                Sheet sheet = wb.getSheetAt(i);
//                //对应sheet页
//                List<List<String>> sheetList = new ArrayList<>();
//
//                int rowSize = sheet.getLastRowNum() + 1;
//                //遍历行
//                for (int j = 0; j < rowSize; j++) {
//                    Row row = sheet.getRow(j);
//                    //略过空行
//                    if (row == null) {
//                        continue;
//                    }
//                    //行中有多少个单元格，也就是有多少列
//                    int cellSize = row.getLastCellNum();
//                    //对应一个数据行
//                    List<String> rowList = new ArrayList<>();
//                    for (int k = 0; k < cellSize; k++) {
//                        Cell cell = row.getCell(k);
//                        String value = null;
//                        if (cell != null) {
//                            value = cell.toString();
//                        }
//                        rowList.add(value);
//                    }
//                    sheetList.add(rowList);
//                }
//                result.add(sheetList);
//            }
//
//            return result;
//        } catch (FileNotFoundException e) {
//            throw e;
//        } finally {
//            if (wb != null) {
//                wb.close();
//            }
//            if (is != null) {
//                is.close();
//            }
//        }
//    }
//}
