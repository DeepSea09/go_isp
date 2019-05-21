package com.wangbin.go_isp.ui.activity.contract

import com.irongbei.zs.ui.base_mvp.presenter.BasePresenter
import com.irongbei.zs.ui.base_mvp.presenter.IBasePresenter
import com.wangbin.go_isp.bean.RoomLikebean
import com.youying.fortunecat.ui.base_mvp.iview.ILoadingView

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp_ccb
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp_ccb.ui.activity.contract
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/5/11
 * <br></br>TIME : 17:24
 * <br></br>MSG :
 * <br></br>************************************************
 */
 interface RoomSearchContract{

    interface IPresenter : IBasePresenter {

        fun roomLike(scanner_code : String,keywords : String)//获取盘点编号

    }

    interface IView : ILoadingView<Presenter> {

        fun showRoomLike(response : List<RoomLikebean>?)
    }

    abstract class Presenter(view: IView) : BasePresenter<IView>(view), IPresenter {
        init {
            view.setPresenter(this)
        }
    }
}
