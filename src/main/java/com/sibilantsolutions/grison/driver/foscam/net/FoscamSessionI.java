package com.sibilantsolutions.grison.driver.foscam.net;

import com.sibilantsolutions.grison.driver.foscam.domain.AlarmNotifyText;
import com.sibilantsolutions.grison.driver.foscam.domain.AudioDataText;
import com.sibilantsolutions.grison.driver.foscam.domain.VideoDataText;

public interface FoscamSessionI
{

    public void onAlarm( AlarmNotifyText alarmNotifyText );
    public void onLostConnection( FoscamConnection connection );
    public void onReceiveAudio( AudioDataText audioDataText );
    public void onReceiveVideo( VideoDataText videoDataText );

}
