package com.sibilantsolutions.grison.rx;

import com.sibilantsolutions.grison.driver.foscam.domain.AudioVideoProtocolOpCodeE;
import com.sibilantsolutions.grison.driver.foscam.domain.Command;
import com.sibilantsolutions.grison.driver.foscam.domain.LoginReqText;
import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import io.reactivex.Completable;

public class AvClientImpl implements AvClient {

    private final ChannelSender channelSender;

    public AvClientImpl(ChannelSender channelSender) {
        this.channelSender = channelSender;
    }

    @Override
    public Completable audioVideoLogin(byte[] dataConnectionId) {
        Command c = new Command();
        c.setProtocol(ProtocolE.AUDIO_VIDEO_PROTOCOL);
        c.setOpCode(AudioVideoProtocolOpCodeE.Login_Req);
        LoginReqText login = new LoginReqText();
        c.setCommandText(login);
        login.setDataConnectionId(dataConnectionId);

        return channelSender.doSend(c);
    }

}
