package com.lifeassistant.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lifeassistant.R;
import com.lifeassistant.retrofit.PM25APIBean;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AirAdapter extends RecyclerView.Adapter<AirAdapter.ViewHolder> {
    // API 取得的 PM2.5 資料
    private ArrayList<PM25APIBean> list;

    public AirAdapter(ArrayList<PM25APIBean> list) {
        this.list = list;
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
        String title = list.get(i).getCounty() + "-" + list.get(i).getSite();
        String density = list.get(i).getPM25();
        String unit = list.get(i).getItemUnit();
        String date = list.get(i).getDataCreationDate().replace("-", "/");
        if (density == null || density.equals("")) {
            density = "0";
        }

        // 設定 PM2.5 級別的顏色
        int color = checkPM25Level(viewHolder, Integer.parseInt(density));
        viewHolder.cardView.setCardBackgroundColor(color);

//        // 設定 ItemView 的內容
//        viewHolder.titleTextView.setText(title);
//        viewHolder.pm25DensityTextView.setText(viewHolder.pm25Density + density + " " + unit);
//        viewHolder.dateTextView.setText(date);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 判斷 PM2.5 的濃度等級，
     *
     * @param viewHolder ItemView
     * @param density    PM2.5 濃度
     * @return res/color 的顏色
     */
    private int checkPM25Level(ViewHolder viewHolder, int density) {
        if (density < 36) {
            return viewHolder.colorLow;
        } else if (density < 54) {
            return viewHolder.colorNormal;
        } else if (density < 71) {
            return viewHolder.colorHigh;
        }
        return viewHolder.colorBoom;
    }

    // 建立 ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder {
        // 宣告元件
        @BindView(R.id.air_card_view)
        CardView cardView;
        @BindColor(R.color.colorLevelLow)
        int colorLow;
        @BindColor(R.color.colorLevelNormal)
        int colorNormal;
        @BindColor(R.color.colorLevelHigh)
        int colorHigh;
        @BindColor(R.color.colorLevelBoom)
        int colorBoom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 綁定 View
            ButterKnife.bind(this, itemView);
        }
    }
}
