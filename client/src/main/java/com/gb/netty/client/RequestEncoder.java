package com.gb.netty.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class RequestEncoder extends MessageToMessageEncoder<Request> {
    ObjectMapper om = new ObjectMapper();
    @Override
    protected void encode(ChannelHandlerContext ctx, Request msg, List<Object> out) throws Exception {
        try{
            byte[] bytes = om.writeValueAsBytes(msg);
            out.add(bytes);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
