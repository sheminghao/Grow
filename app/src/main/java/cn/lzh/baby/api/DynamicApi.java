package cn.lzh.baby.api;

import cn.lzh.baby.http2_rx.Api.BaseApi;
import cn.lzh.baby.http2_rx.HttpService;
import rx.Observable;

/**
 */

public class DynamicApi extends BaseApi {

	private String pageNum;
	public DynamicApi(){
		setCancel(true);
		setCache(false);
		setMothed("dynamic");
	}

	public void setData(String pageNum){
		this.pageNum=pageNum;
	}

	@Override
	public Observable getObservable(HttpService methods) {
		return methods.dynamic(pageNum);
	}
}
