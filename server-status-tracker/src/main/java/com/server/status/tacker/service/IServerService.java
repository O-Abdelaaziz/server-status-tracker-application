package com.server.status.tacker.service;

import com.server.status.tacker.entity.Server;
import com.server.status.tacker.payload.response.ServerResponse;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;

/**
 * @Created 24/06/2024 - 08:38
 * @Package com.server.status.tacker.service
 * @Project server-status-tracker
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
public interface IServerService {
    ServerResponse ping(String ipAddress) throws IOException;

    List<ServerResponse> servers();

    Collection<ServerResponse> serversByLimit(int limit);

    ServerResponse findByUid(String uid);

    ServerResponse findByIpAddress(String ipAddress);

    ServerResponse save(Server server);

    ServerResponse update(String uid, Server server);

    Boolean delete(String uid);
}
