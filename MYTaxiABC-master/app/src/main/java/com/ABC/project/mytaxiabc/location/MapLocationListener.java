package com.ABC.project.mytaxiabc.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.ABC.project.mytaxiabc.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MapLocationListener extends AppCompatActivity implements LocationListener {

    private static final int PERMISSION_REQUEST_LOCATION = 10001;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private MapView mapView;

    public MapLocationListener() {

    }

    @Override
    public void onLocationChanged(Location location) {
        this.mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(location.getLatitude(), location.getLongitude()), 0, true);

        //MapPOIItem marker = this.mapView.findPOIItemByTag(0);
        //marker.setMapPoint(MapPoint.mapPointWithGeoCoord(location.getLatitude(),location.getLongitude()));
        MapPOIItem marker = new MapPOIItem();
        this.mapView.removeAllPOIItems();

        marker.setItemName((int) location.getLatitude() + ", " + (int) location.getLongitude());
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(location.getLatitude(), location.getLongitude()));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        marker.setDraggable(true);
        mapView.addPOIItem(marker);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void setMapView(MapView mapView) {
        this.mapView = mapView;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 승인이 된 경우 다시 그리기
                    recreate();
                } else {
                    // 권한 승인이 안 된 경우 종료
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
