package com.fz.cdh.pcdd.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.entity.ValueSet;

import java.util.List;

/**
 * description 列表对话框适配器
 * Created by caojian on 2016/5/25 15:43.
 */
public class DialogListAdapter extends RecyclerView.Adapter {

    private List<ValueSet> data;
    private OnItemClickListener onItemClickListener;

    public DialogListAdapter(List<ValueSet> data) {
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_layout, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyHolder myHolder = ( MyHolder ) holder;
        ValueSet sort = data.get(position);
        myHolder.tvContent.setText(sort.getTypeName());
        if (sort.isChoosed()) {
//            myHolder.tvContent.setTextColor(0xffff2d4b);
            myHolder.ivChosed.setVisibility(View.VISIBLE);
        } else {
//            myHolder.tvContent.setTextColor(0xff1a1a1a);
            myHolder.ivChosed.setVisibility(View.GONE);
        }
        myHolder.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, DialogListAdapter.this);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(int position, RecyclerView.Adapter adapter);
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public TextView tvContent;
        public ImageView ivChosed;

        public MyHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            ivChosed = (ImageView) itemView.findViewById(R.id.iv_chosed);
        }
    }
}
