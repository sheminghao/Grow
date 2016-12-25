package cn.lzh.baby.utils.tools;

import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import cn.lzh.baby.utils.file.ImageUtils;


/**
 */


public class MoveUtils {
	public static void animToTagOnWindows (FrameLayout frameLayout, ImageView tagView, View toView, float scale) {
		int[] toXY = new int[2];
		toView.getLocationOnScreen(toXY);

		int centerX = (int) (toXY[0] + toView.getMeasuredWidth()/2f);
		int centerY = (int) (toXY[1] + toView.getMeasuredHeight()/2f);
		animToTagOnWindows(frameLayout,tagView,centerX,centerY,scale);
	}

	public static void animToTagOnWindows (final FrameLayout frameLayout, ImageView tagView, int toCenterX, int toCenterY, float scale) {
		int[] winXY = new int[2];
		tagView.getLocationOnScreen(winXY);

		float toX = tagView.getMeasuredWidth()*scale;
		float toY = tagView.getMeasuredHeight()*scale;
		float pivotX = (toCenterX-winXY[0])*1f/tagView.getMeasuredWidth();
		float pivotY = (toCenterY-winXY[1])*1f/tagView.getMeasuredHeight();

		ScaleAnimation scaleAnimation =new ScaleAnimation(1.0f,toX, 1f, toY, Animation.RELATIVE_TO_SELF,pivotX, Animation.RELATIVE_TO_SELF, pivotY);

		final ImageView tempMoveView = new ImageView(tagView.getContext());
		tempMoveView.setScaleType(ImageView.ScaleType.FIT_XY);
		Bitmap tempBm = ImageUtils.getViewBitmap(tagView);
		tempMoveView.setImageBitmap(tempBm);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(tagView.getMeasuredWidth(), tagView.getMeasuredHeight());
		params.setMargins(winXY[0],winXY[1],winXY[0] +tagView.getMeasuredWidth(),winXY[1]+tagView.getMeasuredHeight());
		tempMoveView.setLayoutParams(params);

		frameLayout.addView(tempMoveView);

		scaleAnimation.setDuration(4000);
		scaleAnimation.setFillAfter(true);
		scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				//移除临时显示动画的view
				frameLayout.removeView(tempMoveView);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		tempMoveView.startAnimation(scaleAnimation);
	}
}
