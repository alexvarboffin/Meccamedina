package com.walhalla.ytlib.domen;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;




public class Feed{

    String link;
    String id;
    String yt_channelId;
    String title;
    Author author;
    String published;

    List<YT_Entry> entry;

    public Feed() {
    }

    void fetchFeed(Node node) {
        NodeList nodes = node.getChildNodes();
        if (nodes != null) {
            int n = nodes.getLength();
            for (int i = 0; i < n; i++) {

                node = nodes.item(i);
                String name = node.getNodeName();

                if (name.equalsIgnoreCase(Contract.TAG_LINK)) {
                    link = node.getFirstChild().getNodeValue();
                } else if (name.equalsIgnoreCase(Contract.TAG_ID)) {
                    id = node.getFirstChild().getNodeValue();
                } else if (name.equalsIgnoreCase(Contract.TAG_YT_CHANNELID)) {
                    yt_channelId = node.getFirstChild().getNodeValue();
                } else if (name.equalsIgnoreCase(Contract.TAG_TITLE)) {
                    title = node.getFirstChild().getNodeValue();
                } else if (name.equalsIgnoreCase(Contract.TAG_AUTHOR)) {
                    author = new Author().fetchOne(node);
                } else if (name.equalsIgnoreCase(Contract.TAG_PUBLISHED)) {
                    published = node.getFirstChild().getNodeValue();
                }
                //==============================================================================
                else if (name.equalsIgnoreCase(Contract.Entry.TAG_ENTRY)) {//array
                    YT_Entry e = new YT_Entry().fetchEntryOne(node);
                    entry.add(e);
                }
                //==============================================================================
            }
        } else {

        }
    }

    @Override
    public String toString() {
        return "Feed{" +
                "link='" + link + '\'' +
                ", id='" + id + '\'' +
                ", yt_channelId='" + yt_channelId + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", published='" + published + '\'' +
                ", entry=" + entry +
                '}';
    }
}
