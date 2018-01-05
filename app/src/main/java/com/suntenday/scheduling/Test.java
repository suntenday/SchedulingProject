package com.suntenday.scheduling;

import android.os.Bundle;
import android.view.View;

import com.suntenday.scheduling.dialog.FileDialog;
import com.suntenday.scheduling.utils.WorkbookUtils;
import com.suntenday.scheduling.utils.WorkbookUtils.WorkBookReadListener;

/**
 * com.suntenday.scheduling
 *
 * @author zhengli08275
 * @date 2018/1/5 0005 14:39
 */
public class Test {

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
