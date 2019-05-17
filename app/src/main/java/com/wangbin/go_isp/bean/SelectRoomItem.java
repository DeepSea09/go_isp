package com.wangbin.go_isp.bean;

public class SelectRoomItem {

    private String roomName;
    private String assets_total;
    private String assets_current;

    public String getAssets_total() {
        return assets_total;
    }

    public void setAssets_total(String assets_total) {
        this.assets_total = assets_total;
    }

    public String getAssets_current() {
        return assets_current;
    }

    public void setAssets_current(String assets_current) {
        this.assets_current = assets_current;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
