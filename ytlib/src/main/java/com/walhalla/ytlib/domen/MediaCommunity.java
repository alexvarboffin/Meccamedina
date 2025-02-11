package com.walhalla.ytlib.domen;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



class MediaCommunity implements Contract.Entry.MediaGroup.MediaCommunity, BaseObj<MediaCommunity>{

    private String media_starRating;
    private String media_statistics;



    @Override
    public String toString() {
        return "MediaCommunity{" +
                "media_starRating='" + media_starRating + '\'' +
                ", media_statistics='" + media_statistics + '\'' +
                '}';
    }

    @Override
    public MediaCommunity fetchOne(Node node) {
        MediaCommunity obj = new MediaCommunity();

        NodeList nodeList = node.getChildNodes();

        if (nodeList != null) {
            int n = nodeList.getLength();
            for (int i = 0; i < n; i++) {

                node = nodeList.item(i);
                String name = node.getNodeName();
                if (name.equalsIgnoreCase(TAG_MEDIA_STARRATING)) {

                    Element e = (Element) node;
                    String count = e.getAttribute("count");
                    String average = e.getAttribute("average");
                    String min = e.getAttribute("min");
                    String max = e.getAttribute("max");


                    StringBuilder sb = new StringBuilder();
                    sb.append(count)
                            .append(average)
                            .append("|")
                            .append(min)
                            .append("|")
                            .append(max);
                    //node.getFirstChild().getNodeValue()
                    obj.media_starRating = sb.toString();

                } else if (name.equalsIgnoreCase(TAG_MEDIA_STATISTICS)) {
                    //obj.media_statistics = node.getFirstChild().getNodeValue();
                    Element e = (Element) node;
                    String views = e.getAttribute("views");
                }
            }
        } else {

        }
        return obj;
    }
}