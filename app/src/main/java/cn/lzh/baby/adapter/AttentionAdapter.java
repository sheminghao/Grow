package cn.lzh.baby.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.lzh.baby.R;
import cn.lzh.baby.modle.UserBabyList;

/**
 */

public class AttentionAdapter extends RecyclerView.Adapter<AttentionAdapter.MyViewHolder>{

    private List<UserBabyList.DataBean> list = new ArrayList<>();
    private Context context;

    public AttentionAdapter(Context context){
        this.context = context;
    }

    public void setData(List<UserBabyList.DataBean> list){
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_attention, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getPortrait())
                .placeholder(R.mipmap.ziliaotouxiang)
                .error(R.mipmap.ziliaotouxiang)
                .centerCrop()
                .into(holder.imgBaby);
        holder.tvBabyName.setText(list.get(position).getNickname());
        String sex = "";
        if ("1".equals(list.get(position).getSex())){
            sex = "男宝宝";
        }else if("2".equals(list.get(position).getSex())){
            sex = "女宝宝";
        }
        holder.tvBabySex.setText(sex);
        holder.tvBabyBirth.setText(list.get(position).getBirthday());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgBaby;
        private TextView tvBabyName;
        private TextView tvBabySex;
        private TextView tvBabyBirth;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgBaby = (ImageView) itemView.findViewById(R.id.iv_baby);
            tvBabyName = (TextView) itemView.findViewById(R.id.tv_baby_name);
            tvBabySex = (TextView) itemView.findViewById(R.id.tv_baby_sex);
            tvBabyBirth = (TextView) itemView.findViewById(R.id.tv_baby_birth);
        }
    }
}
