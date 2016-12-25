package cn.lzh.baby.modle;

/**
 * Created by shetj on 2016/12/17.
 */

public class ReUserInfo {
	/**
	 * message : null
	 * code : 1
	 * datum : {"id":3,"baby_id":1,"create_date":"2016-12-17 15:23:37","create_by":"0","update_date":null,"remarks":null,"appellation":"ç\u0088·ç\u0088·","user_id":0,"del_flag":"0","update_by":null}
	 */
	private String message;
	private int code;
	private UserInfo datum;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public UserInfo getDatum() {
		return datum;
	}

	public void setDatum(UserInfo datum) {
		this.datum = datum;
	}

	public static class UserInfo {
		/**
		 * id : 3
		 * baby_id : 1
		 * create_date : 2016-12-17 15:23:37
		 * create_by : 0
		 * update_date : null
		 * remarks : null
		 * appellation : ç·ç·
		 * user_id : 0
		 * del_flag : 0
		 * update_by : null
		 */

		private int id;
		private int baby_id;
		private String appellation;
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getBaby_id() {
			return baby_id;
		}

		public void setBaby_id(int baby_id) {
			this.baby_id = baby_id;
		}


		public String getAppellation() {
			return appellation;
		}

		public void setAppellation(String appellation) {
			this.appellation = appellation;
		}
	}
}
