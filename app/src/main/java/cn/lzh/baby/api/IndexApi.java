package cn.lzh.baby.api;

import cn.lzh.baby.http2_rx.Api.BaseApi;
import cn.lzh.baby.http2_rx.HttpService;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by shetj on 2016/12/13.
 */

public class IndexApi extends BaseApi {

	private String   appellation;
	private int babyId;
	private int userId;

	public IndexApi(){
		setCancel(true);
		setCache(false);
	}

	public void setDate(String appellation,int babyId,int userId){
		this.appellation=appellation;
		this.babyId=babyId;
		this.userId=userId;
	}


	@Override
	public Observable getObservable(HttpService methods) {
		return methods.index(appellation,babyId,userId);
	}
}
