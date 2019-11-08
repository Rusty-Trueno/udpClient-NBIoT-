package com.rongshuai.udpClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.net.InetSocketAddress;

public class udpClient {
    public static final int MessageReceived = 0x99;
    private static int scanPort = 8095;
    public udpClient(int scanPort){
        this.scanPort = scanPort;
    }

    public static void main(String[] args) throws DecoderException {
        EventLoopGroup group = new NioEventLoopGroup();
        byte[] data = {(byte)0x68,(byte)0x86,(byte)0x77,(byte)0x26,
                (byte)0x03,(byte)0x17,(byte)0x67,(byte)0x32,(byte)0x20,
                (byte)0x81,(byte)0x05,(byte)0x20,(byte)0x02,(byte)0x01,(byte)0x00};
        String sdata = Hex.encodeHexString(data,false);
        System.out.println(sdata);
//        String testdata = new String(data);
        try{
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .handler(new udpClientHandler());
            Channel ch = b.bind(0).sync().channel();
            ch.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer(data),
                    new InetSocketAddress("47.104.8.164",scanPort)
            )).sync();
            ch.closeFuture().await();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
}
