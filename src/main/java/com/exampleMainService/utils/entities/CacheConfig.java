package com.exampleMainService.utils.entities;

import java.util.concurrent.TimeUnit;

public class CacheConfig {
    private int duration;
    private TimeUnit unit;
    private int maxSize;
    private boolean isNullsAllowed;

    public CacheConfig(){
        duration = 30;
        unit = TimeUnit.SECONDS;
        maxSize = 100;
        isNullsAllowed = false;
    }

    public CacheConfig(int duration, TimeUnit unit, int maxSize, boolean isNullsAllowed) {
        this.duration = duration;
        this.unit = unit;
        this.maxSize = maxSize;
        this.isNullsAllowed = isNullsAllowed;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isNullsAllowed() {
        return isNullsAllowed;
    }

    public void setNullsAllowed(boolean nullsAllowed) {
        isNullsAllowed = nullsAllowed;
    }
}
