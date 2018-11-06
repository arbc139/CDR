package com.totorody.cdr;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {

    private File cdrFile;
    private List<String> columnOrder;
    private Map<String, CdrColumn<?>> columnMap;
    private Map<String, Pair<Integer, Integer>> indexMap;

    public Parser(File cdrFile, List<String> columnOrder, Map<String, CdrColumn<?>> columnMap) {
        this.cdrFile = cdrFile;
        this.columnOrder = columnOrder;
        this.columnMap = columnMap;

        this.calculateIndexMap();
    }

    public void calculateIndexMap() {
        int startIndex = 0;
        ImmutableMap.Builder<String, Pair<Integer, Integer>> builder = new ImmutableMap.Builder<>();
        for (String columnStr : columnOrder) {
            CdrColumn column = columnMap.get(columnStr);
            builder.put(columnStr, new Pair<>(startIndex, startIndex + column.getLength()));
            startIndex += column.getLength();
        }
        this.indexMap = builder.build();
    }

    public Iterable<Cdr> parseParallel() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(this.cdrFile));
        } catch (FileNotFoundException e) {
            System.out.println("[ERROR] Target CDR File is not found.");
            throw new RuntimeException(e);
        }

        Iterable<String> rawCdrs = splitBy450(reader);
        return Iterables.transform(rawCdrs, this::parseLineParallel);
    }

    private Iterable<String> splitBy450(final BufferedReader reader) {
        StringBuilder builder = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            System.out.println("SplitBy450 has failed on BufferReader.");
            throw new RuntimeException(e);
        }
        return Splitter.fixedLength(Cdr.MAX_SIZE).split(builder.toString());
    }

    private Cdr parseLineParallel(final String rawCdr) {
        Map<String, ?> cdrMap = indexMap.entrySet().parallelStream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            String columnStr = entry.getKey();
                            Pair<Integer, Integer> indexInfo = entry.getValue();
                            int startIndex = indexInfo.getKey();
                            int endIndex = indexInfo.getValue();
                            CdrColumn column = columnMap.get(columnStr);
                            return column.convertRawValue(rawCdr.substring(startIndex, endIndex).trim());
                        }));
        return new Cdr(cdrMap);
    }

    /**
     * Legacy codes (Non-parallel)
     */
    @Deprecated
    public List<Cdr> parse() {
        FileReader reader;
        try {
            reader = new FileReader(this.cdrFile);
        } catch (FileNotFoundException e) {
            System.out.println("[ERROR] Target CDR File is not found.");
            throw new RuntimeException(e);
        }

        List<Cdr> results = new ArrayList<>();
        CdrColumn<Integer> lengthColumn = (CdrColumn<Integer>) columnMap.get("length");

        // TODO(totoro): Generic과 관련된 Unchecked assignment warning을 해결해야함.
        while (true) {
            String rawLength = parseRawColumn(reader, lengthColumn);
            if (rawLength == null) {
                break;
            }
            int length = lengthColumn.convertRawValue(rawLength);

            Cdr cdr = parseLine(reader, length);
            results.add(cdr);
        }

        return results;
    }

    @Deprecated
    private String parseRawColumn(final FileReader reader, final CdrColumn column) {
        try {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < column.getLength(); ++i) {
                int singleCh = reader.read();
                if (singleCh == -1) {
                    return null;
                }
                builder.append((char) singleCh);
            }
            return builder.toString().trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    private Cdr parseLine(final FileReader reader, final int length) {
        int reserved = Cdr.calculateReservedSize(length);
        ImmutableMap.Builder<String, ? super Object> builder = new ImmutableMap.Builder<>();
        builder.put("length", length);
        for (String columnStr : columnOrder) {
            if (columnStr.equals("length")) {
                continue;
            }

            CdrColumn column;
            if (columnStr.equals("reserved") && Cdr.isDifferentReservedSize(reserved)) {
                System.out.println("[CRITICAL] Reserved size different, Total CDR size different!");
                column = columnMap.get(columnStr).clone();
                column.setLength(reserved);
            } else {
                column = columnMap.get(columnStr);
            }
            String rawValue = parseRawColumn(reader, column);
            builder.put(columnStr, column.convertRawValue(rawValue));
        }
        return new Cdr(builder.build());
    }
}
