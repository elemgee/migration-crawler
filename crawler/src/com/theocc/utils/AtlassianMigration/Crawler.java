package com.theocc.utils.AtlassianMigration;

import org.jsoup.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */

public class Crawler {

    private static final Logger logger = LoggerFactory.getLogger(Crawler.class);


    private int MAX_DEPTH = 3;

    public Crawler() {

    }

public static void crawl (int currentDepth, int maxDepth,
                          LinkedList<String> urls2crawl,
                          Set<String> urlsCrawled,
                          Reporter rep) {



            logger.info("urls2crawl: "+Integer.toString(urls2crawl.size()));
            logger.info("urlsCrawled: " + Integer.toString(urlsCrawled.size()));
            String u = urls2crawl.pollFirst();
            logger.info("crawl: "+u);
            logger.info("urls2crawl: "+Integer.toString(urls2crawl.size()));
    logger.info("urlsCrawled: " + Integer.toString(urlsCrawled.size()));
            if (u == null) {
                logger.info("finished crawling urls");
            } else {
                if (urlsCrawled.contains(u)) {
                    logger.info(String.format("%s has been previously parsed", u));
                } else {
                    Connection cnx = Jsoup.connect(u);
                    try {
                        Document2Migrate d2m = Parser.parse(cnx);
                        rep.write(d2m);
                        if (currentDepth <= maxDepth) {
                            logger.info("links in page: " + Integer.toString(d2m.getLinks().size()));
                            Set<String> toAdd = d2m.getLinks()
                                            .stream()
                                            .filter(l -> !urls2crawl.contains(l))
                                            .collect(Collectors.toSet());
                            logger.info("links to add: " + Integer.toString(toAdd.size()));

                            urls2crawl.addAll(d2m.getLinks()
                                    .stream()
                                    .filter(l -> !urls2crawl.contains(l))
                                    .collect(Collectors.toSet()));
                            urlsCrawled.add(u);
                            crawl(0, 1,
                                    urls2crawl,
                                    urlsCrawled,
                                    rep);
                        } else {
                            logger.info("reached max depth");
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }


}
    public static void main(String[] args) throws Exception {

        if (args.length < 1) {
            throw new Exception("You must pass the target URL in as the first argument to this crawler");
        }


        Crawler crawler = new Crawler();
        String targetUrl = args[0];
        String reportDir = "/Users/42larrym/Desktop";
        if ( args.length > 1){
            reportDir = args[1];
        }
        logger.info(String.format("attempting to crawl %s", targetUrl));
//        try {
            Reporter rep = new Reporter(reportDir, "jsoup-test");
            LinkedList<String> crawlThis = new LinkedList<String>(Arrays.asList(targetUrl));
            crawl(0, 3, crawlThis, new HashSet<>(), rep);

//        } catch (IOException iox) {
//            System.err.print(iox.getMessage());
//            iox.printStackTrace();
//        }

    }
}