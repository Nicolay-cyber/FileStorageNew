package com.gb.netty.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.List;

public class RequestDecoder extends MessageToMessageDecoder<byte[]> {
    ObjectMapper om = new ObjectMapper();
    String storagePath= "C:\\Users\\Николай\\IdeaProjects\\FileStorageNew\\server\\src\\main\\java\\com\\gb\\netty\\server\\serverStorage\\";
    @Override
    protected void decode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {
        Request req = om.readValue(msg, Request.class);
        String request = req.getCommand();
        System.out.println("Client sent request: " + request);
        Response response;
        switch (request){
            case "receive file":{
                RandomAccessFile accessFile = new RandomAccessFile(storagePath + req.getFilename(), "r");
                accessFile.seek(req.getPosition());
                accessFile.write(req.getFile());
                System.out.println(req.getFilename() + " file received from the server");
                break;
            }
            case "hi":{
                System.out.println("client said hi!");
                response = new Response("hi");
                ctx.writeAndFlush(response);
                break;
            }
            case "get file":{
                String filename = req.getFilename();
                byte[] buffer = new byte[1024 * 512];
                try (RandomAccessFile accessFile = new RandomAccessFile(filename, "r")) {
                    while (true) {
                        response = new Response("receive file");
                        response.setFilename(filename);
                        response.setPosition(accessFile.getFilePointer());
                        int read = accessFile.read(buffer);
                        if (read < buffer.length - 1) {
                            byte[] tempBuffer = new byte[read];
                            System.arraycopy(buffer, 0, tempBuffer, 0, read);
                            response.setFile(tempBuffer);
                            ctx.writeAndFlush(response);
                            break;
                        } else {
                            response.setFile(buffer);
                            ctx.writeAndFlush(response);
                        }
                        buffer = new byte[1024 * 512];
                    }
                }
                catch (FileNotFoundException e){
                    System.out.println("File isn't found");
                }
                break;
            }
        }

    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
