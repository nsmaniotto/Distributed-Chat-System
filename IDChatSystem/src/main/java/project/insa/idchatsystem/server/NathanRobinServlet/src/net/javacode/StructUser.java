package net.javacode;

import java.io.PrintWriter;
import java.util.ArrayList;

class StructUser {
    public PrintWriter writer = null;
    public ArrayList<StructMsg> cacheLog = null;
    public ArrayList<StructMsg> cacheConv = null;
    public String state = "disconnected";
    public StructUser(PrintWriter writer,ArrayList<StructMsg> cacheLog,ArrayList<StructMsg> cacheConv,String state) {
        this.writer = writer;
        this.cacheLog = cacheLog;
        this.cacheConv = cacheConv;
        this.state = state;
    }
    public StructUser(PrintWriter writer,ArrayList<StructMsg> cacheLog,ArrayList<StructMsg> cacheConv) {
        this.cacheLog = cacheLog;
        this.cacheConv = cacheConv;
    }
}
