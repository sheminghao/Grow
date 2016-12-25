package cn.lzh.baby.ui.play;

import android.content.Context;
import android.content.res.Configuration;
import android.os.PowerManager;
import android.os.Bundle;
import android.view.View;

import com.dou361.ijkplayer.listener.OnPlayerBackListener;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import cn.lzh.baby.R;
import cn.lzh.baby.base.BaseActivity;

public class PlayActivity extends BaseActivity {

	//	1. PlayStateParams.fitParent:可能会剪裁,保持原视频的大小，显示在中心,当原视频的大小超过view的大小超过部分裁剪处理
//	2. PlayStateParams.fillParent:可能会剪裁,等比例放大视频，直到填满View为止,超过View的部分作裁剪处理
//	3. PlayStateParams.wrapcontent:将视频的内容完整居中显示，如果视频大于view,则按比例缩视频直到完全显示在view中
//	4. PlayStateParams.fitXY:不剪裁,非等比例拉伸画面填满整个View
//	5. PlayStateParams.f16_9:不剪裁,非等比例拉伸画面到16:9,并完全显示在View中
//	6. PlayStateParams.f4_3:不剪裁,非等比例拉伸画面到4:3,并完全显示在View中
	private View rootView;
	private PlayerView player;
	private PowerManager.WakeLock wakeLock;
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStatusBarState(R.color.black);
		/**常亮*/
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
		wakeLock.acquire();
		rootView = getLayoutInflater().from(this).inflate(R.layout.activity_play, null);
		setContentView(rootView);
		String url = "http://183.6.245.249/v.cctv.com/flash/mp4video6/TMS/2011/01/05/cf752b1c12ce452b3040cab2f90bc265_h264818000nero_aac32-1.mp4";
		player = new PlayerView(this, rootView)
						.setScaleType(PlayStateParams.wrapcontent)
						.forbidTouch(false)
						.hideMenu(true)
						.setNetWorkTypeTie(true)
						.setPlaySource(url)
						//隐藏标清的什么
						.hideSteam(true)
						//隐藏旋转
						.hideRotation(false)
						//自动连接
						.setAutoReConnect(true,5000)
						.hideCenterPlayer(true)
						.setPlayerBackListener(new OnPlayerBackListener() {
							@Override
							public void onPlayerBack() {
								//这里可以简单播放器点击返回键
								finish();
							}
						})
						.startPlay();

	}

	@Override
	public void onPause() {
		super.onPause();
		if (player != null) {
			player.onPause();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (player != null) {
			player.onResume();
		}
		/**demo的内容，激活设备常亮状态*/
		if (wakeLock != null) {
			wakeLock.acquire();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (player != null) {
			player.onDestroy();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (player != null) {
			player.onConfigurationChanged(newConfig);
		}
	}
	@Override
	public void onBackPressed() {
		if (player != null && player.onBackPressed()) {
			return;
		}
		super.onBackPressed();
		/**demo的内容，恢复设备亮度状态*/
		if (wakeLock != null) {
			wakeLock.release();
		}
	}
}

