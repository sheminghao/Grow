package cn.lzh.baby.utils.tools;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import cn.lzh.baby.utils.view.GlideCircleTransform;
import cn.lzh.baby.utils.view.GlideRoundTransform;

public class ImageLoaderx{

//	all:缓存源资源和转换后的资源
//
//	none:不作任何磁盘缓存
//
//	source:缓存源资源
//
//	result：缓存转换后的资源
	private static ImageLoaderx loader;
	/**单例模式*/
	public synchronized static ImageLoaderx getInstance( ) {
		if (loader == null) {
			loader = new ImageLoaderx();
		}
		return loader;
	}
	/**在控件上展示图片*/
	public void displayImage(Context context, String url, ImageView imageView) {
		Glide.with(context)
						.load(url)
						.centerCrop()
						.diskCacheStrategy(DiskCacheStrategy.ALL)
//					.error(R.mipmap.ic_launcher)
//					.placeholder(R.drawable.ic_launcher)
						.crossFade()
						.into(imageView);

	}

	public void displayGif(Context context, String url, ImageView imageView){
		Glide.with(context)
						.load(url)
						.asGif()
						.diskCacheStrategy(DiskCacheStrategy.ALL)
						.centerCrop()
//					.error(R.mipmap.ic_launcher)
//					.placeholder(R.drawable.ic_launcher)
						.into(imageView);

	}
	public  void LoaderCirc(Context context, String url, ImageView iv) {
			Glide.with(context).load(url).transform(new GlideCircleTransform(context)).into(iv);
	}
	public  void LoaderRound(Context context, String url, ImageView iv) {
			Glide.with(context).load(url).transform(new GlideRoundTransform(context,10)).into(iv);
	}


//
//	public void  clear(Context context){
//		Glide.get(context).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
//		Glide.get(context).clearMemory();//清理内存缓存  可以在UI主线程中进行
//	}
}