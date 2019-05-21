package com.wangbin.go_isp.ui.activity.persenter

import com.irongbei.http.BusinessError
import com.irongbei.http.Http
import com.irongbei.http.HttpCallback
import com.wangbin.go_isp.bean.AssetsCategoryBean
import com.wangbin.go_isp.bean.CategoryRoomAssetsBean
import com.wangbin.go_isp.bean.RoomLikebean
import com.wangbin.go_isp.ui.activity.contract.AssetsBindingContract
import com.wangbin.go_isp.ui.activity.contract.RoomSearchContract

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp_ccb
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp_ccb.ui.activity
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/5/13
 * <br></br>TIME : 14:48
 * <br></br>MSG :
 * <br></br>************************************************
 */
class AssetsBindingPensenter(view: AssetsBindingContract.IView) : AssetsBindingContract.Presenter(view) {
    override fun getassetsRfid(scanner_code: String, rfid: String, assets_code: String) {

        Http.Inventory.getAssetsRfid(scanner_code,rfid,assets_code,object : HttpCallback<CategoryRoomAssetsBean>(){
            override fun onSuccess(data: CategoryRoomAssetsBean?) {
                view?.showAssetsRfid("")

            }
            override fun onBusinessError(error: BusinessError) {
                super.onBusinessError(error)
                view?.showAssetsRfid(error.message)
            }
        })

    }

    override fun getRoomAssetsCategory(scanner_code: String, room_code: String) {

        Http.Inventory.getRoomAssetsCategory(scanner_code,room_code,object : HttpCallback<List<AssetsCategoryBean>>(){
            override fun onSuccess(data: List<AssetsCategoryBean>?) {
                view?.showRoomAssetsCategory(data)
            }

        })
    }

    override fun roomRfid(scanner_code: String, rfid: String, room_code: String) {

        Http.Inventory.roomRfid(scanner_code,rfid,room_code,object : HttpCallback<RoomLikebean>() {
            override fun onSuccess(data: RoomLikebean?) {
                view?.showRoomRfid()

            }

            override fun onBusinessError(error: BusinessError) {
                super.onBusinessError(error)
                view?.showRoomRfid()
            }

        })
    }

    override fun start() {

    }
}
