package com.wangbin.go_isp.bean

import android.support.annotation.Keep

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.bean
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/4/28
 * <br></br>TIME : 16:03
 * <br></br>MSG :
 * <br></br>************************************************
 */
data class ScannerCodeBean(


    /**
     * id : 21
     * inv_type : 1
     * inv_code : 6455745899999
     * org_num : 1
     * op_user : 1
     * op_username : gxy
     * is_complete : 1
     * complete_date : 0000-00-00 00:00:00
     * create_date : 2019-04-10 16:37:26
     * assets_total : 10                 盘点资产个数
     */

    var id: String? = null,
    var inv_type: String? = null,
    var inv_code: String? = null,
    var org_num: String? = null,
    var op_user: String? = null,
    var op_username: String? = null,
    var is_complete: String? = null,
    var complete_date: String? = null,
    var create_date: String? = null,
    var assets_total: String? = null,
    var scannered_assets_total: String? = null

)
