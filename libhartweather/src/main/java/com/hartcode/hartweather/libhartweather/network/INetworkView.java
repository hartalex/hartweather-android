package com.hartcode.hartweather.libhartweather.network;

/**
 *
 */
public interface INetworkView {

    void onNetworkQueueChange(boolean isEmpty);
    void onNetworkError(String error);
}
