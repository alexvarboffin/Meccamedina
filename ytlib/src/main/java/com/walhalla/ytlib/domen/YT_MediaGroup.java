package com.walhalla.ytlib.domen;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class YT_MediaGroup implements Contract.Entry.MediaGroup, BaseObj<YT_MediaGroup>{


    private String media_title;
    private MediaContent media_content;
    private MediaThumbnails media_thumbnail;
    private com.walhalla.ytlib.domen.MediaCommunity media_community;
    private String media_description;

    @Override
    public YT_MediaGroup fetchOne(Node node) {
        NodeList nodeList = node.getChildNodes();
        YT_MediaGroup mediaGroup = new YT_MediaGroup();

        if (nodeList != null) {
            int n = nodeList.getLength();
            for (int i = 0; i < n; i++) {

                node = nodeList.item(i);
                String name = node.getNodeName();

                if (name.equalsIgnoreCase(TAG_MEDIA_TITLE)) {
                    mediaGroup.media_title = node.getFirstChild().getNodeValue();
                } else if (name.equalsIgnoreCase(TAG_MEDIA_CONTENT)) {

                    mediaGroup.media_content = new MediaContent().fetchOne(node);

                } else if (name.equalsIgnoreCase(TAG_MEDIA_THUMBNAIL)) {
                    mediaGroup.media_thumbnail = new MediaThumbnails().fetchOne(node);
                } else if (name.equalsIgnoreCase(TAG_MEDIA_DESCRIPTION)) {
                    mediaGroup.media_description = node.getFirstChild().getNodeValue();
                } else if (name.equalsIgnoreCase(TAG_MEDIA_COMMUNITY)) {
                    mediaGroup.media_community = new com.walhalla.ytlib.domen.MediaCommunity().fetchOne(node);
                }
            }
        }
        return mediaGroup;
    }

    public String getMedia_title() {
        return media_title;
    }

    public void setMedia_title(String media_title) {
        this.media_title = media_title;
    }

    public MediaContent getMedia_content() {
        return media_content;
    }

    public com.walhalla.ytlib.domen.MediaCommunity getMedia_community() {
        return media_community;
    }

    public void setMedia_community(com.walhalla.ytlib.domen.MediaCommunity media_community) {
        this.media_community = media_community;
    }

    public String getMedia_description() {
        return media_description;
    }

    public void setMedia_description(String media_description) {
        this.media_description = media_description;
    }

    public void setMedia_content(MediaContent media_content) {
        this.media_content = media_content;
    }

    public MediaThumbnails getMedia_thumbnail() {
        return media_thumbnail;
    }

    public void setMedia_thumbnail(MediaThumbnails media_thumbnail) {
        this.media_thumbnail = media_thumbnail;
    }

    @Override
    public String toString() {
        return "YT_MediaGroup{" +
                "media_title='" + media_title + '\'' +
                ", media_content=" + media_content +
                ", media_thumbnail=" + media_thumbnail +
                ", media_community=" + media_community +
                ", media_description='" + media_description + '\'' +
                '}';
    }
}
