package com.wangbin.go_isp.ui.activity;

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp.ui.activity
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/5/10
 * <br></br>TIME : 12:39
 * <br></br>MSG :
 * <br></br>************************************************
 */
public class ScanningSizeEvent {

    private int scanning_size;

    public ScanningSizeEvent(int scanning_size) {
        this.scanning_size = scanning_size;
    }

    public int getScanning_size() {
        return scanning_size;
    }
    public void setScanning_size(int scanning_size) {
        this.scanning_size = scanning_size;
    }
}
