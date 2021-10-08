package com.gb.netty.server;

public class Request {
    private String filename;
    private String command;
    private byte[] file;
    private long position;

    public Request() {
    }

    public String getFilename() {
        return filename;
    }

    public String getCommand() {
        return command;
    }

    public byte[] getFile() {
        return file;
    }

    public long getPosition() {
        return position;
    }
}

