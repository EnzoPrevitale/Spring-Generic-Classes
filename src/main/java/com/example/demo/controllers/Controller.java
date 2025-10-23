package com.example.demo.controllers;

import com.example.demo.services.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class Controller<M,
        PK,
        R extends JpaRepository<M, PK>,
        D,
        S extends Service<M, PK, R, D>> {

    private final S service;

    public Controller(S service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<M>> get() {
        return ResponseEntity.ok(service.read());
    }

    @GetMapping("/{id}")
    public ResponseEntity<M> get(@PathVariable PK id) {
        return service.read(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<M> post(@RequestBody D dto) {
        M model = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }
}
