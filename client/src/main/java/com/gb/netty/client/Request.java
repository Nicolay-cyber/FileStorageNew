package com.gb.netty.client;

public class Request {
    private String filename;
    private String command;
    private byte[] file;
    private long position;

    public Request() {
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Request(String command, String filename, byte[] file) {
        this.filename = filename;
        this.command = command;
        this.file = file;
    }

    public Request(String command) {
        this.command = command;
    }

    public Request(String command, String filename) {
        this.filename = filename;
        this.command = command;
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

