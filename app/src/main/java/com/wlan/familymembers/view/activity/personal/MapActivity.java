package com.wlan.familymembers.view.activity.personal;

import android.location.Location;
import android.os.Bundle;

import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.personal.MapModel;
import com.wlan.familymembers.presenter.personal.MapPresenter;
import butterknife.BindView;

/**
 * 类作用：
 * Created by Administrator on 2018/9/29.
 */

public class MapActivity extends BaseActivity<MapModel, MapPresenter> implements Contract.MapContract.View,AMap.OnMyLocationChangeListener{
    @BindView(R.id.mv_content)
    MapView mvContent;
    AMap aMap;
    @Override
    public int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    public void initView() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。


        //以下三种模式从5.1.0版本开始提供
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
        myLocationStyle.interval(60000);




    }


    @Override
    public void initView(Bundle savedInstanceState) {
        mvContent.onCreate(savedInstanceState);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mvContent.getMap();
        }

    }

    @Override
    public void onEvent() {

    }

    @Override
    public BaseView getBaseView() {
        return this;
    }

    @Override
    public void showErrorWithStatus(String msg) {

    }
    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mvContent.onDestroy();
        super.onDestroy();

    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mvContent.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mvContent.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mvContent.onSaveInstanceState(outState);
    }

    @Override
    public void onMyLocationChange(Location location) {

    }
}
