package com.lifeassistant.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

import com.lifeassistant.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExplanationDialog {
    private Context context;
    private Dialog dialog;

    public ExplanationDialog(Context context) {
        this.context = context;

        // 建立 Dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.explanation_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 綁定 View
        ButterKnife.bind(this, dialog);
    }

    // 顯示 Dialog
    public ExplanationDialog show() {
        dialog.show();
        return this;
    }

    @OnClick(R.id.explanation_dialog_button)
    public void confirmClick() {
        dialog.dismiss();
    }
}
