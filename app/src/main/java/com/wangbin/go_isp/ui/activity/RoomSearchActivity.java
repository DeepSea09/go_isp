package com.wangbin.go_isp.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.wangbin.go_isp.R;
import com.wangbin.go_isp.bean.MessageEvent;
import com.wangbin.go_isp.bean.RoomLikebean;
import com.wangbin.go_isp.framework.base.BaseActivity;
import com.wangbin.go_isp.ui.activity.contract.RoomSearchContract;
import com.wangbin.go_isp.ui.activity.persenter.RoomSearchPersenter;
import com.wangbin.go_isp.utils.DefaultOnActionListener;
import com.wangbin.go_isp.utils.PromptTextDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp_ccb
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp_ccb.ui.activity
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/5/11
 * <br></br>TIME : 17:21
 * <br></br>MSG :
 * <br></br>************************************************
 */
public class RoomSearchActivity extends BaseActivity implements RoomSearchContract.IView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_search)
    TextView btnSearch;
    @BindView(R.id.edit_search)
    EditText editSearch;
    private RoomSearchContract.IPresenter presenter;

    private List<RoomLikebean> search_list = new ArrayList<>();
    private OptionsPickerView pvSearchOptions;

    private static final String TAG = "RoomSearchActivity";

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_room_search;
    }

    @Override
    protected void init() {
        fullScreen(this);
        new RoomSearchPersenter(this);
    }

    @Override
    protected void intData() {
        initNoLinkOptionsPicker();
        EventBus.getDefault().register(this);//注册EventBus
    }

    @OnClick({R.id.btn_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                // showSelectRoomDiaog();
                // pvCustomOptions.show();
                String edit = editSearch.getText().toString().trim();
                if (TextUtils.isEmpty(edit)){
                    edit = "";
                }
                presenter.roomLike(getMacAddress(), edit);
                break;
        }


    }

    private void summit(RoomLikebean roomLikebean) {
        Log.e("summit: --------",JSONObject.toJSONString(roomLikebean));

        new PromptTextDialog(this, "【房间】物联绑定", "【资产】物联绑定").changeLayout(R.layout.select_type_rfid_dialog).fastShow("【" + roomLikebean.getAssets_num() + "】" + roomLikebean.getRoom_name(), new DefaultOnActionListener() {
            @Override
            public void onCancel() {

                AssetsBindingActivity.startAssetsBindingActivity(RoomSearchActivity.this, 1, roomLikebean);//资产
            }

            @Override
            public void onConfirm() {


                AssetsBindingActivity.startAssetsBindingActivity(RoomSearchActivity.this, 0, roomLikebean);//房间
            }
        });
    }

    //Message是我们自定义的一个类，里面只放了一个String类型的字段
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if (messageEvent.getMessage() == 0x119) {
            Log.e(TAG, "onMessageEvent: " + "22222222");
            RoomLikebean roomLikebean = new RoomLikebean();
            roomLikebean.setRoom_name(messageEvent.getRoom_name());
            roomLikebean.setRfid_num(messageEvent.getRfid_num());
            roomLikebean.setRoom_code(messageEvent.getRoom_code());
            summit(roomLikebean);

        }
    }

    private void initNoLinkOptionsPicker() {

        pvSearchOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            //返回的分别是三个级别的选中位置

            summit(search_list.get(options1));

        }).setLayoutRes(R.layout.pickerview_custom_options, v -> {
            final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
            tvSubmit.setOnClickListener(v1 -> {
                pvSearchOptions.returnData();
                pvSearchOptions.dismiss();
            });
        }).isDialog(false)
                .setOutSideCancelable(false)
                .build();
        pvSearchOptions.setPicker(search_list);//添加数据

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void showRoomLike(@Nullable List<RoomLikebean> response) {
        if (response.size() > 0) {
            search_list.clear();
            search_list.addAll(response);
            pvSearchOptions.show();
        }
    }

    @Override
    public void setPresenter(@NotNull RoomSearchContract.Presenter presenter) {

        this.presenter = presenter;
    }
}
