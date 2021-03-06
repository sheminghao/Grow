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
import cn.lzh.baby.modle.UploadInfo;
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
		if (EmptyUtils.isEmpty(view.getContent())){
			T.show(view.getContext(),"说点什么吧！",0);
			return ;
		}
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
	public void publish(String urls){
		Log.i("TAG", "=====publish");
        if (TextUtils.isEmpty(view.getContent())){
            T.show(view.getContext(),"内容不能为空！",0);
            return;
        }
        AddDynamic api=new AddDynamic();
        String content=view.getContent();
        String babyId=getBabyId();
        String userId=getUserId();
        String location=view.getLoction();
        String url = urls;
		Log.i("TAG","=====babyId"+babyId+"=userId"+userId+"=content"+content+"=type"+"1"+"=location"+location
				+"=imageUrl"+url+"=token"+UserUitls.getToken());
        api.setData(babyId,userId,content,"1",location,url);
        manager.doHttpDeal(api);
	}

	private String getUrl(List<String> urls) {
		String url="";
		for (String s:urls) {
			url=urls+","+url;
		}
		return url;
	}

	private String getUserId() {
		if (null==UserUitls.getLoginInfo()||null==UserUitls.getLoginInfo().getInfo()){
			return "";
		}
		return UserUitls.getLoginInfo().getInfo().getId()+"";
	}

	private String getBabyId() {
		return UserUitls.getMainInfo().getDatum().getBabyId();
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
			UploadInfo uploadInfo = (UploadInfo) GsonKit.jsonToBean(result, UploadInfo.class);
			if (TextUtils.equals(mothead,"file/upload")){
				Log.i("TAG", "======"+result);
				if (!TextUtils.isEmpty(uploadInfo.getDatum())){
					publish(uploadInfo.getDatum());
				}else{
					publish("");
				}
			}else{
				if (uploadInfo.getCode() == 1) {
					Toast.makeText(view.getContext(), "发布成功", Toast.LENGTH_SHORT).show();
					view.getContext().finish();
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
