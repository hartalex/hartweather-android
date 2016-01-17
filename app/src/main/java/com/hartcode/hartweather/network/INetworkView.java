package com.hartcode.hartweather.network;

/**
 *
 */
public interface INetworkView {

    void onNetworkQueueChange(boolean isEmpty);
    void onNetworkError(String error);
}
