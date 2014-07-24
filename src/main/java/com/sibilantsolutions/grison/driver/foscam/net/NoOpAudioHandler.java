package com.sibilantsolutions.grison.driver.foscam.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.domain.AudioDataText;
import com.sibilantsolutions.grison.evt.AudioHandlerI;

/*package*/ class NoOpAudioHandler implements AudioHandlerI
{

    final static private Logger log = LoggerFactory.getLogger( NoOpAudioHandler.class );

    @Override
    public void onReceive( AudioDataText audioData )
    {
        log.info( "Ignoring audio data." );
    }

}
