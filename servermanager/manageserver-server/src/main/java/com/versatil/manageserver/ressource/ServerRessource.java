package com.versatil.manageserver.ressource;

import com.versatil.manageserver.model.Response;
import com.versatil.manageserver.model.Server;
import com.versatil.manageserver.service.implementation.ServerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static com.versatil.manageserver.model.Status.SERVER_UP;
import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/server")
@RequiredArgsConstructor
public class ServerRessource {
    private final ServerServiceImpl serverService;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers() throws InterruptedException {
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(of("servers", serverService.list(30)))
                        .message("Servers fetched successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ok(
                Response.builder()
                        .timestamp(now())
                        .data(of("server", server))
                        .message(server.getStatus() == SERVER_UP ? "Ping success" : "Ping failed")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server)  {
        return ok(
                Response.builder()
                        .timestamp(now())
                        .data(of("servers", serverService.create(server)))
                        .message("Server created successfully")
                        .status(OK)
                        .statusCode(CREATED.value())
                        .build()
        );
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id)  {
        return ok(
                Response.builder()
                        .timestamp(now())
                        .data(of("servers", serverService.get(id)))
                        .message("Server retrieved successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id)  {
        return ok(
                Response.builder()
                        .timestamp(now())
                        .data(of("deleted", serverService.delete(id)))
                        .message("Server deleted successfully")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
    @GetMapping (path  = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Downloads/" + fileName));
    }
}
