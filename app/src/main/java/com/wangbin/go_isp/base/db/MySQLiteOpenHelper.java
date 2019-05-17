package com.wangbin.go_isp.base.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wangbin.go_isp.utils.MigrationHelper;

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.base.db
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/4/27
 * <br></br>TIME : 20:12
 * <br></br>MSG :  更新升级数据库 操作类
 * <br></br>************************************************
 */
public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, GoIspDbDataDao.class);
    }
}
