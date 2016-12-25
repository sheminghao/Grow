package cn.lzh.baby.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import cn.lzh.baby.AppManager;
import cn.lzh.baby.R;


/**
 * 类名称：BaseActivity<br>
 * 内容摘要： 右滑退出的activity基类<br>
 * 属性描述：<br>
 * 方法描述：<br>
 * 修改备注：   <br>
 * 创建时间： 2016/6/2 9:56 <br>
 * 公司：深圳市华移科技股份有限公司<br>
 *
 * @author shetj<br>
 */

public abstract class BaseSwipeActivity extends SwipeBackAppCompatActivity {
    /**
     * 沉浸式
     */
    protected static SystemBarTintManager mTintManager;
    private AppManager appManager;
    @Subscribe
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
        // 透明状态栏
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
        startAnimation();
        //获取AppManager实例
        appManager= AppManager.getAppManager();
        appManager.addActivity(this);
        ButterKnife.bind(this);
    }

    /**
     * 设置沉浸式   颜色
     */
    public void setStatusBarState(int  colorId) {
        if (Build.VERSION.SDK_INT >= 19) {
            setTranslucentStatus(true);
            mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            // 使StatusBarTintView 和 actionbar的颜色保持一致，风格统一。
            mTintManager.setStatusBarTintResource(colorId);
            // 设置状态栏的文字颜色
            //  mTintManager.setStatusBarDarkMode(false, this);
            mTintManager.setNavigationBarTintColor(colorId);
        }
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    /**
     * 界面开始动画 (此处输入方法执行任务.)
     */
    protected void startAnimation() {
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    /**
     * 界面回退动画 (此处输入方法执行任务.)
     */
    protected void endAnimation() {// 开始动画
        overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
    }


    @Override
    public void onBackPressed() {// 监听回退键是
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {// 设置回退动画
        super.finish();
        endAnimation();
    }


    /**
     * 发布事件
     * @param object
     */
    public void EventPost(Object object){
        EventBus.getDefault().post(object);
    }

    @Override
    public void onStart() {
        super.onStart();
        //注册eventbus
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }


    public void onResume() {
        super.onResume();
        //uemng统计
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        //umeng统计
        MobclickAgent.onPause(this);
    }
    private ProgressDialog progressDialog;
    /**
     * 方法名：  showProgressDialog	<br>
     * 方法描述：TODO 提示正在加载<br>
     * 修改备注：<br>
     * 创建时间： 2016-4-19下午5:03:49<br>
     * @param message
     */
    public void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this,null,
                    message, true, true);
        } else if (progressDialog.isShowing()) {
            progressDialog.setMessage(message);
        }
        progressDialog.show();
    }


    /**
     * 方法名：  hideProgressDialog	<br>
     * 方法描述：TODO 隐藏<br>
     * 修改备注：<br>
     * 创建时间： 2016-4-19下午5:03:37<br>
     */
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
