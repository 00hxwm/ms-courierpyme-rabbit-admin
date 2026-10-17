package com.courierpyme.admin;

import jakarta.validation.constraints.NotBlank;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rabbit")
@Validated
public class RabbitAdminController {

    private final RabbitAdmin rabbitAdmin;

    public RabbitAdminController(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    @PostMapping("/queues")
    public ResponseEntity<Map<String, String>> createQueue(@RequestParam @NotBlank String name) {
        rabbitAdmin.declareQueue(new Queue(name, true));
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("queue", name));
    }

    @DeleteMapping("/queues/{name}")
    public ResponseEntity<Void> deleteQueue(@PathVariable @NotBlank String name) {
        rabbitAdmin.deleteQueue(name);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/exchanges")
    public ResponseEntity<Map<String, String>> createExchange(@RequestParam @NotBlank String name) {
        rabbitAdmin.declareExchange(new DirectExchange(name, true, false));
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("exchange", name));
    }

    @DeleteMapping("/exchanges/{name}")
    public ResponseEntity<Void> deleteExchange(@PathVariable @NotBlank String name) {
        rabbitAdmin.deleteExchange(name);
        return ResponseEntity.noContent().build();
    }
}