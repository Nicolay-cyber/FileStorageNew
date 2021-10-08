package com.gb.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        new Client().start();
    }
    ChannelFuture future;
    public void start() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap client = new Bootstrap();
            client.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new LengthFieldBasedFrameDecoder(
                                            1024*1024*1024,
                                            0,
                                            8,
                                            0,
                                            8
                                    ),
                                    new LengthFieldPrepender(8),
                                    new ByteArrayDecoder(),
                                    new ByteArrayEncoder(),
                                    new ResponseDecoder(),
                                    new RequestEncoder()
                                    );
                        }
                    });

            future = client.connect("localhost", 9000).sync();

            cmd();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    private void cmd() throws InterruptedException {
        System.out.println("? - help");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String cmd = scanner.nextLine();
            String [] s = cmd.split(" ");
            Request request;
            try{
                switch (s[0]){
                    case "quit":{
                        System.exit(1);
                        break;
                    }
                    case "hi":{
                        request = new Request("hi");
                        future.channel().writeAndFlush(request);
                        break;
                    }
                    case "getFile":{
                        request = new Request("get file",s[1]);
                        future.channel().writeAndFlush(request);
                        break;
                    }
                    case "sendFile":{
                        byte[] buffer = new byte[1024 * 512];
                        try (RandomAccessFile accessFile = new RandomAccessFile(s[1], "r")) {
                            while (true) {
                                request = new Request("receive file",s[1]);
                                request.setPosition(accessFile.getFilePointer());
                                int read = accessFile.read(buffer);
                                if (read < buffer.length - 1) {
                                    byte[] tempBuffer = new byte[read];
                                    System.arraycopy(buffer, 0, tempBuffer, 0, read);
                                    request.setFile(tempBuffer);
                                    future.channel().writeAndFlush(request);
                                    break;
                                } else {
                                    request.setFile(buffer);
                                    future.channel().writeAndFlush(request);
                                }
                                buffer = new byte[1024 * 512];
                            }
                        }  catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    default:
                        System.out.println("Unknown command");
                }
            }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println("The command isn't complete");
            }
        }
    }
}
