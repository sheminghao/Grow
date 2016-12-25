package cn.lzh.baby.ui.babyInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.lzh.baby.R;
import cn.lzh.baby.base.BaseActivity;
import cn.lzh.baby.modle.Baby;
import cn.lzh.baby.ui.home.MainActivity;
import cn.lzh.baby.ui.splash.SplashActivity;
import cn.lzh.baby.utils.app.UserUitls;
import cn.lzh.baby.utils.tools.EmptyUtils;
import cn.lzh.baby.utils.tools.ImageLoaderx;
import cn.lzh.baby.utils.view.LoadingDialog;
import cn.lzh.baby.utils.view.MyPopupWindow;
import com.liuguangqiang.ipicker.IPicker;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BabyInfoActivity extends BaseActivity implements BabyInfoView {

	@BindView(R.id.iv_return)
	ImageView ivReturn;
	@BindView(R.id.tv_title)
	TextView tvTitle;
	@BindView(R.id.tv_right)
	TextView tvRight;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.iv_baby)
	ImageView ivBaby;
	@BindView(R.id.tv_name)
	TextView tvName;
	@BindView(R.id.rl_name)
	RelativeLayout rlName;
	@BindView(R.id.tv_sex)
	TextView tvSex;
	@BindView(R.id.rl_sex)
	RelativeLayout rlSex;
	@BindView(R.id.rl_shengri)
	RelativeLayout rlShengri;
	@BindView(R.id.content_baby_info)
	LinearLayout contentBabyInfo;
	@BindView(R.id.rl_image)
	RelativeLayout rlImage;
	@BindView(R.id.content_root)
	CoordinatorLayout contentRoot;
	@BindView(R.id.tv_shengri)
	TextView tvShengri;
	private MyPopupWindow pop;
	private String imagePath;
	private Baby baby;
	private BabyInfoPresenter babyInfoPresenter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStatusBarState(R.color.themdColor);
		setContentView(R.layout.activity_baby_info);
		ButterKnife.bind(this);
		babyInfoPresenter=new BabyInfoPresenter(this);
		tvTitle.setText("宝宝资料");
		if (UserUitls.getBabyInfo()!=null){
			baby=UserUitls.getBabyInfo();
		}else {
			baby = new Baby();
		}
		initView();
	}

	private void initView() {
		tvName.setText(baby.getNickname());
		tvSex.setText(baby.getSex());
		tvShengri.setText(baby.getBirthday());
		IPicker.setLimit(1);
		IPicker.setCropEnable(false);
		IPicker.setOnSelectedListener(new IPicker.OnSelectedListener() {
			@Override
			public void onSelected(List<String> paths) {
				if (paths.size() > 0) {
					imagePath = paths.get(0);
					ImageLoaderx.getInstance().LoaderRound(getContext(), paths.get(0), ivBaby);
				}
			}
		});
	}

	@OnClick({R.id.iv_return, R.id.tv_right, R.id.rl_image,
				 R.id.rl_name, R.id.rl_sex, R.id.rl_shengri})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.iv_return:
				finish();
				break;
			case R.id.tv_right:
				babyInfoPresenter.add();
				break;
			case R.id.rl_name:
				showNamePop();
				break;
			case R.id.rl_sex:
				showSexPop();
				break;
			case R.id.rl_shengri:
				showShengRiPop();
				break;
			case R.id.rl_image:
				IPicker.open(getContext());
				break;
		}
	}

	private void showShengRiPop() {
		pop = new MyPopupWindow(this, R.layout.pop_baby_shengri, contentRoot, true);
		pop.setViewMsg(R.id.edit_shenri, tvShengri.getText().toString(), 2);
		pop.setViewListener(R.id.iv_close, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pop.dismiss();
			}
		});
		pop.setViewListener(R.id.btn_sure, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tvShengri.setText(pop.getString(R.id.edit_shenri));
				pop.dismiss();
			}
		});
	}

	private void showSexPop() {
		pop = new MyPopupWindow(this, R.layout.pop_baby_sex, contentRoot, true);
		pop.setViewMsg(R.id.edit_sex, tvSex.getText().toString(), 2);
		pop.setViewListener(R.id.iv_close, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pop.dismiss();
			}
		});
		pop.setViewListener(R.id.btn_sure, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String sex = pop.getString(R.id.edit_sex);
				if (EmptyUtils.isNotEmpty(sex)) {
					tvSex.setText(sex);
				} else {
					tvSex.setText("男");
				}
				pop.dismiss();
			}
		});
	}

	private void showNamePop() {
		pop = new MyPopupWindow(this, R.layout.pop_baby_name, contentRoot, true);
		pop.setViewMsg(R.id.edit_name,tvName.getText().toString(), 2);
		pop.setViewListener(R.id.iv_close, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pop.dismiss();
			}
		});
		pop.setViewListener(R.id.btn_sure, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tvName.setText(pop.getString(R.id.edit_name));
				pop.dismiss();
			}
		});
	}

	@Override
	public Baby getBabyInfo() {
		String sex =tvSex.getText().toString();
		if (EmptyUtils.isNotEmpty(sex)) {
			baby.setSex(sex);
		} else {
			baby.setSex("男");
		}
		baby.setBirthday(tvShengri.getText().toString());
		baby.setNickname(tvName.getText().toString());
		baby.setPortrait(imagePath);
		return baby;
	}

	@Override
	public String getFilePath() {
		return imagePath;
	}

	@Override
	public void showLoging() {
		LoadingDialog.showLoading(this, "正在保存信息...", true);
	}

	@Override
	public void loadingSuccese(String msg) {
		LoadingDialog.disDialog();
		startActivity(new Intent(this, SplashActivity.class));
		finish();
	}

	@Override
	public void loadingFail(String msg) {
		LoadingDialog.disDialog();
	}

	@Override
	public RxAppCompatActivity getContext() {
		return this;
	}
}
