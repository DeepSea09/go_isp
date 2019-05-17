package com.wangbin.go_isp.ui.presenter


import android.util.Log
import com.irongbei.http.BusinessError
import com.irongbei.http.Http
import com.irongbei.http.HttpCallback
import com.wangbin.go_isp.bean.ScannerCodeBean
import com.wangbin.go_isp.ui.activity.contract.ManualInventoryContract

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.ui.presenter
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/4/26
 * <br></br>TIME : 16:51
 * <br></br>MSG :
 * <br></br>************************************************
 */

class ManualInventoryPersenter(view: ManualInventoryContract.IView) : ManualInventoryContract.Presenter(view) {

    override fun getInventoryNum(scanner_code : String) {

        Http.Inventory.getInventoryNum(scanner_code,object : HttpCallback<ScannerCodeBean>() {
            override fun onSuccess(data: ScannerCodeBean?) {
                view?.showInventoryNum(response = data)

            }


            override fun onStart() {
                super.onStart()
                view?.showLoadingDialog()
            }

            override fun onFinish() {
                super.onFinish()
                view?.dismissLoadingDialog()
            }

        })


    }

    override fun start() {

    }
}