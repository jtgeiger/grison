package com.sibilantsolutions.grison.rx;

import com.sibilantsolutions.grison.driver.foscam.domain.Command;
import com.sibilantsolutions.grison.driver.foscam.domain.KeepAliveText;
import com.sibilantsolutions.grison.driver.foscam.domain.LoginReqText;
import com.sibilantsolutions.grison.driver.foscam.domain.OperationProtocolOpCodeE;
import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import com.sibilantsolutions.grison.driver.foscam.domain.VerifyReqText;
import com.sibilantsolutions.grison.driver.foscam.domain.VideoStartReqText;
import io.reactivex.Completable;

public class OpClientImpl implements OpClient {

    private final ChannelSender channelSender;

    public OpClientImpl(ChannelSender channelSender) {
        this.channelSender = channelSender;
    }

    @Override
    public Completable login() {
        Command c = new Command();
        c.setProtocol(ProtocolE.OPERATION_PROTOCOL);
        c.setOpCode(OperationProtocolOpCodeE.Login_Req);
        LoginReqText login = new LoginReqText();
        c.setCommandText(login);
        login.setDataConnectionId(new byte[0]);

        return channelSender.doSend(c);
    }

    @Override
    public Completable ping() {
        Command c = new Command();
        c.setProtocol(ProtocolE.OPERATION_PROTOCOL);
        c.setOpCode(OperationProtocolOpCodeE.Keep_Alive);
        KeepAliveText text = new KeepAliveText();
        c.setCommandText(text);

        return channelSender.doSend(c);
    }

    @Override
    public Completable verify(String username, String password) {
        Command c = new Command();
        c.setProtocol(ProtocolE.OPERATION_PROTOCOL);
        c.setOpCode(OperationProtocolOpCodeE.Verify_Req);
        VerifyReqText verify = new VerifyReqText();
        c.setCommandText(verify);
        verify.setUsername(username);
        verify.setPassword(password);

        return channelSender.doSend(c);
    }

    @Override
    public Completable videoStart() {
        Command c = new Command();
        c.setProtocol(ProtocolE.OPERATION_PROTOCOL);
        c.setOpCode(OperationProtocolOpCodeE.Video_Start_Req);
        VideoStartReqText video = new VideoStartReqText();
        c.setCommandText(video);
        video.setData(1);

        return channelSender.doSend(c);
    }

    @Override
    public Completable videoEnd() {
        Command c = new Command();
        c.setProtocol(ProtocolE.OPERATION_PROTOCOL);
        c.setOpCode(OperationProtocolOpCodeE.Video_End);

        return channelSender.doSend(c);
    }

}
