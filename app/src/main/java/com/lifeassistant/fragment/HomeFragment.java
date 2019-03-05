package com.lifeassistant.fragment;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lifeassistant.R;
import com.lifeassistant.base.BaseFragment;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.dialog.CustomProgressDialog;
import com.lifeassistant.presenter.HomePresenter;
import com.lifeassistant.view.HomeView;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements HomeView {
    private HomePresenter presenter;

    private CustomProgressDialog progressDialog;

    // 宣告元件
    @BindView(R.id.home_main_image)
    ImageView weatherImageView;
    @BindView(R.id.home_weather_title)
    TextView weatherTitleText;
    @BindView(R.id.home_temp_text)
    TextView temperatureText;
    @BindView(R.id.home_rain_text)
    TextView rainText;
    @BindView(R.id.home_description_text)
    TextView descriptionText;
    @BindView(R.id.home_feel_text)
    TextView feelText;
    @BindView(R.id.home_start_time_text)
    TextView startTimeText;
    @BindView(R.id.home_end_time_text)
    TextView endTimeText;
    @BindView(R.id.home_air_title)
    TextView airTitleText;
    @BindView(R.id.home_aqi_text)
    TextView aqiText;
    @BindView(R.id.home_location_text)
    TextView locationText;
    @BindView(R.id.home_pollutant_text)
    TextView pollutantText;
    @BindView(R.id.home_status_text)
    TextView statusText;
    @BindView(R.id.home_pm25_text)
    TextView pm25Text;
    @BindView(R.id.home_influence_text)
    TextView influenceTimeText;
    @BindView(R.id.home_suggestion_text)
    TextView suggestionText;
    @BindView(R.id.home_monitor_time_text)
    TextView monitorTimeText;
    @BindView(R.id.home_layout)
    LinearLayout mainLayout;

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void initPresenter() {
        presenter = new HomePresenter();
    }

    @Override
    public int getContentViewId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        // 生成進度條
        progressDialog = new CustomProgressDialog(context);

        // 載入畫面的資料
        presenter.setHomeViewData(context);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void closeProgress() {
        progressDialog.close();
    }

    @Override
    public void showView() {
        mainLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideView() {
        mainLayout.setVisibility(View.GONE);
    }

    @Override
    public void showSnackbar(String msg, String action, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(rootView, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(action, listener);
        snackbar.show();
    }

    @Override
    public void showSnackbar(String msg) {
        Snackbar.make(rootView, msg, Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void setWeatherImage(Drawable drawable) {
        weatherImageView.setImageDrawable(drawable);
    }

    @Override
    public void setAirTitle(String airTitle) {
        airTitleText.setText(airTitle);
    }

    @Override
    public void setAirColor(int color) {
        airTitleText.setBackgroundColor(color);
        aqiText.setBackgroundColor(color);
    }

    @Override
    public void setWeatherColor(int color) {
        weatherTitleText.setBackgroundColor(color);
    }

    @Override
    public void setWeatherTitle(String weatherTitle) {
        weatherTitleText.setText(weatherTitle);
    }

    @Override
    public void setTemperatureText(String temperature) {
        temperatureText.setText(temperature);
    }

    @Override
    public void setRainText(String rain) {
        rainText.setText(rain);
    }

    @Override
    public void setDescriptionText(String description) {
        descriptionText.setText(description);
    }

    @Override
    public void setFeelText(String feel) {
        feelText.setText(feel);
    }

    @Override
    public void setStartTimeText(String startTime) {
        startTimeText.setText(startTime);
    }

    @Override
    public void setEndTimeText(String endTime) {
        endTimeText.setText(endTime);
    }

    @Override
    public void setAqiText(String aqi) {
        aqiText.setText(aqi);
    }

    @Override
    public void setLocationText(String location) {
        locationText.setText(location);
    }

    @Override
    public void setPollutantText(String pollutant) {
        pollutantText.setText(pollutant);
    }

    @Override
    public void setStatusText(String status) {
        statusText.setText(status);
    }

    @Override
    public void setPM25Text(String pm25) {
        pm25Text.setText(pm25);
    }

    @Override
    public void setInfluenceText(String influence) {
        influenceTimeText.setText(influence);
    }

    @Override
    public void setSuggestionText(String suggestion) {
        suggestionText.setText(suggestion);
    }

    @Override
    public void setMonitorTimeText(String monitorTime) {
        monitorTimeText.setText(monitorTime);
    }
}