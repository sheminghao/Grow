package cn.lzh.baby.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import cn.lzh.baby.R;
import cn.lzh.baby.adapter.PageAdapter;
import cn.lzh.baby.base.BaseActivity;
import cn.lzh.baby.ui.babyInfo.BabyInfoActivity;
import cn.lzh.baby.ui.publishMood.PublishMoodActivity;
import cn.lzh.baby.ui.publishprivate.PublishPrivateActivity;
import cn.lzh.baby.utils.tools.DensityUtils;
import cn.lzh.baby.utils.tools.L;
import cn.lzh.baby.utils.view.MyPopupWindow;
import cn.lzh.baby.views.MyViewPager;
import com.jakewharton.rxbinding.view.RxView;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class MainActivity extends BaseActivity {

	@BindView(R.id.iv_baby)
	ImageView ivBaby;
	@BindView(R.id.tabs)
	PagerSlidingTabStrip tabs;
	@BindView(R.id.mViewPager)
	MyViewPager mViewPager;
	@BindView(R.id.fab)
	FloatingActionButton fab;
	private ArrayList<Fragment> fragmentList;
	private PageAdapter pageAdapter;
	private MyPopupWindow pop;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		setmViewPager();
		setFabEvent();

	}


	private void setFabEvent(){
		RxView.clicks(fab).subscribe(new Action1<Void>() {
			@Override
			public void call(Void aVoid) {
				int [] positon=new int[2];
				fab.getLocationInWindow(positon);
				L.i(positon[0]+"/"+positon[1]+"");
				showPop(positon);
			}

		});
	}

	private void showPop(final int[] positon) {
		pop=new MyPopupWindow(this,R.layout.pop_case,positon,true,DensityUtil.dip2px(180),DensityUtil.dip2px(300));
		//宝宝资料
		pop.setViewListener(R.id.ly_baobao, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, BabyInfoActivity.class));
				pop.dismiss();
			}
		});
		//发视频
		pop.setViewListener(R.id.ly_p_shipin, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, PublishMoodActivity.class));
				pop.dismiss();
			}
		});
		//发心情
		pop.setViewListener(R.id.ly_p_xinqing, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, PublishMoodActivity.class));
				pop.dismiss();
			}
		});
		//写日记
		pop.setViewListener(R.id.ly_p_yinsi, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, PublishPrivateActivity.class));
				pop.dismiss();
			}
		});
		//我的资料
		pop.setViewListener(R.id.ly_mine, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, PublishPrivateActivity.class));
				pop.dismiss();
			}
		});

	}
	private void setmViewPager() {
		fragmentList = new ArrayList<>();

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources()
						.getDisplayMetrics());
		fragmentList.add(DiaryFragment.newInstance("1月"));
		fragmentList.add(DiaryFragment.newInstance("2月"));
		fragmentList.add(DiaryFragment.newInstance("3月"));
		fragmentList.add(DiaryFragment.newInstance("4月"));
		fragmentList.add(DiaryFragment.newInstance("5月"));
		fragmentList.add(DiaryFragment.newInstance("6月"));
		fragmentList.add(DiaryFragment.newInstance("7月"));
		fragmentList.add(DiaryFragment.newInstance("8月"));
		fragmentList.add(DiaryFragment.newInstance("9月"));
		fragmentList.add(DiaryFragment.newInstance("10月"));
		fragmentList.add(DiaryFragment.newInstance("11月"));
		fragmentList.add(DiaryFragment.newInstance("12月"));
		fragmentList.add(DiaryFragment.newInstance("1月"));
		fragmentList.add(DiaryFragment.newInstance("2月"));
		fragmentList.add(DiaryFragment.newInstance("3月"));
		fragmentList.add(DiaryFragment.newInstance("4月"));
		fragmentList.add(DiaryFragment.newInstance("5月"));
		fragmentList.add(DiaryFragment.newInstance("6月"));
		fragmentList.add(DiaryFragment.newInstance("7月"));
		fragmentList.add(DiaryFragment.newInstance("8月"));
		fragmentList.add(DiaryFragment.newInstance("9月"));
		fragmentList.add(DiaryFragment.newInstance("10月"));
		fragmentList.add(DiaryFragment.newInstance("11月"));
		fragmentList.add(DiaryFragment.newInstance("12月"));
		fragmentList.add(DiaryFragment.newInstance("1月"));
		fragmentList.add(DiaryFragment.newInstance("2月"));
		fragmentList.add(DiaryFragment.newInstance("3月"));
		fragmentList.add(DiaryFragment.newInstance("4月"));
		fragmentList.add(DiaryFragment.newInstance("5月"));
		fragmentList.add(DiaryFragment.newInstance("6月"));
		fragmentList.add(DiaryFragment.newInstance("7月"));
		fragmentList.add(DiaryFragment.newInstance("8月"));
		fragmentList.add(DiaryFragment.newInstance("9月"));
		fragmentList.add(DiaryFragment.newInstance("10月"));
		fragmentList.add(DiaryFragment.newInstance("11月"));
		fragmentList.add(DiaryFragment.newInstance("12月"));
		pageAdapter = new PageAdapter(getSupportFragmentManager(), fragmentList);
		mViewPager.setAdapter(pageAdapter);
		mViewPager.setPageMargin(pageMargin);
		tabs.setViewPager(mViewPager);
		tabs.setUnderlineColor(Color.parseColor("#4ABDFF"));//下划线的颜色
		tabs.setIndicatorColor(Color.parseColor("#30B2FE"));//滑动指示器的颜色
		tabs.setIndicatorHeight(10);//滑动指示器的高度
		tabs.setSelectedTextColor(Color.parseColor("#30B2FE"));
		tabs.setTextSize(DensityUtils.dp2px(this,14));
	}


	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			if((System.currentTimeMillis()-exitTime) > 2000){
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				//退出
				appManager.AppExit(this);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}

