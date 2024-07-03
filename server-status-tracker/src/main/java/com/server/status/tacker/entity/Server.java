package com.server.status.tacker.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.server.status.tacker.enumeration.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

/**
 * @Created 23/06/2024 - 14:06
 * @Package com.server.status.tacker.entity
 * @Project server-status-tracker
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Schema(description = "Server Model Information")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "servers")
public class Server implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "server uid", example = "1db5333e-42b0-44b1-8fb5-7acd84f10009")
    @UuidGenerator
    private String uid;

    @Schema(description = "server owner", example = "O.Abdelaaziz")
    @Column(name = "owner")
    private String owner;

    @Schema(description = "server ipAddress", example = "10.10.10.25")
    @NotEmpty(message = "IP Address cannot be empty or null")
    @Column(name = "ip_address", unique = true)
    private String ipAddress;

    @Schema(description = "server name", example = "Linux Virtual Machine")
    @Column(name = "name")
    private String name;

    @Schema(description = "server memory", example = "64 GB")
    @Column(name = "memory")
    private String memory;

    @Schema(description = "server icon", example = "fas fa-server fa-x2")
    @Column(name = "icon")
    private String icon;

    @Schema(description = "server type", example = "Mail Server")
    @Column(name = "type")
    private String type;

    @Schema(description = "is server deleted", example = "true")
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Schema(description = "server delete date", example = "2024-06-24T13:47:40.273Z")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Schema(description = "server status", example = "SERVER_UP")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Schema(description = "server lastCheck", example = "2024-06-24T13:47:40.273Z")
    @Column(name = "last_check")
    private LocalDateTime lastCheck;
    @Schema(description = "server created date", example = "2024-06-24T13:47:40.273Z")
    @CreationTimestamp
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Schema(description = "server updated date", example = "2024-06-24T13:47:40.273Z")
    @UpdateTimestamp
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "updated_at", nullable = true, insertable = false)
    private LocalDateTime updatedAt;

    public String createdTimeAgo() {
        PrettyTime pretty = new PrettyTime(new Locale("en"));
        if (this.createdAt != null) {
            return pretty.format(this.createdAt);
        }
        return null;
    }

    public String updatedTimeAgo() {
        PrettyTime pretty = new PrettyTime(new Locale("en"));
        if (this.updatedAt != null) {
            return pretty.format(this.updatedAt);
        }
        return null;
    }

    public String lastCheckTimeAgo() {
        PrettyTime pretty = new PrettyTime(new Locale("en"));
        if (this.lastCheck != null) {
            return pretty.format(this.lastCheck);
        }
        return null;
    }
}
