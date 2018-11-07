package com.totorody.cdr;

public class TimeElapser {

    private long startTime;

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public void stop() {
        this.startTime = -1;
    }

    public long elapse() {
        return System.currentTimeMillis() - this.startTime;
    }
}
