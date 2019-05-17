package com.wangbin.go_isp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangbin.go_isp.R;
import com.wangbin.go_isp.adapter.ManualInventoryAdapter;
import com.wangbin.go_isp.base.App;
import com.wangbin.go_isp.base.BaseActivity;
import com.wangbin.go_isp.base.EntityDao.IspDataEntityDao;
import com.wangbin.go_isp.base.db.GoIspDbDataDao;
import com.wangbin.go_isp.bean.GoIspDbData;
import com.wangbin.go_isp.bean.ReceiveRoomCodeData;
import com.wangbin.go_isp.bean.ScannerCodeBean;
import com.wangbin.go_isp.ui.activity.contract.ManualInventoryContract;
import com.wangbin.go_isp.ui.presenter.ManualInventoryPersenter;
import com.wangbin.go_isp.utils.AppUtils;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.ui.activity
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/4/27
 * <br></br>TIME : 9:36
 * <br></br>MSG :
 * <br></br>************************************************
 */

/**
 * 人工盘点
 */
public class ManualInventoryActivity extends BaseActivity implements ManualInventoryContract.IView {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ry_manual_inventory)
    RecyclerView ryManualInventory;
    List<ScannerCodeBean> scannerCode_list = new ArrayList<>();
    private static final String TAG = "ManualInventoryActivity";
    @BindView(R.id.btn_obtain)
    TextView btnObtain;
    private ManualInventoryContract.IPresenter presenter;
    private BaseQuickAdapter manualInventoryAdapter;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_manual_inventory;
    }

    @Override
    protected void init() {
        fullScreen(this);
        ryManualInventory.setLayoutManager(new LinearLayoutManager(this));
        Log.e(TAG, "init: "+ AppUtils.getInstance(App.instance).getMacAddress());
    }


    @OnClick({R.id.btn_obtain})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_obtain:
                presenter.getInventoryNum(getMacAddress());

                break;
            default:
                break;
        }
    }
    public static void startRoomTypeActivity(Activity context) {
        Intent intent = new Intent(context, RoomTypeActivity.class);
        context.startActivity(intent);
        context.finish();
    }

    @Override
    protected void intData() {
        new ManualInventoryPersenter(this);
        presenter.getInventoryNum(getMacAddress());
        manualInventoryAdapter = new ManualInventoryAdapter(R.layout.manual_inventory_activity_item_view, scannerCode_list);
        manualInventoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (scannerCode_list.size() > 0) {
                String inv_code = scannerCode_list.get(position).getInv_code();
                SelectRoomActivity.startSelectRoomActivity(ManualInventoryActivity.this, inv_code);
            }
        });
        ryManualInventory.setAdapter(manualInventoryAdapter);

    }


    @Override
    public void showInventoryNum(ScannerCodeBean response) {
        if (response == null) return;
        scannerCode_list.clear();
        scannerCode_list.add(response);
        manualInventoryAdapter.notifyDataSetChanged();

    }

    @Override
    public void setPresenter(@NotNull ManualInventoryContract.Presenter presenter) {

        this.presenter = presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
