package com.lifeassistant.adapter;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lifeassistant.R;
import com.lifeassistant.retrofit.AQIBean;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AirAdapter extends RecyclerView.Adapter<AirAdapter.ViewHolder> {
    // API 取得的 AQI 資料
    private ArrayList<AQIBean> list;

    // 點擊事件
    private ClickEvent clickEvent;

    public AirAdapter(ArrayList<AQIBean> list, ClickEvent clickEvent) {
        this.list = list;
        this.clickEvent = clickEvent;
    }

    @NonNull
    @Override
    public AirAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // 連結項目布局檔 list_item_air
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_air, viewGroup, false);
        return new AirAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AirAdapter.ViewHolder viewHolder, final int i) {
        // 取得 API 的資料
        String country = list.get(i).getCounty();
        String site = list.get(i).getSiteName();
        String index = list.get(i).getAQI();
        String time = list.get(i).getPublishTime().replace("-", "/");

        // 設定 AQI 指標級別的顏色
        int color = checkPM25Level(viewHolder, Integer.parseInt(index));
        Drawable wrappedDrawable = DrawableCompat.wrap(viewHolder.drawable);
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(color));
        viewHolder.aqiTextView.setBackground(viewHolder.drawable);

        // 設定 ItemView 的畫面
        viewHolder.aqiTextView.setText(index);
        viewHolder.countryTextView.setText(country);
        viewHolder.siteTextView.setText(site);
        viewHolder.timeTextView.setText(time);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickEvent.clickItem(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 判斷 AQI 的指標等級
     *
     * @param viewHolder ItemView
     * @param index      AQI 指標
     * @return res/color 的顏色
     */
    private int checkPM25Level(ViewHolder viewHolder, int index) {
        if (index < 51) {
            return viewHolder.colorGood;
        } else if (index < 101) {
            return viewHolder.colorNormal;
        } else if (index < 151) {
            return viewHolder.colorMaybeBad;
        } else if (index < 201) {
            return viewHolder.colorBad;
        } else if (index < 301) {
            return viewHolder.colorVeryBad;
        }
        return viewHolder.colorDanger;
    }

    public interface ClickEvent {
        // Item 的點擊事件
        void clickItem(int position);
    }

    // 建立 ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder {
        // 宣告元件
        @BindView(R.id.air_aqi_text)
        TextView aqiTextView;
        @BindView(R.id.air_country_text)
        TextView countryTextView;
        @BindView(R.id.air_site_text)
        TextView siteTextView;
        @BindView(R.id.air_time_text)
        TextView timeTextView;
        @BindView(R.id.air_card_view)
        CardView cardView;
        @BindColor(R.color.colorLevelGood)
        int colorGood;
        @BindColor(R.color.colorLevelNormal)
        int colorNormal;
        @BindColor(R.color.colorLevelMaybeBad)
        int colorMaybeBad;
        @BindColor(R.color.colorLevelBad)
        int colorBad;
        @BindColor(R.color.colorLevelVeryBad)
        int colorVeryBad;
        @BindColor(R.color.colorLevelDanger)
        int colorDanger;
        @BindDrawable(R.drawable.shape_circle_textview)
        Drawable drawable;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 綁定 View
            ButterKnife.bind(this, itemView);
        }
    }
}
