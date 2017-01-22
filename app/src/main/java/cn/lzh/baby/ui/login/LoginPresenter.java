package cn.lzh.baby.ui.login;

import android.text.TextUtils;
import android.util.Log;

import cn.lzh.baby.api.FindBabyApi;
import cn.lzh.baby.api.LoginApi;
import cn.lzh.baby.http2_rx.HttpManager;
import cn.lzh.baby.http2_rx.listener.HttpOnNextListener;
import cn.lzh.baby.modle.Baby;
import cn.lzh.baby.modle.LoginInfo;
import cn.lzh.baby.utils.app.UserUitls;
import cn.lzh.baby.utils.json.GsonKit;
import cn.lzh.baby.utils.tools.EmptyUtils;
import cn.lzh.baby.utils.view.LoadingDialog;


/**
 */

public class LoginPresenter implements HttpOnNextListener{
	private LoginView loginView;
	private HttpManager manager;
	public LoginPresenter(LoginView loginView){
		this.loginView=loginView;
		manager=new HttpManager(this,loginView.getContext());
	}

	public void IsLogin(){
		if (!TextUtils.isEmpty(UserUitls.getToken())){
			loginView.goMain();
		}
	}

	public void goRegister(){
		loginView.goRegister();
	}

	public void Login(){
		String userName = loginView.getUsername();
		String password = loginView.getPassword();
		if (TextUtils.isEmpty(userName)) {
			loginView.showMsg("请输入用户名!");
			return;
		}
		if (TextUtils.isEmpty(password)) {
			loginView.showMsg("请输入密码!");
			return;
		}
		//调用接口登录
		loginView.showLoging();
		LoginApi api=new LoginApi(userName, password);
		manager.doHttpDeal(api);
	}

	/**
	 * 检查是不是为空
	 * @param id
	 * @return
	 */
	public boolean check(String id){
		return EmptyUtils.isNotEmpty(id);
	}

	@Override
	public void onNext(String result, String mothead) {
		LoginInfo loginInfo = (LoginInfo) GsonKit.jsonToBean(result, LoginInfo.class);
		if (loginInfo.getCode() == 1) {
			UserUitls.saveToken(loginInfo.getToken());
			UserUitls.saveLoginInfo(loginInfo);
			loginView.loginSuccese("登录成功！");
		}else {
			LoadingDialog.disDialog();
			loginView.showMsg(loginInfo.getMessage());
		}
	}

	@Override
	public void onError(Throwable e) {
		loginView.loginFail(e.getMessage());
	}
}
