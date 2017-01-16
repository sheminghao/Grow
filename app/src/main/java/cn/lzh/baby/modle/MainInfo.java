package cn.lzh.baby.modle;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/8.
 */

public class MainInfo extends BaseInfo implements Serializable{


    /**
     * message : null
     * datum : {"dynamic":[{"babyNickname":"果果","location":"深圳南山","type":"1","url":"","give_flag":"0","id":6,"content":"123","baby_id":1,"userPortrait":null,"create_date":"2017-01-09 20:40:35","user_id":1,"appellation":null,"give_num":666},{"babyNickname":"3","location":null,"type":"1","url":null,"give_flag":"0","id":5,"content":"5庆祝","baby_id":3,"userPortrait":null,"create_date":"2016-12-03 18:02:08","user_id":2,"appellation":null,"give_num":0},{"babyNickname":"玉宇","location":null,"type":"1","url":null,"give_flag":"0","id":4,"content":"4庆祝","baby_id":2,"userPortrait":null,"create_date":"2016-12-03 18:02:08","user_id":3,"appellation":null,"give_num":0}],"timeAxis":["2016-12","2017-01"],"birthday":"2016-12-03","sex":"2","videoNum":"0","nickname":"果果","babyId":"12","picNum":"0","portrait":"http://120.76.234.53:8080/grow/upload/images/1.jpg"}
     */

    private DatumBean datum;

    public DatumBean getDatum() {
        return datum;
    }

    public void setDatum(DatumBean datum) {
        this.datum = datum;
    }

    public static class DatumBean implements Serializable{
        /**
         * dynamic : [{"babyNickname":"果果","location":"深圳南山","type":"1","url":"","give_flag":"0","id":6,"content":"123","baby_id":1,"userPortrait":null,"create_date":"2017-01-09 20:40:35","user_id":1,"appellation":null,"give_num":666},{"babyNickname":"3","location":null,"type":"1","url":null,"give_flag":"0","id":5,"content":"5庆祝","baby_id":3,"userPortrait":null,"create_date":"2016-12-03 18:02:08","user_id":2,"appellation":null,"give_num":0},{"babyNickname":"玉宇","location":null,"type":"1","url":null,"give_flag":"0","id":4,"content":"4庆祝","baby_id":2,"userPortrait":null,"create_date":"2016-12-03 18:02:08","user_id":3,"appellation":null,"give_num":0}]
         * timeAxis : ["2016-12","2017-01"]
         * birthday : 2016-12-03
         * sex : 2
         * videoNum : 0
         * nickname : 果果
         * babyId : 12
         * picNum : 0
         * portrait : http://120.76.234.53:8080/grow/upload/images/1.jpg
         */

        private String birthday;
        private String sex;
        private String videoNum;
        private String secretKey;
        private String nickname;
        private String babyId;
        private String picNum;
        private String portrait;
        private List<DynamicBean> dynamic;
        private List<String> timeAxis;

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

        public String getVideoNum() {
            return videoNum;
        }

        public void setVideoNum(String videoNum) {
            this.videoNum = videoNum;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getBabyId() {
            return babyId;
        }

        public void setBabyId(String babyId) {
            this.babyId = babyId;
        }

        public String getPicNum() {
            return picNum;
        }

        public void setPicNum(String picNum) {
            this.picNum = picNum;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public List<DynamicBean> getDynamic() {
            return dynamic;
        }

        public void setDynamic(List<DynamicBean> dynamic) {
            this.dynamic = dynamic;
        }

        public List<String> getTimeAxis() {
            return timeAxis;
        }

        public void setTimeAxis(List<String> timeAxis) {
            this.timeAxis = timeAxis;
        }

        public static class DynamicBean implements Serializable{
            /**
             * babyNickname : 果果
             * location : 深圳南山
             * type : 1
             * url :
             * give_flag : 0
             * id : 6
             * content : 123
             * baby_id : 1
             * userPortrait : null
             * create_date : 2017-01-09 20:40:35
             * user_id : 1
             * appellation : null
             * give_num : 666
             */

            private String babyNickname;
            private String location;
            private String type;
            private String url;
            private String give_flag;
            private int id;
            private String content;
            private int baby_id;
            private Object userPortrait;
            private String create_date;
            private int user_id;
            private Object appellation;
            private int give_num;

            public String getBabyNickname() {
                return babyNickname;
            }

            public void setBabyNickname(String babyNickname) {
                this.babyNickname = babyNickname;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getGive_flag() {
                return give_flag;
            }

            public void setGive_flag(String give_flag) {
                this.give_flag = give_flag;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getBaby_id() {
                return baby_id;
            }

            public void setBaby_id(int baby_id) {
                this.baby_id = baby_id;
            }

            public Object getUserPortrait() {
                return userPortrait;
            }

            public void setUserPortrait(Object userPortrait) {
                this.userPortrait = userPortrait;
            }

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public Object getAppellation() {
                return appellation;
            }

            public void setAppellation(Object appellation) {
                this.appellation = appellation;
            }

            public int getGive_num() {
                return give_num;
            }

            public void setGive_num(int give_num) {
                this.give_num = give_num;
            }
        }
    }
}
