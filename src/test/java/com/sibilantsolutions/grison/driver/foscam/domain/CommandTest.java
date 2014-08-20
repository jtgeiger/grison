package com.sibilantsolutions.grison.driver.foscam.domain;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sibilantsolutions.grison.util.Convert;
import com.sibilantsolutions.grison.util.ResourceReader;

public class CommandTest
{

    @Test
    public void testParse01()
    {
        byte[] bin = ResourceReader.readResourceAsBytes( "/samples/verify_resp.bin" );

        Command c = Command.parse( bin, 0, bin.length );
        assertEquals( ProtocolE.OPERATION_PROTOCOL, c.getProtocol() );
        assertEquals( OperationProtocolOpCodeE.Verify_Resp, c.getOpCode() );

        VerifyRespText text = (VerifyRespText)c.getCommandText();

        assertEquals( ResultCodeE.CORRECT, text.getResultCode() );
    }

    @Test
    public void testParse02()
    {
        byte[] bin = ResourceReader.readResourceAsBytes( "/samples/alarm_notify.bin" );

        Command c = Command.parse( bin, 0, bin.length );
        assertEquals( ProtocolE.OPERATION_PROTOCOL, c.getProtocol() );
        assertEquals( OperationProtocolOpCodeE.Alarm_Notify, c.getOpCode() );

        AlarmNotifyText text = (AlarmNotifyText)c.getCommandText();

        assertEquals( AlarmTypeE.UNK_01, text.getAlarmType() );
    }

    @Test
    public void testParse03()
    {
        byte[] bin = ResourceReader.readResourceAsBytes( "/samples/UNK02.bin" );

        Command c = Command.parse( bin, 0, bin.length );
        assertEquals( ProtocolE.OPERATION_PROTOCOL, c.getProtocol() );
        assertEquals( OperationProtocolOpCodeE.UNK02, c.getOpCode() );

        Unk02Text text = (Unk02Text)c.getCommandText();

        final int SIZE = 1152;  //0x0480
        byte[] d = new byte[SIZE];
        for ( int i = 0; i < SIZE; i++ )
            d[i] = 0;

        assertArrayEquals( d, text.getData() );
    }

    @Test
    public void testParse04()
    {
        byte[] bin = ResourceReader.readResourceAsBytes( "/samples/keep_alive.bin" );

        Command c = Command.parse( bin, 0, bin.length );
        assertEquals( ProtocolE.OPERATION_PROTOCOL, c.getProtocol() );
        assertEquals( OperationProtocolOpCodeE.Keep_Alive, c.getOpCode() );

        KeepAliveText text = (KeepAliveText)c.getCommandText();

        assertNotNull( text );
    }

    @Test
    public void testParse05()
    {
        byte[] bin = ResourceReader.readResourceAsBytes( "/samples/video_start_req.bin" );

        Command c = Command.parse( bin, 0, bin.length );
        assertEquals( ProtocolE.OPERATION_PROTOCOL, c.getProtocol() );
        assertEquals( OperationProtocolOpCodeE.Video_Start_Req, c.getOpCode() );

        VideoStartReqText text = (VideoStartReqText)c.getCommandText();

        assertEquals( 1, text.getData() );
    }

    @Test
    public void testParse06()
    {
        byte[] bin = ResourceReader.readResourceAsBytes( "/samples/video_start_resp.bin" );

        Command c = Command.parse( bin, 0, bin.length );
        assertEquals( ProtocolE.OPERATION_PROTOCOL, c.getProtocol() );
        assertEquals( OperationProtocolOpCodeE.Video_Start_Resp, c.getOpCode() );

        VideoStartRespText text = (VideoStartRespText)c.getCommandText();

        assertEquals( ResultCodeE.CORRECT, text.getResultCode() );
        assertEquals( "" + (char)0x40 + (char)0x1B + (char)0x25 + (char)0x60, text.getDataConnectionId() );
    }

    @Test
    public void testParse07()
    {
        byte[] bin = ResourceReader.readResourceAsBytes( "/samples/talk_start_req.bin" );

        Command c = Command.parse( bin, 0, bin.length );
        assertEquals( ProtocolE.OPERATION_PROTOCOL, c.getProtocol() );
        assertEquals( OperationProtocolOpCodeE.Talk_Start_Req, c.getOpCode() );

        TalkStartReqText text = (TalkStartReqText)c.getCommandText();

        assertEquals( 1, text.getData() );
    }

