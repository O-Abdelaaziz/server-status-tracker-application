package com.server.status.tacker.resource;

import com.server.status.tacker.entity.Server;
import com.server.status.tacker.enumeration.Status;
import com.server.status.tacker.payload.response.Response;
import com.server.status.tacker.service.IServerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Created 24/06/2024 - 09:00
 * @Package com.server.status.tacker.controller
 * @Project server-status-tracker
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@RestController
@RequestMapping("/servers")
public class ServerController {
    private final IServerService iServerService;

    @Autowired
    public ServerController(IServerService iServerService) {
        this.iServerService = iServerService;
    }

    @GetMapping("/limit/{limit}")
    public ResponseEntity<Response> servers(@PathVariable(name = "limit", value = "30") int limit) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("Servers Retrieved")
                        .data(Map.of("servers", iServerService.serversByLimit(limit)))
                        .build());
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Response> getServer(@PathVariable(name = "uid") String uid) throws IOException {
        Server server = iServerService.findByUid(uid);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("Server Retrieved")
                        .data(Map.of("Server", server))
                        .build());
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable(name = "ipAddress") String ipAddress) throws IOException {
        Server server = iServerService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message(server.getStatus() == Status.SERVER_UP ? "Ping Success" : "Ping Failed")
                        .data(Map.of("Server", server))
                        .build());
    }

    @PostMapping("/save")
    public ResponseEntity<Response> save(@Valid @RequestBody Server server) throws IOException {
        Server savedServer = iServerService.save(server);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .message("Server was created successfully")
                        .data(Map.of("Server", savedServer))
                        .build());
    }

    @PutMapping("/update/{uid}")
    public ResponseEntity<Response> update(@PathVariable(name = "uid") String uid, @Valid @RequestBody Server server) {
        Server updatedServer = iServerService.update(uid, server);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .message("Server was updated successfully")
                        .data(Map.of("Server", updatedServer))
                        .build());
    }

    @DeleteMapping("/delete/{uid}")
    public ResponseEntity<Response> delete(@PathVariable(name = "uid") String uid) {
        Boolean isServerDeleted = iServerService.delete(uid);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .status(HttpStatus.NO_CONTENT)
                        .message(isServerDeleted.booleanValue() == Boolean.TRUE ? "Server was deleted successfully" : "Something went wrong, please tray again later.")
                        .data(Map.of("Server Deleted", isServerDeleted))
                        .build());
    }

    @GetMapping(path = "/icon/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] serverIcon(@PathVariable(name = "fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "Downloads/icons/" + fileName));
    }
}
