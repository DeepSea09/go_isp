package com.wangbin.go_isp.bean

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.bean
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/4/28
 * <br></br>TIME : 20:05
 * <br></br>MSG :
 * <br></br>************************************************
 */
data class InventoryCodeBean (


    /**
     * id : 1
     * org_num : 1
     * room_name : 房间1
     * position :
     * room_code : fangjain1code
     * room_type : 0
     * create_date : 2019-01-15 09:12:35
     * assets_total : 2
     */

    var id: String? = null,
    var org_num: String? = null,
    var room_name: String? = null,
    var position: String? = null,
    var room_code: String? = null,
    var room_type: String? = null,
    var create_date: String? = null,
    var assets_total: Int? = 0,
    var  receive_total : Int?  =0

    )
