package com.wangbin.go_isp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.UHF.scanlable.UfhData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wangbin.go_isp.Constants;
import com.wangbin.go_isp.R;
import com.wangbin.go_isp.adapter.RoomTypeAdapter;
import com.wangbin.go_isp.base.EntityDao.IspDataEntityDao;
import com.wangbin.go_isp.base.PullRefreshActivity;
import com.wangbin.go_isp.base.db.DatabaseManager;
import com.wangbin.go_isp.base.db.GoIspDbDataDao;
import com.wangbin.go_isp.bean.GatherStatusBean;
import com.wangbin.go_isp.bean.GoIspDbData;
import com.wangbin.go_isp.bean.MessageEvent;
import com.wangbin.go_isp.bean.ReceiveRoomCodeData;
import com.wangbin.go_isp.ui.activity.contract.RoomTypeContract;
import com.wangbin.go_isp.ui.presenter.RoomTypePersenter;
import com.wangbin.go_isp.utils.DefaultOnActionListener;
import com.wangbin.go_isp.utils.DividerDecoration;
import com.wangbin.go_isp.utils.PromptTextDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.QueryBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.ui.activity
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/4/26
 * <br></br>TIME : 16:46
 * <br></br>MSG :
 * <br></br>************************************************
 */
public class RoomTypeActivity extends PullRefreshActivity implements RoomTypeContract.IView{

