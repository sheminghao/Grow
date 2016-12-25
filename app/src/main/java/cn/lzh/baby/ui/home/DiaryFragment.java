package cn.lzh.baby.ui.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.lzh.baby.R;
import cn.lzh.baby.adapter.DiaryAdapter;
import cn.lzh.baby.base.BaseFragment;
import cn.lzh.baby.utils.tools.L;
import cn.lzh.baby.views.MyLinearLayoutManager;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 日记
 */
public class DiaryFragment extends BaseFragment {


	@BindView(R.id.iRecyclerView)
	LRecyclerView iRecyclerView;
	private DiaryAdapter diaryAdapter;
	private LRecyclerViewAdapter mLRecyclerViewAdapter;
	private LinearLayoutManager mLinearLayoutManager;

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
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setInfo();
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
	}

	@Override
	protected void initEventAndData() {

	}

	@Override
	protected void lazyLoadData() {

	}
}
