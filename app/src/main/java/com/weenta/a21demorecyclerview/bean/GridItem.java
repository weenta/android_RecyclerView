package com.weenta.a21demorecyclerview.bean;

public class GridItem {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "GridItem{" +
                "url='" + url + '\'' +
                '}';
    }
}
