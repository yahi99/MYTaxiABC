package com.ABC.project.mytaxiabc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ABC.project.mytaxiabc.location.MapEventListener;
import com.ABC.project.mytaxiabc.location.MapLocationListener;
import com.ABC.project.mytaxiabc.models.AddressModel;
import com.ABC.project.mytaxiabc.models.DisplayItem;
import com.ABC.project.mytaxiabc.models.Documents;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_LOCATION = 10001;
    private LocationManager locationManager;
    private MapLocationListener locationListener;
    private MapEventListener eventListener;
    private MapPoint mapPoint;

    public MainActivity() {
        this.eventListener = new MapEventListener(makeHandler());
        this.locationListener = new MapLocationListener(makeHandler());
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

        Location loc = this.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        MapPOIItem marker = new MapPOIItem();

        mapView.setMapViewEventListener(eventListener);

        if(loc != null) {
            mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(loc.getLatitude(), loc.getLongitude()), 0, true);

            //맵 Center 좌표 설정
            marker.setItemName((int) loc.getLatitude() + ", " + (int) loc.getLongitude());
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(loc.getLatitude(), loc.getLongitude()));
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 마커 모양.
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
            marker.setTag(0);
            marker.setDraggable(true);
            mapView.addPOIItem(marker);

            mapViewContainer.addView(mapView);
        }
    }

    public Handler makeHandler() {
        return new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                DisplayItem displayItem = (DisplayItem) msg.obj;
                AddressModel addressModel = displayItem.addressModel;
                Documents documents = addressModel.documents.get(0);

                TextView address = (TextView) findViewById(R.id.address);
                if(addressModel.documents.size() > 0) {
                    if(documents.road_address != null) {
                        if(documents.road_address.building_name != null) address.setText(documents.road_address.building_name);
                        else address.setText(documents.road_address.address_name);
                    }
                    else{
                        address.setText(documents.address.address_name);
                    }
                }

                if(documents.address.address_name != null) address.setText(documents.address.address_name);

                TextView latitude = (TextView) findViewById(R.id.latitude);
                TextView longitude = (TextView) findViewById(R.id.longitude);

                latitude.setText(displayItem.latitude.toString());
                longitude.setText(displayItem.longitude.toString());
            }
        };
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
