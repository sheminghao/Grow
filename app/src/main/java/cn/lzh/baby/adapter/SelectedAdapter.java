package cn.lzh.baby.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.liuguangqiang.ipicker.adapters.BaseAdapter;

import java.util.List;

import cn.lzh.baby.R;


public class SelectedAdapter extends BaseAdapter<String, SelectedAdapter.ViewHolder> {
    private  OnItemListener listener;
    public SelectedAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater, parent, false);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position)==0) {
            holder.bindData(data.get(position));
        }else{
            holder.showSelect();
        }
        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null) {
                    listener.itemClick();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position<data.size()&&position<9){
            return 0;
        }
        return 1;
    }

    public void setItemClic(OnItemListener listener){
        this.listener=listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivPhoto;

        public ViewHolder(LayoutInflater layoutInflater, ViewGroup parent, boolean attachToRoot) {
            super(layoutInflater.inflate(R.layout.item_selected, parent, attachToRoot));
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
        }

        public void bindData(String path) {
            Glide.with(itemView.getContext()).load(path).into(ivPhoto);
        }

        public void showSelect(){
           ivPhoto.setImageResource(R.mipmap.rijitupian);
        }
    }

    public interface  OnItemListener{
        void itemClick();
    }
}
