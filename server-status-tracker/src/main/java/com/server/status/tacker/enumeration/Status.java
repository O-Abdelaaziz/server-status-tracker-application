package com.server.status.tacker.enumeration;

/**
 * @Created 23/06/2024 - 14:10
 * @Package com.server.status.tacker.enumeration
 * @Project server-status-tracker
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
public enum Status {
    SERVER_UP("SERVER_UP"),
    SERVER_DOWN("SERVER_DOWN");

    private String status;

    Status(String status) {
        this.status = status;
    }

    private String getStatus() {
        return this.status;
    }
}
