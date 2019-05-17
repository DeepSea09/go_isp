package com.wangbin.go_isp.base.db;

import android.app.Application;
import android.util.Log;

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.base.db
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/4/27
 * <br></br>TIME : 19:34
 * <br></br>MSG :数据库工具管理类
 * <br></br>************************************************
 */
public class DatabaseManager {

    public static final String DB_NAME = "Go_Isp.db";
    public static final int SCHEMA_VERSION = 1;

    public DatabaseManager() {
    }

    private static MySQLiteOpenHelper mySQLiteOpenHelper;
    private static DaoMaster sDaoMaster;

    public static void initDB(Application app) {


        mySQLiteOpenHelper = new MySQLiteOpenHelper(app, DB_NAME, null);
        sDaoMaster = new DaoMaster(mySQLiteOpenHelper.getWritableDatabase());
    }

    public static DaoSession newSession() {

        if (sDaoMaster == null) {
            throw new RuntimeException("sDaoMaster is null.");
        }
        return sDaoMaster.newSession();
    }


    public static void destroy() {
        try {
            if (sDaoMaster != null) {
                sDaoMaster.getDatabase().close();
                sDaoMaster = null;
            }

            if (mySQLiteOpenHelper != null) {
                mySQLiteOpenHelper.close();
                mySQLiteOpenHelper = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
