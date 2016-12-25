package cn.lzh.baby.utils.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.xutils.common.util.DensityUtil;

/**
 * 类名称：MyPopupWindow<br>
 * 内容摘要： pop自定义 <br>
 * 属性描述：<br>
 * 方法描述：<br>
 * 修改备注：   <br>
 * 创建时间： 2016/9/13 9:36 <br>
 * 公司：深圳市华移科技股份有限公司<br>
 *
 * @author shetj<br>
 */


public class MyPopupWindow extends PopupWindow{
	private View view;

	/**
	 * 我的pop
	 * @param context 上下文
	 * @param rId 资源id
	 * @param cancel 设置是否点击周围取消
	 */
	public MyPopupWindow(Context context,int rId,View parent,boolean  cancel){
		view = LayoutInflater.from(context).inflate(rId,null);
		setContentView(view);
		setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
		setBackgroundDrawable(new BitmapDrawable(null,""));
		showAtLocation(parent, Gravity.CENTER,0,0);
		setOutsideTouchable(true);
		setFocusable(cancel);
		update();
		if (cancel){
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
		}
	}

	public MyPopupWindow(Context context, int rId, final int[] position, boolean  cancel, int widthpx, int heightpx){
		view = LayoutInflater.from(context).inflate(rId,null);
		setContentView(view);
		setWidth(widthpx);
		setHeight(heightpx);
		setBackgroundDrawable(new BitmapDrawable(null,""));
		showAtLocation(view, Gravity.NO_GRAVITY, position[0]+widthpx+DensityUtil.dip2px(35), position[1]-DensityUtil.dip2px(35));
		setOutsideTouchable(true);
		setFocusable(cancel);
		update();
		if (cancel){
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
		}
	}

	/**
	 * 通过id 获取到view
	 * @param rId
	 * @return
	 */
	public View getView(int rId) {
		return view.findViewById(rId);
	}

	/**
	 * 设置view 的监听
	 * @param rId
	 * @param listener
	 */
	public void setViewListener(int rId, View.OnClickListener listener){
		view.findViewById(rId).setOnClickListener(listener);
	}

	/**
	 * 设置view 可不可见
	 * @param rId
	 * @param visibility
	 */
	public void setViewVisibility(int rId,int visibility){
		view.findViewById(rId).setVisibility(visibility);
	}

	/**
	 * 设置 view 上显示的信息
	 * @param rId
	 * @param msg
	 * @param kind  1表示textview 2.editText 3.button
	 */
	public void setViewMsg(int rId,String msg,int kind){
		switch (kind){
			case 1:
				((TextView)view.findViewById(rId)).setText(msg);
				break;
			case 2:
				((EditText)view.findViewById(rId)).setText(msg);
				break;
			case 3:
				((Button)view.findViewById(rId)).setText(msg);
				break;
		}
	}

	public String getString(int rId){
		return ((EditText)view.findViewById(rId)).getText().toString();
	}
}
