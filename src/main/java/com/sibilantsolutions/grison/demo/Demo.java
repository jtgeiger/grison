package com.sibilantsolutions.grison.demo;

import java.net.InetSocketAddress;

import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.domain.AudioStartRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.LoginRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.domain.TalkStartRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.VerifyRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.VideoStartRespText;
import com.sibilantsolutions.grison.driver.foscam.net.FoscamConnection;
import com.sibilantsolutions.grison.driver.foscam.net.FoscamService;
import com.sibilantsolutions.grison.evt.AudioHandlerI;
import com.sibilantsolutions.grison.ui.Ui;
import com.sibilantsolutions.iptools.util.HexDumpDeferred;

public class Demo
{
    final static private Logger log = LoggerFactory.getLogger( Demo.class );

    static private AudioHandlerI audioHandler = new DemoAudioHandler();
    static private JLabel label = new JLabel();
    static private DemoImageHandler imageHandler = new DemoImageHandler();
    static
    {
        imageHandler.setLabel( label );
    }

    static public void demo( final String hostname, final int port, String username, String password )
    {
        Ui.buildUi( label );

        FoscamConnection connection = FoscamConnection.connect( new InetSocketAddress( hostname, port ) );

        FoscamService service = new FoscamService( connection );

        LoginRespText lr = service.login();

        log.info( "Connected to cam={}.", lr.getCameraId() );

        VerifyRespText vr = service.verify( username, password );

        log.info( "Login result={}.", vr.getResultCode() );

        audioStart( hostname, port, service );
        //talkStart( service );
        videoStart( hostname, port, service );

    }

    private static void audioStart( String hostname, int port, FoscamService service )
    {
        AudioStartRespText audioStartResp = service.audioStart();

        if ( audioStartResp.getResultCode() == ResultCodeE.CORRECT )
        {
            String dataConnectionId = audioStartResp.getDataConnectionId();

            if ( dataConnectionId != null )
            {
                log.info( "Audio start connection id={}.", HexDumpDeferred.simpleDump( dataConnectionId ) );

                connectAudioVideo( hostname, port, dataConnectionId );
            }
            else
            {
                log.info( "Audio start success.  Audio should start coming on existing A/V connection." );
            }
        }
    }

    private static void connectAudioVideo( String hostname, int port, String dataConnectionId )
    {
        FoscamConnection audioConnection = FoscamConnection.connect( new InetSocketAddress( hostname, port ) );

        audioConnection.setAudioHandler( audioHandler );
        audioConnection.setImageHandler( imageHandler );

        FoscamService audioService = new FoscamService( audioConnection );

        audioService.audioVideoLogin( dataConnectionId );
    }

    private static void talkStart( FoscamService service )
    {
        TalkStartRespText talkStartResp = service.talkStart();

        if ( talkStartResp.getResultCode() == ResultCodeE.CORRECT )
        {
            String dataConnectionId = talkStartResp.getDataConnectionId();

            log.info( "Talk start connection id={}.", HexDumpDeferred.simpleDump( dataConnectionId ) );

            //TODO
        }
    }

    private static void videoStart( String hostname, int port, FoscamService service )
    {
        VideoStartRespText videoStartResp = service.videoStart();

        if ( videoStartResp.getResultCode() == ResultCodeE.CORRECT )
        {
            String dataConnectionId = videoStartResp.getDataConnectionId();

            if ( dataConnectionId != null )
            {
                log.info( "Video start connection id={}.", HexDumpDeferred.simpleDump( dataConnectionId ) );

                connectAudioVideo( hostname, port, dataConnectionId );
            }
            else
            {
                log.info( "Video start success.  Video should start coming on existing A/V connection." );
            }
        }
    }

}
