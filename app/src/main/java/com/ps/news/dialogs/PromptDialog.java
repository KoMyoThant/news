package com.ps.news.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ps.news.R;
import com.ps.news.utils.ScreenUtils;


/**
 * Created by aung on 10/31/16.
 */

public class PromptDialog extends Dialog {

    private RelativeLayout rlRoot;
    private TextView tvConfirmMsg;
    private TextView btnOk;

    private PromptDelegate mPromptDelegate;

    public PromptDialog(Context context) {
        super(context);
        initDialog();
    }

    private void initDialog() {
        getWindow();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_prompt);

        bindViews();
        bindUserEvents();

        float widthPx = ScreenUtils.getInstance().getScreenWidthPx();
        rlRoot.setLayoutParams(
                new FrameLayout.LayoutParams((int) (widthPx * 0.8f), FrameLayout.LayoutParams.WRAP_CONTENT));
    }

    private void bindViews() {
        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        tvConfirmMsg = (TextView) findViewById(R.id.tv_confirm_msg);
        btnOk = (TextView) findViewById(R.id.btn_prompt_ok);
    }

    private void bindUserEvents() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTapOk(v);
            }
        });
    }

    public void setUpPrompt(String msg) {
        setUpPrompt(msg, null, null);
    }

    public void setUpPrompt(String msg, PromptDelegate promptDelegate) {
        setUpPrompt(msg, null, promptDelegate);
    }

    public void setUpPrompt(String msg, String okBtn) {
        setUpPrompt(msg, okBtn, null);
    }

    public void setUpPrompt(String msg, String okBtn, PromptDelegate promptDelegate) {
        mPromptDelegate = promptDelegate;
        tvConfirmMsg.setText(msg);

        if (!TextUtils.isEmpty(okBtn)) {
            btnOk.setText(okBtn);
        }

        show();
    }

    public void onTapOk(View view) {
        dismiss();
        if (mPromptDelegate != null) {
            mPromptDelegate.onConfirmed();
        }
    }

    public interface PromptDelegate {
        void onConfirmed();
    }
}
