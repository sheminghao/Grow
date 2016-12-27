package cn.lzh.baby.ui.login;

import cn.lzh.baby.api.FindBabyApi;
import cn.lzh.baby.http2_rx.HttpManager;
import cn.lzh.baby.http2_rx.listener.HttpOnNextListener;
import cn.lzh.baby.modle.Baby;
import cn.lzh.baby.utils.app.UserUitls;
import cn.lzh.baby.utils.tools.EmptyUtils;


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
		if (UserUitls.getBabyInfo()!=null){
			loginView.goMain();
		}
	}

	public void goRegister(){
		loginView.goRegister();
	}

	public void Login(){
		String id=loginView.getId();
		if (check(id)){
			//调用接口登录
			loginView.showLoging();
			FindBabyApi api=new FindBabyApi(id);
			manager.doHttpDeal(api);
		}else{
			loginView.showMsg("请先输入宝宝的ID!");
		}
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
		Baby baby=new Baby();
		baby.setId(loginView.getId());
		UserUitls.saveBabyInfo(baby);
		loginView.loginSuccese("登录成功！");
	}

	@Override
	public void onError(Throwable e) {
		loginView.loginFail(e.getMessage());
	}
}
