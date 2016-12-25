package cn.lzh.baby.ui.publishprivate;

import cn.lzh.baby.api.AddDynamic;
import cn.lzh.baby.http2_rx.HttpManager;
import cn.lzh.baby.http2_rx.listener.HttpOnNextListener;
import cn.lzh.baby.utils.app.UserUitls;
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
	* 发布	内容1、图片；2、视频；3、私密日记
	*/
	public void publish(){
		Observable.create(new Observable.OnSubscribe<AddDynamic>() {
			@Override
			public void call(Subscriber<? super AddDynamic> subscriber) {
				AddDynamic api=new AddDynamic();
				String money=view.getMoney();
				String content=view.getContent();
				String babyId=getBabyId();
				String userId=getUserId();
				String location=getLocation();
				api.setData(babyId,userId,content,"3",location,money);
				subscriber.onNext(api);
			}
		}).map(new Func1<AddDynamic, AddDynamic>() {
			@Override
			public AddDynamic call(AddDynamic addDynamic) {
				if (EmptyUtils.isEmpty(addDynamic.getContent())){
					T.show(view.getContext(),"内容不能为空！",0);
					return null;
				}
				if (EmptyUtils.isEmpty(addDynamic.getUrl())){
					T.show(view.getContext(),"消费金额不能为空！",0);
					return null;
				}
				return addDynamic;
			}
		}).subscribe(new Action1<AddDynamic>() {
			@Override
			public void call(AddDynamic addDynamic) {
				if (addDynamic!=null) {
					manager.doHttpDeal(addDynamic);
				}
			}
		});
	}

	private String getLocation() {
		return "";
	}


	public String getBabyId(){
		return UserUitls.getBabyInfo().getId();
	}

	public String getUserId(){
		return UserUitls.getUserInfo().getUserId();
	}

	@Override
	public void onNext(String result, String mothead) {
		if (EmptyUtils.isNotEmpty(result)){
			T.showShort(view.getContext(),result);
		}else {
			T.showShort(view.getContext(),"发布失败");
		}
	}

	@Override
	public void onError(Throwable e) {

	}
}
