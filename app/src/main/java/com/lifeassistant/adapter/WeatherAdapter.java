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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifeassistant.R;
import com.lifeassistant.retrofit.WeatherBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import butterknife.BindDrawable;
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
        // 從 WeatherBean 取得資料集
        WeatherBean.CwbopendataBean.DatasetBean dataset = weatherBean.getCwbopendata().getDataset();

        // 取得地點
        String title = parserLocationData(dataset, i);
        // 取得時間
        String time = parserTimeData(dataset);
        // 取得天氣描述資料
        String[] description = parserDescriptionData(dataset, i);
        // 取得溫度資料
        int[] temperature = parserTemperatureData(dataset, i);
        // 取得體感描述資料
        String feel = parserFeelData(dataset, i);
        // 取得降雨機率資料
        int rain = parserRainData(dataset, i);

        // 設定 Item 的畫面
        viewHolder.titleTextView.setText(title);
        viewHolder.rainTextView.setText(rain + " %");
        viewHolder.tempTextView.setText(temperature[0] + "~" + temperature[1] + " °C");
        viewHolder.feelTextView.setText(feel);
        viewHolder.descriptionTextView.setText(description[0]);
        viewHolder.timeTextView.setText(time);
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

    /**
     * 解析地點資料
     *
     * @param dataset  資料集
     * @param position 在資料集陣列的第幾個位置
     * @return 地點
     */
    private String parserLocationData(WeatherBean.CwbopendataBean.DatasetBean dataset, int position) {
        return dataset.getLocation().get(position).getLocationName();
    }

    /**
     * 解析更新時間資料
     *
     * @param dataset 資料集
     * @return 更新時間
     */
    private String parserTimeData(WeatherBean.CwbopendataBean.DatasetBean dataset) {
        // 取出時間
        String time = dataset.getDatasetInfo().getUpdate().substring(0, 19);
        // 轉換格式
        time = time.replace("-", "/").replace("T", " ");

        return time;
    }

    /**
     * 從 API 獲得3時段天氣描述資料中解析需要的資料
     *
     * @param dataset  資料集
     * @param position 在資料集陣列的第幾個位置
     * @return 天氣描述資料陣列 {描述資料, 描述層級}
     */
    private String[] parserDescriptionData(WeatherBean.CwbopendataBean.DatasetBean dataset, int position) {
        // 主要的描述資料
        String mainDescription;
        int mainDescriptionValue = 0;

        // 取得3個時段的天氣描述
        String[] descriptionName = new String[3];
        int[] descriptionValue = new int[3];
        for (int j = 0; j < 3; j++) {
            descriptionName[j] = dataset.getLocation().get(position).getWeatherElement().get(0).getTime().get(j).getParameter().getParameterName();
            String value = dataset.getLocation().get(position).getWeatherElement().get(0).getTime().get(j).getParameter().getParameterValue();
            // 檢查描述層級資料是否正確
            if (value.equals("") || value == null) {
                descriptionValue[j] = -1;
            } else {
                descriptionValue[j] = Integer.parseInt(value);
            }
        }

        // 判斷哪個描述層級較高
        mainDescription = descriptionName[0];
        mainDescriptionValue = descriptionValue[0];
        for (int k = 1; k < 3; k++) {
            if (descriptionValue[k - 1] < descriptionValue[k]) {
                mainDescription = descriptionName[k];
                mainDescriptionValue = descriptionValue[k];
            }
        }

        // 回傳描述陣列
        return new String[]{mainDescription, String.valueOf(mainDescriptionValue)};
    }

    /**
     * 從 API 獲得3時段溫度資料中解析需要的資料
     *
     * @param dataset  資料集
     * @param position 在資料集陣列的第幾個位置
     * @return 溫度資料陣列 {低溫, 高溫}
     */
    private int[] parserTemperatureData(WeatherBean.CwbopendataBean.DatasetBean dataset, int position) {
        // 取得3個時段的高低溫度
        int[] temperature = new int[6];
        for (int k = 0; k < 3; k++) {
            String valueH = dataset.getLocation().get(position).getWeatherElement().get(1).getTime().get(k).getParameter().getParameterName();
            String valueL = dataset.getLocation().get(position).getWeatherElement().get(2).getTime().get(k).getParameter().getParameterName();
            // 檢查高溫資料是否正確
            if (valueH.equals("") || valueH == null) {
                temperature[k] = -1;
            } else {
                temperature[k] = Integer.parseInt(valueH);
            }
            // 檢查低溫資料是否正確
            if (valueL.equals("") || valueL == null) {
                temperature[k + 3] = -1;
            } else {
                temperature[k + 3] = Integer.parseInt(valueL);
            }
        }
        // 進行溫度排序
        Arrays.sort(temperature);

        // 回傳溫度陣列
        return new int[]{temperature[0], temperature[5]};
    }

    /**
     * 解析體感描述資料
     *
     * @param dataset  資料集
     * @param position 在資料集陣列的第幾個位置
     * @return 體感描述
     */
    private String parserFeelData(WeatherBean.CwbopendataBean.DatasetBean dataset, int position) {
        // 主要體感描述
        String mainFeel;

        // 取得3個時段的體感描述
        String[] feel = new String[3];
        for (int k = 0; k < 3; k++) {
            feel[k] = dataset.getLocation().get(position).getWeatherElement().get(3).getTime().get(k).getParameter().getParameterName();
        }

        // 判斷哪個體感描述較完善
        mainFeel = feel[0];
        for (int k = 1; k < 3; k++) {
            if (mainFeel.length() < feel[k].length()) {
                mainFeel = feel[k];
            }
        }

        return mainFeel;
    }

    /**
     * 解析降雨機率資料
     *
     * @param dataset  資料集
     * @param position 在資料集陣列的第幾個位置
     * @return 降雨機率
     */
    private int parserRainData(WeatherBean.CwbopendataBean.DatasetBean dataset, int position) {
        // 取得3個時段的降雨機率
        int[] rain = new int[3];
        for (int j = 0; j < 3; j++) {
            String value = dataset.getLocation().get(position).getWeatherElement().get(4).getTime().get(j).getParameter().getParameterName();
            // 檢查降雨機率資料是否正確
            if (value.equals("") || value == null) {
                rain[j] = -1;
            } else {
                rain[j] = Integer.parseInt(value);
            }
        }

        // 進行降雨機率排序
        Arrays.sort(rain);

        // 取出最高的機率
        return rain[2];
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