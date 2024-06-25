package com.server.status.tacker.payload.response;

import com.server.status.tacker.enumeration.Status;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Created 25/06/2024 - 09:58
 * @Package com.server.status.tacker.payload.response
 * @Project server-status-tracker
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Data
public class ServerResponse {
    private Long id;
    private String uid;
    private String owner;
    private String ipAddress;
    private String name;
    private String memory;
    private String icon;
    private String type;
    private Status status;
    private LocalDateTime lastCheck;
    private String lastCheckTimeAgo;
    private LocalDateTime createdAt;
    private String createdAtTimeAgo;
    private LocalDateTime updatedAt;
    private String updatedAtTimeAgo;
}
