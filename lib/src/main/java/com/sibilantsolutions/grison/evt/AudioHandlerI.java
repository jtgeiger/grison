package com.sibilantsolutions.grison.evt;

import com.sibilantsolutions.grison.driver.foscam.entity.AudioDataTextEntity;

public interface AudioHandlerI
{

    public void onAudioStopped( AudioStoppedEvt audioStoppedEvt );

    public void onReceive(AudioDataTextEntity audioData);

}
