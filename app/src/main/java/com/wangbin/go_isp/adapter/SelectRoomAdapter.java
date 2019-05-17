package com.wangbin.go_isp.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangbin.go_isp.R;
import com.wangbin.go_isp.bean.ReceiveRoomCodeData;

import java.util.List;

public class SelectRoomAdapter extends BaseQuickAdapter<ReceiveRoomCodeData.RoomInfoBean, BaseViewHolder> {

    public SelectRoomAdapter(int layoutResId, @Nullable List<ReceiveRoomCodeData.RoomInfoBean> data) {
        super(layoutResId, data);
        Log.e("SelectRoomAdapter: ----", JSONObject.toJSONString(data));
    }

    @Override
    protected void convert(BaseViewHolder helper, ReceiveRoomCodeData.RoomInfoBean item) {
        Log.e("convert: -----------", item.getRoom_name());
        helper.setText(R.id.room_name_id,item.getRoom_name());

    }


}
