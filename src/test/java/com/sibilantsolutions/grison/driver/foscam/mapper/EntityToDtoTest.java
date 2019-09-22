package com.sibilantsolutions.grison.driver.foscam.mapper;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

import org.junit.Test;

import com.google.common.primitives.UnsignedInteger;
import com.sibilantsolutions.grison.driver.foscam.domain.AudioFormatE;
import com.sibilantsolutions.grison.driver.foscam.domain.ResultCodeE;
import com.sibilantsolutions.grison.driver.foscam.dto.CommandDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VerifyRespTextDto;
import com.sibilantsolutions.grison.driver.foscam.dto.VideoDataTextDto;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioStartReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.AudioStartRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.FoscamTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.KeepAliveOperationTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginReqAudioVideoTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginReqOperationTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.LoginRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.SearchReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.SearchRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.TalkStartReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.TalkStartRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.Unk02TextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VerifyRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VersionEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoDataTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoStartReqTextEntity;
import com.sibilantsolutions.grison.driver.foscam.entity.VideoStartRespTextEntity;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt8;
import com.sibilantsolutions.grison.net.netty.codec.FoscamTextByteBufDTOEncoder;
import com.sibilantsolutions.grison.net.netty.codec.FoscamTextDtoEncoder;
import com.sibilantsolutions.grison.net.netty.codec.FoscamTextEntityToFoscamTextDto;
import com.sibilantsolutions.grison.net.netty.codec.VerifyRespTextDtoParser;
import com.sibilantsolutions.grison.net.netty.codec.VideoDataTextDtoParser;
import com.sibilantsolutions.grison.net.netty.codec.parse.ResourceToByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.embedded.EmbeddedChannel;

public class EntityToDtoTest {

    private static final ResourceToByteBuf RESOURCE_TO_BYTE_BUF = new ResourceToByteBuf();

    /**
     * Function that skips the command prefix so that it is ready to read the data payload of the command.
     */
    private static final UnaryOperator<ByteBuf> SKIP_COMMAND_FUNCTION = buf ->
            buf.readerIndex(buf.readerIndex() + CommandDto.COMMAND_PREFIX_LENGTH);

    @Test
    public void verifyRespTextDto() {
        final VerifyRespTextEntity entity = VerifyRespTextEntity.builder().resultCode(ResultCodeE.CORRECT).build();

        final VerifyRespTextDto actual = EntityToDto.verifyRespTextDto.apply(entity);

        final Function<String, VerifyRespTextDto> resourceToDto = RESOURCE_TO_BYTE_BUF
                .andThen(SKIP_COMMAND_FUNCTION)
                .andThen(new VerifyRespTextDtoParser());

        final VerifyRespTextDto expected = resourceToDto.apply("/samples/verify_resp.bin");

        assertEquals(expected, actual);
    }

    @Test
    public void verifyRespTextDto_userWrong() {
        final VerifyRespTextEntity entity = VerifyRespTextEntity.builder().resultCode(ResultCodeE.USER_WRONG).build();

        final VerifyRespTextDto actual = EntityToDto.verifyRespTextDto.apply(entity);

        final Function<String, VerifyRespTextDto> resourceToDto = RESOURCE_TO_BYTE_BUF
                .andThen(SKIP_COMMAND_FUNCTION)
                .andThen(new VerifyRespTextDtoParser());

        final VerifyRespTextDto expected = resourceToDto.apply("/samples/verify_resp_USER_WRONG.bin");

        assertEquals(expected, actual);
    }

    @Test
    public void videoDataText() {
        final VideoDataTextEntity entity = VideoDataTextEntity
                .builder()
                .uptime(Duration.ofMillis(59_108L * 10))
                .timestamp(Instant.ofEpochSecond(1_400_688_245))
                .videoData(new byte[44_600])
                .build();

        final VideoDataTextDto actual = EntityToDto.videoDataTextDto.apply(entity);

        final Function<String, VideoDataTextDto> resourceToDto = RESOURCE_TO_BYTE_BUF
                .andThen(SKIP_COMMAND_FUNCTION)
                .andThen(new VideoDataTextDtoParser());

        final VideoDataTextDto expected = resourceToDto.apply("/samples/video_data.bin");

        assertEquals(expected.timestampHundredths(), actual.timestampHundredths());
        assertEquals(expected.framePerSec(), actual.framePerSec());
        assertEquals(expected.reserve(), actual.reserve());
        assertEquals(expected.videoLength(), actual.videoLength());
        //Opting not to validate the data array.

    }

