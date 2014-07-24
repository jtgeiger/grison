package com.sibilantsolutions.grison.driver.foscam.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.domain.VideoDataText;
import com.sibilantsolutions.grison.evt.ImageHandlerI;

/*package*/ class NoOpImageHandler implements ImageHandlerI
{

    final static private Logger log = LoggerFactory.getLogger( NoOpImageHandler.class );

    @Override
    public void onReceive( VideoDataText videoData )
    {
        log.info( "Ignoring video data." );
    }

}
