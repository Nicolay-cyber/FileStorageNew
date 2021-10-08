package com.gb.netty.client;

public class Response {
    private String response;
    private String filename;
    private long position;
    private byte[] file;

    public String getResponse() {
        return response;
    }

    public String getFilename() {
        return filename;
    }

    public long getPosition() {
        return position;
    }

    public byte[] getFile() {
        return file;
    }

}
