package cn.lzh.baby.ui.publishprivate;

import cn.lzh.baby.api.AddDiaryApi;
import cn.lzh.baby.api.AddDynamic;
import cn.lzh.baby.http2_rx.HttpManager;
import cn.lzh.baby.http2_rx.listener.HttpOnNextListener;
import cn.lzh.baby.modle.BaseInfo;
import cn.lzh.baby.utils.app.UserUitls;
import cn.lzh.baby.utils.json.GsonKit;
import cn.lzh.baby.utils.tools.EmptyUtils;
import cn.lzh.baby.utils.tools.T;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by shetj on 2016/12/13.
 */

public class PublishPrivatePresenter implements HttpOnNextListener {

	private HttpManager manager;
	private PublishPrivateView view;
	public PublishPrivatePresenter(PublishPrivateView view){
		this.view=view;
		manager=new HttpManager(this,view.getContext());
	}


	/**
	* 发布 私密日记
	*/
	public void publish(){
		Observable.create(new Observable.OnSubscribe<AddDiaryApi>() {
			@Override
			public void call(Subscriber<? super AddDiaryApi> subscriber) {
				AddDiaryApi api=new AddDiaryApi();
				String money=view.getMoney();
				String content=view.getContent();
				String babyId=getBabyId();
				String userId=getUserId();
				String location=getLocation();
				String token = UserUitls.getLoginInfo().getToken();
				api.setData(content,location,money,token);
				subscriber.onNext(api);
			}
		}).map(new Func1<AddDiaryApi, AddDiaryApi>() {
			@Override
			public AddDiaryApi call(AddDiaryApi addDiaryApi) {
				if (EmptyUtils.isEmpty(addDiaryApi.getContent())){
					T.show(view.getContext(),"内容不能为空！",0);
					return null;
				}
				return addDiaryApi;
			}
		}).subscribe(new Action1<AddDiaryApi>() {
			@Override
			public void call(AddDiaryApi addDiaryApi) {
				if (addDiaryApi!=null) {
					manager.doHttpDeal(addDiaryApi);
				}
			}
		});
	}

	private String getLocation() {
		return "";
	}


	public String getBabyId(){
//		return UserUitls.getBabyInfo().getId();
		return "";
	}

	public String getUserId(){
//		return UserUitls.getUserInfo().getUserId()+"";
		return "";
	}

	@Override
	public void onNext(String result, String mothead) {
		BaseInfo baseInfo = (BaseInfo) GsonKit.jsonToBean(result, BaseInfo.class);
		if (baseInfo.getCode() == 1){
			T.showShort(view.getContext(),baseInfo.getMessage());
			view.getContext().finish();
		}else {
			T.showShort(view.getContext(),"发布失败");
		}
	}

	@Override
	public void onError(Throwable e) {

	}
}
