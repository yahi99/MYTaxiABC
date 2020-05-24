package com.ABC.project.mytaxiabc.location;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;

import com.ABC.project.mytaxiabc.AdressRequester;
import com.ABC.project.mytaxiabc.models.Documents;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;


public class MapEventListener implements MapView.MapViewEventListener {

    private static final int PERMISSION_REQUEST_LOCATION = 10001;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private MapView mapView;

    private Handler handler;
    private Documents documents;
    private Double lat;
    private Double lng;

    public MapEventListener(Handler handler) {
        this.handler = handler;
    }


    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapCenterPoint) {
        MapPOIItem marker = new MapPOIItem();
        mapView.removeAllPOIItems();
        marker.setItemName((int)mapCenterPoint.getMapPointGeoCoord().latitude + ", " + (int) mapCenterPoint.getMapPointGeoCoord().longitude);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(mapCenterPoint.getMapPointGeoCoord().latitude, mapCenterPoint.getMapPointGeoCoord().longitude));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        marker.setDraggable(true);
        marker.setTag(0);
        mapView.addPOIItem(marker);

        lat = mapCenterPoint.getMapPointGeoCoord().latitude;
        lng = mapCenterPoint.getMapPointGeoCoord().longitude;
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapCenterPoint) {
        Thread request = new Thread(new AdressRequester(mapCenterPoint.getMapPointGeoCoord().latitude, mapCenterPoint.getMapPointGeoCoord().longitude, handler));
        request.start();

        lat = mapCenterPoint.getMapPointGeoCoord().latitude;
        lng = mapCenterPoint.getMapPointGeoCoord().longitude;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
}
