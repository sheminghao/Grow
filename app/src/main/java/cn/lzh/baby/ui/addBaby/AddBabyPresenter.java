package cn.lzh.baby.ui.addBaby;

import android.text.TextUtils;
import android.util.Log;

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
import cn.lzh.baby.utils.tools.T;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 */

public class AddBabyPresenter implements HttpOnNextListener{

	private AddBabyView view;
	private HttpManager manager;
	public AddBabyPresenter(AddBabyView View){
		this.view=View;
		manager=new HttpManager(this,view.getContext());
	}

	public void add(){
		if (EmptyUtils.isNotEmpty(view.getFilePath())) {
			view.showLoging();
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
		if (TextUtils.isEmpty(baby.getNickname())){
			T.showShort(view.getContext(), "请填写昵称");
			return;
		}
		if (TextUtils.isEmpty(baby.getSex())){
			T.showShort(view.getContext(), "请填写性别");
			return;
		}
		if (TextUtils.isEmpty(baby.getBirthday())){
			T.showShort(view.getContext(), "请填写生日");
			return;
		}
		if (TextUtils.isEmpty(view.getChenghu())){
			T.showShort(view.getContext(), "请填写称呼");
			return;
		}
		if (TextUtils.isEmpty(view.getFilePath())){
			T.showShort(view.getContext(), "请选择头像");
			return;
		}

		AddBabyApi babyApi=new AddBabyApi();
		babyApi.setBabyInfo(baby, view.getChenghu());
		manager.doHttpDeal(babyApi);
	}


	@Override
	public void onNext(String result, String mothead) {
		Log.i("Tag", "-----add"+result);
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
