package cn.lzh.baby.ui.InvitationCode;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
    @BindView(R.id.yaoqingma)
    EditText etYaoqingma;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.rb_yes)
    RadioButton rbYes;
    @BindView(R.id.btn_add_attention)
    Button btnAddAttention;

    InvitationCodePretener invitationCodePretener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarState(R.color.themdColor);
        setContentView(R.layout.activity_invitation_code);
        ButterKnife.bind(this);
        tvTitle.setText("添加关注宝宝");
        tvRight.setVisibility(View.GONE);
        rbYes.setChecked(false);
        invitationCodePretener = new InvitationCodePretener(this);
    }

    @OnClick({R.id.iv_return, R.id.btn_add_attention})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.btn_add_attention://添加关注
                invitationCodePretener.addAttention();
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

    @Override
    public String getCode() {
        return etYaoqingma.getText().toString().trim();
    }

    @Override
    public String getChenghu() {
        return spinner.getSelectedItem().toString();
    }

    @Override
    public boolean isMainBaby() {
        RadioButton radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        if ("是".equals(radioButton.getText().toString())){
            return true;
        }
        return false;
    }
}
