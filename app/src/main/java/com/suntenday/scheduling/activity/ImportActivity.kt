package com.suntenday.scheduling.activity

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import com.alibaba.fastjson.JSON
import com.suntenday.scheduling.R
import com.suntenday.scheduling.activity.base.BaseActivity
import com.suntenday.scheduling.activity.ui.ImportViewUi
import com.suntenday.scheduling.bean.EmployeeRuleBean
import com.suntenday.scheduling.dialog.FileDialog
import com.suntenday.scheduling.manager.DBManager
//import com.suntenday.scheduling.utils.PoiExcelUtils
import com.suntenday.scheduling.utils.StringUtils
import com.suntenday.scheduling.utils.WorkbookUtils.readFileData
import com.suntenday.scheduling.utils.WorkbookUtils.WorkBookReadListener
import com.suntenday.scheduling.utils.WorkbookUtils.readFileData
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast
import java.util.HashMap
import com.suntenday.scheduling.utils.PermissionUtils.PermissionListener
import com.suntenday.scheduling.utils.PermissionUtils


/**
 * 导入员工
 * @author suntenday
 * @date 2018/1/31 0031 14:18
 */
class ImportActivity : BaseActivity() {

    private var employeeRuleList: MutableList<EmployeeRuleBean> = ArrayList()
    private var filePath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkExternalPermission()
        queryEmployeeRule()
    }


    private fun checkExternalPermission() {
        PermissionUtils.checkStoragePermission(this, 1, object : PermissionListener {

            override fun disallowable() {
            }

            override fun allowable() {
            }
        })
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
//            images.put("xlsx", R.drawable.dialog_file_excel)
            images.put("xls", R.drawable.dialog_file_excel)
            images.put(FileDialog.sEmpty, R.drawable.dialog_file_root)
            var dialog: Dialog = FileDialog.createDialog(id, this, "打开文件", fileDialogCallbackListener(), ".xls;", images)
            return dialog
        }
        return super.onCreateDialog(id);
    }

    fun fileDialogCallbackListener(): FileDialog.FileDialogCallbackListener {
        return FileDialog.FileDialogCallbackListener { bundle ->
            filePath = bundle.getString("path")
            ImportViewUi(this, employeeRuleList, filePath).setContentView(this)
        }
    }

    private var fileData: MutableMap<Int, MutableMap<Int, String>>? = null
    private var excelSize: Int = 0

    fun recordExcelData(content: String, col: Int, row: Int) {
        if (fileData == null) {
            fileData = HashMap()
        }
        var rowData: MutableMap<Int, String>? = fileData!![row]
        if (rowData == null) {
            rowData = HashMap()
        }
        rowData.put(col, content)
        fileData!!.put(row, rowData)
    }

    fun readExcelData() {
        if (StringUtils.isStrNotEmpty(filePath)) {
            readFileData(
                    filePath, workBookReadListener()
            )

            System.out.println("Excel读取内容 == " + JSON.toJSONString(fileData))
            importDBData()
        } else {
            toast("请选择文件")
        }
    }

    private fun importDBData() {
        val employeeRuleBeanList = java.util.ArrayList<EmployeeRuleBean>()
        excelSize = fileData!!.size
        for (i in 0 until excelSize) {
            val excelItemMap = fileData!![i]
            val excelItem = EmployeeRuleBean(excelItemMap)
            employeeRuleBeanList.add(excelItem)
        }
        DBManager.getInstance().addEmployeeRuleList(this, employeeRuleBeanList)
        ImportViewUi(this, employeeRuleBeanList, filePath).setContentView(this)
    }

    private fun workBookReadListener(): WorkBookReadListener {
        return object : WorkBookReadListener {
            override fun recordData(content: String, col: Int, row: Int) {
                recordExcelData(content, col, row)
            }

            override fun recodeError(errorMsg: String) {
                toast(errorMsg)
            }
        }
    }
}