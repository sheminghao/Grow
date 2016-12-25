package cn.lzh.baby.utils.tools;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


//Toast统一管理类
public class T
{

	private T()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isShow = true;

	/**
	 * 短时间显示Toast
	 *
	 * @param context 上下文
	 * @param message 信息
	 */
	public static void showShort(Context context, CharSequence message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 短时间显示Toast
	 *
	 * @param context 上下文
	 * @param message 信息
	 */
	public static void showShort(Context context, int message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 长时间显示Toast
	 *
	 * @param context 上下文
	 * @param message 信息
	 */

	public static void showLong(Context context, CharSequence message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 长时间显示Toast
	 *
	 * @param context 上下文
	 * @param message 信息
	 */
	public static void showLong(Context context, int message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 自定义显示Toast时间
	 *
	 * @param context 上下文
	 * @param message 信息
	 * @param duration 时间
	 */
	public static void show(Context context, CharSequence message, int duration)
	{
		if (isShow)
			Toast.makeText(context, message, duration).show();
	}

	/**
	 * 自定义显示Toast时间
	 *
	 * @param context 上下文
	 * @param message 信息
	 * @param duration 时间
	 */
	public static void show(Context context, int message, int duration)
	{
		if (isShow)
			Toast.makeText(context, message, duration).show();
	}

	public static void showCenterToast(Context context,String message,int duration){
		Toast	toast = Toast.makeText(context,
						message, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void showPicLeftToast(Context context,int resId,String message,int duration){
		Toast	toast = Toast.makeText(context, message, duration);
		toast.setGravity(Gravity.CENTER , 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		toastView.setOrientation(LinearLayout.HORIZONTAL);
		toastView.setGravity(Gravity.CENTER );
		ImageView imageCodeProject = new ImageView(context);
		imageCodeProject.setImageResource(resId);
		toastView.addView(imageCodeProject, 0);
		toast.show();
	}
}