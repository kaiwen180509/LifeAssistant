package com.lifeassistant.adapter;

import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifeassistant.R;
import com.lifeassistant.retrofit.WeatherBean;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    // API 取得的資料
    private WeatherBean weatherBean;

    public WeatherAdapter(WeatherBean weatherBean) {
        this.weatherBean = weatherBean;
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
        // 從 WeatherBean 取得資料
        WeatherBean.CwbopendataBean.DatasetBean dataset = weatherBean.getCwbopendata().getDataset();

        // 取得地點
        String title = dataset.getLocation().get(i).getLocationName();

        // 取得時間並轉換格式
        String time = dataset.getDatasetInfo().getUpdate().substring(0, 19).replace("-", "/").replace("T", " ");

        // 取得3個時段的天氣描述
        String[] descriptionName = new String[3];
        int[] descriptionValue = new int[3];
        for (int k = 0; k < 3; k++) {
            descriptionName[k] = dataset.getLocation().get(i).getWeatherElement().get(0).getTime().get(k).getParameter().getParameterName();
            String value = dataset.getLocation().get(i).getWeatherElement().get(0).getTime().get(k).getParameter().getParameterValue();
            if (value.equals("") || value == null) {
                descriptionValue[k] = -1;
            } else {
                descriptionValue[k] = Integer.parseInt(value);
            }
        }

        // 取得3個時段的最高溫
        int[] temperatureHigh = new int[3];
        for (int k = 0; k < 3; k++) {
            String value = dataset.getLocation().get(i).getWeatherElement().get(1).getTime().get(k).getParameter().getParameterName();
            if (value.equals("") || value == null) {
                temperatureHigh[k] = -1;
            } else {
                temperatureHigh[k] = Integer.parseInt(value);
            }
        }

        // 取得3個時段的最低溫
        int[] temperatureLow = new int[3];
        for (int k = 0; k < 3; k++) {
            String value = dataset.getLocation().get(i).getWeatherElement().get(2).getTime().get(k).getParameter().getParameterName();
            if (value.equals("") || value == null) {
                temperatureLow[k] = -1;
            } else {
                temperatureLow[k] = Integer.parseInt(value);
            }
        }

        // 取得3個時段的體感描述
        String[] feel = new String[3];
        for (int k = 0; k < 3; k++) {
            feel[k] = dataset.getLocation().get(i).getWeatherElement().get(3).getTime().get(k).getParameter().getParameterName();
        }

        // 取得3個時段的降雨機率
        int[] rain = new int[3];
        for (int k = 0; k < 3; k++) {
            String value = dataset.getLocation().get(i).getWeatherElement().get(4).getTime().get(k).getParameter().getParameterName();
            if (value.equals("") || value == null) {
                rain[k] = -1;
            } else {
                rain[k] = Integer.parseInt(value);
            }
        }

        // 進行數值比較，取出適當的值
        Arrays.sort(temperatureLow);
        Arrays.sort(temperatureHigh);
        Arrays.sort(rain);
        int bestLow = temperatureLow[0];
        int bestHigh = temperatureHigh[2];
        int highRainP = rain[2];

        // 進行描述比較
        String longFeel = feel[0];
        String mainDescription = descriptionName[0];
        for (int k = 1; k < 3; k++) {
            // 判斷哪個體感描述較長
            if (longFeel.length() < feel[k].length()) {
                longFeel = feel[k];
            }
            // 判斷哪個描述層級較高
            if (descriptionValue[k - 1] < descriptionValue[k]) {
                mainDescription = descriptionName[k];
            }
        }

        /** 判斷圖片
         *
         */

        // 設定 Item 的畫面
        viewHolder.titleTextView.setText(title);
        viewHolder.rainTextView.setText(highRainP + " %");
        viewHolder.tempTextView.setText(bestLow + "~" + bestHigh + " °C");
        viewHolder.feelTextView.setText(longFeel);
        viewHolder.descriptionTextView.setText(mainDescription);
        viewHolder.timeTextView.setText(time);

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
    class ViewHolder extends RecyclerView.ViewHolder {
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


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 綁定 View
            ButterKnife.bind(this, itemView);
        }
    }
}