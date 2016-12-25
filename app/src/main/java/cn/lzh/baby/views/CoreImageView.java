package cn.lzh.baby.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 以宽生成的imageView
 * Created by shetj on 2016/11/26.
 */

public class CoreImageView extends ImageView {
	public CoreImageView(Context context) {
		super(context);
	}

	public CoreImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CoreImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}
}
