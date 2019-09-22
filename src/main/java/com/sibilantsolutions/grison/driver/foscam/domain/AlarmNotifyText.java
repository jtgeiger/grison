package com.sibilantsolutions.grison.driver.foscam.domain;


import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;

public class AlarmNotifyText
{

    private AlarmTypeE alarmType;       //INT8
    //private String RESERVE            //INT16 (2 bytes; little endian)
    //private String RESERVE            //INT16 (2 bytes; little endian)
    //private String RESERVE            //INT16 (2 bytes; little endian)
    //private String RESERVE            //INT16 (2 bytes; little endian)

    public AlarmTypeE getAlarmType()
    {
        return alarmType;
    }

    public void setAlarmType( AlarmTypeE alarmType )
    {
        this.alarmType = alarmType;
    }

    public static AlarmNotifyText parse( byte[] data, int offset, int length )
    {
        AlarmNotifyText text = new AlarmNotifyText();

        text.alarmType = AlarmTypeE.fromValue(FosInt8.create(data[offset]));

        return text;
    }

    public byte[] toDatastream()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException( "OGTE TODO!" );
    }

}
