package com.wangbin.go_isp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangbin.go_isp.R;
import com.wangbin.go_isp.bean.CategoryRoomAssetsBean;

import java.util.List;

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp_ccb
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp_ccb.adapter
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/5/16
 * <br></br>TIME : 10:20
 * <br></br>MSG :
 * <br></br>************************************************
 */
public class  CategoryRoomAssetsAdapter extends BaseQuickAdapter<CategoryRoomAssetsBean, BaseViewHolder> {

    public CategoryRoomAssetsAdapter(int layoutResId, @Nullable List<CategoryRoomAssetsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryRoomAssetsBean item) {

        helper.setText(R.id.category_assets_code,item.getAssets_code());
        helper.setText(R.id.category_assets_num,"【"+item.getNum()+"】");
        helper.setText(R.id.category_assets_name,item.getAssets_name());
        helper.setText(R.id.category_assets_number,item.getAssets_number()+"");


    }


}