package com.wangbin.go_isp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangbin.go_isp.R;
import com.wangbin.go_isp.bean.AssetsCategoryBean;
import com.wangbin.go_isp.base.App;

import java.util.List;

public class AssetsRoomAdapter extends BaseQuickAdapter<AssetsCategoryBean, BaseViewHolder> {

    public AssetsRoomAdapter(int layoutResId, @Nullable List<AssetsCategoryBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, AssetsCategoryBean item) {
        helper.setText(R.id.tv_assets,item.getCategory_name());
        if (item.isSelected()){
            helper.setTextColor(R.id.tv_assets, App.instance.getResources().getColor(R.color.color_FF9900));
        }else {
            helper.setTextColor(R.id.tv_assets, App.instance.getResources().getColor(R.color.isp_write));
        }


    }


}
