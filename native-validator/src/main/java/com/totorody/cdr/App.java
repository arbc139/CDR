package com.totorody.cdr;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public static void main( String[] args ) {
        ImmutableList<String> columnOrder = new ImmutableList.Builder<String>()
                .add("length")
                .add("serviceIndicator")
                .add("systemNo")
                .add("serviceType")
                .add("subServiceType")
                .add("suppService")
                .add("accountRecordType")
                .add("serviceId")
                .add("numberOfParticipant")
                .add("callingMdn")
                .add("calledMdn")
                .add("chargingMdn")
                .add("dialedDigit")
                .add("callForwardMdn")
                .add("roleNode")
                .add("startTime")
                .add("endTime")
                .add("deliveryTime")
                .add("chargingDuration")
                .add("sipBodySizeUp")
                .add("sipBodySizeDown")
                .add("contentsSizeUp")
                .add("contentsSizeDown")
                .add("receiverCount")
                .add("sipCode")
                .add("detailCode")
                .add("cdrCloseReason")
                .add("acrStartLost")
                .add("acrStopLost")
                .add("acrInterimLost")
                .add("terminalIp")
                .add("terminalType")
                .add("terminalModel")
                .add("connectionType")
                .add("ppsType")
                .add("roamingIndicator")
                .add("p2pContentsCnt")
                .add("p2wContentsCnt")
                .add("w2pContentsCnt")
                .add("w2wContentsCnt")
                .add("orgSvcDomainCode")
                .add("teamSveDomainCode")
                .add("chargingIndicator")
                .add("ratType")
                .add("cellId")
                .add("pcsSfi")
                .add("sgsnMccMnc")
                .add("reserved")
                .build();
        ImmutableMap<String, CdrColumn<?>> columnMap = new ImmutableMap.Builder<String, CdrColumn<?>>()
                .put("length", CdrColumn.generateIntColumn(4))
                .put("serviceIndicator", CdrColumn.generateEnumColumn(
                        2, Arrays.asList("11", "12", "13", "14", "15", "16", "17", "18", "19")
                ))
                .put("systemNo", CdrColumn.generateStringColumn(2))
                .put("serviceType", CdrColumn.generateEnumColumn(
                        3, Arrays.asList(
                                "101", "102", "103", "201", "202", "203", "205", "301", "302", "303", "304", "305",
                                "306", "307", "309", "310", "401", "801", "802")
                ))
                .put("subServiceType", CdrColumn.generateStringColumn(5))
                .put("suppService", CdrColumn.generateEnumColumn(
                        2, Arrays.asList(
                                "", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "90", "91")
                ))
                .put("accountRecordType", CdrColumn.generateEnumColumn(1, Arrays.asList("1", "5")))
                .put("serviceId", CdrColumn.generateStringColumn(46))
                .put("numberOfParticipant", CdrColumn.generateLongColumn(10))
                .put("callingMdn", CdrColumn.generateStringColumn(16))
                .put("calledMdn", CdrColumn.generateStringColumn(24))
                .put("chargingMdn", CdrColumn.generateStringColumn(24))
                .put("dialedDigit", CdrColumn.generateStringColumn(32))
                .put("callForwardMdn", CdrColumn.generateStringColumn(24))
                .put("roleNode", CdrColumn.generateEnumColumn(1, Arrays.asList("1", "2", "5")))
                .put("startTime", CdrColumn.generateLongColumn(15))
                .put("endTime", CdrColumn.generateLongColumn(15))
                .put("deliveryTime", CdrColumn.generateLongColumn(15))
                .put("chargingDuration", CdrColumn.generateLongColumn(10))
                .put("sipBodySizeUp", CdrColumn.generateLongColumn(10))
                .put("sipBodySizeDown", CdrColumn.generateLongColumn(10))
                .put("contentsSizeUp", CdrColumn.generateLongColumn(10))
                .put("contentsSizeDown", CdrColumn.generateLongColumn(10))
                .put("receiverCount", CdrColumn.generateIntColumn(5))
                // TODO(totoro): SIP-Code enum elements가 정해지면 ENUM type으로 변경해야함.
                .put("sipCode", CdrColumn.generateIntColumn(3))
                // TODO(totoro): DETAIL-Code enum elements가 정해지면 ENUM type으로 변경해야함.
                .put("detailCode", CdrColumn.generateIntColumn(4))
                .put("cdrCloseReason", CdrColumn.generateEnumColumn(
                        3, Arrays.asList("0", "1", "2", "3", "4", "5", "6")
                ))
                .put("acrStartLost", CdrColumn.generateBooleanColumn(1))
                .put("acrStopLost", CdrColumn.generateBooleanColumn(1))
                .put("acrInterimLost", CdrColumn.generateEnumColumn(1, Arrays.asList("0", "1", "2")))
                .put("terminalIp", CdrColumn.generateStringColumn(15))
                .put("terminalType", CdrColumn.generateStringColumn(10))
                .put("terminalModel", CdrColumn.generateStringColumn(16))
                .put("connectionType", CdrColumn.generateEnumColumn(
                        2, Arrays.asList("01", "02", "03", "00")
                ))
                .put("ppsType", CdrColumn.generateEnumColumn(
                        5, Arrays.asList(
                                "0", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "11", "12", "13", "14",
                                "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
                                "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42",
                                "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56",
                                "57", "58", "59", "60", "61", "97", "98", "99")
                ))
                .put("roamingIndicator", CdrColumn.generateEnumColumn(3, Arrays.asList("000", "001")))
                .put("p2pContentsCnt", CdrColumn.generateIntColumn(5))
                .put("p2wContentsCnt", CdrColumn.generateIntColumn(5))
                .put("w2pContentsCnt", CdrColumn.generateIntColumn(5))
                .put("w2wContentsCnt", CdrColumn.generateIntColumn(5))
                .put("orgSvcDomainCode", CdrColumn.generateEnumColumn(
                        3, Arrays.asList("011", "016", "019", "774", "770", "502", "900", "901", "999")
                ))
                .put("teamSveDomainCode", CdrColumn.generateEnumColumn(
                        3, Arrays.asList(
                                "011", "016", "019", "111", "901", "999", "502", "203", "204",
                                "205", "206", "207", "208", "209", "210", "211", "220", "221",
                                "222", "223", "224", "225", "226", "227")
                ))
                .put("chargingIndicator", CdrColumn.generateEnumColumn(
                        1, Arrays.asList("0", "1", "2")
                ))
                .put("ratType", CdrColumn.generateEnumColumn(
                        1, Arrays.asList("1", "2", "3")
                ))
                .put("cellId", CdrColumn.generateStringColumn(7))
                .put("pcsSfi", CdrColumn.generateStringColumn(6))
                .put("sgsnMccMnc", CdrColumn.generateStringColumn(6))
                .put("reserved", CdrColumn.generateStringColumn(43))
                .build();

        // Creates & Collects files.
        long startTime, endTime;

        List<Path> cdrPaths;
        try (Stream<Path> paths = Files.walk(Paths.get("cdr-files"))) {
            cdrPaths = paths.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("[ERROR] cdr-files directory is missed. Please check it.");
            throw new RuntimeException(e);
        }

        startTime = System.currentTimeMillis();
        for (Path path : cdrPaths) {
            System.out.println(path.toString());
            executeCdrValidator(path, columnOrder, columnMap);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));

        // Deprecated
