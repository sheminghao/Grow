package cn.lzh.baby.utils.view;

import android.app.Activity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class CodeUtil {
	private TextView codeTV;
	private int offIcon;
	private int onIcon;
	private Timer timer;
	private TimerTask timerTask;
	private Activity activity;
	private int second;
	private static final int DEF_TIME_LENGTH = 60;
	private int timeLength = DEF_TIME_LENGTH;

	public CodeUtil(Activity activity, int offIcon, int onIcon, TextView tv) {
		codeTV = tv;
		this.offIcon = offIcon;
		this.onIcon = onIcon;
		this.activity = activity;
	}

	public CodeUtil(Activity activity, int offIcon, int onIcon, TextView tv,
			int timeLength) {
		codeTV = tv;
		this.offIcon = offIcon;
		this.onIcon = onIcon;
		this.activity = activity;
		this.timeLength = timeLength;
	}

	public void start() {
		stopTimer();
		second = timeLength;
		codeTV.setBackgroundResource(onIcon);
		codeTV.setClickable(false);
		timer = new Timer();
		timerTask = new CodeTimerTask();
		timer.scheduleAtFixedRate(timerTask, 0, 1000);
	}

	public int getSecond(){
		return second;
	}

	private void stopSendCode() {
		stopTimer();
		codeTV.setText("获取验证码");
		codeTV.setBackgroundResource(offIcon);
		codeTV.setClickable(true);
		// codeBtn.setTextColor(android.graphics.Color.WHITE);
	}

	private void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer.purge();
			timer = null;
		}
		if (timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}
	}

	public void stop() {
		second = 0;
	}

	class CodeTimerTask extends TimerTask {

		@Override
		public void run() {
			if (second <= 0) {
				stop();
			} else {
				onWaiting();
			}
		}

		private void stop() {
			activity.runOnUiThread(new Runnable() {
				public void run() {
					stopSendCode();
				}
			});
		}

		private void onWaiting() {
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					codeTV.setText(second + "秒后重发");
					second--;
				}
			});
		}

	}

}
