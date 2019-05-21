package com.wangbin.go_isp.ui.activity.persenter

import com.irongbei.http.Http
import com.irongbei.http.HttpCallback
import com.wangbin.go_isp.bean.CategoryRoomAssetsBean
import com.wangbin.go_isp.bean.RoomLikebean
import com.wangbin.go_isp.ui.activity.contract.CategoryRoomAssetsContract
import com.wangbin.go_isp.ui.activity.contract.RoomSearchContract

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp_ccb
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp_ccb.ui.activity.persenter
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/5/16
 * <br></br>TIME : 10:28
 * <br></br>MSG :
 * <br></br>************************************************
 */
class CategoryRoomAssetsPersenter(view: CategoryRoomAssetsContract.IView) : CategoryRoomAssetsContract.Presenter(view) {

    override fun getCategoryRoomAssets(scanner_code: String, room_code: String, category_code: String) {

        Http.Inventory.getCategoryRoomAssets(scanner_code,room_code,category_code,object : HttpCallback<List<CategoryRoomAssetsBean>>(){
            override fun onSuccess(data: List<CategoryRoomAssetsBean>?) {
                view?.showCategoryRoomAssets(data)
            }

        })
    }


    override fun start() {

    }
}