package com.wangbin.go_isp.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.ui.activity
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/5/7
 * <br></br>TIME : 15:49
 * <br></br>MSG :
 * <br></br>************************************************
 */

data class ReceiveRoomCodeData(
                               var room_info: List<RoomInfoBean>? = emptyList()
){
        data class RoomInfoBean(
                /**
                 * rfid_code : 2fffff
                 * room_name : 无线所库房
                 * room_type : 3
                 * assets_info : ["e111","r111"]
                 */

                var rfid_code: String? = null,
                var room_name: String? = null,
                var room_type: String? = null,
                var assets_num : String? = null,
                var assets_info: List<AssetsInfo>? = emptyList()
        )
    @SuppressLint("ParcelCreator")// 用于处理 Lint 的错误提示
    @Parcelize
     class AssetsInfo(

            /**
             *  rfid_code这个是rfid标签编码，
             *  assets_code这个是资产编码，
             *  rfid_num这个是这个资产一共贴了多少个rfid标签
             */
            var rfid_code : String?=null,
            var assets_code : String? = null,
            var rfid_num : String? = null,
            var rfid_serial_number : Int = 0

    ): Parcelable
}
