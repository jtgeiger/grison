package com.sibilantsolutions.grison.driver.foscam.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.domain.AlarmNotifyText;
import com.sibilantsolutions.grison.evt.AlarmHandlerI;

/*package*/ class NoOpAlarmHandler implements AlarmHandlerI
{
    final static private Logger log = LoggerFactory.getLogger( NoOpAlarmHandler.class );

    @Override
    public void onReceive( AlarmNotifyText ant )
    {
        log.info( "Ignoring alarm data={}.", ant.getAlarmType() );
    }

}