    @Test
    public void testParse08()
    {
        byte[] bin = ResourceReader.readResourceAsBytes( "/samples/talk_start_resp.bin" );

        Command c = Command.parse( bin, 0, bin.length );
        assertEquals( ProtocolE.OPERATION_PROTOCOL, c.getProtocol() );
        assertEquals( OperationProtocolOpCodeE.Talk_Start_Resp, c.getOpCode() );

        TalkStartRespText text = (TalkStartRespText)c.getCommandText();

        assertEquals( ResultCodeE.CORRECT, text.getResultCode() );
        assertEquals( "" + (char)0x7D + (char)0x26 + (char)0xF6 + (char)0x38, text.getDataConnectionId() );
    }

    @Test
    public void testParse09()
    {
        byte[] bin = ResourceReader.readResourceAsBytes( "/samples/video_data.bin" );

        Command c = Command.parse( bin, 0, bin.length );
        assertEquals( ProtocolE.AUDIO_VIDEO_PROTOCOL, c.getProtocol() );
        assertEquals( AudioVideoProtocolOpCodeE.Video_Data, c.getOpCode() );

        VideoDataText text = (VideoDataText)c.getCommandText();

        assertEquals( 0x0000E6E4, text.getTimestamp() );
        assertEquals( 0x537CCE75, text.getFramesPerSec() );
        assertEquals( 0x0000AE38, text.getDataContent().length ); //44600
    }

    @Test
    public void testParse10()
    {
        byte[] bin = ResourceReader.readResourceAsBytes( "/samples/search_resp.bin" );

        Command c = Command.parse( bin, 0, bin.length );
        assertEquals( ProtocolE.SEARCH_PROTOCOL, c.getProtocol() );
        assertEquals( SearchProtocolOpCodeE.Search_Resp, c.getOpCode() );

        SearchRespText text = (SearchRespText)c.getCommandText();

        assertEquals( "00626E4E72BF", text.getCameraId() );
        assertEquals( "cam1", text.getCameraName() );
        assertEquals( SearchRespText.getByAddress( new byte[]{ (byte)192, (byte)168, 69, 21 }  ), text.getCameraIP() );
        assertEquals( SearchRespText.getByAddress( new byte[]{ (byte)255, (byte)255, (byte)255, 0 }  ), text.getNetmask() );
        assertEquals( SearchRespText.getByAddress( new byte[]{ (byte)192, (byte)168, 69, 1 }  ), text.getGatewayIP() );
        assertEquals( SearchRespText.getByAddress( new byte[]{ (byte)192, (byte)168, 69, 1 }  ), text.getDnsIP() );
        assertEquals( "" + (char)11 + (char)37 + (char)2 + (char)56, text.getSysSoftwareVersion() );
        assertEquals( "" + (char)2 + (char)4 + (char)10 + (char)10, text.getAppSoftwareVersion() );
        assertEquals( 80, text.getCameraPort() );
        assertTrue( text.isDhcpEnabled() );
    }

    @Test
    public void testParse11()
    {
        byte[] bin = ResourceReader.readResourceAsBytes( "/samples/init_resp-pri_error.bin" );

        Command c = Command.parse( bin, 0, bin.length );
        assertEquals( ProtocolE.SEARCH_PROTOCOL, c.getProtocol() );
        assertEquals( SearchProtocolOpCodeE.Init_Resp, c.getOpCode() );

        InitRespText text = (InitRespText)c.getCommandText();

        assertEquals( ResultCodeE.PRI_ERROR, text.getResultCode() );
    }

    @Test
    public void testParse12()
    {
        byte[] bin = ResourceReader.readResourceAsBytes( "/samples/init_resp-user_wrong.bin" );

        Command c = Command.parse( bin, 0, bin.length );
        assertEquals( ProtocolE.SEARCH_PROTOCOL, c.getProtocol() );
        assertEquals( SearchProtocolOpCodeE.Init_Resp, c.getOpCode() );

        InitRespText text = (InitRespText)c.getCommandText();

        assertEquals( ResultCodeE.USER_WRONG, text.getResultCode() );
    }

    @Test
    public void testParse13()
    {
        byte[] bin = ResourceReader.readResourceAsBytes( "/samples/init_resp-succeed.bin" );

        Command c = Command.parse( bin, 0, bin.length );
        assertEquals( ProtocolE.SEARCH_PROTOCOL, c.getProtocol() );
        assertEquals( SearchProtocolOpCodeE.Init_Resp, c.getOpCode() );

        InitRespText text = (InitRespText)c.getCommandText();

        assertEquals( ResultCodeE.CORRECT, text.getResultCode() );
    }

