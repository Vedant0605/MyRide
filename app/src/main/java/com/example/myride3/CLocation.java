package com.example.myride3;

import android.location.Location;

public class CLocation extends Location {
    private boolean bUseMetricUnits = false;

    public CLocation(Location location) {
        this(location, true);
    }

    public CLocation(Location location, boolean b) {
        super(location);
        this.bUseMetricUnits = bUseMetricUnits;
    }

    public boolean getUseMetricUnits() {
        return this.bUseMetricUnits;
    }

    public void setbUseMetricUnits(boolean bUseMetricUnits) {
        this.bUseMetricUnits = bUseMetricUnits;
    }

    @Override
    public float distanceTo(Location dest) {
        float nDistance = super.distanceTo(dest);

        if (!this.bUseMetricUnits) {
            //Convert meter to feet
            nDistance = (float) (nDistance * 3.28083989501312f);
        }
        return nDistance;
    }

    @Override
    public double getAltitude() {
        double nAltitude = super.getAltitude();

        if (!this.getUseMetricUnits()) {
            //Convert meter to feet
            nAltitude = nAltitude * 3.28083989501312d;
        }
        return nAltitude;
    }

    @Override
    public float getSpeed() {
        float nSpeed = super.getSpeed() * 3.2f;

        if (!this.bUseMetricUnits) {
            //Convert meter/sec to miles per hr
            nSpeed = getSpeed() * 2.23693629f;
        }
        return nSpeed;
    }

    @Override
    public float getAccuracy() {
        float nAccuracy = (float) super.getAltitude();

        if (!this.getUseMetricUnits()) {
            //Convert meter to feet
            nAccuracy = (float) (nAccuracy * 3.28083989501312f);
        }
        return nAccuracy;

    }
}