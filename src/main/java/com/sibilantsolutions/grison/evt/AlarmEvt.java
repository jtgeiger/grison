package com.sibilantsolutions.grison.evt;

import com.sibilantsolutions.grison.driver.foscam.domain.AlarmNotifyText;

public class AlarmEvt
{

    private final AlarmNotifyText alarmNotify;

    public AlarmEvt(AlarmNotifyText alarmNotify)
    {
        this.alarmNotify = alarmNotify;
    }

    public AlarmNotifyText getAlarmNotify()
    {
        return alarmNotify;
    }

}
