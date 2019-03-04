package com.lifeassistant.adapter;

import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifeassistant.R;
import com.lifeassistant.model.WeatherDataParser;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    // 使用 WeatherDataParser 解析取得的資料
    private WeatherDataParser dataParser;

    public WeatherAdapter(WeatherDataParser dataParser) {
        this.dataParser = dataParser;
    }

    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // 連結項目布局檔 list_item_weather
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_weather, viewGroup, false);
        return new WeatherAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder viewHolder, final int i) {
        // 取得地點
        String title = dataParser.parserLocationData(i);
        // 取得時間
        String time = dataParser.parserTimeData();
        // 取得天氣描述資料
        String[] description = dataParser.parserDescriptionData(i);
        // 取得溫度資料
        int[] temperature = dataParser.parserTemperatureData(i);
        // 取得體感描述資料
        String feel = dataParser.parserFeelData(i);
        // 取得降雨機率資料
        int rain = dataParser.parserRainData(i);
        // 取得天氣圖片
        Drawable image = dataParser.parserWeatherImage(i);
        // 取得天氣顏色
        int color = dataParser.parserColorData(i);

        // 設定 Item 的畫面內容
        viewHolder.titleTextView.setText(title);
        viewHolder.rainTextView.setText(rain + " %");
        viewHolder.tempTextView.setText(temperature[0] + "~" + temperature[1] + " °C");
        viewHolder.feelTextView.setText(feel);
        viewHolder.descriptionTextView.setText(description[0]);
        viewHolder.timeTextView.setText(time);
        viewHolder.titleTextView.setBackgroundColor(color);

        // 設定圖片資源
        viewHolder.mainImageView.setImageDrawable(image);

        // 設定 ICON
        checkTemperature(viewHolder, temperature[1]);

        // 設定擴展與收縮
        viewHolder.expandedLayout.measure(0, 0);
        final int height = viewHolder.expandedLayout.getMeasuredHeight();
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.expandedLayout.getVisibility() == View.GONE) {
                    show(viewHolder.expandedLayout, height);
                } else {
                    dismiss(viewHolder.expandedLayout, height);
                }
            }
        });
    }

    // 判斷高溫來修改 ICON
    private void checkTemperature(ViewHolder viewHolder, int high) {
        // 低於 20 度為寒冷
        if (high < 20) {
            viewHolder.tempImageView.setImageDrawable(viewHolder.weatherColdIcon);
        }
    }

    // 設定 ExpandedLayout 伸展動畫
    public void show(final View view, int height) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator animator = ValueAnimator.ofInt(0, height);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                view.getLayoutParams().height = value;
                view.setLayoutParams(view.getLayoutParams());
            }
        });
        animator.start();
    }

    // 設定 ExpandedLayout 收縮動畫
    public void dismiss(final View view, int height) {
        ValueAnimator animator = ValueAnimator.ofInt(height, 0);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                // 收縮到高度為 0 時隱藏
                if (value == 0) {
                    view.setVisibility(View.GONE);
                }
                view.getLayoutParams().height = value;
                view.setLayoutParams(view.getLayoutParams());
            }
        });
        animator.start();
    }

    @Override
    public int getItemCount() {
        return 21;
    }

    // 建立 ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder implements Cloneable {
        // 宣告元件
        @BindView(R.id.weather_card_view)
        CardView cardView;
        @BindView(R.id.weather_expanded_layout)
        ConstraintLayout expandedLayout;
        @BindView(R.id.weather_item_title)
        TextView titleTextView;
        @BindView(R.id.weather_item_temp_text)
        TextView tempTextView;
        @BindView(R.id.weather_item_rain_text)
        TextView rainTextView;
        @BindView(R.id.weather_item_temp_description_text)
        TextView descriptionTextView;
        @BindView(R.id.weather_item_feel_text)
        TextView feelTextView;
        @BindView(R.id.weather_item_time_text)
        TextView timeTextView;
        @BindView(R.id.weather_item_img)
        ImageView mainImageView;
        @BindView(R.id.weather_item_temp_img)
        ImageView tempImageView;
        @BindDrawable(R.drawable.icon_head_weather_temp_cold)
        Drawable weatherColdIcon;
        @BindColor(R.color.weatherRain)
        int colorRain;
        @BindColor(R.color.weatherSunny)
        int colorSuuny;
        @BindColor(R.color.weatherTitle)
        int colorNormal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 綁定 View
            ButterKnife.bind(this, itemView);
        }
    }
}