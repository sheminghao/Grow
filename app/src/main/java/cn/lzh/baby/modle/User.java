package cn.lzh.baby.modle;

/**
 * Created by shetj on 2016/12/3.
 */

public class User {
	private String babyId;
	private String nickName;
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBabyId() {
		return babyId;
	}

	public void setBabyId(String babyId) {
		this.babyId = babyId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		return "User{" +
						"babyId='" + babyId + '\'' +
						", nickName='" + nickName + '\'' +
						", userId='" + userId + '\'' +
						'}';
	}


}
