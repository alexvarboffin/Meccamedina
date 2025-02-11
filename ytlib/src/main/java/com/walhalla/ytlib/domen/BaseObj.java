package com.walhalla.ytlib.domen;

import org.w3c.dom.Node;



public interface BaseObj<A> {
    A fetchOne(Node node);
}
