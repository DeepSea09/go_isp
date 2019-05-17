package com.wangbin.go_isp.bean;

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.bean
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/4/28
 * <br></br>TIME : 21:26
 * <br></br>MSG :
 * <br></br>************************************************
 */


public class FangjaincodeBean {


    /**
     * room_rfid : 300833B2DDD9014000000000
     * room_assets_total : 2
     * receive_total : 6
     */

    private String room_rfid;
    private int room_assets_total;
    private int receive_total;

    public String getRoom_rfid() {
        return room_rfid;
    }

    public void setRoom_rfid(String room_rfid) {
        this.room_rfid = room_rfid;
    }

    public int getRoom_assets_total() {
        return room_assets_total;
    }

    public void setRoom_assets_total(int room_assets_total) {
        this.room_assets_total = room_assets_total;
    }

    public int getReceive_total() {
        return receive_total;
    }

    public void setReceive_total(int receive_total) {
        this.receive_total = receive_total;
    }
}
