package cn.lzh.baby.api;

import cn.lzh.baby.http2_rx.Api.BaseApi;
import cn.lzh.baby.http2_rx.HttpService;
import rx.Observable;

/**
 * Created by shetj on 2016/12/11.
 */

public class FindBabyApi extends BaseApi {
	private String id;
	public FindBabyApi(String id) {
		setShowProgress(true);
		setCache(false);
		setCancel(true);
		setMothed("findBaby");
		setCookieNetWorkTime(60);
		setCookieNoNetWorkTime(24*60*60);
		this.id=id;
	}
	@Override
	public Observable getObservable(HttpService methods) {
		return methods.findBaby(id);
	}
}
