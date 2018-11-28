package com.sibilantsolutions.grison.driver.foscam.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.entity.VideoDataTextEntity;
import com.sibilantsolutions.grison.evt.ImageHandlerI;

/*package*/ class NoOpImageHandler implements ImageHandlerI
{

    final static private Logger log = LoggerFactory.getLogger( NoOpImageHandler.class );

    @Override
    public void onReceive(VideoDataTextEntity videoData)
    {
        log.info( "Ignoring video data." );
    }

    @Override
    public void onVideoStopped()
    {
        log.info( "Ignoring video stopped." );
    }

}
