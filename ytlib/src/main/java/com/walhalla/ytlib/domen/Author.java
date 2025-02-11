package com.walhalla.ytlib.domen;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class Author implements Contract.Author, BaseObj<Author>{

    private String name;
    private String uri;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


    @Override
    public Author fetchOne(Node node) {
        NodeList nodeList = node.getChildNodes();
        Author author = new Author();

        if (nodeList != null) {
            int n = nodeList.getLength();
            for (int i = 0; i < n; i++) {

                node = nodeList.item(i);
                String name = node.getNodeName();

                if (name.equalsIgnoreCase(TAG_NAME)) {
                    author.setName(node.getFirstChild().getNodeValue());
                } else if (name.equalsIgnoreCase(TAG_URI)) {
                    author.setUri(node.getFirstChild().getNodeValue());
                }
            }
        }
        return author;
    }
}