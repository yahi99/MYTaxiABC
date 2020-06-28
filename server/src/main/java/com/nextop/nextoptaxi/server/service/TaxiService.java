package com.nextop.nextoptaxi.server.service;


import com.nextop.nextoptaxi.server.model.TaxiEntity;
import com.nextop.nextoptaxi.server.repository.SchoolRepository;
import com.nextop.nextoptaxi.server.repository.TaxiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.DoubleToLongFunction;

@Service
public class TaxiService {
    private TaxiRepository taxiRepository;
    private static int KM_RADIUS = 6371;
    private static int MAX_SPEED_PER_MIN = 1;
    private static int DEFAULT_COST = 3800;
    private static int EXTRA_COST = 100;
    private static int DEFAULT_COST_DISTANCE = 2;

    private double degreeToRadian(double degree) {
        return degree * Math.PI / 180;
    }

    public double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        double distanceLatitude = degreeToRadian(latitude1 - latitude2);
        double distanceLongitude = degreeToRadian(longitude1 - longitude2);

        double value = Math.pow(Math.sin(distanceLatitude / 2), 2) +
                Math.pow(Math.sin(distanceLongitude / 2), 2) *
                        Math.cos((degreeToRadian(latitude1)) * Math.cos(degreeToRadian(latitude2)));
        double c = 2 * Math.atan2(Math.sqrt(value), Math.sqrt(1 - value));
        return KM_RADIUS * c;
    }
    public void addTaxiPosition(TaxiEntity taxi){
        this.taxiRepository.save(taxi);
    }
    @Autowired
    public TaxiService(TaxiRepository taxiRepository){
        this.taxiRepository = taxiRepository;
    }
    public ArrayList<TaxiEntity> findTaxiList(){
        ArrayList<TaxiEntity> taxiList = new ArrayList<>(taxiRepository.findAll());
        return taxiList;
    }
    public void addTaxi(TaxiEntity taxi){
        taxiRepository.save(taxi);
    }

    public TaxiEntity getTaxi(long id){
        Optional<TaxiEntity> taxi=taxiRepository.findById(id);
        if(taxi.isEmpty()){
            return null;
        }
        return taxi.get();
    }
    public int calculateEstimateTime(double latitude1,double longitude1,double latitude2,double longitude2){
        double distance = this.calculateDistance(latitude1,longitude1,latitude2,longitude2);
        double movePerMinute = distance/this.MAX_SPEED_PER_MIN;
        return (int) Math.ceil(movePerMinute);
    }
    public int calculateEstimateCost(double latitude1,double longitude1, double latitude2,double longitude2){
        double distance = this.calculateDistance(latitude1,longitude1,latitude2,longitude2);
        if(distance < DEFAULT_COST_DISTANCE){
            return DEFAULT_COST;
        }
        int extraDistance = (int) Math.ceil((distance - DEFAULT_COST_DISTANCE)/0.1);
        return DEFAULT_COST+extraDistance*EXTRA_COST;
    }

}