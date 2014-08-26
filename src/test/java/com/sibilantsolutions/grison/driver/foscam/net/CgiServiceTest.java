package com.sibilantsolutions.grison.driver.foscam.net;

import static org.junit.Assert.assertEquals;

import java.net.InetSocketAddress;

import org.junit.Test;

import com.sibilantsolutions.grison.driver.foscam.domain.DecoderControlE;

public class CgiServiceTest
{

    @Test
    public void test()
    {
        CgiService svc = new CgiService( new InetSocketAddress( "host", 80 ), "myuser", "mypass" );

        assertEquals( "http://host:80/get_status.cgi?user=myuser&pwd=mypass", svc.getStatus() );
        assertEquals( "http://host:80/decoder_control.cgi?command=1&onestep=2&degree=3&user=myuser&pwd=mypass",
                svc.decoderControl( DecoderControlE.STOP_UP, 2, 3 ) );
        assertEquals( "http://host:80/decoder_control.cgi?command=31&onestep=2&degree=3&user=myuser&pwd=mypass",
                svc.decoderControl( DecoderControlE.GO_TO_PRESET_1, 2, 3 ) );
    }

}
