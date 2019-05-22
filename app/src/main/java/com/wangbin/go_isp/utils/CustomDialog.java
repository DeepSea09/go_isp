package com.wangbin.go_isp.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangbin.go_isp.R;
import com.wangbin.go_isp.bean.MessageEvent;
import com.wangbin.go_isp.bean.RoomLikebean;

import org.greenrobot.eventbus.EventBus;


/**
 * 自定义进度对话框
 *
 * Created by wgq on 16/5/12.
 */
public class CustomDialog extends Dialog {
    // private Context context;
    private AnimationDrawable drawable;
    private ImageView image;
    private String msg;


    @SuppressLint("SetTextI18n")
    public CustomDialog(Context context, String msg,int layoutId) {
        super(context, R.style.Dialog);
        // this.context = context;
        setContentView(layoutId);
        this.msg = msg;
        Log.e("showLoadingDialog: ","222222"+layoutId);
        if (layoutId == R.layout.customdialoglayout){
            TextView tv_loadingmsg = findViewById(R.id.tv_loadingmsg);
            TextView tv_loading_content = findViewById(R.id.tv_loading_content);
            tv_loading_content.setVisibility(TextUtils.isEmpty(msg) ? View.GONE : View.VISIBLE);
            if (tv_loading_content.getVisibility() == View.VISIBLE){
                tv_loading_content.setText("您已进入【"+msg+"】");
            }
            tv_loadingmsg.setText(TextUtils.isEmpty(msg) ? "等待您扫描【房间标识】" :"请您开始扫描");
        }
        getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        p.alpha = 1.0f;      //设置本身透明度
        p.dimAmount = 0.0f;      //设置黑暗度
        getWindow().setAttributes(p);     //设置生效
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
    }

    @SuppressLint("SetTextI18n")
    public CustomDialog(Context context, int type, RoomLikebean roomLikebean, int layoutId) {  //0 房间 ，1 资产
        super(context, R.style.Dialog);
        // this.context = context;
        setContentView(layoutId);
        this.msg = msg;
        Log.e("showLoadingDialog: ", "222222" + layoutId);
        if (layoutId == R.layout.customdialoglayouttwo) {
            TextView tv_loadingmsg = findViewById(R.id.tv_loadingmsg);
            TextView tv_loading_content = findViewById(R.id.tv_loading_content);
            TextView dialog_prompt_content_tv = findViewById(R.id.dialog_prompt_content_tv);
            dialog_prompt_content_tv.setText("【"+roomLikebean.getRoom_code()+"】"+roomLikebean.getRoom_name());
            tv_loading_content.setText(type == 0 ? "现在请扫描" : "现在请扫描代表该资产的");
            tv_loadingmsg.setText(type == 0 ? "【房间RFID标签】" : "【资产RFID标签】");
            getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
            p.alpha = 1.0f;      //设置本身透明度
            p.dimAmount = 0.0f;      //设置黑暗度
            getWindow().setAttributes(p);     //设置生效
            this.setCancelable(true);
            this.setCanceledOnTouchOutside(false);
        }

    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        Log.e("dispatchKeyEvent: ",event.toString() );
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == 520){
            EventBus.getDefault().post(new MessageEvent(TextUtils.isEmpty(msg) ? 1000 : 1001,null,null,0));
            return false;
        }else {

            return super.onKeyDown(keyCode, event);
        }

    }

    /**
     * 提示内容
     *
     * @param strMessage
     * @return
     */
    public void setMessage(String strMessage) {
//        TextView tvMsg =this.findViewById(R.id.tv_loadingmsg);
//        tvMsg.setText(strMessage);
    }

}
