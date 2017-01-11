package cn.lzh.baby.ui.location;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.UiSettings;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.cundong.recyclerview.EndlessRecyclerOnScrollListener;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.github.jdsjlzx.view.LoadingFooter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lzh.baby.R;
import cn.lzh.baby.adapter.NearbyAdapter;
import cn.lzh.baby.base.BaseActivity;
import cn.lzh.baby.utils.view.RecyclerViewStateUtils;

public class MapActivity extends BaseActivity implements LocationSource, AMapLocationListener, PoiSearch.OnPoiSearchListener {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.loc_recyclerview)
    RecyclerView recyclerView;
    MapView mapView;

    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private NearbyAdapter nearbyAdapter;

    AMap aMap;
    UiSettings uiSettings;
    PoiSearch.Query query;

    //是否是第一次定位
    private boolean firstLoc = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarState(R.color.themdColor);
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        ButterKnife.bind(this);
        tvRight.setVisibility(View.GONE);
        tvTitle.setText("地图");
        initMap();
    }

    @OnClick({R.id.iv_return, R.id.tv_title, R.id.toolbar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.tv_title:
                break;
        }
    }

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    public void initMap() {
        nearbyAdapter = new NearbyAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(nearbyAdapter);
        recyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        recyclerView.addOnScrollListener(mOnScrollListener);

        aMap = mapView.getMap();
        uiSettings = aMap.getUiSettings();
        uiSettings.setScaleControlsEnabled(true); //比例尺是否可用
        aMap.setLocationSource(this);// 设置定位监听
        aMap.setMyLocationEnabled(true);// 是否可触发定位并显示定位层
        uiSettings.setMyLocationButtonEnabled(true); // 是否显示默认的定位按钮
        MapsInitializer.loadWorldGridMap(true);

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //启动定位
        mLocationClient.startLocation();
        //异步获取定位结果
        AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //解析定位结果
                    }
                }
            }
        };
    }

    private int start = 0;
    private int limit = 20;
    private void initPoi(AMapLocation aMapLocation){
        String ctgr = "汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|" +
                "住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施";
        query = new PoiSearch.Query("", ctgr);
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，
        //POI搜索类型共分为以下20种：汽车服务|汽车销售|
        //汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
        //住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
        //金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(limit);// 设置每页最多返回多少条poiitem
        query.setPageNum(start);//设置查询页码
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(aMapLocation.getLatitude(),
                aMapLocation.getLongitude()), 500, true));//设置周边搜索的中心点以及半径
        poiSearch.searchPOIAsyn();

    }

    private AMapLocation myLocation;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private PoiSearch poiSearch;
    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                myLocation = aMapLocation;
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                if (firstLoc) {
                    initPoi(aMapLocation);
                    firstLoc = false;
                }
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode()+ ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }
    /**
     * 激活定位
     */

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 激活定位停止
     */
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int rCode) {
        Log.i("TAG", "=====poiCode"+rCode);
        Log.i("TAG", "=====start"+start);
        if (rCode == 1000) {
            RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
            ArrayList<PoiItem> pois = poiResult.getPois();
            if(pois.size() < limit){
                isCanLoad = false;
            }
            nearbyAdapter.addData(pois);
            Log.i("TAG", "=====pois" + pois.size());
            for (int j = 0; j < pois.size(); j++) {
                PoiItem poi = pois.get(j);
                Log.i("TAG", "=====poi" + poi.getProvinceName() + poi.getCityName() + poi.getAdName() + poi.getSnippet()
                        + "=" + poi.getTitle());
            }
        }else{
            RecyclerViewStateUtils.setFooterViewState(MapActivity.this, recyclerView, limit, LoadingFooter.State.NetWorkError, mFooterClick);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    private boolean isCanLoad=true;
    /**
     * 自动加载
     */
    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {


        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(recyclerView);
            if(state == LoadingFooter.State.Loading) {
                return;
            }
            if (isCanLoad) {
                RecyclerViewStateUtils.setFooterViewState(MapActivity.this, recyclerView, limit, LoadingFooter.State.Loading, null);
                if (null != myLocation) {
                    start++;
                    initPoi(myLocation);
                }
            } else {
                //the end
                RecyclerViewStateUtils.setFooterViewState(MapActivity.this, recyclerView, limit, LoadingFooter.State.TheEnd, null);
            }
        }
    };

    /**
     * 加载失败后的点击事件
     */
    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(MapActivity.this, recyclerView, limit, LoadingFooter.State.Loading, null);
            initPoi(myLocation);
        }
    };
}
