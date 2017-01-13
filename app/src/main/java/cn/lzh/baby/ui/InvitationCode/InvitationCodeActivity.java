package cn.lzh.baby.ui.InvitationCode;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lzh.baby.R;
import cn.lzh.baby.base.BaseActivity;

public class InvitationCodeActivity extends BaseActivity implements InvitationCodeView{

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarState(R.color.themdColor);
        setContentView(R.layout.activity_invitation_code);
        ButterKnife.bind(this);
        tvTitle.setText("添加关注宝宝");
        tvRight.setVisibility(View.GONE);
    }

    @OnClick({R.id.iv_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
        }
    }

    @Override
    public void showLoging() {

    }

    @Override
    public void loadingSuccese(String msg) {

    }

    @Override
    public void loadingFail(String msg) {

    }

    @Override
    public RxAppCompatActivity getContext() {
        return this;
    }
}
