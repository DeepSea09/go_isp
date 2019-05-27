package com.wangbin.go_isp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangbin.go_isp.R;
import com.wangbin.go_isp.adapter.CategoryRoomAssetsAdapter;
import com.wangbin.go_isp.bean.CategoryRoomAssetsBean;
import com.wangbin.go_isp.bean.RoomLikebean;
import com.wangbin.go_isp.framework.base.BaseActivity;
import com.wangbin.go_isp.ui.activity.contract.CategoryRoomAssetsContract;
import com.wangbin.go_isp.ui.activity.persenter.CategoryRoomAssetsPersenter;
import com.wangbin.go_isp.utils.DividerDecoration;

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
 * <br></br>DATA : 2019/5/15
 * <br></br>TIME : 15:56
 * <br></br>MSG :
 * <br></br>************************************************
 */
public class CategoryRoomAssetsActivity extends BaseActivity implements CategoryRoomAssetsContract.IView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.assests_category_list)
    RecyclerView assestsCategoryList;
    @BindView(R.id.tv_assets_title)
    TextView tvAssetsTitle;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    private RoomLikebean roomLikebean;
    private CategoryRoomAssetsContract.IPresenter presenter;
    private List<CategoryRoomAssetsBean> categoryRoomAssetsBeanList = new ArrayList<>();
    private BaseQuickAdapter assets_adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_assests_category;
    }

    @Override
    protected void init() {
        fullScreen(this);
        new CategoryRoomAssetsPersenter(this);
        roomLikebean = getIntent().getParcelableExtra("roomLikebean");
        assestsCategoryList.setLayoutManager(new LinearLayoutManager(this));
        assestsCategoryList.addItemDecoration(new DividerDecoration(this, LinearLayoutManager.HORIZONTAL, 2,
                ContextCompat.getColor(this, R.color.color_333333)));
        assets_adapter = new CategoryRoomAssetsAdapter(R.layout.category_roomassets_item_view, categoryRoomAssetsBeanList);
        assestsCategoryList.setAdapter(assets_adapter);
        String category_code = getIntent().getStringExtra("category_code");
        String category_name = getIntent().getStringExtra("category_name");
        String[] category_names =  category_name.split("-");
        if(category_names.length>0){
            category_name = category_names[category_names.length-1];
        }
        tvAssetsTitle.setText(roomLikebean.getRoom_name()+"  |  "+category_name);
        presenter.getCategoryRoomAssets(getMacAddress(), roomLikebean.getRoom_code(), TextUtils.isEmpty(category_code) ? "" : category_code);

    }

    @Override
    protected void intData() {
        assets_adapter.setOnItemClickListener((adapter, view1, position) -> {
            Log.e("intData: aaaaaaa",JSONObject.toJSONString(categoryRoomAssetsBeanList.get(position)));
            AssetsBindingActivity.startAssetsBindingActivity(CategoryRoomAssetsActivity.this, categoryRoomAssetsBeanList.get(position).getAssets_code(),categoryRoomAssetsBeanList.get(position).getAssets_name(), 2, roomLikebean);

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void showCategoryRoomAssets(@Nullable List<CategoryRoomAssetsBean> data) {
        if (data != null) {
            categoryRoomAssetsBeanList.clear();
            for (int i = 0; i < data.size(); i++) {
                data.get(i).setAssets_number(i);
            }
            categoryRoomAssetsBeanList.addAll(data);
            assets_adapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            AssetsBindingActivity.startAssetsBindingActivity(CategoryRoomAssetsActivity.this, 1, roomLikebean);//资产
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.tv_cancle})
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.tv_cancle:
              AssetsBindingActivity.startAssetsBindingActivity(CategoryRoomAssetsActivity.this, 1, roomLikebean);//资产
              break;
              default:
                  break;
      }
    }

    @Override
    public void setPresenter(@NotNull CategoryRoomAssetsContract.Presenter presenter) {

        this.presenter = presenter;
    }

    /**
     * @param context
     * @param
     */
    public static void startCategoryRoomAssetsActivity(Activity context, String category_code, RoomLikebean bean, String category_name) {
        Intent intent = new Intent(context, CategoryRoomAssetsActivity.class);
        intent.putExtra("category_code", category_code);
        intent.putExtra("category_name", category_name);
        intent.putExtra("roomLikebean", bean);
        context.startActivity(intent);

    }

}
