package com.wangbin.go_isp.bean

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.bean
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/5/8
 * <br></br>TIME : 11:10
 * <br></br>MSG :
 * <br></br>************************************************
 */

/**  资产rfid码 ，状态  right代表对比正常，lack代表对比丢失，more代表其他移位
 * "q111": "right"
 */
data class GatherStatusBean(
        var rfid_code: String? = null,
        var status: String? = null
)