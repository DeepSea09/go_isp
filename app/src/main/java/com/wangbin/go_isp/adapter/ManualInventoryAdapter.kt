package com.wangbin.go_isp.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wangbin.go_isp.R
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
class ManualInventoryAdapter(layoutResId: Int, data: List<ScannerCodeBean>?) : BaseQuickAdapter<ScannerCodeBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: ScannerCodeBean) {
        var i = item.assets_total!!.toInt()- item.scannered_assets_total!!.toInt();
        helper.setText(R.id.tv_number, item.inv_type)
        helper.setText(R.id.tv_manual_content, "${item.inv_code}【${item.op_username}】【${item.scannered_assets_total}/${item.assets_total}】")


    }

}
