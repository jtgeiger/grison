package com.sibilantsolutions.grison.driver.foscam.domain;

public class SearchReqText implements DatastreamI
{

    //private RESERVE       //INT8=0
    //private RESERVE       //INT8=0
    //private RESERVE       //INT8=0
    //private RESERVE       //INT8=1

    @Override
    public byte[] toDatastream()
    {
        byte[] b = { 0, 0, 0, 1 };

        return b;
    }

}
