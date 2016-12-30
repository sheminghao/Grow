package cn.lzh.baby.modle;

/**
 * Created by Administrator on 2016/12/28.
 */

public class LoginInfo {


    /**
     * message : null
     * token : 3138598c00aa497d9eda5494c6cf34dc4vcN3d
     * code : 1
     * info : {"id":5,"birthday":"","last_login_time":"2016-12-28 21:22:52","login_name":"shemh","portrait":"http://120.76.234.53:8080/grow/upload"}
     */

    private String message;
    private String token;
    private int code;
    private InfoBean info;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * id : 5
         * birthday :
         * last_login_time : 2016-12-28 21:22:52
         * login_name : shemh
         * portrait : http://120.76.234.53:8080/grow/upload
         */

        private int id;
        private String birthday;
        private String last_login_time;
        private String login_name;
        private String portrait;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getLogin_name() {
            return login_name;
        }

        public void setLogin_name(String login_name) {
            this.login_name = login_name;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }
    }
}
