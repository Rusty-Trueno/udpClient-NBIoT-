package com.rongshuai.udpClient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;


public class udpClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx,DatagramPacket packet) throws Exception{
        String body = packet.content().toString(CharsetUtil.UTF_8);
        System.out.println(body);
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx)throws Exception{
        System.out.println("客户端：channelActive");

    }
}
