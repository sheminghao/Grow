package cn.lzh.baby.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 类名称：FileUtils<br>
 * 内容摘要： //说明主要功能。<br>
 * 属性描述：<br>
 * 方法描述：<br>
 * 修改备注：   <br>
 * 创建时间： 2016/6/2 15:36 <br>
 * 公司：深圳市华移科技股份有限公司<br>
 * @author shetj<br>
 */


public class FileUtils {

	/**
	 * 删除文件
	 * @param file
	 */
	public static void deleteFile(File file) {
			if(file.isFile()){
				file.delete();
				return;
			}
			if(file.isDirectory()){
				File[] childFile = file.listFiles();
				if(childFile == null || childFile.length == 0){
					file.delete();
					return;
				}
				for(File f : childFile){
					deleteFile(f);
				}
				file.delete();
			}
		}

	/**
	 * 通过文件保存的路径查找，并且判断是否到了删除文件缓存的时间
	 * @param filePath  文件路径
	 * @return
	 */
	public static boolean isCacheDataFailure(String filePath) {
		boolean failure = false;
		File data = new File(filePath);
		//默认是30天删除一次
		long CACHE_TIME=1000*60*24*30;
		if (data.exists()
						&& (System.currentTimeMillis() - data.lastModified()) > CACHE_TIME)
			failure = true;
		else if (!data.exists())
			failure = true;
		return failure;
	}

	/**
	 *
	 * @param msg 序列化的保存信息
	 * @param cachePath 保存路径
	 * @param fileName 保存文件的命名
	 * @return
	 */
	public static boolean saveObject(Serializable msg, String cachePath,String fileName) {
		String path=SDCardUtils.getPath(cachePath);
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(path+"/"+fileName);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(msg);
			oos.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				oos.close();
				fos.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 读取文件
	 * @param cachePath 文件所在路径下
	 * @param fileName 文件的命名
	 */
	public static Serializable readObject(String cachePath,String fileName) {
		String path=SDCardUtils.getPath(cachePath);
		File dirFile = new File(path+fileName);
		if(!dirFile.exists()){
			return (Serializable)null;
		}
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(path+"/"+fileName);
			ois = new ObjectInputStream(fis);
			return (Serializable) ois.readObject();
		} catch (FileNotFoundException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				fis.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

}
