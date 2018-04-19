package com.edgar.blackroom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder> {

    private ArrayList<BulletinItem> bulletinItems;
    private Context context;

    public ViewPagerAdapter(Context context, ArrayList<BulletinItem> bulletinItems) {
        this.context = context;
        this.bulletinItems = bulletinItems;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_bulletin_item, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvTitle.setText(bulletinItems.get(position).getTitleString());
        holder.tvDate.setText(bulletinItems.get(position).getDateString());
        if (bulletinItems.get(position).shouldBeNew()) {
            holder.tvNewLabel.setVisibility(View.VISIBLE);
        } else {
            holder.tvNewLabel.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return bulletinItems.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;
        protected TextView tvNewLabel;
        protected TextView tvDate;


        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView)itemView.findViewById(R.id.bulletin_item_title);
            tvNewLabel = (TextView)itemView.findViewById(R.id.bulletin_new_label);
            tvDate = (TextView)itemView.findViewById(R.id.bulletin_item_date);

        }
    }

}
