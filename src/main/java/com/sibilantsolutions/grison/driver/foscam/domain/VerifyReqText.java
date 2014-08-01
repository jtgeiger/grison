package com.sibilantsolutions.grison.driver.foscam.domain;

import java.nio.ByteBuffer;

import com.sibilantsolutions.grison.util.Convert;

public class VerifyReqText implements DatastreamI
{

    private String username;
    private String password;

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    @Override
    public byte[] toDatastream()
    {
        ByteBuffer bb = ByteBuffer.allocate( 13 + 13 );

        Convert.put( Convert.padRear( username, 12, (char)0 ), bb );     //TODO: Truncate.
        bb.put( (byte)0 );    //Null terminator.

        Convert.put( Convert.padRear( password, 12, (char)0 ), bb );     //TODO: Truncate.
        bb.put( (byte)0 );    //Null terminator.

        return bb.array();
    }

}
