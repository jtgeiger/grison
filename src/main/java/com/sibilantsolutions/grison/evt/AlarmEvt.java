package com.sibilantsolutions.grison.evt;

import com.sibilantsolutions.grison.driver.foscam.domain.AlarmNotifyText;
import com.sibilantsolutions.grison.driver.foscam.net.FoscamSession;

public class AlarmEvt
{

    private final AlarmNotifyText alarmNotify;
    private final FoscamSession session;

    public AlarmEvt( AlarmNotifyText alarmNotify, FoscamSession session )
    {
        this.alarmNotify = alarmNotify;
        this.session = session;
    }

    public AlarmNotifyText getAlarmNotify()
    {
        return alarmNotify;
    }

    public FoscamSession getSession()
    {
        return session;
    }

}