    @Test
    public void testParse14()
    {
        byte[] bin = ResourceReader.readResourceAsBytes( "/samples/video_start_resp-no_data_conn_id.bin" );

        Command c = Command.parse( bin, 0, bin.length );
        assertEquals( ProtocolE.OPERATION_PROTOCOL, c.getProtocol() );
        assertEquals( OperationProtocolOpCodeE.Video_Start_Resp, c.getOpCode() );

        VideoStartRespText text = (VideoStartRespText)c.getCommandText();

        assertEquals( ResultCodeE.CORRECT, text.getResultCode() );
        assertNull( text.getDataConnectionId() );
    }

    @Test
    public void testParse15()
    {
        byte[] bin = ResourceReader.readResourceAsBytes( "/samples/search_resp02.bin" );

//        System.out.println( HexDump.prettyDump( bin ) );

        Command c = Command.parse( bin, 0, bin.length );
        assertEquals( ProtocolE.SEARCH_PROTOCOL, c.getProtocol() );
        assertEquals( SearchProtocolOpCodeE.Search_Resp, c.getOpCode() );

        SearchRespText text = (SearchRespText)c.getCommandText();

        assertEquals( "00626E4E72BF", text.getCameraId() );
        assertEquals( "cam1", text.getCameraName() );
        assertEquals( SearchRespText.getByAddress( new byte[]{ (byte)192, (byte)168, 69, 21 }  ), text.getCameraIP() );
        assertEquals( SearchRespText.getByAddress( new byte[]{ (byte)255, (byte)255, (byte)255, 0 }  ), text.getNetmask() );
        assertEquals( SearchRespText.getByAddress( new byte[]{ (byte)192, (byte)168, 69, 1 }  ), text.getGatewayIP() );
        assertEquals( SearchRespText.getByAddress( new byte[]{ (byte)192, (byte)168, 69, 1 }  ), text.getDnsIP() );
        assertEquals( "" + (char)11 + (char)37 + (char)2 + (char)56, text.getSysSoftwareVersion() );
        assertEquals( "" + (char)2 + (char)4 + (char)10 + (char)10, text.getAppSoftwareVersion() );
        assertEquals( 61234, text.getCameraPort() );
        assertTrue( text.isDhcpEnabled() );
    }

    @Test
    public void testParse16()
    {
        byte[] bin = ResourceReader.readResourceAsBytes( "/samples/init_req02.bin" );

//        System.out.println( HexDump.prettyDump( bin ) );

        Command c = Command.parse( bin, 0, bin.length );
        assertEquals( ProtocolE.SEARCH_PROTOCOL, c.getProtocol() );
        assertEquals( SearchProtocolOpCodeE.Init_Req, c.getOpCode() );

        InitReqText text = (InitReqText)c.getCommandText();

        assertEquals( "00626E4E72BF", text.getCameraId() );
        assertEquals( "camvis", text.getUsername() );
        assertEquals( "vis,FOSbuy1v", text.getPassword() );
        assertEquals( SearchRespText.getByAddress( new byte[]{ (byte)192, (byte)168, 69, 22 }  ), text.getCameraIP() );
        assertEquals( SearchRespText.getByAddress( new byte[]{ (byte)255, (byte)255, (byte)255, 0 }  ), text.getNetmask() );
        assertEquals( SearchRespText.getByAddress( new byte[]{ (byte)192, (byte)168, 69, 1 }  ), text.getGatewayIP() );
        assertEquals( SearchRespText.getByAddress( new byte[]{ (byte)192, (byte)168, 69, 1 }  ), text.getDnsIP() );
        assertEquals( 61234, text.getCameraPort() );
    }

