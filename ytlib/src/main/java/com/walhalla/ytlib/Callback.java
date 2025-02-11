package com.walhalla.ytlib;
interface Callback<T> {
    // TODO: Add interactor callback methods here
    void onMessageRetrieved(T message);

    void onRetrievalFailed(String error);
}
