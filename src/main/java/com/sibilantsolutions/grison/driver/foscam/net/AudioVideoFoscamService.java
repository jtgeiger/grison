package com.sibilantsolutions.grison.driver.foscam.net;

import com.sibilantsolutions.grison.driver.foscam.domain.AudioFormatE;
import com.sibilantsolutions.grison.driver.foscam.domain.AudioVideoProtocolOpCodeE;
import com.sibilantsolutions.grison.driver.foscam.domain.Command;
import com.sibilantsolutions.grison.driver.foscam.domain.LoginReqText;
import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import com.sibilantsolutions.grison.driver.foscam.domain.TalkDataText;

public class AudioVideoFoscamService extends AbstractFoscamService {

    final private long startMs = System.currentTimeMillis();
    private long serialNumber = 1;

    public AudioVideoFoscamService(FoscamConnection connection) {
        super(connection);
    }

    public void audioVideoLogin(byte[] dataConnectionId) {
        Command c = new Command();
        c.setProtocol(ProtocolE.AUDIO_VIDEO_PROTOCOL);
        c.setOpCode(AudioVideoProtocolOpCodeE.Login_Req);
        LoginReqText login = new LoginReqText();
        c.setCommandText(login);
        login.setDataConnectionId(dataConnectionId);

        sendAsync(c);
    }

    public void talkSend(byte[] adpcm) {
        Command c = new Command();
        c.setProtocol(ProtocolE.AUDIO_VIDEO_PROTOCOL);
        c.setOpCode(AudioVideoProtocolOpCodeE.Talk_Data);
        TalkDataText text = new TalkDataText();
        c.setCommandText(text);
        long now = System.currentTimeMillis();
        long uptimeMs = now - startMs;
        text.setUptimeMs(uptimeMs);
        text.setSerialNumber(serialNumber++);
        text.setTimestampMs(now);
        text.setAudioFormat(AudioFormatE.ADPCM);
        text.setDataContent(adpcm);

        sendAsync(c);
    }

}
