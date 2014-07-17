package com.sibilantsolutions.grison.evt;

import com.sibilantsolutions.grison.driver.foscam.domain.VideoDataText;

public interface ImageHandlerI
{

    public void onReceive( VideoDataText videoData );

}
