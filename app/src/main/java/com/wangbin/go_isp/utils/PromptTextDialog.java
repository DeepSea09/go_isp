package com.wangbin.go_isp.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangbin.go_isp.R;
import com.wangbin.go_isp.adapter.RfidListAdapter;
import com.wangbin.go_isp.utils.IOnActionListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tye on 2018/5/28.
 * 调用方式举例： new PromptTextDialog(this, "确定", true).fastShow("福利", new DefaultOnActionListener());
 */

public class PromptTextDialog extends Dialog implements View.OnClickListener, DialogInterface.OnDismissListener {

    private String mTitle, mContent, mConfirm, mCancel;
    private Integer mFlagImage;
    private IOnActionListener mActionListener;
    private boolean mCancelable = false;
    private boolean mOutside = false;
    private List<String> rfid_list = new ArrayList<>();


    private int mLayoutID = R.layout.dialog_prompt_text;

    public PromptTextDialog(Context context, String aConfirmText) {
        this(context, aConfirmText, null, false);
    }


    public PromptTextDialog(Context context, String aConfirmText, String aCancelText) {
        this(context, aConfirmText, aCancelText, false);
    }

    public PromptTextDialog(Context context, String aConfirmText, boolean aCancelable) {
        this(context, aConfirmText, null, aCancelable);
    }


    public PromptTextDialog(Context context, String aConfirmText, List<String> rfid_list) {
        this(context, aConfirmText, null, rfid_list, false, false);// aOutside默认与aCancelable保持一致
    }

    public PromptTextDialog(Context context, String aConfirmText, String aCancelText, boolean aCancelable) {
        this(context, aConfirmText, aCancelText, null, aCancelable, aCancelable);// aOutside默认与aCancelable保持一致
    }

    /**
     * @param aConfirmText 确定（右侧）文字
     * @param aCancelText  取消（左侧）文字，为null时不显示此按钮，只显示确定
     * @param aCancelable  主要针对返回键是否可点击，true：系统返回键可关闭dialog。 false：系统返回键不会关闭dialog。
     * @param aOutside     是否点击边缘位置消失， true: 点击边缘位置消失 ， false: 点击边缘位置不消失
     */
    public PromptTextDialog(Context context, String aConfirmText, String aCancelText, List<String> rfid_list, boolean aCancelable, boolean aOutside) {
        super(context, R.style.generalThirdDialog);
        mConfirm = aConfirmText;
        mCancel = aCancelText;
        mCancelable = aCancelable;
        this.rfid_list = rfid_list;
        mOutside = aOutside;
        setCanceledOnTouchOutside(mOutside);
    }

    /**
     * 注意，此方法调用为更换layout样式，布局中必须包含以下id：
     * R.id.dialog_prompt_content_tv    用于显示内容
     * R.id.dialog_prompt_text_confirm  用于确定键
     * <p>
     * 和以下几个可选id
     * R.id.dialog_prompt_text_cancel   用于取消键
     * R.id.dialog_prompt_parent_ll     用于点击边缘位置取消（布局限制导致的系统触发区域变小，如设置布局底部间距后，点击底部无法触发事件），{@link #mOutside}值为true时生效，注意设置内容区域可获取焦点，否则点击内容区域也会触发
     * R.id.dialog_prompt_flag_iv       用于内容标识图片
     */
    public PromptTextDialog changeLayout(int aLayoutID) {
        mLayoutID = aLayoutID;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mLayoutID);

        showContent();

        TextView lConfirmView = findViewById(R.id.dialog_prompt_text_confirm);
        if (mConfirm != null) lConfirmView.setText(mConfirm);// mConfirm可以有默认文字
        lConfirmView.setOnClickListener(this);
        View lCancelView = findViewById(R.id.dialog_prompt_text_cancel);

        boolean lShowlRecycleView = rfid_list != null;
        if (lShowlRecycleView) {
            LinearLayout lay_rfid = findViewById(R.id.lay_rfid);
            RecyclerView lRecycleView = findViewById(R.id.rfid_list);
            if (rfid_list.size() > 1) {
                lay_rfid.setVisibility(View.GONE);
                lCancelView.setVisibility(View.VISIBLE );
                lRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
                BaseQuickAdapter roomAdapter = new RfidListAdapter(R.layout.rfid_list__item_view, rfid_list);
                lRecycleView.setAdapter(roomAdapter);
            } else {
                lay_rfid.setVisibility(View.VISIBLE);
                lCancelView.setVisibility(View.GONE );
                TextView textView = findViewById(R.id.tv_rfid);
                TextView tv_rfid_address = findViewById(R.id.tv_rfid_address);
                textView.setText(rfid_list.get(0));
                tv_rfid_address.setText(mContent.substring(mContent.indexOf("】")+1,mContent.length()));
            }
        }

        if (lCancelView instanceof TextView) {
            boolean lShowCancelView = !TextUtils.isEmpty(mCancel);
            lCancelView.setVisibility(lShowCancelView ? View.VISIBLE : View.GONE);
            if (lShowCancelView) {
                ((TextView) lCancelView).setText(mCancel);
                lCancelView.setOnClickListener(this);
            }
            // lConfirmView.setBackgroundResource(lShowCancelView ? R.drawable.bg_fillet_blue_gradient_bottom_right : R.drawable.bg_fillet_blue_gradient_bottom);
        }


        if (mOutside) {
            View lParentView = findViewById(R.id.dialog_prompt_parent_ll);
            if (lParentView != null) lParentView.setOnClickListener(this);
        }

        setCancelable(mCancelable);
        setOnDismissListener(this);

        setFullFillDialog(this);
    }

    /**
     * 设置全屏填充dialog
     */
    public void setFullFillDialog(Dialog aDialog) {
        Window lWindow = aDialog.getWindow();
        if (lWindow != null) {
            WindowManager.LayoutParams lParams = lWindow.getAttributes();
            lParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            lParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            lWindow.setAttributes(lParams);
        }

    }


    public void fastShow(String aContent, IOnActionListener aActionListener) {
        fastShow(null, aContent, null, aActionListener);
    }

    public void fastShow(String aTitle, String aContent, IOnActionListener aActionListener) {
        fastShow(aTitle, aContent, null, aActionListener);
    }

    public void fastShow(String aTitle, String aContent, Integer aFlagImage, IOnActionListener aActionListener) {
        setContent(aTitle, aContent, aFlagImage);
        setOnActionListener(aActionListener);
        show();
    }

    public void setContent(String aTitle, String aContent, Integer aFlagImage) {
        mTitle = aTitle;
        mContent = aContent;
        mFlagImage = aFlagImage;
        showContent();
    }

    private void showContent() {
        // 内容
        View lContentTV = findViewById(R.id.dialog_prompt_content_tv);
        if (lContentTV instanceof TextView)
            ((TextView) lContentTV).setText(mContent == null ? "" : mContent);
        else return;
    }


    public void setOnActionListener(IOnActionListener aActionListener) {
        mActionListener = aActionListener;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.dialog_prompt_text_cancel) {
            if (mActionListener != null) {
                mActionListener.onCancel();
            }
            dismiss();

        } else if (id == R.id.dialog_prompt_text_confirm) {
            if (mActionListener != null) {
                mActionListener.onConfirm();
            }
            dismiss();

        } else if (id == R.id.dialog_prompt_parent_ll) {
            dismiss();

        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        if (mActionListener != null) {
            mActionListener.onDismiss();
            mActionListener = null;
        }
    }
}
