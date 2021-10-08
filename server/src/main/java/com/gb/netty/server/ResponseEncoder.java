package com.gb.netty.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class ResponseEncoder extends MessageToMessageEncoder<Response> {
    ObjectMapper om = new ObjectMapper();
    @Override
    protected void encode(ChannelHandlerContext ctx, Response response, List<Object> out) throws Exception {
        System.out.println("sending message ...");
        byte[] bytes = om.writeValueAsBytes(response);
        out.add(bytes);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
