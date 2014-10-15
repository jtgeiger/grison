package com.sibilantsolutions.grison.driver.foscam.domain;

import com.sibilantsolutions.utils.util.Convert;

public class LoginReqText implements DatastreamI
{

    private String dataConnectionId;

    public String getDataConnectionId()
    {
        return dataConnectionId;
    }

    public void setDataConnectionId( String dataConnectionId )
    {
        this.dataConnectionId = dataConnectionId;
    }

    @Override
    public byte[] toDatastream()
    {
        return dataConnectionId.getBytes( Convert.cs );
    }

}
