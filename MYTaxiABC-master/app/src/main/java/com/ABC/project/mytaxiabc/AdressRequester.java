package com.ABC.project.mytaxiabc;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ABC.project.mytaxiabc.models.AddressModel;
import com.ABC.project.mytaxiabc.models.DisplayItem;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

public class AdressRequester implements Runnable {
    private Handler handler;
    private Double lat;
    private Double lng;

    public AdressRequester(Double lat, Double lng, Handler handler) {
        this.lat = lat;
        this.lng = lng;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory(
                    new HttpRequestInitializer() {
                        @Override
                        public void initialize(HttpRequest request) {
                            request.setParser(new JsonObjectParser(new JacksonFactory()));
                        }
                    });
            String urlString = String.format("https://dapi.kakao.com/v2/local/geo/coord2address.json?x=%s&y=%s", this.lng.toString(), this.lat.toString());
            GenericUrl url = new GenericUrl(urlString);
            HttpHeaders headers = new HttpHeaders();
            headers.setAuthorization("KakaoAK 211b696413a26e4b8edd7dc849095e08");
            HttpRequest request = requestFactory.buildGetRequest(url).setHeaders(headers);

            AddressModel addressModel = request.execute().parseAs(AddressModel.class);
            DisplayItem displayItem = new DisplayItem(lat, lng);
            displayItem.addressModel = addressModel;

            Message msg = this.handler.obtainMessage();
            msg.obj = displayItem;
            this.handler.sendMessage(msg);
        } catch (Exception ex) {
            Log.e("HTTP_REQUEST", ex.toString());
        }
    }
}
