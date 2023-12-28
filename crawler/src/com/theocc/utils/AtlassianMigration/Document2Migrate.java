package com.theocc.utils.AtlassianMigration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Date;
import java.util.Set;

public class Document2Migrate {


    private static final Logger logger = LoggerFactory.getLogger(Document2Migrate.class);


    private String url;
    private String title;
    private Integer statusCode;
    private Date accessed;


    private Set<String> links;

    public Set<String> getReferrers() {
        return referrers;
    }

    public void setReferrers(Set<String> referrers) {
        this.referrers = referrers;
    }

    /**
     * list of URLs that refer to this document
     */
    private Set<String> referrers;

    /**
     *
     * @param u
     * @param scode
     * @param title
     * @param links
     */


    public Document2Migrate(String u, Integer scode, String title, Set<String> links) {
        logger.debug(String.format("Creating doc for %s", u));
        setAccessed(new Date());
        setUrl(u);
        setStatusCode(scode);
        setTitle(title);
        setLinks(links);
    }




    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Date getAccessed() {
        return accessed;
    }

    public void setAccessed(Date accessed) {
        this.accessed = accessed;
    }

    public Set<String> getLinks() {
        return links;
    }

    public void setLinks(Set<String> l) {
        this.links = l;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String t) {
        this.title = t;
    }


    @Override
    public String toString() {
        return String.format("com.theocc.utils.AtlassianMigration.Document2Migrate{%s,%s,%s}", this.getUrl(), this.getTitle(), super.toString());
    }


    public boolean equals(Document2Migrate u) {
        return String.CASE_INSENSITIVE_ORDER.equals(u.getUrl());
    }

}
