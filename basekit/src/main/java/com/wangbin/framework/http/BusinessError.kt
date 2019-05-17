package com.irongbei.http

import com.alibaba.fastjson.JSON

/**
 * @author wanglong
 */
class BusinessError(val status: Int = 0, override val message: String = "") : Exception(message) {
    companion object {
        fun valueOf(str: String?): BusinessError {
            return JSON.parseObject(str).run {
                BusinessError(getIntValue("errCode"), getString("errMsg") ?: "")
            }
        }
    }

    override fun toString() = "status=$status,message=$message"

}