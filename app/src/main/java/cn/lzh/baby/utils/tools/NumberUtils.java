package cn.lzh.baby.utils.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类名称：NumberUtils<br>
 * 内容摘要： //说明主要功能。<br>
 * 属性描述：<br>
 * 方法描述：<br>
 * 修改备注：   <br>
 * 创建时间： 2016/8/6 11:50 <br>
 * 公司：深圳市华移科技股份有限公司<br>
 *
 * @author shetj<br>
 */


public class NumberUtils {

	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
						.compile("^[1][0-9][0-9]{9}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}


	public static boolean isPhone(String mobiles) {
		Pattern p=Pattern.compile("\\d{3}-\\d{8}|\\d{4}-\\d{7}|\\d{11}");
		Matcher m=p.matcher(mobiles);
		return m.matches();
	}


	/**
	 * 验证身份证号是否符合规则
	 * @param text 身份证号
	 * @return
	 */
	public static boolean isIdCard(String text) {
		String regx = "[0-9]{17}x";
		String reg1 = "[0-9]{15}";
		String regex = "[0-9]{18}";
		return text.matches(regx) || text.matches(reg1) || text.matches(regex);
	}

	/** * 检测是否有emoji表情 * @param source * @return */
	public static boolean containsEmoji(String source) {                          //两种方法限制emoji
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
				return true;
			}
		}
		return false;
	}


	/**
	 * 判断是否是Emoji
	 * @param codePoint 比较的单个字符
	 * @return
	 */
	private static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
						|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
						|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
						|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	public static boolean isChinese(char a) {
		int v = (int)a;
		return (v >=19968 && v <= 171941);
	}

	/**
	 * 是不是汉字
	 * @param s
	 * @return
	 */
	public static boolean containsChinese(String s){
		if (null == s || "".equals(s.trim())) return false;
		for (int i = 0; i < s.length(); i++  ) {
			if (isChinese(s.charAt(i))) return true;
		}
		return false;
	}
}
