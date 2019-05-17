package com.wangbin.go_isp.ui.activity.contract

import com.irongbei.zs.ui.base_mvp.presenter.BasePresenter
import com.irongbei.zs.ui.base_mvp.presenter.IBasePresenter
import com.wangbin.go_isp.bean.ScannerCodeBean
import com.youying.fortunecat.ui.base_mvp.iview.IBaseView
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

interface ManualInventoryContract{

    interface IPresenter : IBasePresenter {

        fun getInventoryNum(scanner_code : String)//获取盘点编号

    }

    interface IView : ILoadingView<Presenter> {

        fun showInventoryNum(response : ScannerCodeBean?)
    }

    abstract class Presenter(view: IView) : BasePresenter<IView>(view), IPresenter {
        init {
            view.setPresenter(this)
        }
    }
}