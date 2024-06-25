package com.server.status.tacker.service.impl;

import com.server.status.tacker.converter.ServerRequestConverter;
import com.server.status.tacker.entity.Server;
import com.server.status.tacker.enumeration.Status;
import com.server.status.tacker.payload.response.ServerResponse;
import com.server.status.tacker.repository.ServerRepository;
import com.server.status.tacker.service.IServerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;
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
    private final ServerRequestConverter serverRequestConverter;

    @Autowired
    public ServerServiceImpl(ServerRepository serverRepository, ServerRequestConverter serverRequestConverter) {
        this.serverRepository = serverRepository;
        this.serverRequestConverter = serverRequestConverter;
    }

    @Override
    public ServerResponse ping(String ipAddress) throws IOException {
        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
        server.setLastCheck(LocalDateTime.now());
        Server savedServer = serverRepository.save(server);
        ServerResponse serverResponse = serverRequestConverter.convertServerToServerResponse(savedServer);
        return serverResponse;
    }

    @Override
    public List<ServerResponse> servers() {
        List<Server> servers = serverRepository.findAll();
        return servers
                .stream()
                .map(serverRequestConverter::convertServerToServerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ServerResponse> serversByLimit(int limit) {
        List<Server> servers = serverRepository.findAll().stream().limit(limit).toList();
        return servers
                .stream()
                .map(serverRequestConverter::convertServerToServerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ServerResponse findByUid(String uid) {
        Server server = serverRepository.findByUid(uid);
        if (Objects.isNull(server)) {
            throw new IllegalArgumentException("Server not found by the provided uid: " + uid);
        }
        ServerResponse serverResponse = serverRequestConverter.convertServerToServerResponse(server);
        return serverResponse;
    }

    @Override
    public ServerResponse findByIpAddress(String ipAddress) {
        Server server = serverRepository.findByIpAddress(ipAddress);
        if (Objects.isNull(server)) {
            throw new IllegalArgumentException("Server not found by the provided IP Address: " + ipAddress);
        }
        ServerResponse serverResponse = serverRequestConverter.convertServerToServerResponse(server);

        return serverResponse;
    }

    @Override
    public ServerResponse save(Server server) {
        Server savedServer = serverRepository.save(server);
        ServerResponse serverResponse = serverRequestConverter.convertServerToServerResponse(savedServer);
        return serverResponse;
    }

    @Override
    public ServerResponse update(String uid, Server server) {
        Server getServer = serverRepository.findByUid(uid);
        if (Objects.isNull(getServer)) {
            throw new IllegalArgumentException("Server not found by the provided uid: " + uid);
        }
        getServer.setName(server.getName());
        getServer.setIpAddress(server.getIpAddress());
        getServer.setMemory(server.getMemory());
        getServer.setType(server.getType());
        getServer.setIcon(server.getIcon());
        getServer.setStatus(server.getStatus());

        Server updatedServer = serverRepository.save(getServer);
        ServerResponse serverResponse = serverRequestConverter.convertServerToServerResponse(updatedServer);
        return serverResponse;
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
