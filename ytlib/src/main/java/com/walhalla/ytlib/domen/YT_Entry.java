package com.walhalla.ytlib.domen;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;



public class YT_Entry implements Contract.Entry {

    private String link;
    private String id;
    private String yt_channelId;
    private String title;

    private Author author;
    private String published;

    private String yt_videoId;
    private String updated;

    public YT_MediaGroup getMedia_group() {
        return media_group;
    }

    public void setMedia_group(YT_MediaGroup media_group) {
        this.media_group = media_group;
    }

    private YT_MediaGroup media_group;


    public List<YT_Entry> fetchEntryArr(NodeList arr) {

        //System.out.printf("99");

        List<YT_Entry> data = new ArrayList<>();
        int length = arr.getLength();

        for (int i = 0; i < length; i++) {
            YT_Entry entry = fetchEntryOne(arr.item(i));
            data.add(i,entry);
        }


        //System.out.printf(data.toString());
        return data;
    }

    public YT_Entry fetchEntryOne(Node node) {

        NodeList nodeList = node.getChildNodes();
        YT_Entry entry = new YT_Entry();


//            try {
//                XMLUtils.printDocument(node, System.out);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (TransformerException e) {
//                e.printStackTrace();
//            }

        if (nodeList != null) {


            int n = nodeList.getLength();
            for (int i = 0; i < n; i++) {

                node = nodeList.item(i);
                String name = node.getNodeName();


                if (name.equalsIgnoreCase(TAG_ID)) {
                    entry.id = node.getFirstChild().getNodeValue();
                } else if (name.equalsIgnoreCase(TAG_YT_VIDEOID)) {
                    entry.yt_videoId = node.getFirstChild().getNodeValue();
                } else if (name.equalsIgnoreCase(TAG_YT_CHANNELID)) {
                    entry.yt_channelId = node.getFirstChild().getNodeValue();
                } else if (name.equalsIgnoreCase(TAG_TITLE)) {
                    entry.title = node.getFirstChild().getNodeValue();
                }
                if (name.equalsIgnoreCase(TAG_LINK)) {

                    Element e = (Element) node;

                    String href = e.getAttribute("href");
                    String rel = e.getAttribute("rel");

                    entry.link = href + "|" + rel;

                } else if (name.equalsIgnoreCase(TAG_AUTHOR)) {
                    entry.author = new Author().fetchOne(node);
                } else if (name.equalsIgnoreCase(TAG_PUBLISHED)) {
                    entry.published = node.getFirstChild().getNodeValue();

                } else if (name.equalsIgnoreCase(TAG_UPDATED)) {
                    entry.updated = node.getFirstChild().getNodeValue();
                } else if (name.equalsIgnoreCase(TAG_MEDIA_GROUP)) {//obj
                    entry.media_group = new YT_MediaGroup().fetchOne(node);
                }


            }
        } else {

        }

        return entry;
    }

    @Override
    public String toString() {
        return "YT_Entry{" +
                "link='" + link + '\'' +
                ", id='" + id + '\'' +
                ", yt_channelId='" + yt_channelId + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", published='" + published + '\'' +
                ", yt_videoId='" + yt_videoId + '\'' +
                ", updated='" + updated + '\'' +
                ", media_community=" + media_group +
                '}';
    }



    //Getters and setters
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYt_channelId() {
        return yt_channelId;
    }

    public void setYt_channelId(String yt_channelId) {
        this.yt_channelId = yt_channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getYt_videoId() {
        return yt_videoId;
    }

    public void setYt_videoId(String yt_videoId) {
        this.yt_videoId = yt_videoId;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

}

