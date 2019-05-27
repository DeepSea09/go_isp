package com.wangbin.go_isp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.UHF.scanlable.UfhData;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.wangbin.go_isp.R;
import com.wangbin.go_isp.bean.AssetsCategoryBean;
import com.wangbin.go_isp.bean.MessageEvent;
import com.wangbin.go_isp.bean.RoomLikebean;
import com.wangbin.go_isp.framework.base.BaseActivity;
import com.wangbin.go_isp.ui.activity.contract.AssetsBindingContract;
import com.wangbin.go_isp.ui.activity.persenter.AssetsBindingPensenter;
import com.wangbin.go_isp.utils.AssetsRoomDialog;
import com.wangbin.go_isp.utils.DefaultOnActionListener;
import com.wangbin.go_isp.utils.NewAssetsRoomDialog;
import com.wangbin.go_isp.utils.PromptTextDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp_ccb
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp_ccb.ui.activity
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/5/12
 * <br></br>TIME : 17:22
 * <br></br>MSG :
 * <br></br>************************************************
 */
public class AssetsBindingActivity extends BaseActivity implements AssetsBindingContract.IView {

    @BindView(R.id.tv_back)
    TextView tvBack;
    private RoomLikebean roomLikebean;
    private int tty_speed = 57600;
    private Disposable disposable;
    private Disposable mDisposable;
    private boolean Scanflag = false;
    private static final int MSG_UPDATE_SCANNING = 0x112;
    private static final int MSG_UPDATE_NET = 0x113;
    private MyHandler mHandler = new MyHandler(this);
    private static final String TAG = "AssetsBindingActivity";
    private AssetsBindingContract.IPresenter presenter;
    List<String> rfid_list;
    private int openScanningResult = -1;
    private int type = -1;
    private String category_code;
    private String  category_name;
    @Override
    public void showAssetsRfid(String message) {
        Toast.makeText(AssetsBindingActivity.this, TextUtils.isEmpty(message) ? "绑定成功" : message, Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private class MyHandler extends Handler {

        WeakReference<AssetsBindingActivity> weakReference;

        public MyHandler(AssetsBindingActivity activity) {
            weakReference = new WeakReference<>(activity);

        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AssetsBindingActivity activity = weakReference.get();
            switch (msg.what) {
                case MSG_UPDATE_SCANNING:
                    rfid_list = new ArrayList<>(UfhData.scanResult6c.keySet());
                    break;
                case MSG_UPDATE_NET:
                    activity.stopScanning();
                default:

            }
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_assests_binding;
    }

    @Override
    protected void init() {
        fullScreen(this);
        new AssetsBindingPensenter(this);

    }

    @Override
    protected void intData() {
        openScanning();
        EventBus.getDefault().register(this);//注册EventBus
        roomLikebean = getIntent().getParcelableExtra("roomLikebean");
        type = getIntent().getIntExtra("type", -1);
        getShowLoading();


    }


    @OnClick({R.id.tv_back})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
//                CategoryRoomAssetsActivity.startCategoryRoomAssetsActivity(AssetsBindingActivity.this, category_code, roomLikebean,category_name);
                finish();
                break;
            default:
                break;
        }
    }
    private void getShowLoading() {

        if (type == 1) {
            presenter.getRoomAssetsCategory(getMacAddress(), roomLikebean.getRoom_code());
        } else {
            showLoading(type, roomLikebean, R.layout.customdialoglayouttwo);
        }

    }

    private void showAssetsRoomDiaog(List<AssetsCategoryBean> list) {
        NewAssetsRoomDialog dialog = new NewAssetsRoomDialog();
        dialog.setDataList(list);
        dialog.setRoomName(roomLikebean.getRoom_name());
        dialog.setAssetsRoomListener((category_code,category_name) -> {
            this.category_code = category_code;
            this.category_name = category_name;
            CategoryRoomAssetsActivity.startCategoryRoomAssetsActivity(AssetsBindingActivity.this, category_code, roomLikebean,category_name);
        });

        getSupportFragmentManager().beginTransaction().add(dialog, "AssetsRoomDialog").commitAllowingStateLoss();
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
                        Toast.makeText(AssetsBindingActivity.this, "扫描设备已开启", Toast.LENGTH_SHORT).show();
                        //   RoomTypeActivity.startRoomTypeActivity(SelectRoomActivity.this);

                    }
                });
    }

    //Message是我们自定义的一个类，里面只放了一个String类型的字段
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if (messageEvent.getMessage() == 1000) {
            if (openScanningResult == 0) {
                startScanning();
            } else {
                Toast.makeText(AssetsBindingActivity.this, "扫描设备未开启，请稍后", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void showRoomAssetsCategory(@Nullable List<AssetsCategoryBean> data) {
        if (data != null) {
            showAssetsRoomDiaog(data);
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        SharedPreferences userSettings = getSharedPreferences("setting", 0);
        String strkeycode = userSettings.getString("keycode","520;");
        String[] strkeycodes =  strkeycode.split(";");
        boolean cando = false;
        for (int i = 0;i<strkeycodes.length;i++){
            if(strkeycodes[i].equals(keyCode+"")){
                cando=true;
            }
        }
        if(cando){
            if (openScanningResult == 0) {
                    startScanning();
                    Log.e(TAG, "onKeyDown: " + "22222");
                } else {
                    Toast.makeText(AssetsBindingActivity.this, "扫描设备未开启，请稍后", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
//        switch (keyCode) {
//
//            case 520:
//                Log.e(TAG, "onKeyDown: " + "1111");
//                if (openScanningResult == 0) {
//                    startScanning();
//                    Log.e(TAG, "onKeyDown: " + "22222");
//                } else {
//                    Toast.makeText(AssetsBindingActivity.this, "扫描设备未开启，请稍后", Toast.LENGTH_SHORT).show();
//                }
//                return true;
//
//
//        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Log.e(TAG, "onNewIntent: " + "11111");
        roomLikebean = intent.getParcelableExtra("roomLikebean");
        type = getIntent().getIntExtra("type", -1);
        Log.e(TAG, "onNewIntent: " + type);
        getShowLoading();
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

                    if (aLong >= 3) {
                        mHandler.removeMessages(MSG_UPDATE_NET);
                        mHandler.sendEmptyMessage(MSG_UPDATE_NET);
                    } else {
                        UfhData.read6c(1, 0);
                        mHandler.removeMessages(MSG_UPDATE_SCANNING);
                        mHandler.sendEmptyMessage(MSG_UPDATE_SCANNING);
                    }
                    Log.e(TAG, "accept111: " + aLong);
                });
    }


    private void stopScanning() {
        int rfid_list_size = rfid_list.size();
        if (rfid_list_size == 0) {
            Toast.makeText(AssetsBindingActivity.this, "什么也没扫到，请重试！", Toast.LENGTH_SHORT).show();
            return;
        }
        Scanflag = false;
        UfhData.Set_sound(false);
        UfhData.SoundFlag = false;
        UfhData.scanResult6c.clear();
        missLoading();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        if (rfid_list.size() == 1) {
            setSummitDialog(0x112);
        } else {
            setSummitDialog(0x113);
        }

    }

    /**
     * @param acanning_type 扫描标签后2种结果 0x112  只扫描到一个标签 0x113 扫到多个标签
     *                      <p>
     *                      type  0   房间绑定，1 资产绑定，2 房间内某一分类下的资产信息
     */
    private void setSummitDialog(int acanning_type) {
        new PromptTextDialog(this, acanning_type == 0x112 ? "开始绑定" : "RFID不唯一,请重新扫描标签", rfid_list).changeLayout(R.layout.select_rfid_dialog).fastShow("【" + roomLikebean.getAssets_num()  + "】" + roomLikebean.getRoom_name(),getIntent().getStringExtra("assets_name"), new DefaultOnActionListener() {
            @Override
            public void onConfirm() {
                switch (acanning_type) {
                    case 0x112:
                        if (type == 2) {
                            presenter.getassetsRfid(getMacAddress(), rfid_list.get(0), getIntent().getStringExtra("assets_code"));
                        } else if (type ==0){
                            presenter.roomRfid(getMacAddress(), rfid_list.get(0), TextUtils.isEmpty(roomLikebean.getRoom_code()) ? "" : roomLikebean.getRoom_code());
                        }else {
                            ToastUtils.showShort(" 该房间暂无资产");

                        }
                        break;
                    case 0x113:
                        getShowLoading();
                        break;
                    default:
                        break;
                }
            }
        },1);
    }

    @Override
    public void showRoomRfid() {
        Toast.makeText(AssetsBindingActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new MessageEvent(0x119, roomLikebean.getRoom_code(), roomLikebean.getRoom_name(), roomLikebean.getRfid_num()));
        finish();
    }

    @Override
    public void setPresenter(@NotNull AssetsBindingContract.Presenter presenter) {

        this.presenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //反注册EventBus
        EventBus.getDefault().unregister(this);

        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

    }

    /**
     * '
     *
     * @param context
     * @param type    0 .房间 1 资产
     */
    public static void startAssetsBindingActivity(Activity context, int type, RoomLikebean bean) {
        Intent intent = new Intent(context, AssetsBindingActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("roomLikebean", bean);
        context.startActivity(intent);

    }

    public static void startAssetsBindingActivity(Activity context, String assets_code, int type, RoomLikebean bean) {
        Intent intent = new Intent(context, AssetsBindingActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("assets_code", assets_code);
        intent.putExtra("roomLikebean", bean);
        context.startActivity(intent);
        context.finish();
    }

    public static void startAssetsBindingActivity(Activity context, String assets_code,String assets_name, int type, RoomLikebean bean) {
        Intent intent = new Intent(context, AssetsBindingActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("assets_code", assets_code);
        intent.putExtra("assets_name", assets_name);
        intent.putExtra("roomLikebean", bean);
        context.startActivity(intent);
        context.finish();
    }


}
