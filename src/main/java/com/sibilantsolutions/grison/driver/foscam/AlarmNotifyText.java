package com.sibilantsolutions.grison.driver.foscam;


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

    public static AlarmNotifyText parse( String data )
    {
        AlarmNotifyText text = new AlarmNotifyText();

        int i = 0;

        text.alarmType = AlarmTypeE.fromValue( data.charAt( i++ ) );

        i += 2;
        i += 2;
        i += 2;
        i += 2;

        return text;
    }

    @Override
    public String toDatastream()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException( "OGTE TODO!" );
    }

}
