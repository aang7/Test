package com.raddapp.radika.raddappv001;

/**
 * Created by Luis on 5/27/16.
 */
public class UserStats {

    private double freeFallTime;
    private double parachuteTotalTime;
    private double jumpAltitude;
    private double parachuteOpenedAltitude;
    private double maxSpeed;
    private double horizontalDistance;

    public UserStats() {
    }

    public UserStats(double freeFallTime, double parachuteTotalTime, double jumpAltitude, double parachuteOpenedAltitude, double maxSpeed, double horizontalDistance) {
        this.freeFallTime = freeFallTime;
        this.parachuteTotalTime = parachuteTotalTime;
        this.jumpAltitude = jumpAltitude;
        this.parachuteOpenedAltitude = parachuteOpenedAltitude;
        this.maxSpeed = maxSpeed;
        this.horizontalDistance = horizontalDistance;
    }

    public double getFreeFallTime() {
        return freeFallTime;
    }

    public void setFreeFallTime(double freeFallTime) {
        this.freeFallTime = freeFallTime;
    }

    public double getParachuteTotalTime() {
        return parachuteTotalTime;
    }

    public void setParachuteTotalTime(double parachuteTotalTime) {
        this.parachuteTotalTime = parachuteTotalTime;
    }

    public double getJumpAltitude() {
        return jumpAltitude;
    }

    public void setJumpAltitude(double jumpAltitude) {
        this.jumpAltitude = jumpAltitude;
    }

    public double getParachuteOpenedAltitude() {
        return parachuteOpenedAltitude;
    }

    public void setParachuteOpenedAltitude(double parachuteOpenedAltitude) {
        this.parachuteOpenedAltitude = parachuteOpenedAltitude;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getHorizontalDistance() {
        return horizontalDistance;
    }

    public void setHorizontalDistance(double horizontalDistance) {
        this.horizontalDistance = horizontalDistance;
    }
}
