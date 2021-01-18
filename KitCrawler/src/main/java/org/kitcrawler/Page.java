package org.kitcrawler;

import edu.uci.ics.crawler4j.url.WebURL;

import java.util.Set;

public class Page {

    private String pageURL;
    private String textData;
    private String htmlData;
    private int textLength;
    private int htmlLength;
    private int outgoingLinks;
    private Set<WebURL> links;
    private String title;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Set<WebURL> getLinks() {
        return links;
    }

    public void setLinks(Set<WebURL> links) {
        this.links = links;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public String getTextData() {
        return textData;
    }

    public void setTextData(String textData) {
        this.textData = textData;
    }

    public String getHtmlData() {
        return htmlData;
    }

    public void setHtmlData(String htmlData) {
        this.htmlData = htmlData;
    }

    public int getTextLength() {
        return textLength;
    }

    public void setTextLength(int textLength) {
        this.textLength = textLength;
    }

    public int getHtmlLength() {
        return htmlLength;
    }

    public void setHtmlLength(int htmlLength) {
        this.htmlLength = htmlLength;
    }

    public int getOutgoingLinks() {
        return outgoingLinks;
    }

    public void setOutgoingLinks(int outgoingLinks) {
        this.outgoingLinks = outgoingLinks;
    }
}
