package com.lifeassistant.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.lifeassistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RefreshDialog {
    private Context context;
    private Dialog dialog;

    @BindView(R.id.refresh_dialog_confirm)
    Button confirmButton;

    public RefreshDialog(Context context) {
        this.context = context;

        dialog = new Dialog(context);

        dialog.setContentView(R.layout.refresh_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 綁定 View
        ButterKnife.bind(this, dialog);
    }

    // 顯示 Dialog
    public RefreshDialog show() {
        dialog.show();
        return this;
    }

    // 關閉 Dialog
    public void close() {
        dialog.dismiss();
    }

    // 取消 Dialog
    @OnClick(R.id.refresh_dialog_cancel)
    public void cancel() {
        close();
    }

    // 設定 Confirm Click 事件
    public void setConfirmClick(View.OnClickListener confirmClick) {
        confirmButton.setOnClickListener(confirmClick);
    }
}
