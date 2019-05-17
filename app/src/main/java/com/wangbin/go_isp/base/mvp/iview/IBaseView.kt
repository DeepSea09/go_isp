package com.youying.fortunecat.ui.base_mvp.iview

import com.irongbei.zs.ui.base_mvp.presenter.IBasePresenter


/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.youying.fortunecat.ui.base_mvp.iview
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/4/24
 * <br></br>TIME : 14:40
 * <br></br>MSG :
 * <br></br>************************************************
 */
interface IBaseView<P : IBasePresenter> {
    /**
     * 设置界面
     * @param
     */
    fun setPresenter(presenter: P)


}
