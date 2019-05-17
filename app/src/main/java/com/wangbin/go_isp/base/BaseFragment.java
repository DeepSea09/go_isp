package com.wangbin.go_isp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.wangbin.go_isp.R;
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
 * <br></br>TIME : 15:33
 * <br></br>MSG :
 * <br></br>************************************************
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected Activity mActivity;
    private View view;
    private Unbinder bind;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // customDialog = new CustomDialog(getActivity(),"");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutResource(), container, false);
        mActivity = getActivity();
        // 绑定依赖(ButterKnife)
        bind = ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            initSavedInstance(savedInstanceState);
        }

        setTitleStyle(R.color.color_FFFFFF, R.color.color_FFFFFF);

        init();
        intData();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private CustomDialog customDialog;

    //封装的弹出加载条的方法
    protected void showLoading() {
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
        try {
            customDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //关闭进度条
    protected void missLoading() {
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();

        // 取消绑定(ButterKnife)
        bind.unbind();
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
     * 重新回到页面初始化
     */
    protected void initSavedInstance(Bundle bundle) {
    }

    /**
     * 改变顶部导航栏颜色
     */
    public void setTitleStyle(int backColor, int textColor) {
        // 改变顶部导航栏颜色
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(this.getResources().getColor(backColor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 修改状态栏字体颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(textColor));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        // 状态栏颜色字体(白底黑字)修改 MIUI6+
        setMiuiStatusBarDarkMode(getActivity(), true);

        // 状态栏颜色字体(白底黑字)修改 Flyme4+
        setMeizuStatusBarDarkIcon(getActivity(), true);
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

    @Override
    public void onClick(View v) {

    }
}
