package com.wangbin.go_isp.utils;


import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangbin.go_isp.R;
import com.wangbin.go_isp.adapter.SelectRoomAdapter;
import com.wangbin.go_isp.bean.ReceiveRoomCodeData;

import java.util.ArrayList;
import java.util.List;

public class SelectRoomDialog extends DialogFragment {


    private RecyclerView mRecyclerView;
    private List<ReceiveRoomCodeData.RoomInfoBean> mDataList = new ArrayList<>();


    public   SelectRoomListener  listener ;

    public interface  SelectRoomListener {

        void  clickItem( int position);
    }

    public void  setSelectRoomListener( SelectRoomListener  listener){

        this.listener = listener;
    }

    public void setDataList(List<ReceiveRoomCodeData.RoomInfoBean> mDataList){
        this.mDataList = mDataList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override public void onStart() {
        super.onStart(); Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.85), ViewGroup.LayoutParams.WRAP_CONTENT);

        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_room, null);
        mRecyclerView = view.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        String strmDataList = JSONObject.toJSONString(mDataList);
        BaseQuickAdapter roomAdapter = new SelectRoomAdapter(R.layout.room_item_view, mDataList);
        roomAdapter.setOnItemClickListener((adapter, view1, position) -> {

            if (null != listener){
                dismiss();
                listener.clickItem(position);
            }

        });
        mRecyclerView.setAdapter(roomAdapter);
        builder.setView(view);
        return builder.create();

    }

}
