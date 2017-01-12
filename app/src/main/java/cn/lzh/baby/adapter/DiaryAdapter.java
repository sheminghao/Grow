package cn.lzh.baby.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import cn.lzh.baby.R;
import cn.lzh.baby.modle.MainInfo;
import cn.lzh.baby.ui.play.PlayActivity;
import cn.lzh.baby.utils.tools.GlideImageLoader;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 */

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.MyViewHolder> {

	private Activity activity;
	private List<MainInfo.DatumBean.DynamicBean> list = new ArrayList();
	public DiaryAdapter(Activity activity) {
		this.activity=activity;
	}

	public void setData(List<MainInfo.DatumBean.DynamicBean> list){
		this.list = list;
		notifyDataSetChanged();
	}

	/**
	 * viewType=0;表示视频  ， viewType=2;表示图文  ， viewType=2;表示私密  ，
	 */
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		MyViewHolder holder = null;
		switch (viewType) {
			case 0:
				holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).
								inflate(R.layout.itme_diary_shipin, parent, false), viewType);
				break;
			case 1:
				holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).
								inflate(R.layout.itme_diary_tuwen, parent, false), viewType);
				break;
			case 2:
				holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).
								inflate(R.layout.itme_diary_diray, parent, false), viewType);
				break;
		}
		return holder;
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, int position) {
		switch (holder.getItemViewType()) {
			case 0:
				showShiPin(holder, position);
				break;
			case 1:
				showTuWen(holder, position);
				break;
			case 2:
				showYinsi(holder, position);
				break;
		}
	}

	/**
	 * 展示隐私类信息
	 * @param holder
	 */
	private void showYinsi(MyViewHolder holder, int position) {

	}

	/**
	 * 展示图文信息
	 * @param holder
	 */
	private void showTuWen(MyViewHolder holder, int position) {
        if (null != list.get(position)) {
			holder.tvName.setText(list.get(position).getBabyNickname() + "");
			holder.tvCreateTime.setText(list.get(position).getCreate_date() + "");
			holder.tvLoc.setText(list.get(position).getLocation() + "");
			holder.tvContent.setText(list.get(position).getContent() + "");
        }

		//设置图片集合
		List<Integer> images=new ArrayList<>();
		images.add(R.mipmap.ziliaotouxiang);
		images.add(R.mipmap.ziliaotouxiang);
		images.add(R.mipmap.ziliaotouxiang);
		//设置指示器位置（当banner模式中有指示器时）
		Banner banner= (Banner) holder.itemView.findViewById(R.id.banner);
		banner.setImages(images)
						.setImageLoader(new GlideImageLoader())
						.setIndicatorGravity(BannerConfig.CENTER)
						//设置轮播时间
						.setDelayTime(1500)
						.isAutoPlay(true)//设置自动轮播，默认为true
						.start();
	}

	/**
	 * 展示视频类的信息
	 * @param holder
	 */
	private void showShiPin(MyViewHolder holder, int position) {
		RelativeLayout rl_play= (RelativeLayout) holder.itemView.findViewById(R.id.rl_play);
		rl_play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				v.getContext().startActivity(new Intent(v.getContext(), PlayActivity.class));
			}
		});
	}

	@Override
	public int getItemViewType(int position) {
		int type = Integer.parseInt(list.get(position).getType());
		return type;
	}

	@Override
	public int getItemCount() {
        return list.size();
	}

	class MyViewHolder extends RecyclerView.ViewHolder {

		CircleImageView ivUser;
		TextView tvName;
		TextView tvCreateTime;
		TextView tvLoc;
		TextView tvContent;

		public MyViewHolder(View itemView, int viewType) {
			super(itemView);
			switch (viewType){
				case 0://视频
					break;
				case 1://图文
					ivUser = (CircleImageView) itemView.findViewById(R.id.iv_user);
					tvName = (TextView) itemView.findViewById(R.id.tv_name);
					tvCreateTime = (TextView) itemView.findViewById(R.id.tv_create_time);
					tvLoc = (TextView) itemView.findViewById(R.id.tv_loc);
					tvContent = (TextView) itemView.findViewById(R.id.tv_content);
					break;
				case 2://私密
					break;
			}
		}
	}

}