//        long startTime, endTime;
//
//        // Parses by using ColumnOrder & Map.
//        startTime = System.currentTimeMillis();
//        Parser parser = new Parser(inputFile, columnOrder, columnMap);
//        Iterable<Cdr> cdrs = parser.parseParallel();
//        endTime = System.currentTimeMillis();
//        System.out.println("Parser Execution Time: " + (endTime - startTime));
//
//        // Validates Cdrs.
//        startTime = System.currentTimeMillis();
//        Validator validator = new Validator(columnMap);
//        Iterable<Cdr> errorCdrs = validator.findErrorCdrs(cdrs);
//        System.out.println(errorCdrs);
//        endTime = System.currentTimeMillis();
//        System.out.println("Validator Execution Time: " + (endTime - startTime));

        // Analyzes Error Cdrs
//        startTime = System.currentTimeMillis();
//        Analyzer analyzer = new Analyzer();
//        analyzer.analyzeToConsole(errorCdrs);
//        endTime = System.currentTimeMillis();
//        System.out.println("Analyzer Execution Time: " + (endTime - startTime));
    }

    public static void executeCdrValidator(final Path inputPath, List<String> columnOrder,
                                           Map<String, CdrColumn<?>> columnMap) {
        // Parses by using ColumnOrder & Map.
        TimeElapser elapser = new TimeElapser();
        elapser.start();
        Parser parser = new Parser(inputPath.toFile(), columnOrder, columnMap);
        List<Cdr> cdrs = Lists.newArrayList(parser.parseParallel());
        System.out.println("Parser Execution Time:      "+ elapser.elapse());
        elapser.stop();

        // Validates Cdrs.
        elapser.start();
        Validator validator = new Validator(columnMap);
        List<Cdr> errorCdrs = Lists.newArrayList(validator.findErrorCdrs(cdrs));
        System.out.println("Validator Execution Time:   "+ elapser.elapse());
        elapser.stop();

        elapser.start();
        Analyzer analyzer = new Analyzer();
        analyzer.simpleAnalyze(cdrs, errorCdrs);
        System.out.println("Analyzer Execution Time:    "+ elapser.elapse());
        elapser.stop();

//        Path outputPath = new File("output")
//                .toPath()
//                .resolve(inputPath);
    }
}
