package cn.lzh.baby.modle;

import cn.lzh.baby.utils.tools.TimeUtil;

/**
 */

public class Baby {

	//	参数	是否必填	说明	值
//	nickname	√	宝宝昵称
//	sex	√	宝宝性别1男2女
//	birthday	√	宝宝生日,格式：2016-12-03
//	portrait	√	宝宝头像
	private String nickname="宝宝";
	private String sex="男";
	private String birthday= TimeUtil.getYMDTime();
	private String portrait;

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	@Override
	public String toString() {
		return "Baby{" +
						"nickname='" + nickname + '\'' +
						", sex='" + sex + '\'' +
						", birthday='" + birthday + '\'' +
						", portrait='" + portrait + '\'' +
						'}';
	}

}
