package com.wangbin.go_isp.ui.activity.persenter

import com.irongbei.http.Http
import com.irongbei.http.HttpCallback
import com.wangbin.go_isp.bean.RoomLikebean
import com.wangbin.go_isp.ui.activity.contract.RoomSearchContract

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp_ccb
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp_ccb.ui.activity
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/5/11
 * <br></br>TIME : 17:23
 * <br></br>MSG :
 * <br></br>************************************************
 */
class RoomSearchPersenter(view: RoomSearchContract.IView) : RoomSearchContract.Presenter(view) {

    override fun roomLike(scanner_code: String, keywords: String) {
        Http.Inventory.roomLike(scanner_code,keywords,object : HttpCallback<List<RoomLikebean>>() {
            override fun onSuccess(data: List<RoomLikebean>?) {
                view?.showRoomLike(response = data)

            }

        })
    }

    override fun start() {

    }
}
