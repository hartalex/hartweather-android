package com.hartcode.hartweather.network.threads;

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

    @Override
    public void run()
    {
        while(!this.isCanceled && !Thread.currentThread().isInterrupted())
        {

            this.doThreadWork();

            try {
                logger.debug("Sleeping for {}ms",backoffTime);
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
