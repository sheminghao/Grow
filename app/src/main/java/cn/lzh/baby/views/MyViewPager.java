package cn.lzh.baby.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 类名称：MyViewPager.java <br>
 * 内容摘要： // 是否滑动（可用设置父  子viewpager是否滑动 待完善）。<br>
 * 属性描述：<br>
 * 方法描述：<br>
 * 修改备注：   <br>
 * 创建时间： 2016-3-31下午5:22:28<br>
 * 公司：深圳市华移科技股份有限公司<br>
 * @author shetj<br>
 */
@SuppressLint("ClickableViewAccessibility")
public class MyViewPager extends ViewPager {

	private boolean scrollble=true;
	/** 触摸时按下的点 **/
	PointF downP = new PointF();
	/** 触摸时当前的点 **/
	PointF curP = new PointF();
	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public MyViewPager(Context context) {
		super(context);
	}
	public boolean isScrollble() {
		return scrollble;
	}
	public void setScrollble(boolean scrollble) {
		this.scrollble = scrollble;
	}

	/**
	 * 
	* @Title: onInterceptTouchEvent 
	* @Description: 是否禁止滑动不加这个画面会有微小的变化
	* @param @param arg0
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (!scrollble) {
			return false;
		}else {
			return super.onInterceptTouchEvent(arg0);
		}
		
	}
	//	OnSingleTouchListener onSingleTouchListener;

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (scrollble==true) {
			if(getChildCount()<=1)
			{
				return super.onTouchEvent(arg0);
			}
			//每次进行onTouch事件都记录当前的按下的坐标
			curP.x = arg0.getX();
			curP.y = arg0.getY();
			if(arg0.getAction() == MotionEvent.ACTION_DOWN)
			{
				//记录按下时候的坐标
				//切记不可用 downP = curP ，这样在改变curP的时候，downP也会改变
				downP.x = arg0.getX();
				downP.y = arg0.getY();
				//此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			if(arg0.getAction() == MotionEvent.ACTION_MOVE){
				//此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
				getParent().requestDisallowInterceptTouchEvent(true);
			}

			if(arg0.getAction() == MotionEvent.ACTION_UP || arg0.getAction() == MotionEvent.ACTION_CANCEL){
				//在up时判断是否按下和松手的坐标为一个点
				//如果是一个点，将执行点击事件，这是我自己写的点击事件，而不是onclick
				getParent().requestDisallowInterceptTouchEvent(false);
				if(downP.x==curP.x && downP.y==curP.y){
					return true;
				}
			}
			super.onTouchEvent(arg0); //注意这句不能 return super.onTouchEvent(arg0); 否则触发parent滑动
			return true;
		}else {
			return false;
		}

	}
}