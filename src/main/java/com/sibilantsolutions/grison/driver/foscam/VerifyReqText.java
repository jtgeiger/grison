package com.sibilantsolutions.grison.driver.foscam;

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
    public String toDatastream()
    {
        StringBuilder buf = new StringBuilder( 13 + 13 );

        buf.append( Convert.padRear( username, 12, (char)0 ) );     //TODO: Truncate.
        buf.append( (char)0 );    //Null terminator.

        buf.append( Convert.padRear( password, 12, (char)0 ) );     //TODO: Truncate.
        buf.append( (char)0 );    //Null terminator.

        return buf.toString();
    }

}
