package com.lifeassistant.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lifeassistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AirDialog {
    private Context context;
    private Dialog dialog;

    // 宣告元件
    @BindView(R.id.air_dialog_title)
    TextView dialogTitleTextView;
    @BindView(R.id.air_dialog_location_text)
    TextView locationTextView;
    @BindView(R.id.air_dialog_pollutant_text)
    TextView pollutantTextView;
    @BindView(R.id.air_dialog_aqi_text)
    TextView aqiTextView;
    @BindView(R.id.air_dialog_influence_text)
    TextView influenceTextView;
    @BindView(R.id.air_dialog_suggestion_text)
    TextView suggestionTextView;
    @BindView(R.id.air_dialog_btn)
    Button confirmButton;

    public AirDialog(Context context) {
        this.context = context;

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.air_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        ButterKnife.bind(this, dialog);
    }

    // 顯示 Dialog
    public AirDialog show() {
        dialog.show();
        return this;
    }

    // 設定 Dialog Click 事件
    @OnClick(R.id.air_dialog_btn)
    public void confirm() {
        dialog.dismiss();
    }

    // 設定 Dialog 顏色
    public void setColor(int color) {
        dialogTitleTextView.setBackgroundColor(color);
        confirmButton.setTextColor(color);
    }

    // 設定 Dialog 標題
    public void setDialogTitle(String title) {
        dialogTitleTextView.setText(title);
    }

    // 設定 Dialog 地點
    public void setDialogLocation(String location) {
        locationTextView.setText(location);
    }

    // 設定 Dialog AQI
    public void setDialogAQI(String aqi) {
        aqiTextView.setText(aqi);
    }

    // 設定 Dialog 汙染指標物
    public void setDialogPollutant(String pollutant) {
        pollutantTextView.setText(pollutant);
    }

    // 設定 Dialog 影響
    public void setDialogInfluence(String influence) {
        influenceTextView.setText(influence);
    }

    // 設定 Dialog 建議
    public void setDialogSuggestion(String suggestion) {
        suggestionTextView.setText(suggestion);
    }
}
