package com.server.status.tacker.resource;

import com.server.status.tacker.entity.Server;
import com.server.status.tacker.enumeration.Status;
import com.server.status.tacker.payload.response.Response;
import com.server.status.tacker.service.IServerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Server Controller", description = "Server Management APIs")
@RestController
@RequestMapping("/servers")
public class ServerController {
    private final IServerService iServerService;

    @Autowired
    public ServerController(IServerService iServerService) {
        this.iServerService = iServerService;
    }

    @Operation(
            summary = "Retrieve a list of Servers limited by parameter limit",
            description = "Get a list of Servers object. The response is a list Servers object.",
            tags = {"servers", "getAll"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Server.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @Parameters({@Parameter(name = "limit", description = "Display servers by limit")})
    @GetMapping("/limit/{limit}")
    public ResponseEntity<Response> servers(
            @Parameter(description = "Display servers by limit")
            @PathVariable(name = "limit") int limit) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("Servers Retrieved")
                        .data(Map.of("servers", iServerService.serversByLimit(limit)))
                        .build());
    }

    @Operation(
            summary = "Retrieve a Server by Uid",
            description = "Get a Server object by specifying its uid. The response is Server object.",
            tags = {"servers", "getById"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Server.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @Parameters({@Parameter(name = "uid", description = "Display servers by uid")})
    @GetMapping("/server/{uid}")
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

    @Operation(
            summary = "Retrieve a Server by IP Address",
            description = "Get a Server object by specifying its IP Address. The response is Server object.",
            tags = {"servers", "getByIpAddress"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Server.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @Parameters({@Parameter(name = "ipAddress", description = "Display servers by ipAddress")})
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

    @Operation(
            summary = "Create a Server Object",
            description = "Create a Server object by specifying it object as input. The response is Server object.",
            tags = {"servers", "save"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Server.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @Parameters({@Parameter(name = "server", description = "Save new server by providing a server object")})
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

    @Operation(
            summary = "Update a Server Object",
            description = "Update a Server object by specifying its object and uid as inputs. The response is Server object.",
            tags = {"servers", "update"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Server.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @Parameters({
            @Parameter(name = "server", description = "Update server by providing a server object"),
            @Parameter(name = "uid", description = "Update server by providing a uid parameter", required = true),
    })
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

    @Operation(
            summary = "Delete a Server Object",
            description = "Delete a Server object by specifying it uid as input. The response is a boolean value (true if deleted).",
            tags = {"servers", "delete"})
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema(implementation = Server.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @Parameters({
            @Parameter(name = "uid", description = "delete server by providing a uid parameter", required = true),
    })
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

    @Operation(
            summary = "Retrieve a Server icon by file name",
            description = "Get a Server icon by specifying it file name. The response is Server object.",
            tags = {"servers", "getByFileName"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Server.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping(path = "/icon/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] serverIcon(@PathVariable(name = "fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "Downloads/icons/" + fileName));
    }
}
