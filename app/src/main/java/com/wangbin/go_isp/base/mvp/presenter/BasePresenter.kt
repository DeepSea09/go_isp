package com.irongbei.zs.ui.base_mvp.presenter

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import com.youying.fortunecat.ui.base_mvp.iview.IBaseView
import java.lang.ref.WeakReference


/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.youying.fortunecat.ui.base_mvp.presenter
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/4/24
 * <br></br>TIME : 14:45
 * <br></br>MSG :
 * <br></br>************************************************
 */
abstract class BasePresenter<V : IBaseView<*>>(aView: V) : IBasePresenter {

    private var mViewRef: WeakReference<V>? = null

    val view: V?
        get() = mViewRef?.get()

    val context: Context?
        get() = when (view) {
            is Context -> {
                view as Context
            }
            is Fragment -> {
                (view as Fragment).context
            }
            is View -> {
                (view as View).context
            }
            else -> null
        }


    val activity: Activity?
        get() = context as? Activity

    init {
        mViewRef = WeakReference(aView)
    }

    fun clear() {
        mViewRef?.clear()
    }

    /**
     * 过时，一般可不调用，
     * 如有需要，请调用 [.clear]
     * 待删除
     */
    fun detachView() {
        mViewRef?.clear()
        mViewRef = null
    }

}