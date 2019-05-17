package com.youying.fortunecat.ui.base_mvp.iview


import com.irongbei.zs.ui.base_mvp.presenter.IBasePresenter
/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.youying.fortunecat.ui.base_mvp.iview
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/4/24
 * <br></br>TIME : 14:42
 * <br></br>MSG :
 * <br></br>************************************************
 */
interface ILoadingView<T : IBasePresenter> : IBaseView<T> {

    /**
     * 显示正在加载的对话框
     */
    fun showLoadingDialog()

    /**
     * 消失加载对话框
     */
    fun dismissLoadingDialog()

}
