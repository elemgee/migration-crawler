# OCC-Crawler

This is a basic web crawler intended to gather and report 
on links in the Jira and Confluence sites prior to and after migrating
from Nasdaq to OCC.

All you have to do is pass in the target URL (the URL at the
top of the tree you'd like to explore). This spider will only
check links found directly in the page. If you'd like it to dig
deeper, change the MAX_DEPTH value.



```mermaid
graph TB;
    a{start}--> b(target url);
    subgraph find links
    b --seed the crawler-->urls2crawl;
    urls2crawl --parse next url --> c(getLinks)
    c --write links --> urls2crawl;
    end
    subgraph check links
        urls2crawl --check status--> GET_request;
        GET_request --> parse(parse response);
        parse -- report --> write("report URL, STATUS CODE, TITLE")
    end
    
    
    
    
    
    
```