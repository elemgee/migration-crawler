package com.theocc.utils.AtlassianMigration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class Reporter {


    private static final Logger logger = LoggerFactory.getLogger(Reporter.class);
    private static final DateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final DateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private final FileWriter linkfw;
    private final FileWriter referfw;

    private final CSVPrinter linkcsv;
    private final CSVPrinter refercsv;

    public Reporter(String reportDir, String context) throws IOException {
        if (context == null || context.isEmpty()) {
            context = "default";
        }

        String ts = dateformat.format(new Date());
        String linkrfname = String.format("%s/%s-links-%s.log", reportDir, context, ts);
        logger.info("creating report: " + linkrfname + " in dir " + reportDir);


        // create report for links in a page
        linkfw = new FileWriter(linkrfname, true);
        linkcsv = new CSVPrinter(linkfw, CSVFormat.DEFAULT);
        linkcsv.printRecord(Arrays.asList("url", "title", "statuscode", "accessed", "link"));

        // create referrer report
        String reffname = String.format("%s/%s-referrer-%s.log", reportDir, context, ts);
        referfw = new FileWriter(reffname, true);
        refercsv = new CSVPrinter(referfw, CSVFormat.DEFAULT);
        refercsv.printRecord("target-url", "referrer");

    }


    public void write(Document2Migrate d2m) throws IOException {
        logger.debug("writing " + d2m.toString());
        List<String> ls = d2m.getLinks()
                .stream()
                .map(link -> {
                    try {
                        linkcsv.printRecord(Arrays.asList(
                                d2m.getUrl(), d2m.getTitle(), d2m.getStatusCode().toString(), dfmt.format(d2m.getAccessed()), link));
                   refercsv.printRecord(link, d2m.getUrl());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return link;
                })
                .collect(Collectors.toList());


        logger.debug(ls.toString());
    }


}
