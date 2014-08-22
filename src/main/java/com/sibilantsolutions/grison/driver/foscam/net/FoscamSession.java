package com.sibilantsolutions.grison.driver.foscam.net;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.domain.AudioStartRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.LoginRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.domain.TalkStartRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.VerifyRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.VideoStartRespText;
import com.sibilantsolutions.grison.evt.AudioHandlerI;
import com.sibilantsolutions.grison.evt.ImageHandlerI;
import com.sibilantsolutions.iptools.util.HexDumpDeferred;

public class FoscamSession
{
    final static private Logger log = LoggerFactory.getLogger( FoscamSession.class );

    final private FoscamService operationService;
    final private InetSocketAddress address;
    final private String cameraId;
    final private String firmwareVersion;

    private FoscamService audioVideoService;

    private AudioHandlerI audioHandler;
    private ImageHandlerI imageHandler;

    private FoscamSession( FoscamService operationService, InetSocketAddress address, String cameraId, String firmwareVersion, AudioHandlerI audioHandler, ImageHandlerI imageHandler )
    {
        this.operationService = operationService;
        this.address = address;
        this.cameraId = cameraId;
        this.firmwareVersion = firmwareVersion;
        this.audioHandler = audioHandler;
        this.imageHandler = imageHandler;
    }

    public void audioEnd()
    {
        log.info( "Audio end." );

        operationService.audioEnd();
    }

    public void audioStart()
    {
        log.info( "Audio start." );

        AudioStartRespText audioStartResp = operationService.audioStart();

        if ( audioStartResp.getResultCode() == ResultCodeE.CORRECT )
        {
                //May be null if a/v connection is already established.
            String dataConnectionId = audioStartResp.getDataConnectionId();

            if ( dataConnectionId != null )
            {
                log.info( "Audio start connection id={}.", HexDumpDeferred.simpleDump( dataConnectionId ) );

                connectAudioVideo( dataConnectionId );
            }
            else
            {
                log.info( "Audio start success.  Audio should start coming on existing A/V connection." );
            }
        }
    }

    static public FoscamSession connect( InetSocketAddress address, String username, String password, AudioHandlerI audioHandler, ImageHandlerI imageHandler )
    {
        log.info( "Making session connection to={}, user={}.", address, username );

        FoscamConnection connection = FoscamConnection.connect( address,
                ProtocolE.OPERATION_PROTOCOL );

        FoscamService service = new FoscamService( connection );

            //"Login" is a misnomer: it's really a request for a session to which we will
            //authenticate next with Verify.
        LoginRespText lr = service.login();

        if ( lr.getResultCode() == ResultCodeE.CORRECT )
        {
            VerifyRespText vr = service.verify( username, password );

            if ( vr.getResultCode() == ResultCodeE.CORRECT )
            {
                return new FoscamSession( service, address, lr.getCameraId(),
                        lr.getFirmwareVersion(), audioHandler, imageHandler );
            }
            else
            {
                throw new RuntimeException( "Camera denied login request: " + vr.getResultCode() );
            }
        }
        else
        {
            throw new RuntimeException( "Camera denied session request: " + lr.getResultCode() );
        }

    }

    private void connectAudioVideo( String dataConnectionId )
    {
        if ( audioVideoService == null )
        {
            log.info( "Making Audio/Video connection to={} with data connection id={}.",
                    address, HexDumpDeferred.simpleDump( dataConnectionId ) );

            FoscamConnection avConnection = FoscamConnection.connect( address,
                    ProtocolE.AUDIO_VIDEO_PROTOCOL );

            avConnection.setAudioHandler( audioHandler );
            avConnection.setImageHandler( imageHandler );

            audioVideoService = new FoscamService( avConnection );

            audioVideoService.audioVideoLogin( dataConnectionId );
        }
        else
        {
            log.info( "Audio/Video connection already established." );
        }
    }

    public String getCameraId()
    {
        return cameraId;
    }

    public String getFirmwareVersion()
    {
        return firmwareVersion;
    }

    public void talkEnd()
    {
        log.info( "Talk end." );

        operationService.talkEnd();
    }

    public void talkSend( byte[] adpcm )
    {
        audioVideoService.talkSend( adpcm );
    }

    public void talkStart()
    {
        log.info( "Talk start." );

        TalkStartRespText talkStartResp = operationService.talkStart();

        if ( talkStartResp.getResultCode() == ResultCodeE.CORRECT )
        {
                //May be null if a/v connection is already established.
            String dataConnectionId = talkStartResp.getDataConnectionId();

            if ( dataConnectionId != null )
            {
                log.info( "Talk start connection id={}.", HexDumpDeferred.simpleDump( dataConnectionId ) );

                connectAudioVideo( dataConnectionId );
            }
            else
            {
                log.info( "Talk start success.  Talk can be sent on existing A/V connection." );
            }
        }
    }

    public void videoEnd()
    {
        log.info( "Video end." );

        operationService.videoEnd();
    }

    public void videoStart()
    {
        log.info( "Video start." );

        VideoStartRespText videoStartResp = operationService.videoStart();

        if ( videoStartResp.getResultCode() == ResultCodeE.CORRECT )
        {
                //May be null if a/v connection is already established.
            String dataConnectionId = videoStartResp.getDataConnectionId();

            if ( dataConnectionId != null )
            {
                log.info( "Video start connection id={}.", HexDumpDeferred.simpleDump( dataConnectionId ) );

                connectAudioVideo( dataConnectionId );
            }
            else
            {
                log.info( "Video start success.  Video should start coming on existing A/V connection." );
            }
        }
    }

}
