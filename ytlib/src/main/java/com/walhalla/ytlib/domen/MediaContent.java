package com.walhalla.ytlib.domen;

import org.w3c.dom.Element;
import org.w3c.dom.Node;



class MediaContent {

    private String url, type;
    private String width;
    private String height;

    /**
     * Fetch attr
     *
     * @param node
     * @return
     */
    public MediaContent fetchOne(Node node) {
        Element e = (Element) node;
        MediaContent mediaContent = new MediaContent();
        mediaContent.setUrl(e.getAttribute("url"));
        mediaContent.setType(e.getAttribute("type"));
        mediaContent.setWidth(e.getAttribute("width"));
        mediaContent.setHeight(e.getAttribute("height"));
        return mediaContent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "MediaContent{" +
                "url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
