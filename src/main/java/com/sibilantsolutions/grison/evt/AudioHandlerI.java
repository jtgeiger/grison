package com.sibilantsolutions.grison.evt;

import com.sibilantsolutions.grison.driver.foscam.domain.AudioDataText;

public interface AudioHandlerI
{

    public void onReceive( AudioDataText audioData );

}
