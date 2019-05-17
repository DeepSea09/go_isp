package com.irongbei.http

import android.util.Log
import com.alibaba.fastjson.JSON
import java.lang.reflect.Type

/**
 *
 * 根据泛型，将json解析成对象
 * types的获取方式：
 *
 * (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
 *
 * @author wanglong@irongbei.com
 */

internal object JsonParser {
    @Suppress("UNCHECKED_CAST")
    fun <T> parse(str: String?, types: Array<Type>): T? {

        Log.e("types--","$types")
        return runCatching {
            if (types is List<*>) {
                JSON.parseArray(str, types) as T
            } else {
                JSON.parseObject(str, types[0]) as T
            }
        }.getOrElse { e ->

            Log.e("types--",e.toString())
            throw e
        }
    }
}