    @Test
    public void testToDatastream01()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Login_Req );
        LoginReqText text = new LoginReqText();
        c.setCommandText( text );
        text.setDataConnectionId( "" );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/login_req.bin" );
        byte[] ds = c.toDatastream();

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream02()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Login_Resp );
        LoginRespText lrt = new LoginRespText();
        c.setCommandText( lrt  );
        lrt.setResultCode( ResultCodeE.CORRECT );
        lrt.setCameraId( "00626E4E72BF" );
        lrt.setFirmwareVersion( "" + (char)11 + (char)37 + (char)2 + (char)56 );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/login_resp.bin" );
        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream03()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Verify_Req );
        VerifyReqText text = new VerifyReqText();
        c.setCommandText( text );
        text.setUsername( "camvis" );
        text.setPassword( "vis,FOSbuy1v" );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/verify_req.bin" );
        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream04()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Verify_Resp );
        VerifyRespText text = new VerifyRespText();
        c.setCommandText( text );
        text.setResultCode( ResultCodeE.CORRECT );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/verify_resp.bin" );
        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream05()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.UNK01 );
        c.setCommandText( new EmptyText() );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/UNK01.bin" );
        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream06()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.UNK02 );
        Unk02Text text = new Unk02Text();
        c.setCommandText( text );
        final int SIZE = 1152;  //0x0480
        byte[] d = new byte[SIZE];
        for ( int i = 0; i < SIZE; i++ )
            d[i] = 0;
        text.setData( d );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/UNK02.bin" );
        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream07()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.UNK03 );
        Unk03Text text = new Unk03Text();
        c.setCommandText( text );
        StringBuilder buf = new StringBuilder();
        buf.append( "" + (char)0x20 + (char)0xA3 + (char)0x00 + (char)0x02 + (char)0x02 +
                (char)0x00 + (char)0x00 + (char)0x00 );
        text.setData( buf.toString() );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/UNK03.bin" );
        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream08()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Audio_Start_Req );
        AudioStartReqText text = new AudioStartReqText();
        c.setCommandText( text );
        text.setData( 2 );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/audio_start_req.bin" );
        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream09()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Audio_Start_Resp );
        AudioStartRespText text = new AudioStartRespText();
        c.setCommandText( text );
        text.setResultCode( ResultCodeE.CORRECT );
        text.setDataConnectionId( "" + (char)0x00 + (char)0x58 + (char)0xEA + (char)0x58 );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/audio_start_resp.bin" );
        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream10()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.AUDIO_VIDEO_PROTOCOL );
        c.setOpCode( AudioVideoProtocolOpCodeE.Login_Req );
        LoginReqText text = new LoginReqText();
        c.setCommandText( text );
        text.setDataConnectionId( "" + (char)0x00 + (char)0x58 + (char)0xEA + (char)0x58 );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/audio_login_req.bin" );
        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream11()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.AUDIO_VIDEO_PROTOCOL );
        c.setOpCode( AudioVideoProtocolOpCodeE.Audio_Data );
        AudioDataText text = new AudioDataText();
        c.setCommandText( text );
        text.setTimestamp( Convert.toNumLittleEndian( "" + (char)0x18 + (char)0xD9 + (char)0x03 + (char)0x00 ) );
        text.setSerialNumber( Convert.toNumLittleEndian( "" + (char)0xDC + (char)0xF5 + (char)0x00 + (char)0x00 ) );
        text.setGatherTime( Convert.toNumLittleEndian( "" + (char)0x6A + (char)0x75 + (char)0x5A + (char)0x53 ) );
        text.setAudioFormat( AudioFormatE.ADPCM );
        final int SIZE = 160;   //0xA0
        byte[] data = new byte[SIZE];
        for ( char i = 0; i < SIZE; i++ )
            data[i] = (byte)( i + 1 );
        text.setDataContent( data );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/audio_data-scrubbed.bin" );
        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream12()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Keep_Alive );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/keep_alive.bin" );
        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream13()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Video_Start_Req );
        VideoStartReqText text = new VideoStartReqText();
        c.setCommandText( text );
        text.setData( 1 );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/video_start_req.bin" );
        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream14()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Video_Start_Resp );
        VideoStartRespText text = new VideoStartRespText();
        c.setCommandText( text );
        text.setResultCode( ResultCodeE.CORRECT );
        text.setDataConnectionId( "" + (char)0x40 + (char)0x1B + (char)0x25 + (char)0x60 );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/video_start_resp.bin" );
        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream15()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Talk_Start_Req );
        TalkStartReqText text = new TalkStartReqText();
        c.setCommandText( text );
        text.setData( 1 );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/talk_start_req.bin" );
        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream16()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Talk_Start_Resp );
        TalkStartRespText text = new TalkStartRespText();
        c.setCommandText( text );
        text.setResultCode( ResultCodeE.CORRECT );
        text.setDataConnectionId( "" + (char)0x7D + (char)0x26 + (char)0xF6 + (char)0x38 );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/talk_start_resp.bin" );
        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream17()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.AUDIO_VIDEO_PROTOCOL );
        c.setOpCode( AudioVideoProtocolOpCodeE.Video_Data );
        VideoDataText text = new VideoDataText();
        c.setCommandText( text );
        text.setTimestamp( 0x0000E6E4 );
        text.setFramesPerSec( 0x537CCE75 );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/video_data.bin" );

        byte[] data = new byte[expected.length - 0x24];
        System.arraycopy( expected, 0x24, data, 0, data.length );   //Cheating to create the data.
        text.setDataContent( data );

        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream18()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.SEARCH_PROTOCOL );
        c.setOpCode( SearchProtocolOpCodeE.Search_Req );
        SearchReqText text = new SearchReqText();
        c.setCommandText( text );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/search_req.bin" );

        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream19()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.SEARCH_PROTOCOL );
        c.setOpCode( SearchProtocolOpCodeE.Search_Resp );
        SearchRespText text = new SearchRespText();
        c.setCommandText( text );

        text.setCameraId( "00626E4E72BF" );
        text.setCameraName( "cam1" );
        text.setCameraIP( SearchRespText.getByAddress( new byte[]{ (byte)192, (byte)168, 69, 21 }  ) );
        text.setNetmask( SearchRespText.getByAddress( new byte[]{ (byte)255, (byte)255, (byte)255, 0 }  ) );
        text.setGatewayIP( SearchRespText.getByAddress( new byte[]{ (byte)192, (byte)168, 69, 1 }  ) );
        text.setDnsIP( SearchRespText.getByAddress( new byte[]{ (byte)192, (byte)168, 69, 1 }  ) );
        text.setSysSoftwareVersion( "" + (char)11 + (char)37 + (char)2 + (char)56 );
        text.setAppSoftwareVersion( "" + (char)2 + (char)4 + (char)10 + (char)10 );
        text.setCameraPort( 80 );
        text.setDhcpEnabled( true );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/search_resp.bin" );

        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream20()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.SEARCH_PROTOCOL );
        c.setOpCode( SearchProtocolOpCodeE.Init_Req );
        InitReqText text = new InitReqText();
        c.setCommandText( text );

        text.setCameraId( "00626E4E72BF" );
        text.setUsername( "camvis" );
        text.setPassword( "vis,FOSbuy1v" );
        text.setCameraIP( SearchRespText.getByAddress( new byte[]{ (byte)192, (byte)168, 69, 22 }  ) );
        text.setNetmask( SearchRespText.getByAddress( new byte[]{ (byte)255, (byte)255, (byte)255, 0 }  ) );
        text.setGatewayIP( SearchRespText.getByAddress( new byte[]{ (byte)192, (byte)168, 69, 1 }  ) );
        text.setDnsIP( SearchRespText.getByAddress( new byte[]{ (byte)192, (byte)168, 69, 1 }  ) );
        text.setCameraPort( 80 );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/init_req.bin" );

        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

    @Test
    public void testToDatastream21()
    {
        Command c = new Command();

        c.setProtocol( ProtocolE.SEARCH_PROTOCOL );
        c.setOpCode( SearchProtocolOpCodeE.Init_Req );
        InitReqText text = new InitReqText();
        c.setCommandText( text );

        text.setCameraId( "00626E4E72BF" );
        text.setUsername( "camvis" );
        text.setPassword( "vis,FOSbuy1v" );
        text.setCameraIP( SearchRespText.getByAddress( new byte[]{ (byte)192, (byte)168, 69, 22 }  ) );
        text.setNetmask( SearchRespText.getByAddress( new byte[]{ (byte)255, (byte)255, (byte)255, 0 }  ) );
        text.setGatewayIP( SearchRespText.getByAddress( new byte[]{ (byte)192, (byte)168, 69, 1 }  ) );
        text.setDnsIP( SearchRespText.getByAddress( new byte[]{ (byte)192, (byte)168, 69, 1 }  ) );
        text.setCameraPort( 61234 );

        byte[] expected = ResourceReader.readResourceAsBytes( "/samples/init_req02.bin" );

        byte[] ds = c.toDatastream();

//        System.out.println( HexDump.prettyDump( expected ) );
//        System.out.println( HexDump.prettyDump( ds ) );

        assertArrayEquals( expected, ds );
    }

}
