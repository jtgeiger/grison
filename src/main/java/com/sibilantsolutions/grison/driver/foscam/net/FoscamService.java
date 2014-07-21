package com.sibilantsolutions.grison.driver.foscam.net;

import com.sibilantsolutions.grison.driver.foscam.domain.AudioStartReqText;
import com.sibilantsolutions.grison.driver.foscam.domain.AudioStartRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.Command;
import com.sibilantsolutions.grison.driver.foscam.domain.LoginReqText;
import com.sibilantsolutions.grison.driver.foscam.domain.LoginRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.OperationProtocolOpCodeE;
import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import com.sibilantsolutions.grison.driver.foscam.domain.TalkStartReqText;
import com.sibilantsolutions.grison.driver.foscam.domain.TalkStartRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.VerifyReqText;
import com.sibilantsolutions.grison.driver.foscam.domain.VerifyRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.VideoStartReqText;
import com.sibilantsolutions.grison.driver.foscam.domain.VideoStartRespText;

public class FoscamService
{

    private FoscamConnection connection;

    public FoscamService( FoscamConnection connection )
    {
        this.connection = connection;
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

    public void audioVideoLogin( String dataConnectionId )
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.AUDIO_VIDEO_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Login_Req );
        LoginReqText login = new LoginReqText();
        c.setCommandText( login );
        login.setDataConnectionId( dataConnectionId );

        connection.sendNoReceive( c );
    }

    public LoginRespText login()
    {
        Command c = new Command();
        c.setProtocol( ProtocolE.OPERATION_PROTOCOL );
        c.setOpCode( OperationProtocolOpCodeE.Login_Req );
        LoginReqText login = new LoginReqText();
        c.setCommandText( login );
        login.setDataConnectionId( "" );

        Command response = sendReceive( c );

            //Blind cast; what can we do about it if the wrong thing was received?
        return (LoginRespText)response.getCommandText();
    }

    private Command sendReceive( Command request )
    {
        return connection.sendReceive( request );
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
