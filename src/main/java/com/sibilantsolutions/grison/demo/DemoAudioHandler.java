package com.sibilantsolutions.grison.demo;

import java.io.ByteArrayInputStream;

import com.sibilantsolutions.grison.driver.foscam.domain.AudioDataText;
import com.sibilantsolutions.grison.evt.AudioHandlerI;
import com.sibilantsolutions.grison.sound.adpcm.AdpcmDecoder;
import com.sibilantsolutions.grison.sound.player.MyPlayer;
import com.sibilantsolutions.grison.util.Convert;

public class DemoAudioHandler implements AudioHandlerI
{
    private AdpcmDecoder adpcm = new AdpcmDecoder();
    private MyPlayer player = new MyPlayer( AdpcmDecoder.getDecodeAudioFormat() );

    @Override
    public void onReceive( AudioDataText audioData )
    {
        String dataString = audioData.getDataContent();
        byte[] dataBytes = dataString.getBytes( Convert.cs );
        byte[] decodeBlock = adpcm.decode( dataBytes );
        player.feed( new ByteArrayInputStream( decodeBlock ) );
    }

}
