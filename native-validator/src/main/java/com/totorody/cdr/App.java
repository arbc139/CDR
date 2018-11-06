package com.totorody.cdr;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main( String[] args ) {
        long start = System.nanoTime();
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
        File file = new File("cdr-files/HD_VOICE1/A_100");
        Parser parser = new Parser(file, columnOrder, columnMap);
        Iterable<Cdr> cdrs = parser.parseParallel();
        long end = System.nanoTime();
        System.out.println("Execution time: " + (end - start));
    }

    public static void executeFiles(final File directory) {

    }
}