package cn.lzh.baby.ui.home;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lzh.baby.R;
import cn.lzh.baby.adapter.DiaryAdapter;
import cn.lzh.baby.api.TimeListApi;
import cn.lzh.baby.base.BaseFragment;
import cn.lzh.baby.http2_rx.HttpManager;
import cn.lzh.baby.http2_rx.listener.HttpOnNextListener;
import cn.lzh.baby.modle.MainInfo;
import cn.lzh.baby.utils.json.GsonKit;
import cn.lzh.baby.utils.tools.L;

/**
 * 日记
 */
public class DiaryFragment extends BaseFragment implements HttpOnNextListener{


	@BindView(R.id.iRecyclerView)
	LRecyclerView iRecyclerView;
	private DiaryAdapter diaryAdapter;
	private LRecyclerViewAdapter mLRecyclerViewAdapter;
	private LinearLayoutManager mLinearLayoutManager;
	private HttpManager manager;

	private String time;

	private List<MainInfo.DatumBean.DynamicBean> list = new ArrayList();

	public static DiaryFragment newInstance(String time) {
		DiaryFragment fragment = new DiaryFragment();
		Bundle args = new Bundle();
		args.putString("time", time);
		fragment.setArguments(args);
		return fragment;
	}

	public DiaryFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_diary, container, false);
		ButterKnife.bind(this, view);
		Log.i("TAG", "======DiaryFragment_onCreateView");
		return view;
	}

	@Override
	protected void initEventAndData() {

	}

	@Override
	protected void lazyLoadData() {

	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Log.i("TAG", "======DiaryFragment_onViewCreated");
		setInfo();
	}

	public void setData(List<MainInfo.DatumBean.DynamicBean> list, String time, Context context) {
		Log.i("TAG", "======DiaryFragment_setData");
		this.time = time;
//		this.list = list;
//		if (null != diaryAdapter){
//			diaryAdapter.setData(list);
//		}
		TimeListApi timeListApi = new TimeListApi();
		timeListApi.setDate(time);
		manager = new HttpManager(this, (RxAppCompatActivity)context);
        manager.doHttpDeal(timeListApi);
	}

	private void setInfo() {
		if (getArguments() != null) {
			L.i(getArguments().getString("time"));
		}
		//常情况下NestedScrollView嵌套RecycleView，一是会出现只显示一行的情况，二是滑动异常即事件冲突。
		mLinearLayoutManager=new LinearLayoutManager(getActivity());
		mLinearLayoutManager.setSmoothScrollbarEnabled(true);
		mLinearLayoutManager.setAutoMeasureEnabled(true);
		iRecyclerView.setLayoutManager(mLinearLayoutManager);
		iRecyclerView.setHasFixedSize(true);
		iRecyclerView.setNestedScrollingEnabled(false);
		diaryAdapter = new DiaryAdapter(getActivity());
		mLRecyclerViewAdapter = new LRecyclerViewAdapter(diaryAdapter);
		iRecyclerView.setAdapter(mLRecyclerViewAdapter);
		diaryAdapter.setData(list);
	}

	@Override
	public void onNext(String result, String mothead) {
		Log.i("TAG", "=======DiaryFragment="+result);
		MainInfo mainInfo = (MainInfo) GsonKit.jsonToBean(result, MainInfo.class);
		if (mainInfo.getCode() == 1) {
			if (null != mainInfo.getDatum() && null != mainInfo.getDatum().getDynamic()) {
				diaryAdapter.setData(mainInfo.getDatum().getDynamic());
			}
		}
	}

	@Override
	public void onError(Throwable e) {

	}
}
