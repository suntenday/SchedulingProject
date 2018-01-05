package com.suntenday.scheduling.activity

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import com.suntenday.scheduling.R
import com.suntenday.scheduling.activity.ui.ImportViewUi
import com.suntenday.scheduling.bean.EmployeeRuleBean
import com.suntenday.scheduling.dialog.FileDialog
import com.suntenday.scheduling.manager.DBManager
import com.suntenday.scheduling.utils.StringUtils
import com.suntenday.scheduling.utils.WorkbookUtils.WorkBookReadListener
import com.suntenday.scheduling.utils.WorkbookUtils.readFileData
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast

/**
 * 导入员工
 * @author zhengli08275
 * @date 2018/1/5 0005 10:34
 */
class ImportActivity : Activity() {

    var employeeRuleList: MutableList<EmployeeRuleBean> = ArrayList()
    var filePath:String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queryEmployeeRule()
    }

    /**
     * 删除员工
     */
    fun deleteEmployeeRule(employeeRuleBean: EmployeeRuleBean) {
        if (employeeRuleBean != null) {
            DBManager.getInstance().deleteEmployeeRule(this, employeeRuleBean)
            toast("删除成功")
            queryEmployeeRule()
        }
    }

    /**
     * 查询员工列表
     */
    fun queryEmployeeRule() {
        employeeRuleList = DBManager.getInstance().queryEmployeeRule(this, null, null)
        ImportViewUi(this, employeeRuleList, "请选择文件").setContentView(this)
    }

    private val FileDialogId = 0

    fun selectEmployeeExcel() {
        showDialog(FileDialogId)
    }

    override fun onCreateDialog(id: Int): Dialog {
        if (id == FileDialogId) {
            var images = java.util.HashMap<String, Int>()
            // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹
            // 根目录图标
            images.put(FileDialog.sRoot, R.drawable.dialog_file_root)
            //返回上一层的图标
            images.put(FileDialog.sParent, R.drawable.dialog_file_folder_up)
            //文件夹图标
            images.put(FileDialog.sFolder, R.drawable.dialog_file_folder)
            //文件图标
            images.put("xlsx", R.drawable.dialog_file_excel)
            images.put("xls", R.drawable.dialog_file_excel)
            images.put(FileDialog.sEmpty, R.drawable.dialog_file_root)
            var dialog:Dialog = FileDialog.createDialog(id,this,"打开文件",fileDialogCallbackListener(),".xlsx;.xls;",images)
            return dialog
        }
        return super.onCreateDialog(id);
    }

    fun fileDialogCallbackListener(): FileDialog.FileDialogCallbackListener {
        return FileDialog.FileDialogCallbackListener { bundle -> filePath = bundle.getString("path")
            ImportViewUi(this, employeeRuleList, filePath).setContentView(this)
        }
    }

    fun readExcelData(){
        if(StringUtils.isStrNotEmpty(filePath)){
            readFileData(
                    filePath,workBookReadListener()
            )
        }else{
            toast("请选择文件")
        }
    }

    private fun workBookReadListener(): WorkBookReadListener {
        return object : WorkBookReadListener {
            override fun recordData(content: String, col: Int, row: Int) {
                toast(content+"第"+col+"列"+"第"+row+"行")
            }

            override fun recodeError(errorMsg: String) {
                toast(errorMsg)
            }
        }
    }
}