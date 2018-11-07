package com.totorody.cdr;

import com.google.common.collect.Iterables;

import java.util.Map;

public class Validator {

    private Map<String, CdrColumn<?>> columnMap;

    public Validator(Map<String, CdrColumn<?>> columnMap) {
        this.columnMap = columnMap;
    }

    public Iterable<Cdr> findErrorCdrs(Iterable<Cdr> cdrs) {
        Iterable<Cdr> validatedCdrs = Iterables.transform(cdrs, cdr -> cdr.validate(columnMap));
        return Iterables.filter(validatedCdrs, cdr -> cdr.isInvalid);
    }
}
