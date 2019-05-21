package com.wangbin.go_isp.ui.activity.contract

import com.irongbei.zs.ui.base_mvp.presenter.BasePresenter
import com.irongbei.zs.ui.base_mvp.presenter.IBasePresenter
import com.wangbin.go_isp.bean.AssetsCategoryBean
import com.youying.fortunecat.ui.base_mvp.iview.ILoadingView

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp_ccb
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp_ccb.ui.activity.contract
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/5/13
 * <br></br>TIME : 14:47
 * <br></br>MSG :
 * <br></br>************************************************
 */
interface AssetsBindingContract{

    interface IPresenter : IBasePresenter {

        fun roomRfid(scanner_code: String, rfid : String, room_code : String)//获取盘点编号
        fun getRoomAssetsCategory(scanner_code: String,room_code : String)//查询房间内资产目录信息
        fun getassetsRfid(scanner_code :String,rfid :String,assets_code : String)//资产绑定RFID

    }

    interface IView : ILoadingView<Presenter> {

        fun showRoomRfid()
        fun showRoomAssetsCategory(data : List<AssetsCategoryBean>?)
        fun showAssetsRfid(message : String)
    }

    abstract class Presenter(view: IView) : BasePresenter<IView>(view), IPresenter {
        init {
            view.setPresenter(this)
        }
    }
}
