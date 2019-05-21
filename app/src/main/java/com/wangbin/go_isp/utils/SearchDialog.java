package com.wangbin.go_isp.utils;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangbin.go_isp.R;


public class SearchDialog extends BottomSheetDialogFragment {


    private RecyclerView mRecyclerView;
  //  private List<ReceiveRoomCodeData.RoomInfoBean> mDataList = new ArrayList<>();


    public   SelectRoomListener  listener ;

    public interface  SelectRoomListener {

        void  clickItem(int position);
    }

    public void  setSelectRoomListener( SelectRoomListener  listener){

        this.listener = listener;
    }

//    public void setDataList(List<ReceiveRoomCodeData.RoomInfoBean> mDataList){
//        this.mDataList = mDataList;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.dialog_search, container);
    }
    @Override public void onStart() {
        super.onStart(); Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.85), ViewGroup.LayoutParams.WRAP_CONTENT);

        }
    }
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
