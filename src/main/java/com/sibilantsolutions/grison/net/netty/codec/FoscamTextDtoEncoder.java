package com.sibilantsolutions.grison.net.netty.codec;

import java.util.List;

import com.sibilantsolutions.grison.driver.foscam.dto.AlarmNotifyTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.AudioDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.AudioEndTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.AudioStartReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.AudioStartRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.InitReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.InitRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.KeepAliveAudioVideoTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.KeepAliveOperationTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqAudioVideoTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginReqOperationTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.SearchReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.SearchRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.TalkDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.TalkEndTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.TalkStartReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.TalkStartRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.Unk02TextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoEndTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoStartReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoStartRespTextDto;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

/**
 * Encodes FoscamTextDto instances into Netty ByteBufs.
 *
 * This is meant to be installed in the channel pipeline, such that any Dto can be written to the channel, and this
 * class will encode to ByteBuf.  This is easier than manually registering encoders for each Dto type.
 */
@ChannelHandler.Sharable
public class FoscamTextDtoEncoder extends MessageToMessageEncoder<FoscamTextDto> {

    @Override
    protected void encode(ChannelHandlerContext ctx, FoscamTextDto msg, List<Object> out) {
        switch (msg.opCode()) {
            case Keep_Alive_Operation: {
                new KeepAliveOperationTextDtoEncoder().encode(ctx, (KeepAliveOperationTextDto) msg, out);
            }
            break;

            case Login_Req_Operation: {
                new LoginReqOperationTextDtoEncoder().encode(ctx, (LoginReqOperationTextDto) msg, out);
            }
            break;

            case Login_Resp: {
                new LoginRespTextDtoEncoder().encode(ctx, (LoginRespTextDto) msg, out);
            }
            break;

            case Verify_Req: {
                new VerifyReqTextDtoEncoder().encode(ctx, (VerifyReqTextDto) msg, out);
            }
            break;

            case Verify_Resp: {
                new VerifyRespTextDtoEncoder().encode(ctx, (VerifyRespTextDto) msg, out);
            }
            break;

            case UNK02: {
                new Unk02TextDtoEncoder().encode(ctx, (Unk02TextDto) msg, out);
            }
            break;

            case Video_Start_Req: {
                new VideoStartReqTextDtoEncoder().encode(ctx, (VideoStartReqTextDto) msg, out);
            }
            break;

            case Video_Start_Resp: {
                new VideoStartRespTextDtoEncoder().encode(ctx, (VideoStartRespTextDto) msg, out);
            }
            break;

            case Audio_Start_Req: {
                new AudioStartReqTextDtoEncoder().encode(ctx, (AudioStartReqTextDto) msg, out);
            }
            break;

            case Audio_Start_Resp: {
                new AudioStartRespTextDtoEncoder().encode(ctx, (AudioStartRespTextDto) msg, out);
            }
            break;

            case Search_Req: {
                new SearchReqTextDtoEncoder().encode(ctx, (SearchReqTextDto) msg, out);
            }
            break;

            case Search_Resp: {
                new SearchRespTextDtoEncoder().encode(ctx, (SearchRespTextDto) msg, out);
            }
            break;

            case Keep_Alive_AudioVideo: {
                new KeepAliveAudioVideoTextDtoEncoder().encode(ctx, (KeepAliveAudioVideoTextDto) msg, out);
            }
            break;

            case Login_Req_AudioVideo: {
                new LoginReqAudioVideoTextDtoEncoder().encode(ctx, (LoginReqAudioVideoTextDto) msg, out);
            }
            break;

            case Video_Data: {
                new VideoDataTextDtoEncoder().encode(ctx, (VideoDataTextDto) msg, out);
            }
            break;

            case Audio_Data: {
                new AudioDataTextDtoEncoder().encode(ctx, (AudioDataTextDto) msg, out);
            }
            break;

            case Init_Req: {
                new InitReqTextDtoEncoder().encode(ctx, (InitReqTextDto) msg, out);
            }
            break;

            case Init_Resp: {
                new InitRespTextDtoEncoder().encode(ctx, (InitRespTextDto) msg, out);
            }
            break;

            case Alarm_Notify: {
                new AlarmNotifyTextDtoEncoder().encode(ctx, (AlarmNotifyTextDto) msg, out);
            }
            break;

            case Talk_Start_Req: {
                new TalkStartReqTextDtoEncoder().encode(ctx, (TalkStartReqTextDto) msg, out);
            }
            break;

            case Talk_Start_Resp: {
                new TalkStartRespTextDtoEncoder().encode(ctx, (TalkStartRespTextDto) msg, out);
            }
            break;

            case Talk_End: {
                new TalkEndTextDtoEncoder().encode(ctx, (TalkEndTextDto) msg, out);
            }
            break;

            case Audio_End: {
                new AudioEndTextDtoEncoder().encode(ctx, (AudioEndTextDto) msg, out);
            }
            break;

            case Talk_Data: {
                new TalkDataTextDtoEncoder().encode(ctx, (TalkDataTextDto) msg, out);
            }
            break;

            case Video_End: {
                new VideoEndTextDtoEncoder().encode(ctx, (VideoEndTextDto) msg, out);
            }
            break;

            default:
                throw new UnsupportedOperationException("Unexpected opcode=" + msg.opCode());
        }
    }
}
