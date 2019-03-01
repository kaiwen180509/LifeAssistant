package com.lifeassistant.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lifeassistant.R;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<String> mData;

    public MainAdapter(List<String> data) {
        mData = data;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // 連結項目布局檔 list_item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(mData.get(i));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // 新增某個 Item
    public void addItem(int position, String text) {
        mData.add(position, text);
        notifyItemInserted(position);
    }

    // 刪除某個 Item
    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    // 建立 ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder {
        // 宣告元件
        private TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.main_text);
        }
    }

}
