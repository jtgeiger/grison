package com.sibilantsolutions.grison.driver.foscam;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sibilantsolutions.grison.util.Convert;
import com.sibilantsolutions.grison.util.ResourceLoader;

public class CommandTest
{

    @Test
    public void testParse01()
    {
        String bin = ResourceLoader.loadResource( "/samples/verify_resp.bin" );

        Command c = Command.parse( bin );

        VerifyRespText text = (VerifyRespText)c.getCommandText();

        assertEquals( ResultCode.CORRECT, text.getResultCode() );
    }

    @Test
    public void testParse02()
    {
        String bin = ResourceLoader.loadResource( "/samples/alarm_notify.bin" );

        Command c = Command.parse( bin );

        AlarmNotifyText text = (AlarmNotifyText)c.getCommandText();

        assertEquals( AlarmTypeE.UNK_01, text.getAlarmType() );
    }

    @Test
    public void testParse03()
    {
        String bin = ResourceLoader.loadResource( "/samples/UNK02.bin" );

        Command c = Command.parse( bin );

        Unk02Text text = (Unk02Text)c.getCommandText();

        final int SIZE = 1152;  //0x0480
        StringBuilder buf = new StringBuilder( SIZE );
        for ( int i = 0; i < SIZE; i++ )
            buf.append( (char)0 );

        assertEquals( buf.toString(), text.getData() );
    }

    @Test
    public void testToDatastream01()
    {
        Command c = new Command();

        c.setProtocol( Protocol.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCode.Login_Req );
        LoginReqText text = new LoginReqText();
        c.setCommandText( text );
        text.setDataConnectionId( "" );

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
        lrt.setResultCode( ResultCode.CORRECT );
        lrt.setCameraId( "00626E4E72BF" );
        lrt.setFirmwareVersion( "" + (char)11 + (char)37 + (char)2 + (char)56 );

        String expected = ResourceLoader.loadResource( "/samples/login_resp.bin" );
        String ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertEquals( expected, ds );
    }

    @Test
    public void testToDatastream03()
    {
        Command c = new Command();

        c.setProtocol( Protocol.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCode.Verify_Req );
        VerifyReqText text = new VerifyReqText();
        c.setCommandText( text );
        text.setUsername( "camvis" );
        text.setPassword( "vis,FOSbuy1v" );

        String expected = ResourceLoader.loadResource( "/samples/verify_req.bin" );
        String ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertEquals( expected, ds );
    }

    @Test
    public void testToDatastream04()
    {
        Command c = new Command();

        c.setProtocol( Protocol.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCode.Verify_Resp );
        VerifyRespText text = new VerifyRespText();
        c.setCommandText( text );
        text.setResultCode( ResultCode.CORRECT );

        String expected = ResourceLoader.loadResource( "/samples/verify_resp.bin" );
        String ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertEquals( expected, ds );
    }

    @Test
    public void testToDatastream05()
    {
        Command c = new Command();

        c.setProtocol( Protocol.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCode.UNK01 );
        c.setCommandText( new EmptyText() );

        String expected = ResourceLoader.loadResource( "/samples/UNK01.bin" );
        String ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertEquals( expected, ds );
    }

    @Test
    public void testToDatastream06()
    {
        Command c = new Command();

        c.setProtocol( Protocol.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCode.UNK02 );
        Unk02Text text = new Unk02Text();
        c.setCommandText( text );
        final int SIZE = 1152;  //0x0480
        StringBuilder buf = new StringBuilder( SIZE );
        for ( int i = 0; i < SIZE; i++ )
            buf.append( (char)0 );
        text.setData( buf.toString() );

        String expected = ResourceLoader.loadResource( "/samples/UNK02.bin" );
        String ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertEquals( expected, ds );
    }

    @Test
    public void testToDatastream07()
    {
        Command c = new Command();

        c.setProtocol( Protocol.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCode.UNK03 );
        Unk03Text text = new Unk03Text();
        c.setCommandText( text );
        StringBuilder buf = new StringBuilder();
        buf.append( "" + (char)0x20 + (char)0xA3 + (char)0x00 + (char)0x02 + (char)0x02 +
                (char)0x00 + (char)0x00 + (char)0x00 );
        text.setData( buf.toString() );

        String expected = ResourceLoader.loadResource( "/samples/UNK03.bin" );
        String ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertEquals( expected, ds );
    }

    @Test
    public void testToDatastream08()
    {
        Command c = new Command();

        c.setProtocol( Protocol.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCode.Audio_Start_Req );
        AudioStartReqText text = new AudioStartReqText();
        c.setCommandText( text );
        text.setData( 2 );

        String expected = ResourceLoader.loadResource( "/samples/audio_start_req.bin" );
        String ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertEquals( expected, ds );
    }

    @Test
    public void testToDatastream09()
    {
        Command c = new Command();

        c.setProtocol( Protocol.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCode.Audio_Start_Resp );
        AudioStartRespText text = new AudioStartRespText();
        c.setCommandText( text );
        text.setResultCode( ResultCode.CORRECT );
        text.setDataConnectionId( "" + (char)0x00 + (char)0x58 + (char)0xEA + (char)0x58 );

        String expected = ResourceLoader.loadResource( "/samples/audio_start_resp.bin" );
        String ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertEquals( expected, ds );
    }

    @Test
    public void testToDatastream10()
    {
        Command c = new Command();

        c.setProtocol( Protocol.AUDIO_VIDEO_PROTOCOL );
        c.setOpCode( AudioVideoProtocolOpCode.Login_Req );
        LoginReqText text = new LoginReqText();
        c.setCommandText( text );
        text.setDataConnectionId( "" + (char)0x00 + (char)0x58 + (char)0xEA + (char)0x58 );

        String expected = ResourceLoader.loadResource( "/samples/audio_login_req.bin" );
        String ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertEquals( expected, ds );
    }

    @Test
    public void testToDatastream11()
    {
        Command c = new Command();

        c.setProtocol( Protocol.AUDIO_VIDEO_PROTOCOL );
        c.setOpCode( AudioVideoProtocolOpCode.Audio_Data );
        AudioDataText text = new AudioDataText();
        c.setCommandText( text );
        text.setTimestamp( Convert.toNumLittleEndian( "" + (char)0x18 + (char)0xD9 + (char)0x03 + (char)0x00 ) );
        text.setSerialNumber( Convert.toNumLittleEndian( "" + (char)0xDC + (char)0xF5 + (char)0x00 + (char)0x00 ) );
        text.setGatherTime( Convert.toNumLittleEndian( "" + (char)0x6A + (char)0x75 + (char)0x5A + (char)0x53 ) );
        text.setAudioFormat( AudioFormat.ADPCM );
        final int SIZE = 160;   //0xA0
        StringBuilder buf = new StringBuilder( SIZE );
        for ( char i = 0; i < SIZE; i++ )
            buf.append( (char)( i + 1 ) );
        text.setDataContent( buf.toString() );

        String expected = ResourceLoader.loadResource( "/samples/audio_data-scrubbed.bin" );
        String ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertEquals( expected, ds );
    }

}
