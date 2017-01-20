package cn.lzh.baby.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import cn.lzh.baby.R;
import cn.lzh.baby.ui.home.MainActivity;
import cn.lzh.baby.ui.login.LoginActivity;
import cn.lzh.baby.base.BaseMainActivity;
import cn.lzh.baby.utils.app.UserUitls;
import cn.lzh.baby.utils.view.LoadingDialog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends BaseMainActivity implements SplashView{

	@BindView(R.id.btn_mother)
	Button btnMother;
	@BindView(R.id.btn_father)
	Button btnFather;
	@BindView(R.id.btn_g_father)
	Button btnGFather;
	@BindView(R.id.btn_g_mather)
	Button btnGMather;
	@BindView(R.id.btn_w_father)
	Button btnWFather;
	@BindView(R.id.btn_w_mather)
	Button btnWMather;
	private String nick;//昵称
	private SplashPresenter presenter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStatusBarState(R.color.themdColor);
		setContentView(R.layout.activity_splash);
		ButterKnife.bind(this);
		presenter=new SplashPresenter(this);
		if (UserUitls.isLogin()) {
			if (!TextUtils.isEmpty(UserUitls.getToken())) {
				goMain();
			} else {
				goLogin();
			}
		}else{
			goLogin();
		}
	}

	@Override
	public void goLogin() {
		startActivity(new Intent(this, LoginActivity.class));
	}

	@Override
	public void goMain() {
		startActivity(new Intent(this, MainActivity.class));
	}

	@Override
	public String getSelect(int  id) {
		switch (id) {
			case R.id.btn_mother:
				nick = getString(R.string.mama);
				break;
			case R.id.btn_father:
				nick = getString(R.string.father);
				break;
			case R.id.btn_g_father:
				nick = getString(R.string.g_father);
				break;
			case R.id.btn_g_mather:
				nick = getString(R.string.g_mather);
				break;
			case R.id.btn_w_father:
				nick = getString(R.string.w_father);
				break;
			case R.id.btn_w_mather:
				nick = getString(R.string.w_mather);
				break;
		}
		return nick;
	}


	@OnClick(value = {R.id.btn_mother, R.id.btn_father, R.id.btn_g_father, R.id.btn_g_mather, R.id.btn_w_father, R.id.btn_w_mather})
	public void onClick(View view) {
		presenter.goLogin(view.getId());
	}

	@Override
	public void showLoging() {
		LoadingDialog.showLoading(this,"正在获取信息...",true);
	}

	@Override
	public void loadingSuccese(String msg) {
		LoadingDialog.disDialog();
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
