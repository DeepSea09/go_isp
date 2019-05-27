package com.wangbin.go_isp.utils;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangbin.go_isp.bean.AssetsCategoryBean;
import com.wangbin.go_isp.bean.CategoryRoomAssetsBean;
import com.wangbin.go_isp.R;
import com.wangbin.go_isp.adapter.AssetsRoomAdapter;

import java.util.ArrayList;
import java.util.List;


public class NewAssetsRoomDialog extends DialogFragment {


    private RecyclerView mRecyclerView;
    private List<AssetsCategoryBean> mDataList = new ArrayList<>();
    private String roomName;
    private String category_code;
    private String category_name;
    public AssetsRoomListener listener;

    public interface AssetsRoomListener {

        void clickItem(String category_code,String category_name);
    }

    public void setAssetsRoomListener(AssetsRoomListener listener) {

        this.listener = listener;
    }


    public void setDataList(List<AssetsCategoryBean> mDataList) {
        this.mDataList = mDataList;
    }

    public void setRoomName(String roomName) {

        this.roomName = roomName;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.85), ViewGroup.LayoutParams.WRAP_CONTENT);

        }
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.new_dialog_room, null);
        mRecyclerView = view.findViewById(R.id.assets_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        BaseQuickAdapter roomAdapter = new AssetsRoomAdapter(R.layout.assets_list__item_view, mDataList);
        roomAdapter.setOnItemClickListener((adapter, view1, position) -> {
            category_code = mDataList.get(position).getCategory_code();
            category_name = mDataList.get(position).getCategory_name();
            for (int i = 0; i < mDataList.size(); i++) {
                mDataList.get(i).setSelected(false);
            }
            mDataList.get(position).setSelected(true);
            roomAdapter.notifyDataSetChanged();
        });
        mRecyclerView.setAdapter(roomAdapter);
        TextView roomName_tv = view.findViewById(R.id.dialog_prompt_content_tv);
        roomName_tv.setText("【资产物联】" + roomName);
        TextView textView = view.findViewById(R.id.dialog_prompt_text_commit);
        textView.setOnClickListener(v -> {
            if (null != listener) {
                if (TextUtils.isEmpty(category_code)){
                    ToastUtils.showShort("请选择分类");
                    return;
                }

                dismiss();
                listener.clickItem(category_code,category_name);
            }
        });
        builder.setView(view);
        return builder.create();

    }

}
