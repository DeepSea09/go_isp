package com.wangbin.go_isp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.UHF.scanlable.UfhData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangbin.go_isp.R;
import com.wangbin.go_isp.adapter.ActivityRoomAdapter;
import com.wangbin.go_isp.base.BaseActivity;
import com.wangbin.go_isp.base.EntityDao.IspDataEntityDao;
import com.wangbin.go_isp.bean.AssetsNumByEndInvBean;
import com.wangbin.go_isp.bean.GoIspDbData;
import com.wangbin.go_isp.bean.InventoryCodeBean;
import com.wangbin.go_isp.bean.MessageEvent;
import com.wangbin.go_isp.bean.ReceiveRoomCodeData;
import com.wangbin.go_isp.ui.activity.contract.SelectRoomContract;
import com.wangbin.go_isp.ui.presenter.SelectRoomPersenter;
import com.wangbin.go_isp.utils.DefaultOnActionListener;
import com.wangbin.go_isp.utils.PromptTextDialog;
import com.wangbin.go_isp.utils.SelectRoomDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.ui.activity
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/4/24
 * <br></br>TIME : 12:40
 * <br></br>MSG :
 * <br></br>************************************************
 */

public class SelectRoomActivity extends BaseActivity implements SelectRoomContract.IView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ry_select_room)
    RecyclerView rySelectRoom;
    @BindView(R.id.tv_receive_again)
    TextView tvReceiveAgain;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private List<InventoryCodeBean> mDataList = new ArrayList<>();
    private List<ReceiveRoomCodeData.RoomInfoBean> receiveRoomCode_list = new ArrayList<>();
    private int tty_speed = 57600;
    private byte addr = (byte) 0xff;
    private static final String TAG = "SelectRoomActivity";
    private static final int SCAN_INTERVAL = 100;
    private BaseQuickAdapter roomAdapter;
    private SelectRoomContract.IPresenter iPresenter;
    private Disposable mDisposable;
    private boolean Scanflag = false;
    private static final int MSG_UPDATE_SCANNING= 0x112;
    private static final int MSG_UPDATE_NET= 0x113;
    private MyHandler mHandler = new MyHandler(this);
    List<String> rfid_list ;

    private class MyHandler extends Handler {

        WeakReference<SelectRoomActivity> weakReference;
        public MyHandler(SelectRoomActivity activity) {
            weakReference = new WeakReference<>(activity);

        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SelectRoomActivity activity = weakReference.get();
                switch (msg.what) {
                    case MSG_UPDATE_SCANNING:

                        rfid_list = new ArrayList<>(UfhData.scanResult6c.keySet());
                        Log.e("handleMessage: ----", com.alibaba.fastjson.JSONObject.toJSONString(rfid_list));
                        break;
                    case MSG_UPDATE_NET:
                       activity.stopScanning();
                        default:

                }
            }
        }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_select_room;
    }

    private Disposable disposable;
    private int openScanningResult = -1;
    private String inventory_code;


    @Override
    protected void init() {
        fullScreen(this);
        new SelectRoomPersenter(this);
        openScanning();
        EventBus.getDefault().register(this);//注册EventBus
        rySelectRoom.setLayoutManager(new GridLayoutManager(this, 2));

    }

    public static void startSelectRoomActivity(Context context, String inventory_code) {

        Intent intent = new Intent(context, SelectRoomActivity.class);
        intent.putExtra("inventory_code", inventory_code);
        context.startActivity(intent);
    }

    @Override
    protected void intData() {
        inventory_code = getIntent().getStringExtra("inventory_code");
        iPresenter.getRoomInfoByInvCode(getMacAddress(), inventory_code, false);
        roomAdapter = new ActivityRoomAdapter(R.layout.room_activity_item_view, mDataList);
//        roomAdapter.setOnItemClickListener((adapter, view, position) -> {
//            RoomTypeActivity.startRoomTypeActivity(SelectRoomActivity.this, receiveRoomCode_list.get(position).getAssets_info(), inventory_code, receiveRoomCode_list.get(position).getRoom_name(), receiveRoomCode_list.get(position).getRfid_code(), receiveRoomCode_list.get(position).getAssets_num());
//        });
        rySelectRoom.setAdapter(roomAdapter);

    }


    private void showSelectRoomDiaog() {

        SelectRoomDialog dialog = new SelectRoomDialog();
        dialog.setDataList(receiveRoomCode_list);
        dialog.setSelectRoomListener(position -> {

            //showLoading("办公室001");
            RoomTypeActivity.startRoomTypeActivity(SelectRoomActivity.this, receiveRoomCode_list.get(position).getAssets_info(), inventory_code, receiveRoomCode_list.get(position).getRoom_name(), receiveRoomCode_list.get(position).getRfid_code(), receiveRoomCode_list.get(position).getAssets_num());

        });
        getSupportFragmentManager().beginTransaction().add(dialog, "SelectRoomDialog").commitAllowingStateLoss();
        //dialog.show(getSupportFragmentManager(), "SelectRoomDialog");
    }

    @OnClick({R.id.tv_receive_again, R.id.tv_submit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_receive_again:
                iPresenter.getRoomInfoByInvCode(getMacAddress(), inventory_code, true);
                break;
            case R.id.tv_submit:
                iPresenter.getAssetsNumByEndInv(getMacAddress(), inventory_code);
                break;
        }


    }

    /**
     * 打开设备进行扫描
     */
    private void openScanning() {


         disposable = Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            int result = UfhData.UhfGetData.OpenUhf(tty_speed, (byte) 255, 4, 0, null);
            if (result == 0) {
                UfhData.UhfGetData.GetUhfInfo();
            }
            emitter.onNext(result);
            emitter.onComplete();

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Log.e(TAG, "accept: " + result);
                    if (result == 0) {
                        openScanningResult = result;
                        Toast.makeText(SelectRoomActivity.this, "扫描设备已开启", Toast.LENGTH_SHORT).show();
                        //   RoomTypeActivity.startRoomTypeActivity(SelectRoomActivity.this);

                    }
                });
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        Log.e(TAG, "dispatchKeyEvent: " + event.toString());
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e(TAG, "onKeyDown:-------" + keyCode);
        switch (keyCode) {
            case 521:
            case 522:
            case 523:
            case 520:
                Log.e(TAG, "onKeyDown: " + "1111");
                if (openScanningResult == 0) {
                    startScanning();
                    Log.e(TAG, "onKeyDown: " + "22222");
                } else {
                    Toast.makeText(SelectRoomActivity.this, "扫描设备未开启，请稍后", Toast.LENGTH_SHORT).show();
                }
                return true;


        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 开始扫描
     */

    private void startScanning() {
        if (Scanflag) return;
        Scanflag = true;
        UfhData.Set_sound(true);
        UfhData.SoundFlag = false;
        UfhData.scanResult6c.clear();
        UfhData.Result6c.clear();

        mDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribe(aLong -> {

                    if (aLong >= 3){
                        mHandler.removeMessages(MSG_UPDATE_NET);
                        mHandler.sendEmptyMessage(MSG_UPDATE_NET);
                    }else {
                        UfhData.read6c(1, 0);
                        mHandler.removeMessages(MSG_UPDATE_SCANNING);
                        mHandler.sendEmptyMessage(MSG_UPDATE_SCANNING);
                    }
                    Log.e(TAG, "accept111: "+aLong );
                });
    }

    //Message是我们自定义的一个类，里面只放了一个String类型的字段
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if (messageEvent.getMessage() == 1000) {
            if (openScanningResult == 0) {
                Log.e(TAG, "onMessageEvent: " + "111111111111111111111111");
                startScanning();
            } else {
                Toast.makeText(SelectRoomActivity.this, "扫描设备未开启，请稍后", Toast.LENGTH_SHORT).show();
            }

        }
    }

    //Message是我们自定义的一个类，里面只放了一个String类型的字段
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScanningSizeEvent(ScanningSizeEvent scanningSizeEvent) {
        showLoading("", R.layout.customdialoglayout);
        iPresenter.getRoomInfoByInvCode(getMacAddress(), inventory_code, false);

    }

    private void stopScanning() {
        Scanflag=false;
        UfhData.Set_sound(false);
        UfhData.SoundFlag = false;
        UfhData.scanResult6c.clear();
        missLoading();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        Log.e(TAG, "stopScanning: "+mDisposable.isDisposed());

        if (rfid_list.size()>0){
            iPresenter.getReceiveRoomCode(getMacAddress(), inventory_code, rfid_list);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //反注册EventBus

        Log.e(TAG, "onDestroy: "+"11111222" );
        EventBus.getDefault().unregister(this);
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Override
    public void setPresenter(@NotNull SelectRoomContract.Presenter presenter) {

        this.iPresenter = presenter;
    }

    @Override
    public void showRoomInfoByInvCodeData(@Nullable List<InventoryCodeBean> data) {
        if (!data.isEmpty()) {
            mDataList.clear();
            mDataList.addAll(data);
            roomAdapter.notifyDataSetChanged();
            showLoading("", R.layout.customdialoglayout);
        }

    }

    @Override
    public void showtReceiveRoomCodeData(@Nullable List<ReceiveRoomCodeData.RoomInfoBean> data) {

        receiveRoomCode_list.clear();
        receiveRoomCode_list.addAll(data);
       String strreceiveRoomCode_list =  com.alibaba.fastjson.JSONObject.toJSONString(receiveRoomCode_list);
        Log.e("showtReceive--", strreceiveRoomCode_list);
        showSelectRoomDiaog();

    }

    @Override
    public void showAssetsNumByEndInv(@Nullable AssetsNumByEndInvBean data) {
        new PromptTextDialog(this, "确定", "取消").fastShow("本次盘点扫描找到【" + data.getAssets_find_num() + "】个资产,还有【" + data.getLack_assets_num() + "】资产未扫描到，确定提交本次盘点？", new DefaultOnActionListener() {
            @Override
            public void onConfirm() {
                iPresenter.getsetInventoryEnd(getMacAddress(), inventory_code);

            }
        });
    }

    @Override
    public void showsetInventoryEnd() {
        Toast.makeText(SelectRoomActivity.this, "已结束此次盘点", Toast.LENGTH_SHORT).show();
        ManualInventoryActivity.startRoomTypeActivity(SelectRoomActivity.this);

    }
}
