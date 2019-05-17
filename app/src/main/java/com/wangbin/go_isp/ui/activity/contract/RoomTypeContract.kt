package com.wangbin.go_isp.ui.activity.contract

import com.irongbei.zs.ui.base_mvp.presenter.BasePresenter
import com.irongbei.zs.ui.base_mvp.presenter.IBasePresenter
import com.wangbin.go_isp.bean.GatherStatusBean
import com.wangbin.go_isp.bean.ReceiveRoomCodeData
import com.youying.fortunecat.ui.base_mvp.iview.IBaseView

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.ui.activity.contract
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/5/8
 * <br></br>TIME : 10:05
 * <br></br>MSG :
 * <br></br>************************************************
 */
interface RoomTypeContract{

    interface IPresenter : IBasePresenter {

        fun getAssetsCodeListByRfid(scanner_code : String ,inventory_code : String ,rfid_gather : List<String>)
        fun receiveAppContrastRes(room_rfid :String,scanner_code :String,inventory_code : String,rfid_gather : List<GatherStatusBean>)

    }

    interface IView : IBaseView<Presenter> {

        fun  showtAssetsCodeListByRfidData(data:List<ReceiveRoomCodeData.AssetsInfo>?)
        fun  showReceiveAppContrastRes()

    }

    abstract class Presenter(view: IView) : BasePresenter<IView>(view), IPresenter {
        init {
            view.setPresenter(this)
        }
    }
}