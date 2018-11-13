package com.totorody.cdr;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.StreamSupport;

public class Analyzer {

    public void analyzeToConsole(Iterable<Cdr> cdrs) {
        for (Cdr cdr : cdrs) {
            System.out.println(cdr);
        }
    }

    public void simpleAnalyze(Iterable<Cdr> cdrs, Iterable<Cdr> errorCdrs) {
        StringBuilder builder = new StringBuilder();
        builder.append("[Total] ")
                .append(StreamSupport.stream(cdrs.spliterator(), true).count())
                .append("\n")
                .append("[Error] ")
                .append(StreamSupport.stream(errorCdrs.spliterator(), true).count());
        System.out.println(builder.toString());
    }

    public void analyzeToFile(Iterable<Cdr> cdrs, File outputFile) {
        checkFileDirectory(outputFile);
        System.out.println(outputFile.toString());
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile));
            for (Cdr cdr : cdrs) {
                writer.write(cdr.toString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to write output file in Analyzer.");
            throw new RuntimeException(e);
        }
    }

    private void checkFileDirectory(File outputFile) {
        Path parentDir = outputFile.toPath().getParent();
        if (!Files.exists(parentDir)) {
            try {
                Files.createDirectories(parentDir);
            } catch (IOException e) {
                System.out.println("[ERROR] Failed to create parent directories.");
                throw new RuntimeException(e);
            }
        }
    }
}
