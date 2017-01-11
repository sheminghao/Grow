package cn.lzh.baby.adapter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import java.util.ArrayList;

public class PageAdapter extends FragmentPagerAdapter
//				implements PagerSlidingTabStrip.IconTabProvider
{
	private ArrayList<Fragment> list;
	private String[] titles = {"1月"};
//	private final int[] ICONS = { R.drawable.ic_launcher, R.drawable.ic_launcher,
//					R.drawable.ic_launcher, R.drawable.ic_launcher ,R.drawable.ic_launcher, R.drawable.ic_launcher ,R.drawable.ic_launcher, R.drawable.ic_launcher };
	public PageAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList) {
		super(fm);
		this.list=fragmentList;
	}

	public void setTitles(String[] titles){
		this.titles = titles;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		SpannableStringBuilder ssb = new SpannableStringBuilder(titles[position]); // space added before text for
		ForegroundColorSpan fcs = new ForegroundColorSpan(Color.BLUE);//字体颜色设置为绿色
		ssb.setSpan(fcs, 1, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//设置字体颜色
		ssb.setSpan(new RelativeSizeSpan(1.2f), 1, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ssb;
	}
//	public int getPageIconResId(int position) {
//		return ICONS[position];
//	}
	public Fragment getItem(int position) {
		return list.get(position);
	}
	@Override
	public int getCount() {
		return list.size();
	}
}
