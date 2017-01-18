package cn.lzh.baby.api;

import android.util.Log;

import cn.lzh.baby.http2_rx.Api.BaseApi;
import cn.lzh.baby.http2_rx.HttpService;
import cn.lzh.baby.utils.app.UserUitls;
import rx.Observable;

/**
 * Created by shetj on 2016/12/13.
 */

public class AddDynamic extends BaseApi {
	private String babyId;	//√	宝宝id
	private String userId	;//√	用户id
	private String content;//	√	内容
	private String type	;//√	内容1、图片；2、视频；3、私密日记
	private String location;//	√	定位地址
	private String imageUrl	;//√	图片url地址，多个用,隔开。如果是视频，则第一个是视频地址，第二个是预览图
	private String token;

	public AddDynamic(){
		setMothed("dynamic/add");
		setCancel(false);
		setCache(false);
	}

	public void setData(String babyId, String userId, String content,
	                    String type, String location, String imageUrl){
		this.babyId=babyId;
		this.userId=userId;
		this.content=content;
		this.type=type;
		this.location=location;
		this.imageUrl=imageUrl;
		if (null != UserUitls.getToken()) {
			token = UserUitls.getToken();
		}
	}

	public String getBabyId() {
		return babyId;
	}

	public void setBabyId(String babyId) {
		this.babyId = babyId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String url) {
		this.imageUrl = url;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public Observable getObservable(HttpService methods) {
		return methods.addDynamic(babyId,userId,content,type,location,imageUrl, token);
	}
}
