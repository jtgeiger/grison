package com.sibilantsolutions.grison.net.netty.codec;

import java.net.InetSocketAddress;
import java.util.List;

import com.sibilantsolutions.grison.net.netty.codec.dto.FoscamTextByteBufDTO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;

public class FoscamTextByteBufDTOToDatagramPacketEncoder extends MessageToMessageEncoder<FoscamTextByteBufDTO> {

    //This address:port is defined in the Foscam docs.
    private static final String BROADCAST_ADDRESS = "255.255.255.255";
    private static final int BROADCAST_PORT = 10_000;

    @Override
    protected void encode(ChannelHandlerContext ctx, FoscamTextByteBufDTO msg, List<Object> out) {
        //It's Netty's job to release this buf after the datagram is written.
        final ByteBuf buf = FoscamTextByteBufDTOEncoder.allocateBuf(ctx, msg);
        new FoscamTextByteBufDTOEncoder().encode(ctx, msg, buf);
        final DatagramPacket datagramPacket = new DatagramPacket(buf, new InetSocketAddress(BROADCAST_ADDRESS, BROADCAST_PORT));
        out.add(datagramPacket);
    }

}
