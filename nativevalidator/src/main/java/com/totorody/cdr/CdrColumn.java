package com.totorody.cdr;

import com.google.common.base.Function;

public class CdrColumn {

    int length;
    Runnable constraint;

    public CdrColumn(int length, Runnable constraint) {
        this.length = length;
        this.constraint = constraint;
    }
}
