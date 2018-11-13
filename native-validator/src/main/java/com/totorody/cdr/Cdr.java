package com.totorody.cdr;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cdr {

    public static int MAX_SIZE = 450;
    private static int RESERVED_SIZE = 43;

    public static int calculateReservedSize(final int length) {
        return RESERVED_SIZE - (MAX_SIZE - length);
    }

    public static boolean isDifferentReservedSize(final int reserved) {
        return reserved != RESERVED_SIZE;
    }

    public boolean isInvalid;
    public List<String> invalidColumns;
    private Map<String, ?> cdrMap;

    public Map<String, ?> getCdrMap() {
        return cdrMap;
    }

    public Cdr(Map<String, ?> cdrMap) {
        this.cdrMap = cdrMap;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n-------[CDR]-------\n")
                .append(this.cdrMap.toString())
                .append("\n")
                .append("isInvalid: ")
                .append(isInvalid)
                .append("\n")
                .append("invalidColumns: ")
                .append(invalidColumns == null ? "" : invalidColumns.toString())
                .append("\n");
        return builder.toString();
    }

    @Deprecated
    public Cdr validate(final Map<String, CdrColumn<?>> columnMap) {
        Cdr newCdr = new Cdr(cdrMap);
        newCdr.invalidColumns = cdrMap.entrySet().parallelStream()
                .filter(entry -> {
                    CdrColumn column = columnMap.get(entry.getKey());
                    return !column.validate(entry.getValue());
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        if (!newCdr.invalidColumns.isEmpty()) {
            newCdr.isInvalid = true;
        }
        return newCdr;
    }

    public static Cdr createCdrWithValidate(final Map<String, ?> cdrMap,
                                            final Map<String, CdrColumn<?>> columnMap) {
        Cdr cdr = new Cdr(cdrMap);
        cdr.invalidColumns = cdrMap.entrySet().parallelStream()
                .filter(entry -> {
                    CdrColumn column = columnMap.get(entry.getKey());
                    return !column.validate(entry.getValue());
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        if (!cdr.invalidColumns.isEmpty()) {
            cdr.isInvalid = true;
        }
        return cdr;
    }

    public boolean isEmpty() {
        return cdrMap.isEmpty();
    }
}

    /*
    public int length;
    public String serviceIndicator;
    public String systemNo;
    public int serviceType;
    public int subServiceType;
    public int suppService;
    public int accountRecordType;
    public String serviceId;
    public int numberOfParticipant;
    public String callingMdn;
    public String calledMdn;
    public String chargingMdn;
    public String dialedDigit;
    public String callForwardMdn;
    public int roleNode;
    public long startTime;
    public long endTime;
    public long deliveryTime;
    public int chargingDuration;
    public int sipBodySizeUp;
    public int sipBodySizeDown;
    public int contentsSizeUp;
    public int contentsSizeDown;
    public int receiverCount;
    public int sipCode;
    public int detailCode;
    public int cdrCloseReason;
    public boolean acrStartLost;
    public boolean acrStopLost;
    public int acrInterimLost;
    public String terminalIp;
    public String terminalType;
    public String terminalModel;
    public String connectionType;
    public String ppsType;
    public String roamingIndicator;
    public int p2pContentsCnt;
    public int p2wContentsCnt;
    public int w2pContentsCnt;
    public int w2wContentsCnt;
    public String orgSvcDomainCode;
    public String teamSveDomainCode;
    public int chargingIndicator;
    public int ratType;
    public String cellId;
    public String pcsSfi;
    public int sgsnMccMnc;

    public Cdr(int length, int serviceIndicator, int systemNo, int serviceType, int subServiceType, int suppService,
               int accountRecordType, String serviceId, int numberOfParticipant, String callingMdn, String calledMdn,
               String chargingMdn, String dialedDigit, String callForwardMdn, int roleNode, long startTime,
               long endTime, long deliveryTime, int chargingDuration, int sipBodySizeUp, int sipBodySizeDown,
               int contentsSizeUp, int contentsSizeDown, int receiverCount, int sipCode, int detailCode,
               int cdrCloseReason, boolean acrStartLost, boolean acrStopLost, int acrInterimLost, String terminalIp,
               String terminalType, String terminalModel, String connectionType, String ppsType,
               String roamingIndicator, int p2pContentsCnt, int p2wContentsCnt, int w2pContentsCnt, int w2wContentsCnt,
               String orgSvcDomainCode, String teamSveDomainCode, int chargingIndicator, int ratType, String cellId,
               String pcsSfi, int sgsnMccMnc) {
        this.length = length;
        this.serviceIndicator = serviceIndicator;
        this.systemNo = systemNo;
        this.serviceType = serviceType;
        this.subServiceType = subServiceType;
        this.suppService = suppService;
        this.accountRecordType = accountRecordType;
        this.serviceId = serviceId;
        this.numberOfParticipant = numberOfParticipant;
        this.callingMdn = callingMdn;
        this.calledMdn = calledMdn;
        this.chargingMdn = chargingMdn;
        this.dialedDigit = dialedDigit;
        this.callForwardMdn = callForwardMdn;
        this.roleNode = roleNode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deliveryTime = deliveryTime;
        this.chargingDuration = chargingDuration;
        this.sipBodySizeUp = sipBodySizeUp;
        this.sipBodySizeDown = sipBodySizeDown;
        this.contentsSizeUp = contentsSizeUp;
        this.contentsSizeDown = contentsSizeDown;
        this.receiverCount = receiverCount;
        this.sipCode = sipCode;
        this.detailCode = detailCode;
        this.cdrCloseReason = cdrCloseReason;
        this.acrStartLost = acrStartLost;
        this.acrStopLost = acrStopLost;
        this.acrInterimLost = acrInterimLost;
        this.terminalIp = terminalIp;
        this.terminalType = terminalType;
        this.terminalModel = terminalModel;
        this.connectionType = connectionType;
        this.ppsType = ppsType;
        this.roamingIndicator = roamingIndicator;
        this.p2pContentsCnt = p2pContentsCnt;
        this.p2wContentsCnt = p2wContentsCnt;
        this.w2pContentsCnt = w2pContentsCnt;
        this.w2wContentsCnt = w2wContentsCnt;
        this.orgSvcDomainCode = orgSvcDomainCode;
        this.teamSveDomainCode = teamSveDomainCode;
        this.chargingIndicator = chargingIndicator;
        this.ratType = ratType;
        this.cellId = cellId;
        this.pcsSfi = pcsSfi;
        this.sgsnMccMnc = sgsnMccMnc;
    }
    */