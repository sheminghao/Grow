package cn.lzh.baby.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.lzh.baby.R;
import cn.lzh.baby.base.BaseActivity;
import cn.lzh.baby.ui.babyInfo.BabyInfoActivity;
import cn.lzh.baby.ui.home.MainActivity;
import cn.lzh.baby.ui.register.RegisterActivity;
import cn.lzh.baby.ui.splash.SplashActivity;
import cn.lzh.baby.utils.tools.T;
import cn.lzh.baby.utils.view.LoadingDialog;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class LoginActivity extends BaseActivity implements LoginView {

	@BindView(R.id.edit_Id)
	EditText editId;
	@BindView(R.id.btn_next)
	Button btnNext;
	LoginPresenter presenter;
	@BindView(R.id.btn_register)
	Button btnRegister;
	@BindView(R.id.tv_pwd)
	TextView tvPwd;
	@BindView(R.id.et_pwd)
	EditText etPwd;


	@Subscribe
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStatusBarState(R.color.themdColor);
		setContentView(R.layout.activity_login);
		ButterKnife.bind(this);

		tvPwd.setText(Html.fromHtml("密&nbsp;&nbsp;&nbsp;&nbsp;码: "));

		presenter = new LoginPresenter(this);
		RxView.clicks(btnNext).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
			@Override
			public void call(Void aVoid) {
				presenter.Login();
//				goMain();
			}
		});
		RxView.clicks(btnRegister).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
			@Override
			public void call(Void aVoid) {
				presenter.goRegister();
			}
		});
		presenter.IsLogin();
	}

	@Override
	public String getUsername() {
		return editId.getText().toString().trim();
	}

	@Override
	public String getPassword() {
		return etPwd.getText().toString().trim();
	}

	@Override
	public void showLoging() {
		LoadingDialog.showLoading(this, "登录...", true);
	}

	@Override
	public void loginSuccese(String msg) {
		LoadingDialog.disDialog();
		showMsg(msg);
		goMain();
	}

	@Override
	public void loginFail(String msg) {
		LoadingDialog.disDialog();
	}

	@Override
	public RxAppCompatActivity getContext() {
		return this;
	}

	@Override
	public void showMsg(String msg) {
		T.show(this, msg, 0);
	}

	@Override
	public void goRegister() {
		startActivity(new Intent(this, RegisterActivity.class));
	}

	@Override
	public void goMain() {
		startActivity(new Intent(this, MainActivity.class));
	}
}
