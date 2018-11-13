package com.totorody.cdr;

import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Validator {

    private Map<String, CdrColumn<?>> columnMap;

    public Validator(Map<String, CdrColumn<?>> columnMap) {
        this.columnMap = columnMap;
    }

    public Iterable<Cdr> findErrorCdrsParallelV2(Iterable<Cdr> cdrs) {
        return Iterables.filter(cdrs, cdr -> cdr.isInvalid);
    }

    @Deprecated
    public Iterable<Cdr> findErrorCdrsParallel(Iterable<Cdr> cdrs) {
        // Iterable<Cdr> validatedCdrs = Iterables.transform(cdrs, cdr -> cdr.validate(columnMap));
        return Iterables.filter(cdrs, cdr -> {
            Cdr validatedCdr = Cdr.createCdrWithValidate(cdr.getCdrMap(), columnMap);
            return validatedCdr.isInvalid;
        });
    }

    @Deprecated
    public Iterable<Cdr> findErrorCdrs(Iterable<Cdr> cdrs) {
        List<Cdr> errorCdrs = new ArrayList<>();
        for (Cdr cdr : cdrs) {
            Cdr candidate = cdr.validate(columnMap);
            if (candidate.isInvalid) {
                errorCdrs.add(candidate);
            }
        }
        return errorCdrs;
    }
}
