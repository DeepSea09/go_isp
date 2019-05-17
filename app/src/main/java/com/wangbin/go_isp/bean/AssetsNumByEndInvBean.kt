package com.wangbin.go_isp.bean

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.bean
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/5/8
 * <br></br>TIME : 15:28
 * <br></br>MSG :
 * <br></br>************************************************
 */


/**
 * assets_inv_num代表了我们一共要盘点的资产个数，lack_assets_num代表了失联了多少资产的个数
 *
 * assets_find_num 就是已发现的
 */
data class AssetsNumByEndInvBean(

        var assets_inv_num: Int? = 0,
        var lack_assets_num: Int? = 0,
        var assets_find_num: Int? = 0
)