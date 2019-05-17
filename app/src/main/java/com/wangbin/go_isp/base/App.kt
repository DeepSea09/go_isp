package com.wangbin.go_isp.base


import android.support.multidex.MultiDexApplication
import butterknife.internal.Utils.listOf

import com.wangbin.base.OkGoApp
import com.wangbin.base.ResourceUtilApp
import com.wangbin.go_isp.base.db.DatabaseManager

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.base
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/4/23
 * <br></br>TIME : 13:47
 * <br></br>MSG :
 * <br></br>************************************************
 */
class App : MultiDexApplication() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        //初始化其他第三方 app
         DatabaseManager.initDB(this)
        listOf(OkGoApp, ResourceUtilApp).forEach { it.onCreate(this) }
    }
}
