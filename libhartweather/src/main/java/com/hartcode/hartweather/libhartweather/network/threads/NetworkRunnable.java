/**
*
*    HartWeather - A Simple Weather Android App
*    Copyright (C) 2016  Alex Hart
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see 
*    <https://github.com/hartcode/hartweather-android/blob/master/LICENSE>.
*
*/

package com.hartcode.hartweather.libhartweather.network.threads;

import org.slf4j.*;

/**
 *
 */
public class NetworkRunnable implements Runnable
{
    private static final Logger logger = LoggerFactory.getLogger(NetworkRequestRunnable.class);
    protected static final long DEFAULT_BACK_OFF_TIME = 100;
    protected static final long DEFAULT_BACK_OFF_TIME_MAX = 60000;
    protected boolean isCanceled = false;
    protected long backoffTime = DEFAULT_BACK_OFF_TIME;
    protected final static int HTTP_OK = 200;
    protected final static double BACKOFF_MULTIPLE = 2;

    @Override
    public void run()
    {
        while(!this.isCanceled && !Thread.currentThread().isInterrupted())
        {

            this.doThreadWork();

            try {
                Thread.sleep(backoffTime);
            } catch (InterruptedException e) {
                logger.warn("Network Request Runnable Thread was interrupted.", e);
            }
        }
    }
    public void stopThread() {
        this.isCanceled = true;
    }

    protected void doThreadWork()
    {

    }


}
