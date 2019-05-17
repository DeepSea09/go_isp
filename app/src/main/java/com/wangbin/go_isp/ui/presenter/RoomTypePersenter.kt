package com.wangbin.go_isp.ui.presenter

import com.irongbei.http.Http
import com.irongbei.http.HttpCallback
import com.wangbin.go_isp.bean.FangjaincodeBean
import com.wangbin.go_isp.bean.GatherStatusBean
import com.wangbin.go_isp.bean.ReceiveRoomCodeData
import com.wangbin.go_isp.ui.activity.contract.RoomTypeContract

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.ui.presenter
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/5/8
 * <br></br>TIME : 10:05
 * <br></br>MSG :
 * <br></br>************************************************
 */
class RoomTypePersenter(view: RoomTypeContract.IView) : RoomTypeContract.Presenter(view) {
    override fun receiveAppContrastRes(room_rfid: String, scanner_code: String, inventory_code: String, rfid_gather: List<GatherStatusBean>) {
        Http.Inventory.receiveAppContrastRes(room_rfid,scanner_code,inventory_code,rfid_gather,object : HttpCallback<FangjaincodeBean>(){
            override fun onSuccess(data: FangjaincodeBean?) {
                view?.showReceiveAppContrastRes()

            }

        } )

    }

    override fun start() {
    }

    override fun getAssetsCodeListByRfid(scanner_code: String, inventory_code: String, rfid_gather: List<String>) {

        Http.Inventory.getAssetsCodeListByRfid(scanner_code,inventory_code,rfid_gather,object : HttpCallback<List<ReceiveRoomCodeData.AssetsInfo>>(){
            override fun onSuccess(data: List<ReceiveRoomCodeData.AssetsInfo>?) {

                view?.showtAssetsCodeListByRfidData(data)


            }

        } )
    }


}