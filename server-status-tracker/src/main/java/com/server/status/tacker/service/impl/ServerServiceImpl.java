package com.server.status.tacker.service.impl;

import com.server.status.tacker.entity.Server;
import com.server.status.tacker.enumeration.Status;
import com.server.status.tacker.repository.ServerRepository;
import com.server.status.tacker.service.IServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Created 24/06/2024 - 08:39
 * @Package com.server.status.tacker.service.impl
 * @Project server-status-tracker
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Transactional
@Service
public class ServerServiceImpl implements IServerService {
    private final ServerRepository serverRepository;

    @Autowired
    public ServerServiceImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        Server server = findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        return serverRepository.save(server);
    }

    @Override
    public List<Server> servers() {
        return serverRepository.findAll();
    }

    @Override
    public Collection<Server> serversByLimit(int limit) {
        return serverRepository.findAll().stream().limit(limit).collect(Collectors.toList());
    }

    @Override
    public Server findByUid(String uid) {
        Server server = serverRepository.findByUid(uid);
        if (Objects.isNull(server)) {
            throw new IllegalArgumentException("Server not found by the provided uid: " + uid);
        }
        return server;
    }

    @Override
    public Server findByIpAddress(String ipAddress) {
        Server server = serverRepository.findByIpAddress(ipAddress);
        if (Objects.isNull(server)) {
            throw new IllegalArgumentException("Server not found by the provided IP Address: " + ipAddress);
        }
        return server;
    }

    @Override
    public Server save(Server server) {
        return serverRepository.save(server);
    }

    @Override
    public Server update(String uid, Server server) {
        Server getServer = findByUid(uid);
        getServer.setName(server.getName());
        getServer.setIpAddress(server.getIpAddress());
        getServer.setMemory(server.getMemory());
        getServer.setType(server.getType());
        getServer.setIcon(server.getIcon());
        getServer.setStatus(server.getStatus());
        return serverRepository.save(getServer);
    }

    @Override
    public Boolean delete(String uid) {
        Server getServer = serverRepository.findByUid(uid);
        if (Objects.isNull(getServer)) {
            return false;
        }
        serverRepository.delete(getServer);
        return true;
    }

    private String setServerIcon() {
        String[] imageNames = {"server1.png", "server2.png", "server3.png", "server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/servers/icon/" + imageNames[new Random().nextInt(imageNames.length)]).toUriString();
    }
}
