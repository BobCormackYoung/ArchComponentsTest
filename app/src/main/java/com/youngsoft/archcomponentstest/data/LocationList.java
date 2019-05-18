package com.youngsoft.archcomponentstest.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "LocationList_Table")
public class LocationList {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String locationName;
    private int climbCount;
    private double gpsLongitude;
    private double gpsLatitude;
    private boolean isGps;

    public LocationList(String locationName, int climbCount, double gpsLongitude, double gpsLatitude, boolean isGps) {
        this.locationName = locationName;
        this.climbCount = climbCount;
        this.gpsLongitude = gpsLongitude;
        this.gpsLatitude = gpsLatitude;
        this.isGps = isGps;
    }

    public static LocationList[] populateLocationListData() {
        return new LocationList[]{
                new LocationList("Frankenjura", 1, 54.3429525771945, -1.77630758914893, true),
                new LocationList("Flatanger", 1, 53.3429525771945, -1.77630758914893, true),
                new LocationList("Arco", 0, 52.3429525771945, -2.77630758914893, true),
                new LocationList("Domusnovas", 1, 0, 0, false),
                new LocationList("Villanueva del Rosario", 1, 0, 0, false),
                new LocationList("Oliana", 1, 0, 0, false),
                new LocationList("Stanage", 1, 49.4655084457863, 16.2469161159163, true),
                new LocationList("Siurana", 1, 50.4655084457863, 16.2469161159163, true),
                new LocationList("La Cova de l'Ocell", 1, 51.4655084457863, 16.2469161159163, true),
                new LocationList("Margalef", 1, 52.4655084457863, 16.2469161159163, true),
        };
    }

    public int getId() {
        return id;
    }

    public String getLocationName() {
        return locationName;
    }

    public int getClimbCount() {
        return climbCount;
    }

    public double getGpsLongitude() {
        return gpsLongitude;
    }

    public double getGpsLatitude() {
        return gpsLatitude;
    }

    public boolean isGps() {
        return isGps;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLocationName(String input) {
        locationName = input;
    }

    public void setClimbCount(int input) {
        climbCount = input;
    }

    public void setGpsLongitude(double input) {
        gpsLongitude = input;
    }

    public void setGpsLatitude(double input) {
        gpsLatitude = input;
    }

    public void setIsGps(boolean input) {
        isGps = input;
    }

}
