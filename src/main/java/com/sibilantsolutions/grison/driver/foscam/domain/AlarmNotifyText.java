package com.sibilantsolutions.grison.driver.foscam.domain;


public class AlarmNotifyText implements DatastreamI
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

        text.alarmType = AlarmTypeE.fromValue( data[offset] );

        return text;
    }

    @Override
    public byte[] toDatastream()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException( "OGTE TODO!" );
    }

}
