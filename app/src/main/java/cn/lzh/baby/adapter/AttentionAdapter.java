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
import cn.lzh.baby.api.SetMainBabyApi;
import cn.lzh.baby.http2_rx.HttpManager;
import cn.lzh.baby.http2_rx.listener.HttpOnNextListener;
import cn.lzh.baby.modle.BaseInfo;
import cn.lzh.baby.modle.UserBabyList;
import cn.lzh.baby.ui.attention.AttentionView;
import cn.lzh.baby.utils.json.GsonKit;
import cn.lzh.baby.utils.view.LoadingDialog;

/**
 */

public class AttentionAdapter extends RecyclerView.Adapter<AttentionAdapter.MyViewHolder>{

    private List<UserBabyList.DataBean> list = new ArrayList<>();
    private Context context;
    private AttentionView attentionView;

    public AttentionAdapter(Context context, AttentionView attentionView){
        this.context = context;
        this.attentionView = attentionView;
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
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
        holder.tvSetAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialog.showLoading(attentionView.getContext(), "", true);
                SetMainBabyApi setMainBabyApi = new SetMainBabyApi(list.get(position).getBaby_id()+"");
                HttpManager manager = new HttpManager(new HttpOnNextListener() {
                    @Override
                    public void onNext(String result, String mothead) {
                        LoadingDialog.disDialog();
                        BaseInfo baseInfo = (BaseInfo) GsonKit.jsonToBean(result, BaseInfo.class);
                        if (baseInfo.getCode() == 1){
                            UserBabyList.DataBean dataBean = list.get(position);
                            list.remove(position);
                            list.add(0, dataBean);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LoadingDialog.disDialog();
                    }
                }, attentionView.getContext());
                manager.doHttpDeal(setMainBabyApi);
            }
        });
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
        private TextView tvSetAs;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgBaby = (ImageView) itemView.findViewById(R.id.iv_baby);
            tvBabyName = (TextView) itemView.findViewById(R.id.tv_baby_name);
            tvBabySex = (TextView) itemView.findViewById(R.id.tv_baby_sex);
            tvBabyBirth = (TextView) itemView.findViewById(R.id.tv_baby_birth);
            tvSetAs = (TextView) itemView.findViewById(R.id.tv_set_as);
        }
    }
}
