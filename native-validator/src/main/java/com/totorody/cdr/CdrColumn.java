package com.totorody.cdr;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CdrColumn<T> {

    public static CdrColumn generateIntColumn(final int length) {
        final int maximum = Integer.valueOf(String.format(
                "1%s",
                String.join("", Collections.nCopies(length, "0"))
        ));
        return new CdrColumn<>(
                length, null,
                rawValue -> {
                    try {
                        return Integer.valueOf(rawValue);
                    } catch (NumberFormatException e) {
                        return -1;
                    }
                },
                (value, _empty) -> value < maximum);
    }

    public static CdrColumn generateLongColumn(final int length) {
        final long maximum = Long.valueOf(String.format(
                "1%s",
                String.join("", Collections.nCopies(length, "0"))
        ));
        return new CdrColumn<>(
                length, null,
                rawValue -> {
                    try {
                        return Long.valueOf(rawValue);
                    } catch (NumberFormatException e) {
                        return -1L;
                    }
                },
                (value, _empty) -> value < maximum);
    }

    public static CdrColumn generateStringColumn(final int length) {
        return new CdrColumn<>(
                length, null,
                rawValue -> rawValue,
                (value, _empty) -> value.length() < length);
    }

    public static CdrColumn generateBooleanColumn(final int length) {
        return new CdrColumn<>(
                length, null,
                rawValue -> rawValue.equals("T") ? Boolean.TRUE : Boolean.FALSE,
                (value, _empty) -> value);
    }

    public static CdrColumn generateEnumColumn(final int length, final List<String> candidates) {
        return new CdrColumn<>(
                length, candidates,
                rawValue -> rawValue,
                (value, _candidates) -> _candidates.contains(value)
        );
    }

    private int length;
    private List<String> enumCandidates;
    private Function<String, T> convertor;
    private BiFunction<T, List<String>, Boolean> constraint;

    public CdrColumn(int length, @Nullable List<String> enumCandidates,
                     Function<String, T> convertor,
                     BiFunction<T, List<String>, Boolean> constraint) {
        this.length = length;
        this.enumCandidates = enumCandidates;
        this.convertor = convertor;
        this.constraint = constraint;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public T convertRawValue(String rawValue) {
        return convertor.apply(rawValue);
    }

    public CdrColumn<T> clone() {
        return new CdrColumn<T>(length, enumCandidates, convertor, constraint);
    }

    public boolean validate(T value) {
        return constraint.apply(value, enumCandidates);
    }
}
