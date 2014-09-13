package com.sibilantsolutions.grison.driver.foscam.net;

import java.net.InetSocketAddress;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sibilantsolutions.grison.driver.foscam.domain.AudioStartRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.DecoderControlE;
import com.sibilantsolutions.grison.driver.foscam.domain.LoginRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.PresetAndDelay;
import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.domain.TalkStartRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.VerifyRespText;
import com.sibilantsolutions.grison.driver.foscam.domain.VideoStartRespText;
import com.sibilantsolutions.grison.evt.AudioHandlerI;
import com.sibilantsolutions.grison.evt.AudioStoppedEvt;
import com.sibilantsolutions.grison.evt.ImageHandlerI;
import com.sibilantsolutions.grison.evt.LostConnectionEvt;
import com.sibilantsolutions.grison.evt.LostConnectionHandlerI;
import com.sibilantsolutions.grison.evt.VideoStoppedEvt;
import com.sibilantsolutions.iptools.util.HexDumpDeferred;

public class FoscamSession
{
    final static private Logger log = LoggerFactory.getLogger( FoscamSession.class );

    final private FoscamService operationService;
    final private InetSocketAddress address;
    final private String cameraId;
    final private String firmwareVersion;

    private FoscamService audioVideoService;

    final private CgiService cgiService;

    final private AudioHandlerI audioHandler;
    final private ImageHandlerI imageHandler;

    private FoscamSession( FoscamService operationService, InetSocketAddress address, String cameraId, String firmwareVersion, CgiService cgiService, AudioHandlerI audioHandler, ImageHandlerI imageHandler )
    {
        this.operationService = operationService;
        this.address = address;
        this.cameraId = cameraId;
        this.firmwareVersion = firmwareVersion;
        this.cgiService = cgiService;
        this.audioHandler = audioHandler;
        this.imageHandler = imageHandler;
    }

    public void audioEnd()
    {
        log.info( "Audio end." );

        operationService.audioEnd();
    }

    public boolean audioStart()
    {
        log.info( "Audio start." );

        AudioStartRespText audioStartResp = operationService.audioStart();

        boolean success = false;

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

            success = true;
        }

        return success;
    }

    static public FoscamSession connect( InetSocketAddress address, String username, String password, AudioHandlerI audioHandler, ImageHandlerI imageHandler, final LostConnectionHandlerI lostConnectionHandler )
    {
        log.info( "Making session connection to={}, user={}.", address, username );

        FoscamConnection operationConnection = FoscamConnection.connect( address,
                ProtocolE.OPERATION_PROTOCOL );

        operationConnection.setLostConnectionHandler( lostConnectionHandler );

        FoscamService operationService = new FoscamService( operationConnection );

            //"Login" is a misnomer: it's really a request for a session to which we will
            //authenticate next with Verify.
        LoginRespText lr = operationService.login();

        if ( lr.getResultCode() == ResultCodeE.CORRECT )
        {
            VerifyRespText vr = operationService.verify( username, password );

            if ( vr.getResultCode() == ResultCodeE.CORRECT )
            {
                CgiService cgiService = new CgiService( address, username, password );

                return new FoscamSession( operationService, address, lr.getCameraId(),
                        lr.getFirmwareVersion(), cgiService, audioHandler, imageHandler );
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

            avConnection.setLostConnectionHandler( new LostConnectionHandlerI()
            {

                @Override
                public void onLostConnection( LostConnectionEvt evt )
                {
                    audioHandler.onAudioStopped( new AudioStoppedEvt() );
                    imageHandler.onVideoStopped( new VideoStoppedEvt() );
                }
            } );

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

    public void presetPatrol( List<PresetAndDelay> presetAndDelayMilliseconds )
    {
        boolean isRunning = true;

        for ( int i = 0; isRunning; i++ )
        {
            int index = i % presetAndDelayMilliseconds.size();
            PresetAndDelay pad = presetAndDelayMilliseconds.get( index );

            int preset = pad.getPreset();
            long durationMs = pad.getDurationMs();

            String url;

            switch ( preset )
            {
                case 1:
                    url = cgiService.decoderControl( DecoderControlE.GO_TO_PRESET_1, 0, 0 );
                    break;

                case 2:
                    url = cgiService.decoderControl( DecoderControlE.GO_TO_PRESET_2, 0, 0 );
                    break;

                case 3:
                    url = cgiService.decoderControl( DecoderControlE.GO_TO_PRESET_3, 0, 0 );
                    break;

                case 4:
                    url = cgiService.decoderControl( DecoderControlE.GO_TO_PRESET_4, 0, 0 );
                    break;

                case 5:
                    url = cgiService.decoderControl( DecoderControlE.GO_TO_PRESET_5, 0, 0 );
                    break;

                case 6:
                    url = cgiService.decoderControl( DecoderControlE.GO_TO_PRESET_6, 0, 0 );
                    break;

                case 7:
                    url = cgiService.decoderControl( DecoderControlE.GO_TO_PRESET_7, 0, 0 );
                    break;

                case 8:
                    url = cgiService.decoderControl( DecoderControlE.GO_TO_PRESET_8, 0, 0 );
                    break;

                case 9:
                    url = cgiService.decoderControl( DecoderControlE.GO_TO_PRESET_9, 0, 0 );
                    break;

                case 10:
                    url = cgiService.decoderControl( DecoderControlE.GO_TO_PRESET_10, 0, 0 );
                    break;

                case 11:
                    url = cgiService.decoderControl( DecoderControlE.GO_TO_PRESET_11, 0, 0 );
                    break;

                case 12:
                    url = cgiService.decoderControl( DecoderControlE.GO_TO_PRESET_12, 0, 0 );
                    break;

                case 13:
                    url = cgiService.decoderControl( DecoderControlE.GO_TO_PRESET_13, 0, 0 );
                    break;

                case 14:
                    url = cgiService.decoderControl( DecoderControlE.GO_TO_PRESET_14, 0, 0 );
                    break;

                case 15:
                    url = cgiService.decoderControl( DecoderControlE.GO_TO_PRESET_15, 0, 0 );
                    break;

                case 16:
                    url = cgiService.decoderControl( DecoderControlE.GO_TO_PRESET_16, 0, 0 );
                    break;

                default:
                    throw new IllegalArgumentException( "Unexpected preset=" + preset );
            }

            CgiService.exec( url );

            try
            {
                Thread.sleep( durationMs );
            }
            catch ( InterruptedException e )
            {
                // TODO Auto-generated catch block
                throw new UnsupportedOperationException( "MY TODO!", e );
            }
        }
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

    public boolean talkStart()
    {
        log.info( "Talk start." );

        TalkStartRespText talkStartResp = operationService.talkStart();

        boolean success = false;

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

            success = true;
        }

        return success;
    }

    public void videoEnd()
    {
        log.info( "Video end." );

        operationService.videoEnd();
    }

    public boolean videoStart()
    {
        log.info( "Video start." );

        VideoStartRespText videoStartResp = operationService.videoStart();

        boolean success = false;

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

            success = true;
        }

        return success;
    }

}
