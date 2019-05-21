package com.wangbin.go_isp.ui.activity.contract

import com.irongbei.zs.ui.base_mvp.presenter.BasePresenter
import com.irongbei.zs.ui.base_mvp.presenter.IBasePresenter
import com.wangbin.go_isp.bean.AssetsCategoryBean
import com.wangbin.go_isp.bean.CategoryRoomAssetsBean
import com.youying.fortunecat.ui.base_mvp.iview.ILoadingView

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp_ccb
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp_ccb.ui.activity.contract
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/5/16
 * <br></br>TIME : 10:29
 * <br></br>MSG :
 * <br></br>************************************************
 */
interface CategoryRoomAssetsContract{

    interface IPresenter : IBasePresenter {

        fun getCategoryRoomAssets(scanner_code: String, room_code : String,category_code : String)//获取房间内某一分类下的资产信息

    }

    interface IView : ILoadingView<Presenter> {

        fun showCategoryRoomAssets(data : List<CategoryRoomAssetsBean>?)
    }

    abstract class Presenter(view: IView) : BasePresenter<IView>(view), IPresenter {
        init {
            view.setPresenter(this)
        }
    }
}
