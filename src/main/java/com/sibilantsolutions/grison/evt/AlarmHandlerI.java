package com.sibilantsolutions.grison.evt;

import com.sibilantsolutions.grison.driver.foscam.domain.AlarmNotifyText;

public interface AlarmHandlerI
{

   public void onReceive( AlarmNotifyText ant );

}
