package com.sibilantsolutions.grison.driver.foscam.domain;

public class LoginReqText implements DatastreamI
{

    private byte[] dataConnectionId;

    public byte[] getDataConnectionId()
    {
        return dataConnectionId;
    }

    public void setDataConnectionId(byte[] dataConnectionId)
    {
        this.dataConnectionId = dataConnectionId;
    }

    @Override
    public byte[] toDatastream()
    {
        return dataConnectionId;
    }

}
