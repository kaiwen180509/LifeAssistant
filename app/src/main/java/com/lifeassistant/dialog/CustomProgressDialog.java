package com.lifeassistant.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.lifeassistant.R;

public class CustomProgressDialog {
    private Context context;
    private Dialog dialog;

    public CustomProgressDialog(Context context) {
        this.context = context;

        dialog = new Dialog(context, R.style.CustomProgressDialog);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.setCancelable(false);
        // 置中
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    // 關閉
    public void close() {
        dialog.dismiss();
    }

    // 顯示
    public Dialog show() {
        dialog.show();
        return dialog;
    }
}
