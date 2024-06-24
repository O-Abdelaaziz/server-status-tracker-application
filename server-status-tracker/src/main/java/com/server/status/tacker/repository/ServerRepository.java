package com.server.status.tacker.repository;

import com.server.status.tacker.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Created 24/06/2024 - 08:34
 * @Package com.server.status.tacker.repository
 * @Project server-status-tracker
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {
    Server findByIpAddress(String ipAddress);

    Server findByUid(String uid);
}
