package com.theocc.utils.AtlassianMigration;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Referer {


    public static final Logger logger = LoggerFactory.getLogger(Referer.class);

    public String target;
    public String referer;

    public Referer( String targ, String ref) {
        target = targ;
        referer = ref;
    }
}
