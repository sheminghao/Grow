package cn.lzh.baby.http2_rx.Api;

/**
 * Created by Administrator on 2016/12/28.
 */

public class UrlConfig {

    /*基础url*/
    public static final String URL="http://120.76.234.53:1111/grow/api/";

    /*文件url*/
    public static final String FILE_URL="http://120.76.234.53:1111/grow/upload";
//    public static final String FILE_URL="http://192.168.1.102:8090/grow/upload";

    /**
     * 登录
     */
    public static final String LOGIN = "user/login";
    /**
     * 注册
     */
    public static final String REGISTER = "user/register";

    /**
     * 首页
     */
    public static final String MAININFO = "main/mainInfo";

    /**
     * 获取首页时光轴动态接口
     */
    public static final String TIMELIST = "main/timeList";

    /**
     * 添加私密日记
     */
    public static final String ADD_DIARY = "diary/add";

    /**
     * 宝宝列表接口
     */
    public static final String USER_BABY_LIST  = "baby/userBabyList";

}
