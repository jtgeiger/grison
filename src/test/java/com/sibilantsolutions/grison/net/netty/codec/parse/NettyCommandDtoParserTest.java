package com.sibilantsolutions.grison.net.netty.codec.parse;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.IntStream;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import com.google.common.primitives.UnsignedInteger;
import com.sibilantsolutions.grison.driver.foscam.domain.AudioFormatE;
import com.sibilantsolutions.grison.driver.foscam.domain.ProtocolE;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.AlarmNotifyTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.AudioDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.driver.foscam.dto.FoscamOpCode;
import com.sibilantsolutions.grison.driver.foscam.dto.InitReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.InitRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.KeepAliveOperationTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespDetailsDto;
import com.sibilantsolutions.grison.driver.foscam.dto.LoginRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.SearchRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.TalkStartReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.TalkStartRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.Unk02TextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoStartReqTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoStartRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.mapper.DtoToEntity;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt16R;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32R;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;
import com.sibilantsolutions.grison.net.netty.codec.NettyCommandDtoParser;
import io.netty.buffer.ByteBuf;

public class NettyCommandDtoParserTest {

    @NotNull
    private static CommandDto basics(String path, ProtocolE protocolE, FoscamOpCode foscamOpCode, int textLength) {
        final ByteBuf buf = new ResourceToByteBuf().apply(path);
        final CommandDto dto = new NettyCommandDtoParser(Clock.systemUTC()).parse(buf);
        assertEquals(protocolE, dto.protocol());
        assertEquals(foscamOpCode, dto.operationCode());
        assertEquals(FosInt8.ZERO, dto.reserve1());
        assertArrayEquals(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}, dto.reserve2());
        assertEquals(FosInt32.create(UnsignedInteger.valueOf(textLength)), dto.textLength());
        assertEquals(FosInt32.create(UnsignedInteger.valueOf(textLength)), dto.reserve3());
        return dto;
    }

    @Test
    public void parse01_verify_resp() {
        final CommandDto dto = basics("/samples/verify_resp.bin", ProtocolE.OPERATION_PROTOCOL, FoscamOpCode.Verify_Resp, 3);
        assertEquals(VerifyRespTextDto.builder()
                .reserve(FosInt8.ZERO)
                .resultCode(FosInt16.ZERO)
                .build(), dto.text());
    }

    @Test
    public void parse02_alarm_notify() {
        final CommandDto dto = basics("/samples/alarm_notify.bin", ProtocolE.OPERATION_PROTOCOL, FoscamOpCode.Alarm_Notify, 9);
        assertEquals(AlarmNotifyTextDto.builder()
                .type(FosInt8.create((byte) 3))
                .reserve1(FosInt16.ZERO)
                .reserve2(FosInt16.ZERO)
                .reserve3(FosInt16.create((short) 100))
                .reserve4(FosInt16.create((short) 100))
                .build(), dto.text());
    }

    @Test
    public void parse03_UNK02() {
        final CommandDto dto = basics("/samples/UNK02.bin", ProtocolE.OPERATION_PROTOCOL, FoscamOpCode.UNK02, 1152);
        assertEquals(Unk02TextDto.builder()
                .data(new byte[1152])
                .build(), dto.text());
    }

    @Test
    public void parse04_keep_alive() {
        final CommandDto dto = basics("/samples/keep_alive.bin", ProtocolE.OPERATION_PROTOCOL, FoscamOpCode.Keep_Alive_Operation, 0);
        assertEquals(KeepAliveOperationTextDto.builder()
                .build(), dto.text());
    }

    @Test
    public void parse05_video_start_req() {
        final CommandDto dto = basics("/samples/video_start_req.bin", ProtocolE.OPERATION_PROTOCOL, FoscamOpCode.Video_Start_Req, 1);
        assertEquals(VideoStartReqTextDto.builder()
                .reserve(FosInt8.ONE)
                .build(), dto.text());
    }

    @Test
    public void parse06_video_start_resp() {
        final CommandDto dto = basics("/samples/video_start_resp.bin", ProtocolE.OPERATION_PROTOCOL, FoscamOpCode.Video_Start_Resp, 6);
        assertEquals(VideoStartRespTextDto.builder()
                .result(FosInt16.ZERO)
                .dataConnectionId(FosInt32.create(UnsignedInteger.valueOf(0x60251B40)))
                .build(), dto.text());
    }

    @Test
    public void parse07_talk_start_req() {
        final CommandDto dto = basics("/samples/talk_start_req.bin", ProtocolE.OPERATION_PROTOCOL, FoscamOpCode.Talk_Start_Req, 1);
        assertEquals(TalkStartReqTextDto.builder()
                .reserve(FosInt8.ONE)
                .build(), dto.text());
    }

    @Test
    public void parse08_talk_start_resp() {
        final CommandDto dto = basics("/samples/talk_start_resp.bin", ProtocolE.OPERATION_PROTOCOL, FoscamOpCode.Talk_Start_Resp, 6);
        assertEquals(TalkStartRespTextDto.builder()
                .result(FosInt16.ZERO)
                .dataConnectionId(FosInt32.create(UnsignedInteger.valueOf(0x38F6267D)))
                .build(), dto.text());
    }

    @Test
    public void parse09_video_data() {
        final CommandDto dto = basics("/samples/video_data.bin", ProtocolE.AUDIO_VIDEO_PROTOCOL, FoscamOpCode.Video_Data, 44613);
        assertEquals(VideoDataTextDto.builder()
                .timestampHundredths(FosInt32.create(UnsignedInteger.valueOf(0x0000E6E4)))
                .framePerSec(FosInt32.create(UnsignedInteger.valueOf(0x537CCE75)))
                .reserve(FosInt8.ZERO)
                .videoLength(FosInt32.create(UnsignedInteger.valueOf(44600)))
                .videoData(((VideoDataTextDto)dto.text()).videoData())  //Testing array against self.
                .build(), dto.text());
    }

    @Test
    public void parse10_search_resp() {
        final CommandDto dto = basics("/samples/search_resp.bin", ProtocolE.SEARCH_PROTOCOL, FoscamOpCode.Search_Resp, 65);
        assertEquals(SearchRespTextDto.builder()
                .cameraId("00626E4E72BF\0".getBytes(StandardCharsets.ISO_8859_1))
                .cameraName("cam1\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0".getBytes(StandardCharsets.ISO_8859_1))
                .cameraPort(FosInt16R.create((short) 80))
                .dhcpEnabled(FosInt8.ONE)
                .dns(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(new byte[]{ (byte)192, (byte)168, 69, 1 }).getInt())))
                .gateway(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(new byte[]{ (byte)192, (byte)168, 69, 1 }).getInt())))
                .ip(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(new byte[]{ (byte)192, (byte)168, 69, 21 }).getInt())))
                .mask(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(new byte[]{ (byte)255, (byte)255, (byte)255, 0 }).getInt())))
                .appSoftwareVersion(new byte[]{ 2, 4, 10, 10 })
                .reserve(SearchRespTextDto.RESERVE)
                .sysSoftwareVersion(new byte[]{ 11, 37, 2, 56 })
                .build(), dto.text());
    }

    @Test
    public void parse11_init_resp_pri_error() {
        final CommandDto dto = basics("/samples/init_resp-pri_error.bin", ProtocolE.SEARCH_PROTOCOL, FoscamOpCode.Init_Resp, 2);
        assertEquals(InitRespTextDto.builder()
                .resultCode(FosInt16.create((short) 6))
                .build(), dto.text());
    }

    @Test
    public void parse12_init_resp_user_wrong() {
        final CommandDto dto = basics("/samples/init_resp-user_wrong.bin", ProtocolE.SEARCH_PROTOCOL, FoscamOpCode.Init_Resp, 2);
        assertEquals(InitRespTextDto.builder()
                .resultCode(FosInt16.ONE)
                .build(), dto.text());
    }

    @Test
    public void parse13_init_resp_succeed() {
        final CommandDto dto = basics("/samples/init_resp-succeed.bin", ProtocolE.SEARCH_PROTOCOL, FoscamOpCode.Init_Resp, 2);
        assertEquals(InitRespTextDto.builder()
                .resultCode(FosInt16.ZERO)
                .build(), dto.text());
    }

    @Test
    public void parse14_video_start_resp_no_data_conn_id() {
        final CommandDto dto = basics("/samples/video_start_resp-no_data_conn_id.bin", ProtocolE.OPERATION_PROTOCOL, FoscamOpCode.Video_Start_Resp, 2);
        assertEquals(VideoStartRespTextDto.builder()
                .result(FosInt16.ZERO)
                .build(), dto.text());
    }

    @Test
    public void parse15_search_resp02() {
        final CommandDto dto = basics("/samples/search_resp02.bin", ProtocolE.SEARCH_PROTOCOL, FoscamOpCode.Search_Resp, 65);
        assertEquals(SearchRespTextDto.builder()
                .cameraId("00626E4E72BF\0".getBytes(StandardCharsets.ISO_8859_1))
                .cameraName("cam1\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0".getBytes(StandardCharsets.ISO_8859_1))
                .cameraPort(FosInt16R.create((short) 61234))
                .dhcpEnabled(FosInt8.ONE)
                .dns(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(new byte[]{ (byte)192, (byte)168, 69, 1 }).getInt())))
                .gateway(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(new byte[]{ (byte)192, (byte)168, 69, 1 }).getInt())))
                .ip(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(new byte[]{ (byte)192, (byte)168, 69, 21 }).getInt())))
                .mask(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(new byte[]{ (byte)255, (byte)255, (byte)255, 0 }).getInt())))
                .appSoftwareVersion(new byte[]{ 2, 4, 10, 10 })
                .reserve(SearchRespTextDto.RESERVE)
                .sysSoftwareVersion(new byte[]{ 11, 37, 2, 56 })
                .build(), dto.text());
    }

    @Test
    public void parse16_init_req02() {
        final CommandDto dto = basics("/samples/init_req02.bin", ProtocolE.SEARCH_PROTOCOL, FoscamOpCode.Init_Req, 61);
        assertEquals(InitReqTextDto.builder()
                .reserve1(FosInt8.ZERO)
                .reserve2(FosInt8.ZERO)
                .reserve3(FosInt8.ZERO)
                .reserve4(FosInt8.ONE)
                .cameraId("00626E4E72BF\0".getBytes(StandardCharsets.ISO_8859_1))
                .cameraPort(FosInt16R.create((short) 61234))
                .dns(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(new byte[]{ (byte)192, (byte)168, 69, 1 }).getInt())))
                .gateway(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(new byte[]{ (byte)192, (byte)168, 69, 1 }).getInt())))
                .ip(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(new byte[]{ (byte)192, (byte)168, 69, 22 }).getInt())))
                .mask(FosInt32R.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(new byte[]{ (byte)255, (byte)255, (byte)255, 0 }).getInt())))
                .user("camvis\0\0\0\0\0\0\0".getBytes(StandardCharsets.ISO_8859_1))
                .password("vis,FOSbuy1v\0".getBytes(StandardCharsets.ISO_8859_1))
                .build(), dto.text());
    }

    @Test
    public void parse17_login_resp() {
        final CommandDto dto = basics("/samples/login_resp.bin", ProtocolE.OPERATION_PROTOCOL, FoscamOpCode.Login_Resp, 27);
        assertEquals(LoginRespTextDto.builder()
                .resultCode(ResultCodeE.CORRECT.value)
                .loginRespDetails(LoginRespDetailsDto.builder()
                        .cameraId("00626E4E72BF\0".getBytes(StandardCharsets.ISO_8859_1))
                        .firmwareVersion(new byte[]{11, 37, 2, 56})
                        .build())
                .build(), dto.text());
    }

    @Test
    public void parse18_verify_resp_USER_WRONG() {
        final CommandDto dto = basics("/samples/verify_resp_USER_WRONG.bin", ProtocolE.OPERATION_PROTOCOL, FoscamOpCode.Verify_Resp, 2);
        assertEquals(VerifyRespTextDto.builder()
                .resultCode(FosInt16.ONE)
                .build(), dto.text());
    }

    @Test
    public void parseAudio() {
        final CommandDto dto = basics("/samples/audio_data-scrubbed.bin", ProtocolE.AUDIO_VIDEO_PROTOCOL, FoscamOpCode.Audio_Data, 177);
        final AudioDataTextEntity actual = DtoToEntity.audioDataTextEntity.apply((AudioDataTextDto) dto.text());
        byte[] data = new byte[160];    //0xA0
        IntStream.range(0, data.length).forEach(i -> data[i] = (byte) (i + 1));
        final AudioDataTextEntity expected = AudioDataTextEntity
                .builder()
                .uptime(Duration.ofMillis(0x0003D918 * 10))
                .serialNumber(0x0000F5DC)
                .timestamp(Instant.ofEpochSecond(0x535A756A))
                .audioFormat(AudioFormatE.ADPCM)
                .data(data)
                .build();
        assertThat(actual, is(expected));
    }

}