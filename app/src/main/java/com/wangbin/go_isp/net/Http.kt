package com.irongbei.http


import android.util.Log
import com.alibaba.fastjson.JSON
import com.wangbin.go_isp.Constants
import com.wangbin.go_isp.bean.*

/**
 * @author wanglong
 */
object Http : BaseHttp() {
    init {
        //设置基础url地址
        BASE_URL = Constants.NET_URL
    }


    object Inventory {

        /**
         * 获取盘点编号接口
         */
        fun getInventoryNum(scanner_code: String, callback: HttpCallback<ScannerCodeBean>) {
            post_params("inventoryapi/getInventoryNum",
                    mapOf("scanner_code" to scanner_code), callback = callback)


        }

        /**
         * app通过盘点编号获取需要盘点的房间列表
         */

        fun getRoomInfoByInvCode(scanner_code: String,inventory_code: String, callback: HttpCallback<List<InventoryCodeBean>>) {

            post_params("inventoryapi/getRoomInfoByInvCode",
                    mapOf("inventory_code" to inventory_code,"scanner_code" to  scanner_code), callback = callback)

        }

        /**
         * 3、扫描枪把扫描出来的rfid编码总和传递给后端，获取rfid编码总和中，
         * 如果有房间rfid编码，返回房间下所有需要进行盘点的资产rfid信息
         *
         * 传递参数：scanner_code    扫描枪rfid编码
        inventory_code      盘点编号编码
        code_gather         扫描枪扫描出来的rfid编码总和 例如：["q","w","e","r","qqqqqq","wwwwww","eeeeee"]     json格式
         */


        fun receiveRoomCode(scanner_code: String, inventory_code: String, code_gather: List<String>, callback: HttpCallback<List<ReceiveRoomCodeData.RoomInfoBean>>) {
            Log.e("aaaaaaaaaaaaaaa",JSON.toJSONString(code_gather));
            Log.e("ccccccccccccc",scanner_code);
            Log.e("iiiiiiiiiiii",inventory_code);
            Log.e("------",Constants.NET_URL+"inventoryapi/receiveRoomCode");
            post_params("inventoryapi/receiveRoomCode",
                    mapOf("scanner_code" to scanner_code,
                            "inventory_code" to inventory_code,
                            "code_gather" to JSON.toJSONString(code_gather)),
                    callback = callback)


        }

        /**
         * rfid_gather 扫描出来的所有的rfid编码
         */
        fun getAssetsCodeListByRfid(scanner_code: String, inventory_code: String, rfid_gather: List<String>, callback: HttpCallback<List<ReceiveRoomCodeData.AssetsInfo>>) {

            post_params("inventoryapi/getAssetsCodeListByRfid",
                    mapOf("scanner_code" to scanner_code,
                            "inventory_code" to inventory_code,
                            "rfid_gather" to JSON.toJSONString(rfid_gather)),
                    callback = callback)

        }
        /**
         *  扫描枪盘点fangjain1code房间（app上点击房间rfid编码动作）
         *
         *    传递参数：room_rfid    房间rfid编码
                        scanner_code    扫描枪rfid编码
                       inventory_code    资产编号
                      rfid_gather        对比后的扫描结果   例如：{"q111": "right","w111": "lack","y111": "more"}   json格式
                       q111代表资产rfid编码，right代表对比正常，lack代表对比丢失，more代表其他移位
         *
         *
         */
        fun receiveAppContrastRes(room_rfid : String,scanner_code :String,inventory_code :String,rfid_gather : List<GatherStatusBean>,callback: HttpCallback<FangjaincodeBean>){
            Log.e("room_rfid----",room_rfid)
            Log.e("scanner_code----",scanner_code)
            Log.e("inventory_code----",inventory_code)
            Log.e("rfid_gather----",JSON.toJSONString(rfid_gather))
            post_params("inventoryapi/receiveAppContrastRes",
                    mapOf("room_rfid" to room_rfid,
                            "scanner_code" to scanner_code,
                            "inventory_code" to inventory_code,
                            "rfid_gather" to JSON.toJSONString(rfid_gather)),
                    callback = callback)

        }

        fun getAssetsNumByEndInv( scanner_code : String,inventory_code : String,callback: HttpCallback<AssetsNumByEndInvBean>){

            post_params("inventoryapi/getAssetsNumByEndInv",
                    mapOf("scanner_code" to scanner_code,
                            "inventory_code" to inventory_code),
                    callback = callback)
        }

        /**
         * app告知确认结束盘点接口
         *
         * 传递参数：inventory_code      盘点编号
         */
        fun getInventoryEnd(scanner_code: String,inventory_code  : String,callback: HttpCallback<ScannerCodeBean>){

            post_params("inventoryapi/setInventoryEnd",
                    mapOf("inventory_code" to inventory_code,"scanner_code" to scanner_code),
                    callback = callback)

        }

    }


}