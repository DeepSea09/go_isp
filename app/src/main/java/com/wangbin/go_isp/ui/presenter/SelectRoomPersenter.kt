package com.wangbin.go_isp.ui.presenter


import android.util.Log
import com.irongbei.http.Http
import com.irongbei.http.HttpCallback
import com.wangbin.go_isp.bean.AssetsNumByEndInvBean
import com.wangbin.go_isp.bean.InventoryCodeBean
import com.wangbin.go_isp.bean.ReceiveRoomCodeData
import com.wangbin.go_isp.bean.ScannerCodeBean
import com.wangbin.go_isp.ui.activity.contract.SelectRoomContract

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

class SelectRoomPersenter(view: SelectRoomContract.IView) : SelectRoomContract.Presenter(view) {
    override fun getsetInventoryEnd(scanner_code: String, inventory_code: String) {
        Http.Inventory.getInventoryEnd(scanner_code, inventory_code, object : HttpCallback<ScannerCodeBean>() {
            override fun onSuccess(data: ScannerCodeBean?) {
               view?.showsetInventoryEnd()
            }
        })

    }

    override fun getAssetsNumByEndInv(scanner_code: String,inventory_code: String) {

        Http.Inventory.getAssetsNumByEndInv(scanner_code, inventory_code, object : HttpCallback<AssetsNumByEndInvBean>() {
            override fun onSuccess(data: AssetsNumByEndInvBean?) {
                view?.showAssetsNumByEndInv(data)
            }

        })

    }


    override fun start() {
    }

    override fun getRoomInfoByInvCode(scanner_code: String, inventory_code: String, isReceiveAgain: Boolean) {

        Http.Inventory.getRoomInfoByInvCode(scanner_code, inventory_code, object : HttpCallback<List<InventoryCodeBean>>() {
            override fun onSuccess(data: List<InventoryCodeBean>?) {
                view?.showRoomInfoByInvCodeData(data)
            }

            override fun onStart() {
                super.onStart()
                if (isReceiveAgain)
                    view?.showLoadingDialog()
            }

            override fun onFinish() {
                super.onFinish()
                if (isReceiveAgain)
                    view?.dismissLoadingDialog()
            }


        })
    }

    override fun getReceiveRoomCode(scanner_code: String, inventory_code: String, code_gather: List<String>) {

        Http.Inventory.receiveRoomCode(scanner_code, inventory_code, code_gather, object : HttpCallback<List<ReceiveRoomCodeData.RoomInfoBean>>() {
            override fun onSuccess(data: List<ReceiveRoomCodeData.RoomInfoBean>?) {
                view?.showtReceiveRoomCodeData(data)
            }

        })

    }
}


