package com.sibilantsolutions.grison.demo;

import java.io.ByteArrayInputStream;

import com.sibilantsolutions.grison.driver.foscam.domain.AudioDataText;
import com.sibilantsolutions.grison.evt.AudioHandlerI;
import com.sibilantsolutions.grison.evt.AudioStoppedEvt;
import com.sibilantsolutions.grison.sound.adpcm.AdpcmDecoder;
import com.sibilantsolutions.grison.sound.player.MyPlayer;

public class DemoAudioHandler implements AudioHandlerI
{
    private final AdpcmDecoder adpcm = new AdpcmDecoder();
    private final MyPlayer player = new MyPlayer( AdpcmDecoder.getDecodeAudioFormat() );


    @Override
    public void onAudioStopped( AudioStoppedEvt audioStoppedEvt )
    {
        //No-op.
    }

    @Override
    public void onReceive( AudioDataText audioData )
    {
        byte[] dataBytes = audioData.getDataContent();
        byte[] decodeBlock = adpcm.decode( dataBytes );
        player.feed( new ByteArrayInputStream( decodeBlock ) );
    }

}
