package com.wangbin.go_isp.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wangbin.go_isp.R
import com.wangbin.go_isp.bean.ReceiveRoomCodeData
import com.wangbin.go_isp.bean.ScannerCodeBean

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.adapter
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/4/28
 * <br></br>TIME : 23:16
 * <br></br>MSG :
 * <br></br>************************************************
 */
class RoomTypeAdapter(layoutResId: Int, data: List<ReceiveRoomCodeData.AssetsInfo>?) : BaseQuickAdapter<ReceiveRoomCodeData.AssetsInfo, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: ReceiveRoomCodeData.AssetsInfo) {
        helper.setText(R.id.tv_circle_num,"${item.rfid_serial_number}")
        helper.setText(R.id.tv_room_type_content,"${item.assets_code} (${item.rfid_num})")


    }

}
