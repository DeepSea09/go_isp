package com.wangbin.go_isp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangbin.go_isp.R;

import java.util.List;

public class RfidListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public RfidListAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_rfid,item);

    }


}
