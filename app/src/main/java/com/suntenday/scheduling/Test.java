package com.suntenday.scheduling;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.suntenday.scheduling.bean.EmployeeRuleBean;
import com.suntenday.scheduling.dialog.FileDialog;
import com.suntenday.scheduling.manager.DBManager;
import com.suntenday.scheduling.utils.WorkbookUtils;
import com.suntenday.scheduling.utils.WorkbookUtils.WorkBookReadListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.suntenday.scheduling
 *
 * @author zhengli08275
 * @date 2018/1/5 0005 14:39
 */
public class Test extends Activity{

    private Map<Integer, Map<Integer, String>> fileData;
    private int excelSize = 0;

    public void test(String content, Integer col, Integer row){
        if(fileData==null){
            fileData = new HashMap<>();
        }
        Map<Integer,String> rowData = fileData.get(row);
        if(rowData == null){
            rowData = new HashMap<>();
        }
        rowData.put(col,content);
        fileData.put(row,rowData);

    }

    public void importDBData(){
        ArrayList<EmployeeRuleBean> employeeRuleBeanList = new ArrayList<>();
        excelSize = fileData.size();
        for(int i=0;i<=excelSize-1;i++){
            Map<Integer,String> excelItemMap = fileData.get(i);
            EmployeeRuleBean excelItem = new EmployeeRuleBean(excelItemMap);
            employeeRuleBeanList.add(excelItem);
        }
        DBManager.getInstance().addEmployeeRuleList(this,employeeRuleBeanList);
    }

    public FileDialog.FileDialogCallbackListener fileDialogCallbackListener() {
        return new FileDialog.FileDialogCallbackListener() {
            @Override
            public void callback(Bundle bundle) {
                String filePath = bundle.getString("path");
            }
        };
    }

    public WorkBookReadListener workBookReadListener(){
        return new WorkBookReadListener() {
            @Override
            public void recordData(String content, int col, int row) {

            }

            @Override
            public void recodeError(String errorMsg) {

            }
        };
    }

}
