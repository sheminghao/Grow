package cn.lzh.baby.ui.publishMood;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.lzh.baby.api.AddDynamic;
import cn.lzh.baby.api.UploadApi;
import cn.lzh.baby.http2_rx.HttpManager;
import cn.lzh.baby.http2_rx.listener.HttpOnNextListener;
import cn.lzh.baby.modle.BaseInfo;
import cn.lzh.baby.utils.app.UserUitls;
import cn.lzh.baby.utils.json.GsonKit;
import cn.lzh.baby.utils.tools.EmptyUtils;
import cn.lzh.baby.utils.tools.T;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 */

public class PublishMoodPresenter implements HttpOnNextListener{
	private PublishView view;
	private HttpManager manager;
	public PublishMoodPresenter(PublishView view){
		this.view=view;
		manager=new HttpManager(this,view.getContext());
	}

	/**
	 * 上传多张图片
	 */
	public void uploadImage(){
		if (view.getUploadImage().size() == 0){
			T.show(view.getContext(),"请选择图片！",0);
			return;
		}
		Observable.create(new Observable.OnSubscribe<UploadApi>() {
			@Override
			public void call(Subscriber<? super UploadApi> subscriber) {
				UploadApi api=new UploadApi();
				api.setPart(filesToMultipartBody(view.getUploadImage()));
				subscriber.onNext(api);
			}
		}).subscribe(new Action1<UploadApi>() {
			@Override
			public void call(UploadApi uploadApi) {
				if (uploadApi.getPart().size()>0){
					manager.doHttpDeal(uploadApi);
				}
			}
		});
	}

	/**
	 * 发布心情
	 */
	public void publish(final List<String> urls){
		Observable.create(new Observable.OnSubscribe<AddDynamic>() {
			@Override
			public void call(Subscriber<? super AddDynamic> subscriber) {
				AddDynamic api=new AddDynamic();
				String content=view.getContent();
				String babyId=getBabyId();
				String userId=getUserId();
				String location=view.getLoction();
				String url=getUrl(urls);
				api.setData(babyId,userId,content,"1",location,url);
				Log.i("TAG", "======"+api.toString());
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

	private String getUrl(List<String> urls) {
		String url="";
		for (String s:urls) {
			url=urls+","+url;
		}
		return url;
	}

	private String getUserId() {
		return UserUitls.getLoginInfo().getInfo().getId()+"";
	}

	private String getBabyId() {
		return UserUitls.getBabyInfo().getId();
	}


	/**
	 * 多文件上传 不使用表单提交
	 */
	private  MultipartBody filesToMultipartBody(List<File> files) {
		MultipartBody.Builder builder = new MultipartBody.Builder();

//		for (File file : files) {
//
//		}
		for (int i = 0; i < files.size(); i++) {
			RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), files.get(i));
			builder.addFormDataPart("file"+i, files.get(i).getName(), requestBody);
		}
		builder.setType(MultipartBody.FORM);
		MultipartBody multipartBody = builder.build();
		return multipartBody;
	}

	/**
	 * 多文件上传使用表单提交
	 */
	private List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
		List<MultipartBody.Part> parts = new ArrayList<>(files.size());
		for (File file : files) {
			// TODO: 16-4-2  这里为了简单起见，没有判断file的类型
			RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
			MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
			parts.add(part);
		}
		return parts;
	}


	@Override
	public void onNext(String result, String mothead) {
		if (result!=null){
			BaseInfo baseInfo = (BaseInfo) GsonKit.jsonToBean(result, BaseInfo.class);
			if (TextUtils.equals(mothead,"file/upload")){
				Log.i("TAG", "======"+result);
				List<String> urls = new ArrayList<>();
				publish(urls);
			}else{
				if (baseInfo.getCode() == 1) {
					Toast.makeText(view.getContext(), "发布成功", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(view.getContext(), "发布失败", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	@Override
	public void onError(Throwable e) {

	}
}
