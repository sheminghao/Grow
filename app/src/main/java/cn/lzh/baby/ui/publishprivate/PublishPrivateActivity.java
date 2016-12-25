package cn.lzh.baby.ui.publishprivate;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.lzh.baby.R;
import cn.lzh.baby.base.BaseActivity;
import cn.lzh.baby.utils.tools.EmptyUtils;
import cn.lzh.baby.utils.view.LoadingDialog;
import cn.lzh.baby.utils.view.MyPopupWindow;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublishPrivateActivity extends BaseActivity implements PublishPrivateView {

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
	@BindView(R.id.tv_xiaofen)
	TextView tvXiaofen;
	@BindView(R.id.iv_xiaofen)
	ImageView ivXiaofen;
	@BindView(R.id.iv_xiapjiantou)
	ImageView ivXiapjiantou;
	@BindView(R.id.tv_number)
	TextView tvNumber;
	@BindView(R.id.content_publish_private)
	LinearLayout contentPublishPrivate;
	@BindView(R.id.rl_xiaofei)
	RelativeLayout rlXiaofei;
	@BindView(R.id.content_root)
	CoordinatorLayout contentRoot;
	@BindView(R.id.edit_content)
	EditText editContent;
	private MyPopupWindow pop;
	private PublishPrivatePresenter presenter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStatusBarState(R.color.themdColor);
		setContentView(R.layout.activity_publish_private);
		ButterKnife.bind(this);
		tvTitle.setText("私密日记");
		tvRight.setText("发布");
		presenter = new PublishPrivatePresenter(this);
	}

	@OnClick({R.id.iv_return, R.id.tv_right, R.id.rl_xiaofei,R.id.rl_weizhi})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.iv_return:
				finish();
				break;
			case R.id.tv_right:
				presenter.publish();
				break;
			case R.id.rl_xiaofei:
				showXiaoFeiPop();
				break;
			case R.id.rl_weizhi:

				break;
		}
	}

	private void showXiaoFeiPop() {
		String msg = tvNumber.getText().toString().toString().trim();
		pop = new MyPopupWindow(this, R.layout.pop_user_xiaofei, contentRoot, true);
		if (EmptyUtils.isNotEmpty(msg)){
			pop.setViewMsg(R.id.edit_jin, msg, 2);
		}
		pop.setViewListener(R.id.iv_close, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pop.dismiss();
			}
		});
		pop.setViewListener(R.id.btn_sure, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tvNumber.setText(pop.getString(R.id.edit_jin));
				pop.dismiss();
			}
		});
	}

	@Override
	public String getMoney() {
		return tvNumber.getText().toString().trim();
	}

	@Override
	public String getLoction() {
		return tvWeizhi.getText().toString().trim();
	}

	@Override
	public String getContent() {
		return editContent.getText().toString().trim();
	}

	@Override
	public void showLoging() {
		LoadingDialog.showLoading(this, "正在发布...", true);
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
