package com.gb.netty.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.io.RandomAccessFile;
import java.util.List;

public class ResponseDecoder extends MessageToMessageDecoder<byte[]> {

    ObjectMapper om = new ObjectMapper();
    String storagePath= "C:\\Users\\Николай\\IdeaProjects\\FileStorageNew\\client\\src\\clientStorage\\";
    @Override
    protected void decode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {
        Response res = om.readValue(msg, Response.class);
        String response = res.getResponse();
        System.out.println("receive answer from server: " + response);
        switch (response){
            case "receive file":{
                RandomAccessFile accessFile = new RandomAccessFile(storagePath + res.getFilename(), "rw");
                accessFile.seek(res.getPosition());
                accessFile.write(res.getFile());
                System.out.println(res.getFilename() + " file received from the server");
                break;
            }
            default:
                System.out.println("Server's response: " + response);
        }
        out.add(res);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
