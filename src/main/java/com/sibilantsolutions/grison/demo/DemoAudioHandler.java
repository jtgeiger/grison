package com.sibilantsolutions.grison.demo;

import static com.sibilantsolutions.grison.sound.adpcm.AdpcmDecoderAudioFormat.decodeAudioFormat;

import java.io.ByteArrayInputStream;

import com.sibilantsolutions.grison.driver.foscam.entity.AudioDataTextEntity;
import com.sibilantsolutions.grison.evt.AudioHandlerI;
import com.sibilantsolutions.grison.evt.AudioStoppedEvt;
import com.sibilantsolutions.grison.sound.adpcm.AdpcmDecoder;
import com.sibilantsolutions.grison.sound.player.MyPlayer;

public class DemoAudioHandler implements AudioHandlerI
{
    private final AdpcmDecoder adpcm = new AdpcmDecoder();
    private final MyPlayer player = new MyPlayer(decodeAudioFormat);

    private AudioDataTextEntity lastAudioDataTextEntity = null;

    @Override
    public void onAudioStopped( AudioStoppedEvt audioStoppedEvt )
    {
        //No-op.
    }

    @Override
    public void onReceive(AudioDataTextEntity audioData)
    {
        //We'll get invoked for every state change (including video), but only process if the audio changed.
        if (audioData.equals(lastAudioDataTextEntity)) {
            return;
        }

        lastAudioDataTextEntity = audioData;

        byte[] dataBytes = audioData.data();
        byte[] decodeBlock = adpcm.decode( dataBytes );
        player.feed( new ByteArrayInputStream( decodeBlock ) );
    }

}
