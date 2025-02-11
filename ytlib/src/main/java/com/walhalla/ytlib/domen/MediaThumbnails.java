package com.walhalla.ytlib.domen;

import org.w3c.dom.Element;
import org.w3c.dom.Node;



class MediaThumbnails {

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    private String url;
    private String width;
    private String height;

    public MediaThumbnails fetchOne(Node node) {
        Element e = (Element) node;

        MediaThumbnails obj = new MediaThumbnails();
        obj.setUrl(e.getAttribute("url"));
        obj.setWidth(e.getAttribute("width"));
        obj.setHeight(e.getAttribute("height"));
        return obj;
    }

    @Override
    public String toString() {
        return "MediaThumbnails{" +
                "url='" + url + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
