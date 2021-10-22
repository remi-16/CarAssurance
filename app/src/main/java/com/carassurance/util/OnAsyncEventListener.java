package com.carassurance.util;


public interface OnAsyncEventListener {
    void onSuccess();
    void onFailure(Exception e);
}