package com.lifeassistant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.lifeassistant.R;
import com.lifeassistant.model.LifeSharePreference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    // 設定 Item View 的標籤
    private final static int WEATHER_ITEM = 0;
    private final static int AIR_ITEM = 1;

    // 宣告 Spinner 的介面
    private AirSpinnerSelected airSpinnerSelected;
    private WeatherSpinnerSelected weatherSpinnerSelected;

    public SettingAdapter(Context context, AirSpinnerSelected airSpinnerSelected, WeatherSpinnerSelected weatherSpinnerSelected) {
        this.context = context;
        this.airSpinnerSelected = airSpinnerSelected;
        this.weatherSpinnerSelected = weatherSpinnerSelected;
    }

    // Air Spinner 的介面
    public interface AirSpinnerSelected {
        // 選擇某個選項
        void select(int position);
    }

    // Weather Spinner 的介面
    public interface WeatherSpinnerSelected {
        // 選擇某個選項
        void select(int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;

        // Weather Item View
        if (i == WEATHER_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.setting_item_weather, viewGroup, false);
            viewHolder = new WeatherViewHolder(view);
        }
        // Air Item View
        if (i == AIR_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.setting_item_air, viewGroup, false);
            viewHolder = new AirViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        // Weather Item View
        if (viewHolder instanceof WeatherViewHolder) {
            WeatherViewHolder weatherViewHolder = (WeatherViewHolder) viewHolder;

            // Weather Spinner 的內容設定
            String[] array = context.getResources().getStringArray(R.array.weather_location);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, array);
            weatherViewHolder.weatherSpinner.setAdapter(adapter);

            // Weather Spinner 的預設
            weatherViewHolder.weatherSpinner.setSelection(getDefaultData(i));

            // Weather Spinner 的選擇事件
            weatherViewHolder.weatherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    weatherSpinnerSelected.select(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        // Air Item View
        if (viewHolder instanceof AirViewHolder) {
            AirViewHolder airViewHolder = (AirViewHolder) viewHolder;

            // Air Spinner 的內容設定
            String[] array = context.getResources().getStringArray(R.array.air_location);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, array);
            airViewHolder.airSpinner.setAdapter(adapter);

            // Air Spinner 的預設
            airViewHolder.airSpinner.setSelection(getDefaultData(i));

            // Air Spinner 的選擇事件
            airViewHolder.airSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    airSpinnerSelected.select(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    // 取得 Spinner 的預設值
    private int getDefaultData(int type) {
        LifeSharePreference preference = new LifeSharePreference(context);

        // 取得 WEATHER 預設資料
        if (type == WEATHER_ITEM) {
            // 取得 WEATHER 的地點資料，遍歷取得位置回傳
            String[] array = context.getResources().getStringArray(R.array.weather_location);
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(preference.readDefaultWeatherLocation())) {
                    return i;
                }
            }
        }
        // 取得 AIR 預設資料
        if (type == AIR_ITEM) {
            // 取得 AIR 的觀測站資料，遍歷取得位置回傳
            String[] array = context.getResources().getStringArray(R.array.air_location);
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(preference.readDefaultAQISite())) {
                    return i;
                }
            }
        }
        // 預設 0
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        // 位置 0 放 Weather Item View
        if (position == 0) {
            return WEATHER_ITEM;
        }
        // 位置 1 放 Air Item View
        if (position == 1) {
            return AIR_ITEM;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    // 設定的 Weather Item
    class WeatherViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.setting_weather_spinner)
        Spinner weatherSpinner;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            // 綁定 View
            ButterKnife.bind(this, itemView);
        }
    }

    // 設定的 Air Item
    class AirViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.setting_air_spinner)
        Spinner airSpinner;

        public AirViewHolder(View itemView) {
            super(itemView);
            // 綁定 View
            ButterKnife.bind(this, itemView);
        }
    }
}
