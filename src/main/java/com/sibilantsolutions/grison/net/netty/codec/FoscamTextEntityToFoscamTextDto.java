package com.sibilantsolutions.grison.net.netty.codec;

import java.util.List;

import com.sibilantsolutions.grison.driver.foscam.dto.FoscamTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.AlarmNotifyTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioEndTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioStartReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioStartRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.FoscamTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.InitReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.InitRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.KeepAliveAudioVideoTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.KeepAliveOperationTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginReqAudioVideoTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginReqOperationTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.SearchReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.SearchRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.TalkDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.TalkStartReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.TalkStartRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.Unk02TextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoEndTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoStartReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoStartRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.mapper.EntityToDto;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

/**
 * Encodes FoscamTextEntity instances to FoscamTextDto instances.
 *
 * This is meant to be installed in the channel pipeline, such that any FoscamTextEntity can be written to the channel,
 * and this class will encode to FoscamTextDto.  This allows the application to work exclusively with entities, and
 * let the framework translate into dtos.
 */
@ChannelHandler.Sharable
public class FoscamTextEntityToFoscamTextDto extends MessageToMessageEncoder<FoscamTextEntity> {
    @Override
    protected void encode(ChannelHandlerContext ctx, FoscamTextEntity msg, List<Object> out) {

        final FoscamTextDto dto;

        if (msg instanceof AlarmNotifyTextEntity) {
            dto = EntityToDto.alarmNotifyTextDto.apply((AlarmNotifyTextEntity) msg);
        } else if (msg instanceof AudioDataTextEntity) {
            dto = EntityToDto.audioDataTextDto.apply((AudioDataTextEntity) msg);
        } else if (msg instanceof AudioStartReqTextEntity) {
            dto = EntityToDto.audioStartReqTextDto.apply((AudioStartReqTextEntity) msg);
        } else if (msg instanceof AudioStartRespTextEntity) {
            dto = EntityToDto.audioStartRespTextDto.apply((AudioStartRespTextEntity) msg);
        } else if (msg instanceof AudioEndTextEntity) {
            dto = EntityToDto.audioEndTextDto.apply((AudioEndTextEntity) msg);
        } else if (msg instanceof LoginReqAudioVideoTextEntity) {
            dto = EntityToDto.loginReqAudioVideoTextDto.apply((LoginReqAudioVideoTextEntity) msg);
        } else if (msg instanceof LoginReqOperationTextEntity) {
            dto = EntityToDto.loginReqOperationTextDto.apply((LoginReqOperationTextEntity) msg);
        } else if (msg instanceof LoginRespTextEntity) {
            dto = EntityToDto.loginRespTextDto.apply((LoginRespTextEntity) msg);
        } else if (msg instanceof SearchReqTextEntity) {
            dto = EntityToDto.searchReqTextDto.apply((SearchReqTextEntity) msg);
        } else if (msg instanceof SearchRespTextEntity) {
            dto = EntityToDto.searchRespTextDto.apply((SearchRespTextEntity) msg);
        } else if (msg instanceof TalkDataTextEntity) {
            dto = EntityToDto.talkDataTextDto.apply((TalkDataTextEntity) msg);
        } else if (msg instanceof TalkStartReqTextEntity) {
            dto = EntityToDto.talkStartReqTextDto.apply((TalkStartReqTextEntity) msg);
        } else if (msg instanceof TalkStartRespTextEntity) {
            dto = EntityToDto.talkStartRespTextDto.apply((TalkStartRespTextEntity) msg);
        } else if (msg instanceof Unk02TextEntity) {
            dto = EntityToDto.unk02TextDto.apply((Unk02TextEntity) msg);
        } else if (msg instanceof VerifyReqTextEntity) {
            dto = EntityToDto.verifyReqTextDto.apply((VerifyReqTextEntity) msg);
        } else if (msg instanceof VerifyRespTextEntity) {
            dto = EntityToDto.verifyRespTextDto.apply((VerifyRespTextEntity) msg);
        } else if (msg instanceof VideoDataTextEntity) {
            dto = EntityToDto.videoDataTextDto.apply((VideoDataTextEntity) msg);
        } else if (msg instanceof VideoEndTextEntity) {
            dto = EntityToDto.videoEndTextDto.apply((VideoEndTextEntity) msg);
        } else if (msg instanceof VideoStartReqTextEntity) {
            dto = EntityToDto.videoStartReqTextDto.apply((VideoStartReqTextEntity) msg);
        } else if (msg instanceof VideoStartRespTextEntity) {
            dto = EntityToDto.videoStartRespTextDto.apply((VideoStartRespTextEntity) msg);
        } else if (msg instanceof KeepAliveOperationTextEntity) {
            dto = EntityToDto.keepAliveOperationTextDto.apply((KeepAliveOperationTextEntity) msg);
        } else if (msg instanceof KeepAliveAudioVideoTextEntity) {
            dto = EntityToDto.keepAliveAudioVideoTextDto.apply((KeepAliveAudioVideoTextEntity) msg);
        } else if (msg instanceof InitReqTextEntity) {
            dto = EntityToDto.initReqTextDto.apply((InitReqTextEntity) msg);
        } else if (msg instanceof InitRespTextEntity) {
            dto = EntityToDto.initRespTextDto.apply((InitRespTextEntity) msg);
        } else {
            throw new UnsupportedOperationException("Unexpected msg=" + msg);
        }

        out.add(dto);
    }
}
