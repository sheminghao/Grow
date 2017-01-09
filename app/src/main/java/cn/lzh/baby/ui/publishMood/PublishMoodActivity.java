package cn.lzh.baby.ui.publishMood;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuguangqiang.ipicker.IPicker;
import com.liuguangqiang.ipicker.events.IPickerEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lzh.baby.R;
import cn.lzh.baby.adapter.SelectedAdapter;
import cn.lzh.baby.base.BaseActivity;
import cn.lzh.baby.utils.view.LoadingDialog;

public class PublishMoodActivity extends BaseActivity implements PublishView {

	@BindView(R.id.iv_return)
	ImageView ivReturn;
	@BindView(R.id.tv_title)
	TextView tvTitle;
	@BindView(R.id.tv_right)
	TextView tvRight;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.tv_weizhi)
	TextView tvWeizhi;
	@BindView(R.id.iv_weizhi)
	ImageView ivWeizhi;
	@BindView(R.id.content_publish_mood)
	LinearLayout contentPublishMood;
	@BindView(R.id.iRecyclerView)
	RecyclerView iRecyclerView;
	@BindView(R.id.editText)
	EditText editText;
	private ArrayList<String> selectPictures = new ArrayList<>();
	private SelectedAdapter adapter;
	private PublishMoodPresenter presenter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStatusBarState(R.color.themdColor);
		setContentView(R.layout.activity_publish_mood);
		ButterKnife.bind(this);
		tvTitle.setText("发表心情");
		tvRight.setText("发布");
		presenter=new PublishMoodPresenter(this);
		initView();
	}

	@Subscribe
	public void onEvent(IPickerEvent event) {
	}

	private void initView() {
		IPicker.setLimit(9);
		IPicker.setCropEnable(false);
		IPicker.setOnSelectedListener(new IPicker.OnSelectedListener() {
			@Override
			public void onSelected(List<String> paths) {
				selectPictures.clear();
				selectPictures.addAll(paths);
				adapter.notifyDataSetChanged();
			}
		});
		adapter = new SelectedAdapter(getApplicationContext(), selectPictures);
		iRecyclerView.setLayoutManager(new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false));
		iRecyclerView.setAdapter(adapter);
		adapter.setItemClic(new SelectedAdapter.OnItemListener() {
			@Override
			public void itemClick() {
				IPicker.open(getApplicationContext(), selectPictures);
			}
		});
	}

	@OnClick({R.id.iv_return, R.id.tv_title, R.id.tv_right, R.id.toolbar, R.id.tv_weizhi, R.id.rl_weizhi})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.iv_return:
				finish();
				break;
			case R.id.tv_title:
				break;
			case R.id.tv_right:
				presenter.uploadImage();
				break;
			case R.id.rl_weizhi:
				break;
		}
	}


	@Override
	public List<File> getUploadImage() {
		List<File> files = new ArrayList<>();
		for (String path : selectPictures) {
			files.add(new File(path));
		}
		return files;
	}

	@Override
	public String getLoction() {
		return tvWeizhi.getText().toString().trim();
	}

	@Override
	public String getContent() {
		return editText.getText().toString().trim();
	}

	@Override
	public void showLoging() {
		LoadingDialog.showLoading(this, "正在发布心情...", true);
	}

	@Override
	public void loadingSuccese(String msg) {
		LoadingDialog.disDialog();
	}

	@Override
	public void loadingFail(String msg) {
		LoadingDialog.disDialog();
	}

	@Override
	public RxAppCompatActivity getContext() {
		return this;
	}
}
