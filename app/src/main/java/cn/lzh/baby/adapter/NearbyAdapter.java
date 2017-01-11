package cn.lzh.baby.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.lzh.baby.R;
import cn.lzh.baby.modle.UserBabyList;
import cn.lzh.baby.ui.location.MapActivity;

/**
 */

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.MyViewHolder>{

    private List<PoiItem> list = new ArrayList<>();
    private Context context;

    public NearbyAdapter(Context context){
        this.context = context;
    }

    public void setData(List<PoiItem> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<PoiItem> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_nearby, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final PoiItem poi = list.get(position);
        holder.tvTitle.setText(poi.getTitle());
        holder.tvLoc.setText(poi.getProvinceName() + poi.getCityName() + poi.getAdName() + poi.getSnippet());
        holder.layoutNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.itemClick(position, poi.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvLoc;
        private LinearLayout layoutNearby;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvLoc = (TextView) itemView.findViewById(R.id.tv_loc);
            layoutNearby = (LinearLayout) itemView.findViewById(R.id.layout_nearby);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void itemClick(int position, String title);
    }
}
