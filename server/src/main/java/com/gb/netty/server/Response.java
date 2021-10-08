package com.gb.netty.server;

public class Response {
    private String response;
    private String filename;
    private long position;
    private byte[] file;

    public Response(String response) {
        this.response = response;
    }

    public Response() {
    }

    public void setResponse(String response) {
        this.response = response;
    }

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

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
