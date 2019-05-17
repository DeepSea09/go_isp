package com.wangbin.go_isp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangbin.go_isp.R;
import com.wangbin.go_isp.bean.InventoryCodeBean;

import java.util.List;

public class ActivityRoomAdapter extends BaseQuickAdapter<InventoryCodeBean, BaseViewHolder> {

    public ActivityRoomAdapter(int layoutResId, @Nullable List<InventoryCodeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InventoryCodeBean item) {
        helper.setText(R.id.tv_room_name,item.getRoom_name());
        helper.setText(R.id.tv_assets,"【"+item.getReceive_total()+"/"+item.getAssets_total()+"】资产");
        helper.setBackgroundRes(R.id.bg_grid_lay,getInventoryStatus(item.getReceive_total(),item.getAssets_total()));

    }


    /**
     *    0  未盘点 ，1  盘点未完成 ，2 盘点已完成
     * @return
     */
    private int getInventoryStatus(int receive_total,int assets_total){

        if (receive_total >= assets_total){
            return R.drawable.bg_grid_room_green;
        }else if (receive_total > 0  &&   receive_total < assets_total){
            return  R.drawable.bg_grid_room_yellow;
        }else {
            return  R.drawable.bg_grid_room_child;
        }

    }


}
