package com.server.status.tacker.entity;

import com.server.status.tacker.enumeration.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * @Created 23/06/2024 - 14:06
 * @Package com.server.status.tacker.entity
 * @Project server-status-tracker
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
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

    @UuidGenerator
    private String uid;

    @NotEmpty(message = "IP Address cannot be empty or null")
    @Column(name = "ip_address", unique = true)
    private String ipAddress;

    @Column(name = "name")
    private String name;

    @Column(name = "memory")
    private String memory;

    @Column(name = "icon")
    private String icon;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private Status status;
}
