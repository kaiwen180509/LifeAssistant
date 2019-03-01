package com.lifeassistant.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifeassistant.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    // API 取得的資料
    private ArrayList<String> list;

    public WeatherAdapter(ArrayList<String> list) {
        this.list = list;
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // 建立 ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder {
        // 宣告元件

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 綁定 View
            ButterKnife.bind(this, itemView);
        }
    }
}