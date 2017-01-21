package cn.lzh.baby.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lzh.baby.R;
import cn.lzh.baby.adapter.PageAdapter;
import cn.lzh.baby.api.AddAttentionApi;
import cn.lzh.baby.api.MainApi;
import cn.lzh.baby.base.BaseActivity;
import cn.lzh.baby.http2_rx.HttpManager;
import cn.lzh.baby.http2_rx.listener.HttpOnNextListener;
import cn.lzh.baby.modle.Baby;
import cn.lzh.baby.modle.LoginInfo;
import cn.lzh.baby.modle.MainInfo;
import cn.lzh.baby.modle.User;
import cn.lzh.baby.ui.InvitationCode.InvitationCodeActivity;
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
	@BindView(R.id.swipeRefreshLayout)
	SwipeRefreshLayout swipeRefreshLayout;
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
    @BindView(R.id.img_scan)
    ImageView imgScan;
	@BindView(R.id.app_bar)
	AppBarLayout appBarLayout;

	private ArrayList<Fragment> fragmentList;
	private PageAdapter pageAdapter;
	private MyPopupWindow pop;
	private HttpManager manager;
	private final int QR_CODE = 1002;

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
		MainInfo mainInfo = UserUitls.getMainInfo();
		if (null != mainInfo) {
			initViewData(mainInfo);
		}
		loadData();
	}

	private void loadData(){
		Log.i("TAG", "==========token="+ UserUitls.getToken());
		manager=new HttpManager(this, this);
		MainApi api=new MainApi(UserUitls.getToken()+"");
		manager.doHttpDeal(api);
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
		RxView.clicks(imgQRCode).subscribe(new Action1<Void>() {
			@Override
			public void call(Void aVoid) {
				Bitmap bitmap = QRCodeUtil.createQRImage(getMyUUID()+"-"+
						UserUitls.getMainInfo().getDatum().getSecretKey(), 200, 200, null, "");
				if (null != bitmap) {
					showQRDialog(bitmap);
				}
			}

		});
        RxView.clicks(imgScan).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                //去扫描界面
                startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), QR_CODE);
            }
        });
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				loadData();
			}
		});
		appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
			@Override
			public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

				if (verticalOffset >= 0) {
					swipeRefreshLayout.setEnabled(true);
				} else {
					swipeRefreshLayout.setEnabled(false);
				}
			}
		});
	}

	/**
	 * 二维码
	 * @param bitmap
     */
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
		WindowManager wm = this.getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();
		lp.width = width/2;
		lp.height = width/2;
		lp.gravity = Gravity.CENTER;
		dialogWindow.setAttributes(lp);

		alterDialog.setContentView(imageView);
		alterDialog.show();
	}

	private void showPop(final int[] positon) {
		pop=new MyPopupWindow(this,R.layout.pop_case,positon,true,DensityUtil.dip2px(180),DensityUtil.dip2px(300));
		//我的宝宝
		pop.setViewListener(R.id.ly_baobao, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, AttentionActivity.class));
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
				startActivity(new Intent(MainActivity.this, BabyInfoActivity.class));
				pop.dismiss();
			}
		});
	}
	private void setmViewPager() {
		fragmentList = new ArrayList<>();

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources()
						.getDisplayMetrics());
//		fragmentList.add(DiaryFragment.newInstance("1月"));
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
		swipeRefreshLayout.setRefreshing(false);
		MainInfo mainInfo = (MainInfo) GsonKit.jsonToBean(result, MainInfo.class);
		UserUitls.saveMainInfo(mainInfo);
			if (mainInfo.getCode() == 1) {
                if (null != mainInfo.getDatum()) {
                    Baby baby = new Baby();
                    baby.setNickname(mainInfo.getDatum().getNickname());
                    baby.setSex(mainInfo.getDatum().getSex());
                    baby.setId(mainInfo.getDatum().getBabyId());
                    baby.setBirthday(mainInfo.getDatum().getBirthday());
                    baby.setPortrait(mainInfo.getDatum().getPortrait());
                    UserUitls.saveBabyInfo(baby);

                    initViewData(mainInfo);
                }
			}else if (mainInfo.getCode() == 422){
				Log.i("Tag", "-----code"+422);
				UserUitls.setIsLogin(false);
				appManager.exitLogin(MainActivity.this);
				T.showShort(MainActivity.this, "登录失效，请重新登录");
			}
	}

	private void initViewData(MainInfo mainInfo) {
		if (null != mainInfo.getDatum()) {
			Bitmap bitmap = QRCodeUtil.createQRImage(getMyUUID()+"-"+mainInfo.getDatum().getSecretKey(), 200, 200, null, "");
			if (null != bitmap) {
				imgQRCode.setImageBitmap(bitmap);
			}
			Glide.with(MainActivity.this)
					.load(mainInfo.getDatum().getPortrait())
					.placeholder(R.mipmap.ziliaotouxiang)
					.error(R.mipmap.ziliaotouxiang)
					.centerCrop()
					.into(ivBaby);

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

			int titleSize = 0;
			if (null != mainInfo.getDatum().getTimeAxis()){
				titleSize=mainInfo.getDatum().getTimeAxis().size();
			}
			String[] titles = new String[titleSize];
			fragmentList.clear();
			for (int i = 0; i < titleSize; i++) {
				List<String> timeAxis = mainInfo.getDatum().getTimeAxis();
				String[] timeAxiss = timeAxis.get(i).split("-");
				String mouth = timeAxiss[1];
				if ("0".equals(mouth.substring(0, 1))) {
					mouth = mouth.substring(1, 2);
				}
				titles[i] = mouth + "月";
				Log.i("Tag", "-----title" + "-" + titles[i]);
				DiaryFragment diaryFragment = DiaryFragment.newInstance(timeAxis.get(i)+"-01");
				diaryFragment.setData(mainInfo.getDatum().getDynamic(), timeAxis.get(i)+"-01", this);
				fragmentList.add(diaryFragment);
			}
			pageAdapter.setTitles(titles);
			pageAdapter.notifyDataSetChanged();
			tabs.notifyDataSetChanged();
		}
	}

	@Override
	public void onError(Throwable e) {
		swipeRefreshLayout.setRefreshing(false);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode==QR_CODE) {
				String result = data.getExtras().getString("result");
				String secretKey = result.substring(37,result.length());
				Log.i("TAG", "------secretKey="+result+","+secretKey);
				Intent intent = new Intent(MainActivity.this, InvitationCodeActivity.class);
				intent.putExtra("secretKey", secretKey);
				startActivity(intent);
			}
		}
	}

}

