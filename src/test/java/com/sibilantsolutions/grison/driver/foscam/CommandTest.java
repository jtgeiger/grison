package com.sibilantsolutions.grison.driver.foscam;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sibilantsolutions.grison.util.ResourceLoader;

public class CommandTest
{

    @Test
    public void testToDatastream01()
    {
        Command c = new Command();

        c.setProtocol( Protocol.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCode.Login_Req );
        c.setCommandText( new EmptyText() );

        assertEquals( ResourceLoader.loadResource( "/samples/login_req.bin" ), c.toDatastream() );
    }

    @Test
    public void testToDatastream02()
    {
        Command c = new Command();

        c.setProtocol( Protocol.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCode.Login_Resp );
        LoginRespText lrt = new LoginRespText();
        c.setCommandText( lrt  );
        lrt.setResult( 0 );
        lrt.setCameraId( "00626E4E72BF" );
        lrt.setFirmwareVersion( "" + (char)11 + (char)37 + (char)2 + (char)56 );

        String expected = ResourceLoader.loadResource( "/samples/login_resp.bin" );
        String ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertEquals( expected, ds );
    }

}
