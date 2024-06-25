package com.server.status.tacker.converter;

import com.server.status.tacker.entity.Server;
import com.server.status.tacker.payload.response.ServerResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Created 25/06/2024 - 10:00
 * @Package com.server.status.tacker.converter
 * @Project server-status-tracker
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Component
public class ServerRequestConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public ServerRequestConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ServerResponse convertServerToServerResponse(Server server) {

        ServerResponse serverResponse = modelMapper.map(server, ServerResponse.class);
        serverResponse.setCreatedAtTimeAgo(server.createdTimeAgo());
        serverResponse.setUpdatedAtTimeAgo(server.updatedTimeAgo());
        serverResponse.setLastCheckTimeAgo(server.lastCheckTimeAgo());

        return serverResponse;
    }


    public Server convertServerResponseToServer(ServerResponse serverResponse) {
        Server server = modelMapper.map(serverResponse, Server.class);
        return server;
    }
}
