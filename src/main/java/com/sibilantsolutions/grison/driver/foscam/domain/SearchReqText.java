package com.sibilantsolutions.grison.driver.foscam.domain;

public class SearchReqText implements DatastreamI
{

    //private RESERVE       //INT8=0
    //private RESERVE       //INT8=0
    //private RESERVE       //INT8=0
    //private RESERVE       //INT8=1

    @Override
    public String toDatastream()
    {
        StringBuilder buf = new StringBuilder( 4 );

        buf.append( (char)0 );
        buf.append( (char)0 );
        buf.append( (char)0 );
        buf.append( (char)1 );

        return buf.toString();
    }

}
