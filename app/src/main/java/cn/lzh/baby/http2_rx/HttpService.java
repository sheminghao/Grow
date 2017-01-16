package cn.lzh.baby.http2_rx;

import java.util.List;

import cn.lzh.baby.http2_rx.Api.UrlConfig;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * service统一接口数据
 * Created by WZG on 2016/7/16.
 */
public interface HttpService {

    @POST("AppFiftyToneGraph/videoLink")
    Observable<String> getAllVedioBy(@Body boolean once_no);

    /*断点续传下载接口*/
    @Streaming/*大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String start, @Url String url);

    /**
     *上传文件 上传一个文件
     * */
    @Multipart
    @POST("AppYuFaKu/uploadHeadImg")
    Observable<String> uploadImage(@Part("uid") RequestBody uid, @Part("auth_key") RequestBody auth_key,
                                   @Part MultipartBody.Part file);
    /**
     * 登录接口
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<String> login(@Field("loginName") String loginName, @Field("password") String password);

    /**
     * 注册接口
     */
    @FormUrlEncoded
    @POST(UrlConfig.REGISTER)
    Observable<String> register(@Field("loginName") String loginName, @Field("password") String password);

    /**
     * 首页接口
     */
    @FormUrlEncoded
    @POST(UrlConfig.MAININFO)
    Observable<String> mainInfo(@Field("token") String token);

    /**
     * 添加私密日记
     */
    @FormUrlEncoded
    @POST(UrlConfig.ADD_DIARY)
    Observable<String> addDiary(@Field("content") String content,
                                @Field("location") String location,
                                @Field("spend") String spend,
                                @Field("token") String token);

    /**
     * 宝宝列表
     */
    @FormUrlEncoded
    @POST(UrlConfig.USER_BABY_LIST)
    Observable<String> userBabylist(@Field("token") String token);

    /**
     * 通过 List<MultipartBody.Part> 传入多个part实现多文件上传
     * @param parts 每个part代表一个 使用@Multipart注解方法，并用@Part注解方法参数，类型是List< okhttp3.MultipartBody.Part>
     * @return 状态信息
     */
    @Multipart
    @POST("file/upload")
    Observable<String> uploadImageWithParts(@Part() List<MultipartBody.Part> parts);

    /**
     * 通过 MultipartBody和@body作为参数来上传
     * @param multipartBody MultipartBody包含多个Part
     * @return 状态信息 不使用@Multipart注解方法，直接使用@Body注解方法参数，类型是okhttp3.MultipartBody
     */
    @POST("file/upload")
    Observable<String> uploadImageWithRequestBody(@Body MultipartBody multipartBody);

    /**
     * 宝宝接口  <br>
     * User: shetj(375105540@qq.com) <br>
     * create at 2016/12/11 13:43<br>
     */
    @GET("baby/findBaby")
    Observable<String> findBaby(@Query("id") String id);

    /**
     * 宝宝注册接口
     */
    @FormUrlEncoded
    @POST("baby/add")
    Observable<String> add(@Field("nickname") String nickname, @Field("sex") String sex,
                           @Field("birthday") String birthday, @Field("portrait") String portrait,
                           @Field("appellation") String appellation, @Field("token") String token);

    /**
     * 上传文件接口
     */
    @Multipart
    @POST("file/upload")
    Observable<String> upload(@Part MultipartBody.Part file);
	/**
   * 首页接口
   */
    @FormUrlEncoded
    @POST("baby/index")
    Observable<String> index(@Field("appellation") String appellation, @Field("babyId") int babyId, @Field("userId") int userId);

	/**
	 * 获取首页动态的接口
     */
    @POST("dynamic/list")
    Observable<String> dynamic(@Query("pageNum") String pageNum);

    /**
     * 设为主宝宝
     */
    @FormUrlEncoded
    @POST("baby/setMainBaby")
    Observable<String> setMainBaby(@Field("babyId") String babyId, @Field("token") String token);

    /**
     * 添加关注宝宝
     */
    @FormUrlEncoded
    @POST("/baby/follow")
    Observable<String> addAttention(@Field("code") String code, @Field("appellation") String appellation,
                                    @Field("mainFlag") String mainFlag, @Field("token") String token);

	/**
	 * 添加动态接口
   参数	是否必填	说明	值
   babyId	√	宝宝id
   userId	√	用户id
   content	√	内容
   type	√	内容1、图片；2、视频；3、私密日记
   location	√	定位地址
   url	√	图片url地址，多个用,隔开。如果是视频，则第一个是视频地址，第二个是预览图
   */
    @FormUrlEncoded
    @POST("dynamic/add")
    Observable<String> addDynamic(@Field("babyId") String babyId, @Field("userId") String userId,
                                  @Field("content") String content, @Field("type") String type,
                                  @Field("location") String location, @Field("url") String url);
}
