package com.vhome.vhome.parents.TrackUtil;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.trace.model.CoordType;
import com.baidu.trace.model.TraceLocation;

import java.util.ArrayList;
import java.util.List;

import com.vhome.vhome.MyApp;
import com.vhome.vhome.parents.model.CurrentLocation;

import static com.vhome.vhome.parents.TrackUtil.BitmapUtil.bmArrowPoint;
import static com.vhome.vhome.parents.TrackUtil.BitmapUtil.bmEnd;
import static com.vhome.vhome.parents.TrackUtil.BitmapUtil.bmStart;


/**
 * 地图工具类
 * Created by zhh .
 */

public class MapUtil {

    private static MapUtil INSTANCE = new MapUtil();

    private MapStatus mapStatus = null;

    private Marker mMoveMarker = null;

    public MapView mapView = null;

    public BaiduMap baiduMap = null;

    public LatLng lastPoint = null;

    private MyLocationData locData;

    /**
     * 路线覆盖物
     */
    public Overlay polylineOverlay = null;

    private float mCurrentZoom = 18.0f;

    private MapUtil() {
    }

    public static MapUtil getInstance() {
        return INSTANCE;
    }

    public void init(MapView view) {
        mapView = view;
        baiduMap = mapView.getMap();//获取的是传进来的
        mapView.showZoomControls(true);
        baiduMap.setMyLocationEnabled(true);
        hideLogo();
        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,true,null));
        baiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {//缩放比例变化监听
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                mCurrentZoom = mapStatus.zoom;
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {

            }
        });
    }

    public void onPause() {
        if (null != mapView) {
            mapView.onPause();
        }
    }

    public void onResume() {
        if (null != mapView) {
            mapView.onResume();
        }
    }

    public void clear() {
        lastPoint = null;
        if (null != mMoveMarker) {
            mMoveMarker.remove();
            mMoveMarker = null;
        }
        if (null != polylineOverlay) {
            polylineOverlay.remove();
            polylineOverlay = null;
        }
        if (null != baiduMap) {
            baiduMap.clear();
            baiduMap = null;
        }
        mapStatus = null;
        if (null != mapView) {
            mapView.onDestroy();
            mapView = null;
        }
    }
    /**
    隐藏logo
        */
    private void hideLogo() {
        View child = mapView.getChildAt(1);
        //判断
        if(null != child &&
                (child instanceof ImageView ||
                        child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }
    }
    /**
     * 将轨迹实时定位点转换为地图坐标
     */
    public static LatLng convertTraceLocation2Map(TraceLocation location) {
        if (null == location) {
            return null;
        }
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        if (Math.abs(latitude - 0.0) < 0.000001 && Math.abs(longitude - 0.0) < 0.000001) {
            return null;
        }
        LatLng currentLatLng = new LatLng(latitude, longitude);
        if (CoordType.wgs84 == location.getCoordType()) {
            LatLng sourceLatLng = currentLatLng;
            CoordinateConverter converter = new CoordinateConverter();
            converter.from(CoordinateConverter.CoordType.GPS);
            converter.coord(sourceLatLng);
            currentLatLng = converter.convert();
        }
        return currentLatLng;
    }
    /**
     * 将轨迹坐标对象转换为地图坐标对象
     */
    public static LatLng convertTrace2Map(com.baidu.trace.model.LatLng traceLatLng) {
        return new LatLng(traceLatLng.latitude, traceLatLng.longitude);
    }

    /**
     * 设置地图中心：使用已有定位信息；
     */
    public void setCenter(float direction) {
        if (!CommonUtil.isZeroPoint(CurrentLocation.latitude, CurrentLocation.longitude)) {
            LatLng currentLatLng = new LatLng(CurrentLocation.latitude, CurrentLocation.longitude);
            updateMapLocation(currentLatLng, direction);
            animateMapStatus(currentLatLng);
            return;
        }
    }
    /**
     * 绘制历史轨迹
     */
    public void drawHistoryTrack(List<LatLng> points, boolean staticLine) {
        baiduMap.clear();
        if (points == null || points.size() == 0) {
            if (null != polylineOverlay) {
                polylineOverlay.remove();
                polylineOverlay = null;
            }
            return;
        }

        if (points.size() == 1) {
            OverlayOptions startOptions = new MarkerOptions().position(points.get(0)).icon(bmStart)
                    .zIndex(9).draggable(true);
            baiduMap.addOverlay(startOptions);
            animateMapStatus(points.get(0));
            return;
        }

        LatLng  startPoint = points.get(0);
        LatLng  endPoint = points.get(points.size() - 1);

        // 添加起点图标
        OverlayOptions startOptions = new MarkerOptions()
                .position(startPoint).icon(BitmapUtil.bmStart)
                .zIndex(9).draggable(true);
        if(staticLine==true) {
            // 添加终点图标
            OverlayOptions endOptions = new MarkerOptions().position(endPoint)
                    .icon(bmEnd).zIndex(9).draggable(true);
            baiduMap.addOverlay(endOptions);
        }

        // 添加路线（轨迹）
        OverlayOptions polylineOptions = new PolylineOptions().width(10)
                .color(Color.BLUE).points(points);

        baiduMap.addOverlay(startOptions);
        // 添加路线（轨迹）
        drawMyRoute(points);

        OverlayOptions markerOptions =
                new MarkerOptions().flat(true).anchor(0.5f, 0.5f).icon(bmArrowPoint)
                        .position(points.get(points.size() - 1));
        mMoveMarker = (Marker) baiduMap.addOverlay(markerOptions);

        animateMapStatus(points);

    }

    //4.画轨迹方法
    protected void drawMyRoute(List<LatLng> points2) {
        //添加纹理图片
        List<BitmapDescriptor> textureList = new ArrayList<BitmapDescriptor>();
        BitmapDescriptor mRedTexture = BitmapDescriptorFactory.fromAsset("road_arrow.png");//箭头图片
        textureList.add(mRedTexture);
        // 添加纹理图片对应的顺序
        List<Integer> textureIndexs = new ArrayList<Integer>();
        for (int i=0;i<points2.size();i++){
            textureIndexs.add(0);
        }
        OverlayOptions options = new PolylineOptions()
                .textureIndex(textureIndexs)//设置分段纹理index数组
                .customTextureList(textureList)//设置线段的纹理，建议纹理资源长宽均为2的n次方
                .dottedLine(true)
                .color(Color.RED)
                .width(15)
                .points(points2);
        polylineOverlay = baiduMap.addOverlay(options);
    }

    public void updateMapLocation(LatLng currentPoint, float direction) {

        if(currentPoint == null){
            return;
        }

        locData = new MyLocationData.Builder().accuracy(0).
                direction(direction).
                latitude(currentPoint.latitude).
                longitude(currentPoint.longitude).build();
        baiduMap.setMyLocationData(locData);

    }

    public void animateMapStatus(List<LatLng> points) {
        if (null == points || points.isEmpty()) {
            return;
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng point : points) {
            builder.include(point);
        }
        MapStatusUpdate msUpdate = MapStatusUpdateFactory.newLatLngBounds(builder.build());
        baiduMap.animateMapStatus(msUpdate);
    }
    public void animateMapStatus(LatLng point) {
        MapStatus.Builder builder = new MapStatus.Builder();
        mapStatus = builder.target(point).build();
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mapStatus));
    }
}
