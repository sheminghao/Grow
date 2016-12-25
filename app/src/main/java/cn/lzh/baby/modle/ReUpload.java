package cn.lzh.baby.modle;

/**
 * Created by shetj on 2016/12/18.
 */

public class ReUpload {

	/**
	 * message : null
	 * failed : null
	 * code : 1
	 * datum : {"file":"/images/2016/12/18/147796385718414779642560023.png"}
	 */

	private Object message;
	private Object failed;
	private int code;
	private DatumBean datum;

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public Object getFailed() {
		return failed;
	}

	public void setFailed(Object failed) {
		this.failed = failed;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public DatumBean getDatum() {
		return datum;
	}

	public void setDatum(DatumBean datum) {
		this.datum = datum;
	}

	public static class DatumBean {
		/**
		 * file : /images/2016/12/18/147796385718414779642560023.png
		 */

		private String file;

		public String getFile() {
			return file;
		}

		public void setFile(String file) {
			this.file = file;
		}
	}
}
