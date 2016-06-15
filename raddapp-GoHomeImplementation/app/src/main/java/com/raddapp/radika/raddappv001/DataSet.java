package com.raddapp.radika.raddappv001;

/**
 * Created by edd on 2/18/2015.
 */
public class DataSet {
    private double current;
    private double last;
    private double change;    // per second
    private long   timestamp = System.currentTimeMillis();

    public void set(double value) {
        long now       = System.currentTimeMillis();
        this.last      = this.current;
        this.current   = value;
        this.change    = (this.current - this.last) / (now - this.timestamp);
        this.timestamp = now;
    }

    public double getCurrent() {
        return current;
    }
    public double getChange() {
        return change;
    }
}
