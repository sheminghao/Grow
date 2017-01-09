package cn.lzh.baby.modle;

import java.util.List;

/**
 */

public class UserBabyList extends BaseInfo {


    /**
     * id : 13
     * birthday : 2017-01-01
     * sex : 2
     * baby_id : 9
     * main_flag : 1
     * nickname : 果果
     * my_flag : 1
     * appellation : 叔叔
     * user_id : 5
     * portrait : http://120.76.234.53:8080/grow/upload123
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private String birthday;
        private String sex;
        private int baby_id;
        private String main_flag;
        private String nickname;
        private String my_flag;
        private String appellation;
        private int user_id;
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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getBaby_id() {
            return baby_id;
        }

        public void setBaby_id(int baby_id) {
            this.baby_id = baby_id;
        }

        public String getMain_flag() {
            return main_flag;
        }

        public void setMain_flag(String main_flag) {
            this.main_flag = main_flag;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMy_flag() {
            return my_flag;
        }

        public void setMy_flag(String my_flag) {
            this.my_flag = my_flag;
        }

        public String getAppellation() {
            return appellation;
        }

        public void setAppellation(String appellation) {
            this.appellation = appellation;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }
    }
}
