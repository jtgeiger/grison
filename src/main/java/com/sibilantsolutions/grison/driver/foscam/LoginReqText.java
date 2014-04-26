package com.sibilantsolutions.grison.driver.foscam;

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
    public String toDatastream()
    {
        return dataConnectionId;
    }

}
