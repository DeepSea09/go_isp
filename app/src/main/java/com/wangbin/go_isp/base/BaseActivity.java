package com.wangbin.go_isp.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.wangbin.go_isp.Constants;
import com.wangbin.go_isp.net.NetworkViewCallback;
import com.wangbin.go_isp.ui.activity.MainActivity;
import com.wangbin.go_isp.R;
import com.wangbin.go_isp.utils.AppUtils;
import com.wangbin.go_isp.utils.CustomDialog;


import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.base
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/4/23
 * <br></br>TIME : 13:43
 * <br></br>MSG :
 * <br></br>************************************************
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener,NetworkViewCallback {

    private long mPreTime;
    private Context mContext;
    private Unbinder bind;
    private Activity mCurrentActivity;
    private String loadingMsg ;
    private int layoutId;
    private CustomDialog dialog;;
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beforeDoing();

        setContentView(getLayoutResource());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = getApplicationContext();

        // 绑定依赖(ButterKnife)
        bind = ButterKnife.bind(this);

        setTitleStyle(R.color.color_FFFFFF, R.color.color_FFFFFF);

        init();
        intData();
    }

    protected String getMacAddress(){
        return AppUtils.getInstance(App.instance).getMacAddress();
    }

    @Override
    public void showLoadingDialog() {
        if (null != dialog && dialog.isShowing()){
            dialog.dismiss();
        }
     showLoading("",R.layout.defaultcustomdialoglayout);
    }

    @Override
    public void dismissLoadingDialog() {
        missLoading();
    }

    /**
     * 设置布局前的准备工作
     */
    public void beforeDoing() {

    }

    /**
     * EventBus是否已注册
     */
    public boolean isEventBusRegisted(Object subscribe) {
        return EventBus.getDefault().isRegistered(subscribe);
    }

    /**
     * 注册EventBus
     */
    public void registerEventBus(Object subscribe) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe);
        }
    }

    /**
     * 反注册EventBus
     */
    public void unregisterEventBus(Object subscribe) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe);
        }
    }

    //封装的弹出加载条的方法
    protected void showLoading(String loadingMsg,int layoutId) {
        if (!isFinishing()) {
            this.loadingMsg = loadingMsg;
            this.layoutId  =layoutId;
            showDialog(Constants.CustomProgressDialog);
        }
    }

    //关闭进度条
    protected void missLoading() {
        removeDialog(Constants.CustomProgressDialog);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case Constants.CustomProgressDialog:
                dialog = new CustomDialog(this,loadingMsg,layoutId);
                break;

            default:
                dialog = null;
        }
        return dialog;
    }


    /**
     * 改变顶部导航栏颜色
     */
    public void setTitleStyle(int backColor, int textColor) {
        // 改变顶部导航栏颜色
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(this.getResources().getColor(backColor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 修改状态栏字体颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(textColor));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        // 状态栏颜色字体(白底黑字)修改 MIUI6+
        setMiuiStatusBarDarkMode(this, true);

        // 状态栏颜色字体(白底黑字)修改 Flyme4+
        setMeizuStatusBarDarkIcon(this, true);
    }

    /**
     * 状态栏颜色字体(白底黑字)修改 MIUI6+
     */
    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 通过设置全屏，设置状态栏透明
     */
    public void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
                //                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
                //                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    /**
     * 状态栏颜色字体(白底黑字)修改 Meizu
     */
    public static boolean setMeizuStatusBarDarkIcon(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * 设置布局文件
     *
     * @return 布局文件id
     */
    protected abstract int getLayoutResource();

    /**
     * 页面初始化
     */
    protected abstract void init();

    /**
     * 页面数据初始化
     */
    protected abstract void intData();

    /**
     * 统一退出控制
     */
    @Override
    public void onBackPressed() {
        if (mCurrentActivity instanceof MainActivity) {
            if (System.currentTimeMillis() - mPreTime > 2000) {// 两次点击间隔大于2秒
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
                mPreTime = System.currentTimeMillis();
                return;
            } else {
                System.exit(0);
            }
        }

        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // 取消绑定(ButterKnife)
        bind.unbind();
    }
}

