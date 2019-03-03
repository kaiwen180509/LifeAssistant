package com.lifeassistant.adapter;

import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifeassistant.R;
import com.lifeassistant.model.WeatherDataParser;

import java.util.Calendar;

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

        // 設定 Item 的畫面
        viewHolder.titleTextView.setText(title);
        viewHolder.rainTextView.setText(rain + " %");
        viewHolder.tempTextView.setText(temperature[0] + "~" + temperature[1] + " °C");
        viewHolder.feelTextView.setText(feel);
        viewHolder.descriptionTextView.setText(description[0]);
        viewHolder.timeTextView.setText(time);
        checkRain(viewHolder, rain);

        // 設定天氣的圖片
        parserWeatherImage(viewHolder, Integer.parseInt(description[1]));

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

    // 判斷降雨機率來修改標題顏色
    private void checkRain(ViewHolder viewHolder, int rain) {
        // 壞天氣
        if (rain >= 60) {
            viewHolder.titleTextView.setBackgroundColor(viewHolder.colorRain);
        }

        // 好天氣
        if (rain <= 20) {
            viewHolder.titleTextView.setBackgroundColor(viewHolder.colorSuuny);
        }
    }

    // 根據描述層級來解析圖片
    private void parserWeatherImage(ViewHolder viewHolder, int level) {
        // 取得目前的時間
        Calendar calendar = Calendar.getInstance();
        String hour = (String) DateFormat.format("kk", calendar.getTime());

        // 判斷目前是晚上還是白天
        boolean night = false;
        if (Integer.parseInt(hour) >= 18 || Integer.parseInt(hour) <= 6) {
            night = true;
        }

        // API 回傳的層級錯誤，使用預設圖片
        if (level < 0 || level > 42) {
            viewHolder.mainImageView.setImageDrawable(viewHolder.weatherCloud);
        }
        // 晴天
        if (level == 1 || level == 24) {
            if (night) {
                viewHolder.mainImageView.setImageDrawable(viewHolder.weatherSunnyNight);
            } else {
                viewHolder.mainImageView.setImageDrawable(viewHolder.weatherSunny);
            }
        }
        // 多雲時晴
        if ((level >= 2 && level <= 3) || level == 25) {
            if (night) {
                viewHolder.mainImageView.setImageDrawable(viewHolder.weatherSunnyCloudNight);
            } else {
                viewHolder.mainImageView.setImageDrawable(viewHolder.weatherSunnyCloud);
            }
        }
        // 多雲
        if ((level >= 4 && level <= 7) || level == 27 || level == 28) {
            viewHolder.mainImageView.setImageDrawable(viewHolder.weatherCloud);
        }
        // 雨天
        if ((level >= 8 && level <= 14) || level == 20 || (level >= 29 && level <= 32) || level == 38 || level == 39) {
            viewHolder.mainImageView.setImageDrawable(viewHolder.weatherRain);
        }
        // 雷雨
        if ((level >= 15 && level <= 18) || (level >= 21 && level <= 22) || (level >= 33 && level <= 36) || level == 41) {
            viewHolder.mainImageView.setImageDrawable(viewHolder.weatherThunder);
        }
        // 太陽雨
        if (level == 19) {
            if (night) {
                viewHolder.mainImageView.setImageDrawable(viewHolder.weatherSunnyRainNight);
            } else {
                viewHolder.mainImageView.setImageDrawable(viewHolder.weatherSunnyRain);
            }
        }
        // 有雪
        if (level == 23 || level == 37 || level == 42) {
            viewHolder.mainImageView.setImageDrawable(viewHolder.weatherSnow);
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
        @BindDrawable(R.drawable.icon_weather_sunny)
        Drawable weatherSunny;
        @BindDrawable(R.drawable.icon_weather_sunny_cloud)
        Drawable weatherSunnyCloud;
        @BindDrawable(R.drawable.icon_weather_cloud)
        Drawable weatherCloud;
        @BindDrawable(R.drawable.icon_weather_rain_normal)
        Drawable weatherRain;
        @BindDrawable(R.drawable.icon_weather_sunny_rain)
        Drawable weatherSunnyRain;
        @BindDrawable(R.drawable.icon_weather_thunder)
        Drawable weatherThunder;
        @BindDrawable(R.drawable.icon_weather_snow)
        Drawable weatherSnow;
        @BindDrawable(R.drawable.icon_weather_sunny_night)
        Drawable weatherSunnyNight;
        @BindDrawable(R.drawable.icon_weather_sunny_cloud_night)
        Drawable weatherSunnyCloudNight;
        @BindDrawable(R.drawable.icon_weather_sunny_rain_night)
        Drawable weatherSunnyRainNight;
        @BindColor(R.color.weatherRain)
        int colorRain;
        @BindColor(R.color.weatherSunny)
        int colorSuuny;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 綁定 View
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected ViewHolder clone() {
            ViewHolder viewHolder = null;

            try {
                viewHolder = (ViewHolder) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return viewHolder;
        }
    }
}