package cn.lzh.baby.ui.register;

import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lzh.baby.R;
import cn.lzh.baby.base.BaseActivity;
import rx.functions.Action1;

public class RegisterActivity extends BaseActivity implements RegisterView{

    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_pwd)
    TextView tvPwd;
    @BindView(R.id.tv_confirm_pwd)
    TextView tvConfirmPwd;
    RegisterPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        presenter = new RegisterPresenter(this);
        tvUsername.setText(Html.fromHtml("&nbsp;&nbsp;&nbsp;&nbsp;用户名: "));
        tvPwd.setText(Html.fromHtml("&nbsp;&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;&nbsp;码: "));
        tvConfirmPwd.setText(Html.fromHtml("确认密码: "));

        RxView.clicks(btnRegister).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                presenter.register();
            }
        });
    }

    @Override
    public void register() {
        finish();
    }

    @Override
    public RxAppCompatActivity getContext() {
        return this;
    }
}
