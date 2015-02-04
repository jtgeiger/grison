package com.sibilantsolutions.grison.evt;

import com.sibilantsolutions.grison.driver.foscam.net.FoscamSession;

public class LostConnectionEvt
{

    private final FoscamSession session;

    public LostConnectionEvt( FoscamSession session ) {
        this.session = session;
    }

    public FoscamSession getSession()
    {
        return session;
    }

}