   ;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ry_room_type)
    RecyclerView ryRoomType;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private static final int SCAN_INTERVAL = 100;
   private Disposable disposable;
   private List<ReceiveRoomCodeData.AssetsInfo> assetsInfo_list = new ArrayList<>();
    private List<ReceiveRoomCodeData.AssetsInfo> assetsInfo_rfid_list;
    private static final String TAG = "RoomTypeActivity";
    private int openScanningResult = -1;
    private Disposable mDisposable;
    private RoomTypeContract.IPresenter presenter;
   private BaseQuickAdapter roomAdapter;
    private IspDataEntityDao ispDataEntityDao;
    List<GoIspDbData> dbDatas_list = new ArrayList<>();
    private String room_name;
    private String  inventory_code;
    private int scanning_size;
    private List<String> scanning_list;
    private MyHandler mHandler = new MyHandler(this);
    private boolean Scanflag = false;
    private class MyHandler extends Handler {

        WeakReference<RoomTypeActivity> weakReference;
        public MyHandler(RoomTypeActivity activity) {
            weakReference = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RoomTypeActivity activity = weakReference.get();
            switch (msg.what) {
                case Constants.MSG_UPDATE_SCANNING:
                    scanning_list = new ArrayList<>(UfhData.scanResult6c.keySet());
                    break;
                case Constants.MSG_UPDATE_NET:
                    activity.stopScanning();
                default:

            }
        }
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_room_type;
    }

    @Override
    protected void init() {
        fullScreen(this);
        //setRefreshListener(refreshLayout, false);
        //refreshLayout.autoRefresh();
        new RoomTypePersenter(this);
        ispDataEntityDao = new IspDataEntityDao();
        EventBus.getDefault().register(this);//注册EventBus
        openScanning();
        ryRoomType.addItemDecoration(new DividerDecoration(this, LinearLayoutManager.HORIZONTAL, 2,
                ContextCompat.getColor(this, R.color.color_3D3D3D)));
        ryRoomType.addItemDecoration(new DividerDecoration(this, LinearLayoutManager.VERTICAL, 2,
                ContextCompat.getColor(this, R.color.color_3D3D3D)));
        ryRoomType.setLayoutManager(new GridLayoutManager(this, 2));

    }

    @Override
    public void onRefresh(@Nullable RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void intData() {
        assetsInfo_rfid_list=  getIntent().getParcelableArrayListExtra("assetsInfo");
        assetsInfo_list.addAll(assetsInfo_rfid_list);
        room_name=  getIntent().getStringExtra("room_name");
        inventory_code = getIntent().getStringExtra("inventory_code");
        tvTitle.setText(room_name+"-【"+getIntent().getStringExtra("assets_num")+"】");
        setRoomListSerialNumber();
        roomAdapter = new RoomTypeAdapter(R.layout.room_type_item_view, assetsInfo_list);
        ryRoomType.setAdapter(roomAdapter);
        showLoading(room_name,R.layout.customdialoglayout);
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
                    Toast.makeText(RoomTypeActivity.this, "扫描设备未开启，请稍后", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
//        switch (keyCode) {
//
//            case 520:
//                if (openScanningResult == 0) {
//                    startScanning();
//                    Log.e(TAG, "onKeyDown: " + "22222");
//                } else {
//                    Toast.makeText(RoomTypeActivity.this, "扫描设备未开启，请稍后", Toast.LENGTH_SHORT).show();
//                }
//                return true;
//
//        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 开始扫描
     */
    /**
     * 打开设备进行扫描
     */
    private void openScanning() {

         disposable = Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            int result = UfhData.UhfGetData.OpenUhf(57600, (byte) 255, 4, 0, null);
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
                        Toast.makeText(RoomTypeActivity.this, "扫描设备已开启", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    /**
     *  存储扫描到的rfid，按房间名存储
     */
    private void  saveRfidCode(List<String> rfid_list){
        List<GoIspDbData> list = ispDataEntityDao.getEntityDao().queryBuilder().
                where(GoIspDbDataDao.Properties.RoomName.eq(room_name)).list();
        boolean isHave = false;
        for (GoIspDbData dbData : list){
            //查重
             isHave = JSON.toJSONString(rfid_list).equals(dbData.getRfidCode());
             if (isHave) break;
        }
        if (!isHave){
            GoIspDbData goIspDbData = new GoIspDbData();
            String rfid_string = JSON.toJSONString(rfid_list);
            goIspDbData.setRfidCode(rfid_string);
            goIspDbData.setRoomName(room_name);
            dbDatas_list.add(goIspDbData);
            ispDataEntityDao.getEntityDao().insertOrReplaceInTx(dbDatas_list);
        }
    }

    //Message是我们自定义的一个类，里面只放了一个String类型的字段
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if (messageEvent.getMessage() == 1001) {
            if (openScanningResult == 0) {
                startScanning();
                Log.e(TAG, "onKeyDown: " + "22222");
            } else {
                Toast.makeText(RoomTypeActivity.this, "扫描设备未开启，请稍后", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @OnClick({R.id.tv_submit})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_submit:
                QueryBuilder qb = ispDataEntityDao.getEntityDao().queryBuilder();
                qb.where(GoIspDbDataDao.Properties.RoomName.eq(room_name));
                List<GoIspDbData> goIspDbData_list = qb.list();

                Log.e(TAG, "onClick: "+goIspDbData_list.size()+"-----"+assetsInfo_rfid_list.size() );
                List<GatherStatusBean> rfid_right = new ArrayList<>();// 匹配上的
                List<GatherStatusBean> rfid_lack = new ArrayList<>(); // 失联的
                List<GatherStatusBean> rfid_more = new ArrayList<>(); // 位移的
                List<String> rfid_common = new ArrayList<>();
                List<String> goIspDbData_rfid = new ArrayList<>();
                List<String> assetsInfo_rfid = new ArrayList<>();
                for (GoIspDbData goIspDbData : goIspDbData_list){  //取出数据库中扫描到的rfid码
                    goIspDbData_rfid.addAll(getIspDbDataRfidCode(goIspDbData.getRfidCode()));
                }
                goIspDbData_rfid = removeDuplicate(goIspDbData_rfid);//去掉重复的rfid码
                scanning_size = goIspDbData_rfid.size();
                for (int i = 0 ; i<assetsInfo_rfid_list.size();i++){
                    assetsInfo_rfid.add(assetsInfo_rfid_list.get(i).getRfid_code());
                }
                int size = goIspDbData_rfid.size();
                for (int i = 0;i<size;i++){
                    if (assetsInfo_rfid.contains(goIspDbData_rfid.get(i))){
                        GatherStatusBean bean_right = new GatherStatusBean();
                        bean_right.setRfid_code(goIspDbData_rfid.get(i));
                        bean_right.setStatus("right");
                        rfid_right.add(bean_right);
                        rfid_common.add(goIspDbData_rfid.get(i));
                    }
                }
                goIspDbData_rfid.removeAll(rfid_common);
                for (int i = 0;i<goIspDbData_rfid.size();i++){
                    GatherStatusBean bean_more = new GatherStatusBean();
                    bean_more.setRfid_code(goIspDbData_rfid.get(i));
                    bean_more.setStatus("more");
                    rfid_more.add(bean_more);
                }
                assetsInfo_rfid.removeAll(rfid_common);
                for (int i = 0;i<assetsInfo_rfid.size();i++){
                    GatherStatusBean bean_lack = new GatherStatusBean();
                    bean_lack.setRfid_code(assetsInfo_rfid.get(i));
                    bean_lack.setStatus("lack");
                    rfid_lack.add(bean_lack);
                }
                List<GatherStatusBean> rfid_gather = new ArrayList<>();
                rfid_gather.addAll(rfid_right);
                rfid_gather.addAll(rfid_lack);
                rfid_gather.addAll(rfid_more);
                summit(rfid_gather);
                break;
                default:
                    break;

        }

    }

    private void summit(List<GatherStatusBean> rfid_gather) {

        new PromptTextDialog(this, "确定", "取消").fastShow("该房间扫描结果【"+scanning_size+"】个资产", new DefaultOnActionListener() {
                @Override
                public void onConfirm() {
                    presenter.receiveAppContrastRes(getIntent().getStringExtra("room_rfid"),getMacAddress(),inventory_code,rfid_gather);

                }
            });
    }

    /**
     *
     * @param list  去掉重复的rfid码
     * @return
     */
    private List<String> removeDuplicate(List<String> list){
        Set set = new HashSet();
        List<String> listNew=new ArrayList<>();
        set.addAll(list);
        listNew.addAll(set);
        return  listNew;
    }

    private List<String> getIspDbDataRfidCode(String code){
        String sub = code.substring(1,code.length()-1); //去掉中括号
        String sub_list = sub.replace("\"", "");//去掉双引号
        String demoArray[] = sub_list.split(",");
        return Arrays.asList(demoArray);
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
                        mHandler.removeMessages(Constants.MSG_UPDATE_NET);
                        mHandler.sendEmptyMessage(Constants.MSG_UPDATE_NET);
                    }else {
                        UfhData.read6c(1, 0);
                        mHandler.removeMessages(Constants.MSG_UPDATE_SCANNING);
                        mHandler.sendEmptyMessage(Constants.MSG_UPDATE_SCANNING);
                    }
                    Log.e(TAG, "accept111: "+aLong );
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //反注册EventBus
        EventBus.getDefault().unregister(this);
        if (mDisposable!= null && !mDisposable.isDisposed()){
            mDisposable.dispose();
        }
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }

    }
    public static void startRoomTypeActivity(Context context, List<ReceiveRoomCodeData.AssetsInfo> list_info,String inventory_code,String room_name,String room_rfid,String assets_num ) {
        Intent intent = new Intent(context, RoomTypeActivity.class);
        intent.putParcelableArrayListExtra("assetsInfo", (ArrayList<? extends Parcelable>) list_info);
        intent.putExtra("inventory_code",inventory_code);
        intent.putExtra("assets_num",assets_num);
        intent.putExtra("room_name",room_name);
        intent.putExtra("room_rfid",room_rfid);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void showtAssetsCodeListByRfidData(@Nullable List<ReceiveRoomCodeData.AssetsInfo> data) {

        if (!data.isEmpty()) {
            assetsInfo_list.clear();
            assetsInfo_list.addAll(data);
            setRoomListSerialNumber();
            roomAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void setPresenter(@NotNull RoomTypeContract.Presenter presenter) {

        this.presenter = presenter;
    }
    private void stopScanning() {
        Scanflag=false;
        UfhData.Set_sound(false);
        UfhData.SoundFlag = false;
        UfhData.scanResult6c.clear();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        missLoading();
        Log.e(TAG, "stopScanning: "+mDisposable.isDisposed());
        if (scanning_list.size()>0){
            saveRfidCode(scanning_list);//保存扫描结果
            Log.e(TAG, "stopScanning: "+ scanning_size );
            presenter.getAssetsCodeListByRfid(getMacAddress(),inventory_code,scanning_list);
        }

    }


    /**
     *  数组排序
     */
    private void setRoomListSerialNumber(){

        if (assetsInfo_list.size() > 0){
            for (int i= 0;i<assetsInfo_list.size();i++){
                assetsInfo_list.get(i).setRfid_serial_number(i+1);
            }
        }

    }

    @Override
    public void showReceiveAppContrastRes() {
        deleteDbData();
        Toast.makeText(RoomTypeActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new ScanningSizeEvent(scanning_size));
        finish();

    }

    private void  deleteDbData(){
        ispDataEntityDao.deleteAll();
    }
}
