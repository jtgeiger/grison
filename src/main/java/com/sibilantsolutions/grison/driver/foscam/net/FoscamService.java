package com.sibilantsolutions.grison.driver.foscam.net;

import com.sibilantsolutions.grison.driver.foscam.domain.*;

public class FoscamService
{
    //final static private Logger log = LoggerFactory.getLogger( FoscamService.class );

    private FoscamConnection connection;

    final private long startMs = System.currentTimeMillis();
    private long serialNumber = 1;

    public FoscamService( FoscamConnection connection )
    {
        this.connection = connection;
    }

    public void audioEnd()
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Audio_End );

        connection.sendAsync( c );
    }

    public AudioStartRespText audioStart()
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Audio_Start_Req );
        AudioStartReqText audio = new AudioStartReqText();
        c.setCommandText( audio );
        audio.setData( 1 );

        Command response = sendReceive( c );

            //Blind cast; what can we do about it if the wrong thing was received?
        return (AudioStartRespText)response.getCommandText();
    }

    public void audioVideoLogin(byte[] dataConnectionId)
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.AUDIO_VIDEO_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Login_Req );
        LoginReqText login = new LoginReqText();
        c.setCommandText( login );
        login.setDataConnectionId( dataConnectionId );

        connection.sendAsync( c );
    }

    public LoginRespText login()
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Login_Req );
        LoginReqText login = new LoginReqText();
        c.setCommandText( login );
        login.setDataConnectionId(new byte[0]);

        Command response = sendReceive( c );

            //Blind cast; what can we do about it if the wrong thing was received?
        return (LoginRespText)response.getCommandText();
    }

    private Command sendReceive( Command request )
    {
        return connection.sendReceive( request );
    }


    public void talkEnd()
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Talk_End );

        connection.sendAsync( c );
    }

    public void talkSend( byte[] adpcm )
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.AUDIO_VIDEO_PROTOCOL );
        c.setOpCode( AudioVideoProtocolOpCodeE.Talk_Data );
        TalkDataText text = new TalkDataText();
        c.setCommandText( text );
        long now = System.currentTimeMillis();
        long uptimeMs = now - startMs;
        text.setUptimeMs( uptimeMs );
        text.setSerialNumber( serialNumber++ );
        text.setTimestampMs( now );
        text.setAudioFormat( AudioFormatE.ADPCM );
        text.setDataContent( adpcm );

        connection.sendAsync( c );
    }

    public TalkStartRespText talkStart()
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Talk_Start_Req );
        TalkStartReqText talk = new TalkStartReqText();
        c.setCommandText( talk );
        talk.setData( 1 );

        Command response = sendReceive( c );

            //Blind cast; what can we do about it if the wrong thing was received?
        return (TalkStartRespText)response.getCommandText();
    }

    public VerifyRespText verify( final String username, final String password )
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Verify_Req );
        VerifyReqText verify = new VerifyReqText();
        c.setCommandText( verify );
        verify.setUsername( username );
        verify.setPassword( password );

        Command response = sendReceive( c );

            //Blind cast; what can we do about it if the wrong thing was received?
        return (VerifyRespText)response.getCommandText();
    }

    public void videoEnd()
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Video_End );

        connection.sendAsync( c );
    }

    public VideoStartRespText videoStart()
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Video_Start_Req );
        VideoStartReqText video = new VideoStartReqText();
        c.setCommandText( video );
        video.setData( 1 );

        Command response = sendReceive( c );

            //Blind cast; what can we do about it if the wrong thing was received?
        return (VideoStartRespText)response.getCommandText();
    }

}
