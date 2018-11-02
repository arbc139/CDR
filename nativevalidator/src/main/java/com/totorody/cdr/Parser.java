package com.totorody.cdr;

import com.google.common.collect.ImmutableMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser {

    private File cdrFile;

    public Parser(File cdrFile) {
        this.cdrFile = cdrFile;
        /*

    public int length;
    public int serviceIndicator;
    public int systemNo;
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
         */
        ImmutableMap<String, CdrColumn> columnMap = new ImmutableMap.Builder<String, CdrColumn>()
                .put("length", )
                .build();
    }

    public Cdr[] parse() {
        FileReader reader;
        try {
            reader = new FileReader(this.cdrFile);
        } catch (FileNotFoundException e) {
            System.out.println("[ERROR] Target CDR File is not found.");
            throw new RuntimeException(e);
        }

        try {

        } catch (IOException e) {
            System.out.println("[ERROR] I/O Exception occured on file parsing.");
            throw new RuntimeException(e);
        }
    }
}
