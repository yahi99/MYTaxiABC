package com.ABC.project.mytaxiabc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_LOCATION = 10001;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude = 37.5571992;
    private double longitude = 126.970536;

    public MainActivity() {
        this.locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 안드로이드에서 권한 확인이 의무화 되어서 작성된 코드! 개념만 이해
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
                return;
            }
        }

        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0.01f, this.locationListener);

        Location loc = this.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);

        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(loc.getLatitude(), loc.getLongitude()), 1, true);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(0);
        if (loc != null) marker.setMapPoint(MapPoint.mapPointWithGeoCoord(loc.getLatitude(), loc.getLongitude()));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker);
        mapViewContainer.addView(mapView);
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
