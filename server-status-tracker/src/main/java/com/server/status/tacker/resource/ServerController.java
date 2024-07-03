package com.server.status.tacker.resource;

import com.server.status.tacker.entity.Server;
import com.server.status.tacker.payload.requests.ServerRequest;
import com.server.status.tacker.payload.response.ServerResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Collection;

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
//    @Parameters({@Parameter(name = "limit", description = "Display servers by limit")})
    @GetMapping
    public ResponseEntity<Collection<ServerResponse>> servers() {
        return ResponseEntity.ok(iServerService.serversByLimit(30));
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
    @GetMapping("/{uid}")
    public ResponseEntity<ServerResponse> getServer(@PathVariable(name = "uid") String uid) throws IOException {
        ServerResponse server = iServerService.findByUid(uid);
        return ResponseEntity.ok(server);
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
    public ResponseEntity<ServerResponse> pingServer(@PathVariable(name = "ipAddress") String ipAddress) throws IOException {
        ServerResponse server = iServerService.ping(ipAddress);
        return ResponseEntity.ok(server);
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
    @PostMapping
    public ResponseEntity<ServerResponse> save(@Valid @RequestBody ServerRequest serverRequest) throws IOException {
        ServerResponse savedServer = iServerService.save(serverRequest);
        return ResponseEntity.ok(savedServer);
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
    @PutMapping("/{uid}")
    public ResponseEntity<ServerResponse> update(@PathVariable(name = "uid") String uid, @Valid @RequestBody ServerRequest serverRequest) throws IOException {
        ServerResponse updatedServer = iServerService.update(uid, serverRequest);
        return ResponseEntity.ok(updatedServer);
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
    @DeleteMapping("/{uid}")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "uid") String uid) {
        Boolean isServerDeleted = iServerService.delete(uid);
        return ResponseEntity.ok(isServerDeleted);
    }
}