    @Test
    public void videoDataText2() {
        final VideoDataTextEntity entity = VideoDataTextEntity
                .builder()
                .uptime(Duration.ofMillis(4_230_881_480L))
                .timestamp(Instant.ofEpochMilli(1_566_749_828_000L))
                .videoData(new byte[39_436])
                .build();

        final VideoDataTextDto actual = EntityToDto.videoDataTextDto.apply(entity);

        final Function<String, VideoDataTextDto> resourceToDto = RESOURCE_TO_BYTE_BUF
                .andThen(SKIP_COMMAND_FUNCTION)
                .andThen(new VideoDataTextDtoParser());

        final VideoDataTextDto expected = resourceToDto.apply("/samples/video_data2.bin");

        assertEquals(expected.timestampHundredths(), actual.timestampHundredths());
        assertEquals(expected.framePerSec(), actual.framePerSec());
        assertEquals(expected.reserve(), actual.reserve());
        assertEquals(expected.videoLength(), actual.videoLength());
        //Opting not to validate the data array.

    }

    @Test
    public void videoDataText_large() {
        final VideoDataTextEntity entity = VideoDataTextEntity
                .builder()
                .uptime(Duration.ofMillis(22_895_263_570L))
                .timestamp(Instant.parse("2051-05-03T18:03:48Z"))
                .videoData(new byte[0])
                .build();

        final VideoDataTextDto actual = EntityToDto.videoDataTextDto.apply(entity);

        final VideoDataTextDto expected = VideoDataTextDto
                .builder()
                .timestampHundredths(FosInt32.create(UnsignedInteger.fromIntBits(0x88776655)))
                .framePerSec(FosInt32.create(UnsignedInteger.valueOf("2566749828")))
                .videoLength(FosInt32.create(UnsignedInteger.ZERO))
                .videoData(new byte[0])
                .reserve(FosInt8.ZERO)
                .build();

        assertEquals(expected.timestampHundredths(), actual.timestampHundredths());
        assertEquals(expected.framePerSec(), actual.framePerSec());
        assertEquals(expected.reserve(), actual.reserve());
        assertEquals(expected.videoLength(), actual.videoLength());
        //Opting not to validate the data array.

    }

    private static void assertEntity(FoscamTextEntity entity, String filename) {
        final EmbeddedChannel channel = new EmbeddedChannel();
        channel
                .pipeline()
                .addLast(new FoscamTextByteBufDTOEncoder())
                .addLast(new FoscamTextDtoEncoder())
                .addLast(new FoscamTextEntityToFoscamTextDto());

        assertThat(channel.writeOutbound(entity), is(true));
        ByteBuf actual = channel.readOutbound();
        final ByteBuf expected = new ResourceToByteBuf().apply(filename);

        StringBuilder buf = new StringBuilder();
        buf.append("actual:\n");
        ByteBufUtil.appendPrettyHexDump(buf, actual);
        buf.append("\nexpected:\n");
        ByteBufUtil.appendPrettyHexDump(buf, expected);
        assertThat(buf.toString(), actual, is(expected));
    }

    @Test
    public void toDatastream01() {
        final LoginReqOperationTextEntity entity = LoginReqOperationTextEntity
                .builder()
                .build();

        assertEntity(entity, "/samples/login_req.bin");
    }

    @Test
    public void toDatastream02() {
        final LoginRespTextEntity entity = LoginRespTextEntity
                .builder()
                .resultCode(ResultCodeE.CORRECT)
                .cameraId("00626E4E72BF")
                .version(VersionEntity
                        .builder()
                        .major(11)
                        .minor(37)
                        .patch(2)
                        .buildNum(56)
                        .build())
                .build();

        assertEntity(entity, "/samples/login_resp.bin");
    }

    @Test
    public void toDatastream03() {
        final VerifyReqTextEntity entity = VerifyReqTextEntity
                .builder()
                .username("camvis")
                .password("vis,FOSbuy1v")
                .build();

        assertEntity(entity, "/samples/verify_req.bin");
    }

    @Test
    public void toDatastream04() {
        final VerifyRespTextEntity entity = VerifyRespTextEntity
                .builder()
                .resultCode(ResultCodeE.CORRECT)
                .build();

        assertEntity(entity, "/samples/verify_resp.bin");
    }

    //TODO 5

    @Test
    public void toDatastream06() {
        final Unk02TextEntity entity = Unk02TextEntity
                .builder()
                .build();

        assertEntity(entity, "/samples/UNK02.bin");
    }

    //TODO 7

    @Test
    public void toDatastream08() {
        final AudioStartReqTextEntity entity = AudioStartReqTextEntity
                .builder()
                .build();

        assertEntity(entity, "/samples/audio_start_req.bin");
    }

