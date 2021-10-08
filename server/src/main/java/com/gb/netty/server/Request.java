package com.gb.netty.server;

public class Request {
    private String filename;
    private String command;
    private byte[] file;
    private long position;

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public void setPosition(long position) {
        this.position = position;
    }

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

