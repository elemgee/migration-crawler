package com.theocc.utils.AtlassianMigration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    private static final Logger logger = LoggerFactory.getLogger(Parser.class);
    /**
     * Connection used to access links internal to the target site.
     *  used to handle persistent authentication
     */
    private Connection cnx;

    /**
     * connection used to access external links
     */
    private Connection extCnx;

    public Parser() throws IOException {

    }


    public static Document2Migrate parse(Connection c) throws IOException{
        Document doc = c.get();
        int statusCode = c.response().statusCode();


        logger.info(String.format("target: \"%s\" (%s)", doc.title(), c.request().url()));

        List<String> links =   doc.select("a[href]")
                .stream()
                .map(element -> element.absUrl("href"))
                .collect(Collectors.toList());

        Document2Migrate d2m = new Document2Migrate(c.request().url().toString(), statusCode, doc.title(), new HashSet(links));
        return d2m;

    }
}
