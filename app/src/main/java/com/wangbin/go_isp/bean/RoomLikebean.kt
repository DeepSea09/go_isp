package com.wangbin.go_isp.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import com.contrarywind.interfaces.IPickerViewData
import kotlinx.android.parcel.Parcelize

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp_ccb
 * <br></br>PACKAGE_NAME : com.wangbin.bean
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/5/11
 * <br></br>TIME : 17:28
 * <br></br>MSG :
 * <br></br>************************************************
 */
@SuppressLint("ParcelCreator")// 用于处理 Lint 的错误提示
@Parcelize
class RoomLikebean (

    /**
     * room_code : 2
     * room_name : 有线实验室01
     */

    var room_code: String? = null,
    var room_name: String? = null,
    var assets_num: String? = null,
    var rfid_num : Int ? = 0
): Parcelable,IPickerViewData {
    override fun getPickerViewText(): String? {
        return  room_name
    }
}
