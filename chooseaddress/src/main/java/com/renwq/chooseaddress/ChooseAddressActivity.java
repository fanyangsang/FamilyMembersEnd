package com.renwq.chooseaddress;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.interfaces.MapCameraMessage;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ChooseAddressActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private MapView mapView;
    private TextView tvLocation;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private AMapLocationClient mLocationClient;
    private LatLng latLng;
    private LocationSource.OnLocationChangedListener mListener = new LocationSource.OnLocationChangedListener() {
        @Override
        public void onLocationChanged(Location location) {

        }
    };
    private AMapLocationClientOption mLocationOption;

    private AMapLocationListener locationListener = new AMapLocationListener() {
        //AMapLocationListener接口实现的方法 用于接收异步返回的定位结果，参数是AMapLocation类型。
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                    tvLocation.setText("当前选择位置：" + amapLocation.getAddress());
                    latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                    Log.e("onLocationChanged", amapLocation.getAddress());
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };
    private GeocodeSearch geocoderSearch;
    private View tvOk;
    private String district="";
    private String city="";
    private String province="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);
        tvLocation = findViewById(R.id.tv_location);
        tvOk = findViewById(R.id.tv_ok);
        requestPermissions();
        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        aMap = mapView.getMap();
        if (Build.VERSION.SDK_INT >= 21) {
            myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
            aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
            aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
            //设置我的位置改变监听器
            aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
                @Override//位置变更时
                public void onMyLocationChange(Location location) {
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                    queryAddressInfo(latLng);
                }
            });
            aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        } else {
            aMap.setLocationSource(new LocationSource() {
                @Override
                public void activate(OnLocationChangedListener onLocationChangedListener) {
                    mListener = onLocationChangedListener;
                    if (mLocationClient == null) {
                        //初始化定位
                        mLocationClient = new AMapLocationClient(ChooseAddressActivity.this);
                        //初始化定位参数
                        mLocationOption = new AMapLocationClientOption();
                        //设置定位回调监听
                        mLocationClient.setLocationListener(locationListener);
                        //设置为高精度定位模式
                        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                        //设置定位参数
                        mLocationClient.setLocationOption(mLocationOption);
                        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
                        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
                        // 在定位结束后，在合适的生命周期调用onDestroy()方法
                        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
                        mLocationClient.startLocation();//启动定位
                    }
                }

                @Override
                public void deactivate() {
                    mListener = null;
                    if (mLocationClient != null) {
                        mLocationClient.stopLocation();
                        mLocationClient.onDestroy();
                    }
                    mLocationClient = null;
                }
            });
        }
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                latLng = cameraPosition.target;
                queryAddressInfo(latLng);
            }
        });
        geocoderSearch = new GeocodeSearch(this);


        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(province)||TextUtils.isEmpty(city)||TextUtils.isEmpty(district)){
                    Toast.makeText(getApplicationContext(),"定位中",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("latlng", latLng);
                intent.putExtra("province", province);
                intent.putExtra("city",city);
                intent.putExtra("district", district);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        findViewById(R.id.iv_back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void requestPermissions() {
        List<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_WIFI_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CHANGE_WIFI_STATE);
        }
        if (permissions.size() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions.toArray(new String[0]), REQUEST_CODE);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }


    private MyHandler myHandler = new MyHandler(this);

    private class MyHandler extends Handler {
        private WeakReference<ChooseAddressActivity> weakReference;

        public MyHandler(ChooseAddressActivity chooseAddressActivity) {
            weakReference = new WeakReference<>(chooseAddressActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String text = (String) msg.obj;
                    weakReference.get().updateText(text);
                    break;
            }
        }
    }

    private void updateText(String text) {
        tvLocation.setText("当前选择的地点：" + text);
    }

    private void queryAddressInfo(LatLng latLng) {
        if (geocoderSearch != null) {
            final RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latLng.latitude, latLng.longitude), 100, GeocodeSearch.AMAP);
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        RegeocodeAddress address = geocoderSearch.getFromLocation(query);
                        Message message = Message.obtain();
                        message.what = 1;
                        message.obj = address.getFormatAddress();
                        myHandler.sendMessage(message);
                        province = address.getProvince();
                        city = address.getCity();
                        district = address.getDistrict();
                    } catch (AMapException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }
    }
}

