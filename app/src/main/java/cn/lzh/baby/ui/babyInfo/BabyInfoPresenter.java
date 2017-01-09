package cn.lzh.baby.ui.babyInfo;

import android.text.TextUtils;

import java.io.File;

import cn.lzh.baby.api.AddBabyApi;
import cn.lzh.baby.api.UploadImageApi;
import cn.lzh.baby.http2_rx.Api.BaseResultEntity;
import cn.lzh.baby.http2_rx.HttpManager;
import cn.lzh.baby.http2_rx.listener.HttpOnNextListener;
import cn.lzh.baby.http2_rx.listener.upload.ProgressRequestBody;
import cn.lzh.baby.http2_rx.listener.upload.UploadProgressListener;
import cn.lzh.baby.modle.Baby;
import cn.lzh.baby.utils.app.UserUitls;
import cn.lzh.baby.utils.json.GsonKit;
import cn.lzh.baby.utils.tools.EmptyUtils;
import cn.lzh.baby.utils.tools.L;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by shetj on 2016/12/11.
 */

public class BabyInfoPresenter implements HttpOnNextListener{

	private BabyInfoView view;
	private HttpManager manager;
	public BabyInfoPresenter(BabyInfoView View){
		this.view=View;
		manager=new HttpManager(this,view.getContext());
	}

	public void add(){
		view.showLoging();
		if (EmptyUtils.isNotEmpty(view.getFilePath())) {
			uploadImage();
		}else{
			addbabyInfo(view.getBabyInfo());
		}
	}

	public void uploadImage(){
		if (EmptyUtils.isNotEmpty(view.getFilePath())) {
			File file = new File(view.getFilePath());
			if (file.exists()) {
				UploadImageApi uploadImageApi = new UploadImageApi();
				RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
				MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), new ProgressRequestBody(requestBody,
								new UploadProgressListener() {
									@Override
									public void onProgress(long currentBytesCount, long totalBytesCount) {
									}
								}));
				uploadImageApi.setPart(part);
				manager.doHttpDeal(uploadImageApi);
			}
		}
	}

	public void addbabyInfo(Baby baby){

		AddBabyApi babyApi=new AddBabyApi();
		babyApi.setBabyInfo(baby);
		manager.doHttpDeal(babyApi);
	}


	@Override
	public void onNext(String result, String mothead) {
		BaseResultEntity baseResulte= (BaseResultEntity) GsonKit.jsonToBean(result,BaseResultEntity.class);
		if (TextUtils.equals(mothead,"file/upload")){
			Baby baby=view.getBabyInfo();
			baby.setPortrait(baseResulte.getDatum().get("file"));
			addbabyInfo(baby);
		}else{
			Baby baby= (Baby) GsonKit.jsonToBean(GsonKit.objectToJson(baseResulte.getDatum()),Baby.class);
			UserUitls.saveBabyInfo(baby);
			view.loadingSuccese("保存信息成功");
		}
	}

	@Override
	public void onError(Throwable e) {
		if (e.getMessage()!=null) {
			L.i(e.getMessage());
		}
		view.loadingFail("保存信息失败！");
	}
}
