package com.sibilantsolutions.grison.driver.foscam.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.evt.AlarmEvt;
import com.sibilantsolutions.grison.evt.AlarmHandlerI;

/*package*/ class NoOpAlarmHandler implements AlarmHandlerI
{
    final static private Logger log = LoggerFactory.getLogger( NoOpAlarmHandler.class );

    @Override
    public void onAlarm( AlarmEvt evt )
    {
        log.info( "Ignoring alarm data={}.", evt.getAlarmNotify().getAlarmType() );
    }

}
