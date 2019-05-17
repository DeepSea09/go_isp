package com.wangbin.go_isp.ui.activity.contract

import com.irongbei.zs.ui.base_mvp.presenter.BasePresenter
import com.irongbei.zs.ui.base_mvp.presenter.IBasePresenter
import com.wangbin.go_isp.bean.AssetsNumByEndInvBean
import com.wangbin.go_isp.bean.InventoryCodeBean
import com.wangbin.go_isp.bean.ReceiveRoomCodeData
import com.youying.fortunecat.ui.base_mvp.iview.ILoadingView

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.ui.activity.contract
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/4/26
 * <br></br>TIME : 16:51
 * <br></br>MSG :
 * <br></br>************************************************
 */

interface SelectRoomContract{

    interface IPresenter : IBasePresenter {

        fun getRoomInfoByInvCode(scanner_code : String,inventory_code : String,isReceiveAgain : Boolean)//获取盘点编号
        fun getReceiveRoomCode(scanner_code : String ,inventory_code : String ,  code_gather : List<String>)
        fun getAssetsNumByEndInv(scanner_code : String,inventory_code : String)
        fun getsetInventoryEnd(scanner_code : String,inventory_code : String)

    }

    interface IView : ILoadingView<Presenter> {

        fun showRoomInfoByInvCodeData(data: List<InventoryCodeBean>?)
        fun  showtReceiveRoomCodeData(data:List<ReceiveRoomCodeData.RoomInfoBean>?)
        fun showAssetsNumByEndInv(data: AssetsNumByEndInvBean?)
        fun showsetInventoryEnd()
    }

    abstract class Presenter(view: IView) : BasePresenter<IView>(view), IPresenter {
        init {
            view.setPresenter(this)
        }
    }
}