package com.hartcode.hartweather.list;

import com.hartcode.hartweather.libweatherapi.*;

/**
 *
 */
public interface IView {
    void showErrorMessage(String message);
    void weatherDataChange();
}
