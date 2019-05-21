package com.wangbin.go_isp.bean;

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.bean
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/4/30
 * <br></br>TIME : 19:40
 * <br></br>MSG :
 * <br></br>************************************************
 */
public class MessageEvent {
    public int message;
    public String room_code= null;
    public String room_name= null;
    public int  rfid_num  = 0;

    public MessageEvent(int message,String room_code,String room_name,int rfid_num) {
        this.message = message;
        this.room_code = room_code;
        this.room_name = room_name;
        this.rfid_num = rfid_num;
    }

    public MessageEvent(int message) {
        this.message = message;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public String getRoom_code() {
        return room_code;
    }

    public void setRoom_code(String room_code) {
        this.room_code = room_code;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public int getRfid_num() {
        return rfid_num;
    }

    public void setRfid_num(int rfid_num) {
        this.rfid_num = rfid_num;
    }
}
