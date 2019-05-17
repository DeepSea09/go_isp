package com.irongbei.http

import com.alibaba.fastjson.JSON
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.HttpParams
import com.lzy.okgo.model.Response
import com.lzy.okgo.request.base.Request



/**
 * @author wanglong
 */
open class BaseHttp {
    companion object {
        /**
         * 是否使用异步模式，默认true
         */
        var ASYNC_MODE: Boolean = true


        /**
         * API接口的根 URL 路径
         */
        lateinit var BASE_URL: String
    }


    /**
     * get 请求
     */
    protected fun <T> get(pathOrUrl: String, params: Map<String, String> = emptyMap(), callback: HttpCallback<T>) {
        val url = getFullUrl(pathOrUrl)

        request(OkGo.get<T>(url).params(params), callback)
    }
    /**
     * get 请求
     */
    protected fun <T> post_params(pathOrUrl: String, params:Map<String, String> = emptyMap(), callback: HttpCallback<T>) {
        val url = getFullUrl(pathOrUrl)
        request(OkGo.post<T>(url).params(params), callback)
    }

    /**
     * 获得全路径
     */
    private fun getFullUrl(path: String): String {
        val url = if (path.startsWith("http")) path else BASE_URL + path
        if (!url.startsWith("http")) {
            throw NullPointerException("$url should start with http or https")
        }
        return url
    }

    private fun getUpJson(pathOrUrl: String, dataToUp: Any? = null): Pair<String, String?> {
        val url = getFullUrl(pathOrUrl)
        val json = JSON.toJSONString(dataToUp ?: "")
        return Pair(url, json)
    }


    private fun getUpParams(pathOrUrl: String, dataToUp:Map<String, String> = emptyMap()): Pair<String, HttpParams?>{
         val url = getFullUrl(pathOrUrl)
         var params = HttpParams()
        dataToUp.forEach{
            params.put(it.key,it.value)
        }
        return  Pair(url, params)

    }

    /**
     * post 请求
     */
    protected fun <T> post(pathOrUrl: String, dataToUp: Any? = null, callback: HttpCallback<T>) {
        var (url, json) = getUpJson(pathOrUrl, dataToUp)
        request(OkGo.post<T>(url).upJson(json), callback)
    }

    /**
     * post 请求
     */
    protected fun <T> post(pathOrUrl: String, dataToUp:Map<String, String> = emptyMap(), callback: HttpCallback<T>) {
        var (url, params) = getUpParams(pathOrUrl, dataToUp)
        request(OkGo.post<T>(url).params(params), callback)
    }

    /**
     * put 请求
     */
    protected fun <T> put(pathOrUrl: String, dataToUp: Any? = null, callback: HttpCallback<T>) {
        var (url, json) = getUpJson(pathOrUrl, dataToUp)
        request(OkGo.put<T>(url).upJson(json), callback)
    }

    /**
     * 请求发送前统一处理，如设置基础参数，headers等
     */
    private fun <T, R : Request<T, *>> request(request: Request<T, R>, callback: HttpCallback<T>): Request<T, R> {
        /** 统一的头部，和参数处理*/
        //request.headers("", "")
        //request.params("", "")
        return JsonCallback.valueOf(callback).run {
            if (ASYNC_MODE) {
                /** 非阻塞异步请求，回调在UI线程中处理，用于APP中使用 */
                request.execute(this)
            } else {
                /** 阻塞方式同步请求，供测试模式下使用*/
                val response = request.execute()
                runCatching {
                    onSuccess(Response.success(false, convertResponse(response), request.rawCall, response))
                }.onFailure {
                    onError(Response.error(false, request.rawCall, response, it))
                }
            }
            request
        }
    }
}
