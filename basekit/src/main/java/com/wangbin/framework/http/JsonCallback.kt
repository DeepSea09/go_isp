package com.irongbei.http

import android.text.TextUtils
import android.util.Log
import com.alibaba.fastjson.JSON.parseObject
import com.wangbin.framework.bean.ServerResult
import com.lzy.okgo.callback.AbsCallback
import com.lzy.okgo.model.Response
import com.lzy.okgo.request.base.Request
import org.json.JSONObject


/**
 * the Json Callback
 * wanglong
 */


internal abstract class JsonCallback<T>(private val httpCallback: HttpCallback<T>?) : AbsCallback<T>() {

    companion object {
        fun <T> valueOf(callback: HttpCallback<T>?) = object : JsonCallback<T>(callback) {
            override fun onSuccess(response: Response<T>?) {
                callback?.onSuccess(response?.body())
            }
        }
    }

    override fun onStart(request: Request<T, out Request<Any, Request<*, *>>>?) {
        httpCallback?.onStart()
    }

    override fun onFinish() {
        httpCallback?.onFinish()
    }


    /**
     * (responseCode == 404 || responseCode >= 500)
     */
    override fun onError(response: Response<T>?) {
        val code: Int = response?.code() ?: 0
        val body = response?.rawResponse?.body()?.string()
        when {
            response?.exception is BusinessError -> httpCallback?.onBusinessError(response.exception as BusinessError)
            body != null -> {
                kotlin.runCatching {
                    val e = BusinessError.valueOf(body)
                    httpCallback?.onBusinessError(e)
                }.onFailure {
                    onNetworkError(code, response)
                }
            }
            else -> onNetworkError(code, response)
        }
    }

    private fun onNetworkError(code: Int, response: Response<T>?) {
        val message = response?.exception?.message ?: ""
       // Logger.d("http.onNetworkError:code=$code,message=$message")
        httpCallback?.onNetworkError(code, message)
    }

    /**
     * (responseCode != 404 && responseCode < 500)
     */
    override fun convertResponse(response: okhttp3.Response): T? {
        val body = response.body()?.string()
        val httpUrl = response.request().url().toString()

        val checkServerResult = checkServerResult(body)
        if (httpUrl.equals( "http://192.168.3.251:8001/"+"inventoryapi/receiveRoomCode")){
            //val parseObject = parseObject<ReceiveRoomCodeData>(checkServerResult.data, ReceiveRoomCodeData::class.java)
            if (response.code() == 200){
               if (TextUtils.isEmpty(checkServerResult.data)){
                   throw BusinessError.valueOf(body)
               }
                val jsonObject = JSONObject(checkServerResult.data)
                val array = jsonObject.getJSONArray("room_info")
                if (!TextUtils.isEmpty(array.toString())){
                    return  httpCallback?.parse(array.toString())
                }else{
                   throw BusinessError.valueOf(body)
               }
            }else{
                throw Exception("网络连接失败")
            }
        } else {
            if (response.code() == 200) {
                if (!TextUtils.isEmpty(checkServerResult.data)) {
                    return httpCallback?.parse(checkServerResult.data)
                } else {
                    throw BusinessError.valueOf(body)
                }
            } else {

                throw Exception("网络连接失败吧v发的")
            }
        }
    }

    /**
     * 检测服务器返回数据，并解析到实体类中
     *
     * @param aServerResponse 服务器返回Json数据
     * @return 对应实体类，对象不为空
     */
    fun checkServerResult(aServerResponse: String?): ServerResult {
        try {

            return parseObject<ServerResult>(aServerResponse, ServerResult::class.java)
        } catch (e: Exception) {
            return ServerResult()
        }

    }
}
