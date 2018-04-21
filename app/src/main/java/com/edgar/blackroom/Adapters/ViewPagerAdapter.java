package com.edgar.blackroom.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edgar.blackroom.Items.BulletinItem;
import com.edgar.blackroom.R;

import java.util.ArrayList;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder> {

    private ArrayList<BulletinItem> bulletinItems;
    private Context context;
    private ItemClickListener mItemClickListener;

    public ViewPagerAdapter(Context context, ArrayList<BulletinItem> bulletinItems) {
        this.context = context;
        this.bulletinItems = bulletinItems;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_bulletin_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final int tmpPosition = position;
        holder.tvTitle.setText(bulletinItems.get(position).getTitleString());
        holder.tvDate.setText(bulletinItems.get(position).getDateString());
        if (bulletinItems.get(position).shouldBeNew()) {
            holder.tvNewLabel.setVisibility(View.VISIBLE);
        } else {
            holder.tvNewLabel.setVisibility(View.GONE);
        }
        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(tmpPosition);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bulletinItems.size();
    }

    public void setOnItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;
        protected TextView tvNewLabel;
        protected TextView tvDate;
        protected LinearLayout itemRoot;


        public MyViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.bulletin_item_title);
            tvNewLabel = itemView.findViewById(R.id.bulletin_new_label);
            tvDate = itemView.findViewById(R.id.bulletin_item_date);
            itemRoot = itemView.findViewById(R.id.item_root_layout);

        }
    }
}
