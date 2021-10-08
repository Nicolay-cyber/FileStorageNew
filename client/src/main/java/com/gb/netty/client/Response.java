package com.gb.netty.client;

public class Response {
    public void setResponse(String response) {
        this.response = response;
    }

    public Response() {
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
