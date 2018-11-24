package com.ps.news.dialogs;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ps.news.R;
import com.ps.news.utils.ScreenUtils;


/**
 * Created by aung on 10/27/16.
 */

public class ConfirmDialog extends BaseDialog {

    RelativeLayout rlRoot;

    TextView tvConfirmMsg;

    Button btnConfirmYes;

    Button btnConfirmNo;

    LinearLayout llDialogControls;

    private YesNoConfirmDelegate mDelegate;

    public ConfirmDialog(Context context) {
        super(context);
        initDialog();
    }

    private void initDialog() {
        getWindow();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_confirm);

        rlRoot = findViewById(R.id.rl_root);
        tvConfirmMsg = findViewById(R.id.tv_confirm_msg);
        btnConfirmYes = findViewById(R.id.btn_confirm_yes);
        btnConfirmNo = findViewById(R.id.btn_confirm_no);
        llDialogControls = findViewById(R.id.ll_dialog_controls);

        btnConfirmYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (mDelegate != null) {
                    mDelegate.onConfirmYes();
                }
            }
        });

        btnConfirmNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (mDelegate != null) {
                    mDelegate.onConfirmNo();
                }
            }
        });

        float widthPx = ScreenUtils.getInstance().getScreenWidthPx();
        rlRoot.setLayoutParams(
                new FrameLayout.LayoutParams((int) (widthPx * 0.8f), FrameLayout.LayoutParams.WRAP_CONTENT));
    }

    public void setUpConfirmation(String msg, final YesNoConfirmDelegate yesNoConfirmDelegate) {
        setUpConfirmation(msg, null, null, yesNoConfirmDelegate);
    }

    public void setUpConfirmation(String msg, String yesBtn, String cancelBtn, final YesNoConfirmDelegate yesNoConfirmDelegate) {
        setUpConfirmation(msg, yesBtn, cancelBtn, true, yesNoConfirmDelegate);
    }

    public void setUpConfirmation(String msg, String yesBtn, String cancelBtn, boolean cancellable, final YesNoConfirmDelegate yesNoConfirmDelegate) {
        mDelegate = yesNoConfirmDelegate;
        tvConfirmMsg.setText(msg);

        setCancelable(cancellable);

        if (!TextUtils.isEmpty(yesBtn)) {
            btnConfirmYes.setText(yesBtn);
        } else {
            yesBtn = String.valueOf(btnConfirmYes.getText());
        }

        if (!TextUtils.isEmpty(cancelBtn)) {
            btnConfirmNo.setText(cancelBtn);
        } else {
            cancelBtn = String.valueOf(btnConfirmNo.getText());
        }

        if (yesBtn.length() > 12
                || cancelBtn.length() > 12) {
            llDialogControls.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(btnConfirmYes.getLayoutParams());
            lp.setMargins(0, 0, 0, (int) ScreenUtils.getPixelFromDPI(getContext(), 8));

            btnConfirmYes.setLayoutParams(lp);
            btnConfirmNo.setLayoutParams(lp);
        }

        show();
    }

    public interface YesNoConfirmDelegate {

        void onConfirmYes();

        void onConfirmNo();
    }
}
