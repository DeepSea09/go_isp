package com.wangbin.go_isp.base.EntityDao;


import android.util.Log;

import com.wangbin.go_isp.base.db.DatabaseManager;
import com.wangbin.go_isp.base.db.GoIspDbDataDao;
import com.wangbin.go_isp.bean.GoIspDbData;

/**
 * Created by ji_cheng on 2016/12/6.
 */
public class IspDataEntityDao extends BaseEntityDao<GoIspDbDataDao, GoIspDbData, Long> {

    @Override
    protected GoIspDbDataDao initEntityDao() {

        return DatabaseManager.newSession().getGoIspDbDataDao();
    }


}
