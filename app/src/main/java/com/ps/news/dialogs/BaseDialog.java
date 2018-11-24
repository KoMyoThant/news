package com.ps.news.dialogs;

import android.app.Dialog;
import android.content.Context;


/**
 * Created by aung on 10/26/16.
 */

public abstract class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        super(context);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
