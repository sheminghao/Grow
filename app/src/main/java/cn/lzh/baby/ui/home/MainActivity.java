package cn.lzh.baby.ui.home;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.jakewharton.rxbinding.view.RxView;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lzh.baby.R;
import cn.lzh.baby.adapter.PageAdapter;
import cn.lzh.baby.api.MainApi;
import cn.lzh.baby.base.BaseActivity;
import cn.lzh.baby.http2_rx.HttpManager;
import cn.lzh.baby.http2_rx.listener.HttpOnNextListener;
import cn.lzh.baby.modle.Baby;
import cn.lzh.baby.modle.MainInfo;
import cn.lzh.baby.ui.attention.AttentionActivity;
import cn.lzh.baby.ui.babyInfo.BabyInfoActivity;
import cn.lzh.baby.ui.publishMood.PublishMoodActivity;
import cn.lzh.baby.ui.publishprivate.PublishPrivateActivity;
import cn.lzh.baby.utils.app.UserUitls;
import cn.lzh.baby.utils.json.GsonKit;
import cn.lzh.baby.utils.tools.DensityUtils;
import cn.lzh.baby.utils.tools.L;
import cn.lzh.baby.utils.tools.QRCodeUtil;
import cn.lzh.baby.utils.tools.T;
import cn.lzh.baby.utils.view.MyPopupWindow;
import cn.lzh.baby.views.MyViewPager;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements HttpOnNextListener {

	@BindView(R.id.iv_baby)
	ImageView ivBaby;
	@BindView(R.id.tabs)
	PagerSlidingTabStrip tabs;
	@BindView(R.id.mViewPager)
	MyViewPager mViewPager;
	@BindView(R.id.fab)
	FloatingActionButton fab;
	@BindView(R.id.tv_baby_name)
	TextView tvBabyName;
	@BindView(R.id.tv_baby_sex)
	TextView tvBabySex;
	@BindView(R.id.img_sex)
	ImageView imgSex;
	@BindView(R.id.tv_baby_birth)
	TextView tvBabyBirth;
	@BindView(R.id.tv_video_num)
	TextView tvVideoNum;
	@BindView(R.id.tv_pic_num)
	TextView tvPicNum;
	@BindView(R.id.img_qr_code)
	ImageView imgQRCode;
	private ArrayList<Fragment> fragmentList;
	private PageAdapter pageAdapter;
	private MyPopupWindow pop;
	private HttpManager manager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		setmViewPager();
		setFabEvent();
		initData();
	}

	private void initData(){
		manager=new HttpManager(this, this);
		MainApi api=new MainApi(UserUitls.getLoginInfo().getToken()+"");
		manager.doHttpDeal(api);
	}

	private void setFabEvent(){
		Bitmap bitmap = QRCodeUtil.createQRImage(getMyUUID()+getMyUUID(), 200, 200, null, "");
		if (null != bitmap) {
			imgQRCode.setImageBitmap(bitmap);
		}
		RxView.clicks(fab).subscribe(new Action1<Void>() {
			@Override
			public void call(Void aVoid) {
				int [] positon=new int[2];
				fab.getLocationInWindow(positon);
				L.i(positon[0]+"/"+positon[1]+"");
				showPop(positon);
			}

		});
		RxView.clicks(imgQRCode).subscribe(new Action1<Void>() {
			@Override
			public void call(Void aVoid) {
				Bitmap bitmap = QRCodeUtil.createQRImage(getMyUUID()+getMyUUID(), 200, 200, null, "");
				if (null != bitmap) {
					showQRDialog(bitmap);
				}
			}

		});
	}

	private void showQRDialog(Bitmap bitmap){
		Dialog alterDialog = new Dialog(this);
		alterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		alterDialog.setCancelable(true);
		ImageView imageView = new ImageView(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 200);
		imageView.setLayoutParams(params);
		imageView.setImageBitmap(bitmap);

		Window dialogWindow = alterDialog.getWindow();
		dialogWindow.getDecorView().setPadding(0,0,0,0);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.width = 320;
		lp.height = 320;
		lp.gravity = Gravity.CENTER;
		dialogWindow.setAttributes(lp);

		alterDialog.setContentView(imageView);
		alterDialog.show();
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
				startActivity(new Intent(MainActivity.this, AttentionActivity.class));
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

	@Override
	public void onNext(String result, String mothead) {
		Log.i("Tag", "-----main"+result);
		MainInfo mainInfo = (MainInfo) GsonKit.jsonToBean(result, MainInfo.class);
			if (mainInfo.getCode() == 1) {
                if (null != mainInfo.getDatum()) {
                    Baby baby = new Baby();
                    baby.setNickname(mainInfo.getDatum().getNickname());
                    baby.setSex(mainInfo.getDatum().getSex());
                    baby.setId(mainInfo.getDatum().getBabyId());
                    baby.setBirthday(mainInfo.getDatum().getBirthday());
                    baby.setPortrait(mainInfo.getDatum().getPortrait());
                    UserUitls.saveBabyInfo(baby);

                    tvBabyName.setText(mainInfo.getDatum().getNickname());
                    String sex = "";
                    if ("1".equals(mainInfo.getDatum().getSex())) {
                        sex = "男宝宝";
                        imgSex.setImageResource(R.mipmap.nan);
                    } else if ("2".equals(mainInfo.getDatum().getSex())) {
                        sex = "女宝宝";
                        imgSex.setImageResource(R.mipmap.nv);
                    } else {
                        sex = mainInfo.getDatum().getSex();
                    }
                    tvBabySex.setText(sex);
                    tvBabyBirth.setText(mainInfo.getDatum().getBirthday());

                    tvVideoNum.setText(mainInfo.getDatum().getVideoNum());
                    tvPicNum.setText(mainInfo.getDatum().getPicNum());
                }
			}else if (mainInfo.getCode() == 422){
				Log.i("Tag", "-----code"+422);
				appManager.exitLogin(MainActivity.this);
			}
	}

	@Override
	public void onError(Throwable e) {
	}


}