    @Test
    public void toDatastream09() {
        final AudioStartRespTextEntity entity = AudioStartRespTextEntity
                .builder()
                .result(ResultCodeE.CORRECT)
                .dataConnectionId(FosInt32.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(new byte[]{0x00, 0x58, (byte) 0xEA, 0x58}).order(ByteOrder.LITTLE_ENDIAN).getInt())))
                .build();

        assertEntity(entity, "/samples/audio_start_resp.bin");
    }

    @Test
    public void toDatastream10() {
        final LoginReqAudioVideoTextEntity entity = LoginReqAudioVideoTextEntity
                .builder()
                .dataConnectionId(FosInt32.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(new byte[]{0x00, 0x58, (byte) 0xEA, 0x58}).order(ByteOrder.LITTLE_ENDIAN).getInt())))
                .build();

        assertEntity(entity, "/samples/audio_login_req.bin");
    }

    @Test
    public void toDatastream11() {
        byte[] data = new byte[160];    //0xA0
        IntStream.range(0, data.length).forEach(i -> data[i] = (byte) (i + 1));

        final AudioDataTextEntity entity = AudioDataTextEntity
                .builder()
                .uptime(Duration.ofMillis(0x0003D918 * 10))
                .serialNumber(0x0000F5DC)
                .timestamp(Instant.ofEpochSecond(0x535A756A))
                .audioFormat(AudioFormatE.ADPCM)
                .data(data)
                .build();

        assertEntity(entity, "/samples/audio_data-scrubbed.bin");
    }

    @Test
    public void toDatastream12() {
        final KeepAliveOperationTextEntity entity = KeepAliveOperationTextEntity
                .builder()
                .build();

        assertEntity(entity, "/samples/keep_alive.bin");
    }

    @Test
    public void toDatastream13() {
        final VideoStartReqTextEntity entity = VideoStartReqTextEntity
                .builder()
                .build();

        assertEntity(entity, "/samples/video_start_req.bin");
    }

    @Test
    public void toDatastream14() {
        final VideoStartRespTextEntity entity = VideoStartRespTextEntity
                .builder()
                .result(ResultCodeE.CORRECT)
                .dataConnectionId(FosInt32.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(new byte[]{0x40, 0x1B, 0x25, 0x60}).order(ByteOrder.LITTLE_ENDIAN).getInt())))
                .build();

        assertEntity(entity, "/samples/video_start_resp.bin");
    }

    @Test
    public void toDatastream15() {
        final TalkStartReqTextEntity entity = TalkStartReqTextEntity
                .builder()
                .build();

        assertEntity(entity, "/samples/talk_start_req.bin");
    }

    @Test
    public void toDatastream16() {
        final TalkStartRespTextEntity entity = TalkStartRespTextEntity
                .builder()
                .result(ResultCodeE.CORRECT)
                .dataConnectionId(FosInt32.create(UnsignedInteger.fromIntBits(ByteBuffer.wrap(new byte[]{0x7D, 0x26, (byte) 0xF6, 0x38}).order(ByteOrder.LITTLE_ENDIAN).getInt())))
                .build();

        assertEntity(entity, "/samples/talk_start_resp.bin");
    }

    @Test
    public void toDatastream17() {

        final String filename = "/samples/video_data.bin";
        final ByteBuf expected = new ResourceToByteBuf().apply(filename);
        byte[] data = new byte[expected.readableBytes() - 0x24];
        //Cheating to create the data.
        expected.readerIndex(0x24).readBytes(data);

        final VideoDataTextEntity entity = VideoDataTextEntity
                .builder()
                .uptime(Duration.ofMillis(0x0000E6E4 * 10))
                .timestamp(Instant.ofEpochSecond(0x537CCE75))
                .videoData(data)
                .build();

        assertEntity(entity, filename);
    }

    @Test
    public void toDatastream18() {
        final SearchReqTextEntity entity = SearchReqTextEntity
                .builder()
                .build();

        assertEntity(entity, "/samples/search_req.bin");
    }

    @Test
    public void toDatastream19() throws UnknownHostException {
        final SearchRespTextEntity entity = SearchRespTextEntity
                .builder()
                .cameraId("00626E4E72BF")
                .cameraName("cam1")
                .address(new InetSocketAddress("192.168.69.21", 80))
                .mask(InetAddress.getByName("255.255.255.0"))
                .gateway(InetAddress.getByName("192.168.69.1"))
                .dns(InetAddress.getByName("192.168.69.1"))
                .sysSoftwareVersion(VersionEntity
                        .builder()
                        .major(11)
                        .minor(37)
                        .patch(2)
                        .buildNum(56)
                        .build())
                .appSoftwareVersion(VersionEntity
                        .builder()
                        .major(2)
                        .minor(4)
                        .patch(10)
                        .buildNum(10)
                        .build())
                .isDhcpEnabled(true)
                .build();

        assertEntity(entity, "/samples/search_resp.bin");
    }

    //TODO: 20
    //TODO: 21

    @Test
    public void toDatastream22() {
        final VerifyRespTextEntity entity = VerifyRespTextEntity
                .builder()
                .resultCode(ResultCodeE.USER_WRONG)
                .build();

        assertEntity(entity, "/samples/verify_resp_USER_WRONG.bin");
    }

}