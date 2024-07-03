package com.server.status.tacker.payload.requests;

import lombok.Data;

/**
 * @Created 02/07/2024 - 13:36
 * @Package com.server.status.tacker.payload.requests
 * @Project server-status-tracker
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Data
public class ServerRequest {
    private String name;
    private String owner;
    private String ipAddress;
    private String memory;
    private String type;
    private String icon;
}
