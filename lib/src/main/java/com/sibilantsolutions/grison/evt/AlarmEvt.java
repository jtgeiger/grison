package com.sibilantsolutions.grison.evt;

import com.sibilantsolutions.grison.driver.foscam.domain.AlarmTypeE;

public class AlarmEvt
{

    private final AlarmTypeE alarmType;

    public AlarmEvt(AlarmTypeE alarmType)
    {
        this.alarmType = alarmType;
    }

    public AlarmTypeE getAlarmType()
    {
        return alarmType;
    }

}
