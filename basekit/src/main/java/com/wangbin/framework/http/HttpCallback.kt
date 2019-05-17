package com.irongbei.http

import android.text.TextUtils
import com.blankj.utilcode.util.ToastUtils
import java.lang.reflect.ParameterizedType


/**
 * BaseHttp 回调
 * @author wanglong
 */
abstract class HttpCallback<T> {

    private var types = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments

    fun parse(str: String?): T? = JsonParser.parse(str, types)


    open fun onStart() {

    }

    open fun onFinish() {

    }

    abstract fun onSuccess(data: T?)


    open fun onBusinessError(error: BusinessError) {
        if (!TextUtils.isEmpty(error.message)) {
            ToastUtils.showShort(error.message)
        }

    }

    open fun onNetworkError(status: Int, error: String) {
         ToastUtils.showShort("网络连接失败")
    }


}