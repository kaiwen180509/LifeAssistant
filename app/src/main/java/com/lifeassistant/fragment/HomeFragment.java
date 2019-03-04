package com.lifeassistant.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifeassistant.R;
import com.lifeassistant.base.BaseFragment;
import com.lifeassistant.base.BasePresenter;
import com.lifeassistant.presenter.HomePresenter;
import com.lifeassistant.view.HomeView;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements HomeView {
    private HomePresenter presenter;

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
        // 載入畫面的資料
        presenter.setHomeViewData(context);
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