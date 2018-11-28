package com.sibilantsolutions.grison.evt;

import com.sibilantsolutions.grison.driver.foscam.entity.VideoDataTextEntity;

public interface ImageHandlerI
{

    void onReceive(VideoDataTextEntity videoData);

    void onVideoStopped();

}
