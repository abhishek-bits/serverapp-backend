package com.tutorial.controller;

import com.tutorial.entity.Response;
import com.tutorial.entity.Server;
import com.tutorial.enums.Status;
import com.tutorial.service.impl.ServerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/server")
public class ServerController {

    @Autowired
    private ServerServiceImpl serverService;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers() throws InterruptedException {
//        TimeUnit.SECONDS.sleep(3);
        /*
         * Handling errors is extremely important.
         */
//        throw new RuntimeException("Something went wrong!");
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("servers", serverService.list(30)))
                        .message("Servers retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server", server))
                        .message(null != server && server.getStatus().equals(Status.SERVER_UP) ? "Ping success" : "Ping failure")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server", serverService.create(server)))
                        .message("Server created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value()) // HTTP Status = 201
                        .build());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server", serverService.get(id)))
                        .message("Server retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("deleted", serverService.delete(id)))
                        .message("Server deleted")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping(path = "/image/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(
                "D:\\IntelliJ Projects\\full-stack-tutorial\\src\\main\\resources\\images\\"
//                System.getProperty("INTELLIJ_PROJECT_DIR") // comes from IntelliJ Path Variables.
                        + fileName));
    }
}
