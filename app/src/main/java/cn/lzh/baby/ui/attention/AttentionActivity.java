package cn.lzh.baby.ui.attention;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lzh.baby.R;
import cn.lzh.baby.adapter.AttentionAdapter;
import cn.lzh.baby.base.BaseActivity;
import cn.lzh.baby.modle.UserBabyList;
import cn.lzh.baby.ui.InvitationCode.InvitationCodeActivity;
import cn.lzh.baby.ui.addBaby.AddBabyActivity;
import cn.lzh.baby.utils.view.LoadingDialog;

public class AttentionActivity extends BaseActivity implements AttentionView{

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.btn_add_baby)
    TextView btnAddBaby;
    @BindView(R.id.btn_attention_baby)
    TextView btnAttentionBaby;
    @BindView(R.id.iRecyclerView)
    RecyclerView recyclerView;

    private AttentionPresenter attentionPresenter;

    AttentionAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarState(R.color.themdColor);
        setContentView(R.layout.activity_attention);
        ButterKnife.bind(this);
        tvTitle.setText("关注宝宝");
        tvRight.setVisibility(View.GONE);
        attentionPresenter = new AttentionPresenter(this);
        init();
    }

    private void init(){
        adapter = new AttentionAdapter(this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        loadData();
    }

    @OnClick({R.id.iv_return,R.id.btn_add_baby,R.id.btn_attention_baby})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.btn_add_baby://添加宝宝
                Intent intent = new Intent(this, AddBabyActivity.class);
                startActivityForResult(intent, 1001);
                break;
            case R.id.btn_attention_baby://关注宝宝
                startActivity(new Intent(this, InvitationCodeActivity.class));
                break;
        }
    }

    @Override
    public void loadData() {
        attentionPresenter.loadData();
    }

    @Override
    public void refresh(UserBabyList userBabyList) {
        Log.i("TAG", "======refresh"+userBabyList.getData().toString());
        if (null != userBabyList && null != userBabyList.getData()) {
            adapter.setData(userBabyList.getData());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoging() {
        LoadingDialog.showLoading(this, "加载中...", true);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK){
            loadData();
        }
    }
}
