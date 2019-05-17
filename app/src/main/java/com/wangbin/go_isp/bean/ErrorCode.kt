package com.wangbin.go_isp.bean

/**
 * @author wanglong
 */
enum class ErrorCode(var code: Int, var desc: String) {
    CODE_200(200, "成功"),
    CODE_30000(30000 , "redis操作失败"),
    CODE_30004(30004, "扫描枪信息必传"),
    CODE_30005(30005, "必传字段不能为空"),
    CODE_30006(30006, "当前无可盘点编号"),
    CODE_30007(30007, "所传递信息中无符合的房间信息"),
    CODE_30011(30011, "已确认盘点,不能再次操作"),
    CODE_30013(30013, "扫描枪不合法"),
    CODE_30014(30014, "盘点编号不合法")

}