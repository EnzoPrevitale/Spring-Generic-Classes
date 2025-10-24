package com.example.demo.generics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class Controller<
        M extends Model<PK>, PK, R extends JpaRepository<M, PK> & Repository<M, PK>, D, S extends Service<M, PK, R, D>> {

    private final S service;
    private final SqlConverter<M, PK> converter;
    private final Class<M> modelClass;

    public Controller(S service, SqlConverter<M, PK> converter, Class<M> modelClass) {
        this.service = service;
        this.converter = converter;
        this.modelClass = modelClass;
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

    @GetMapping("/fields")
    public Map<String, String> getFields() {
        return converter.convert(modelClass);
    }

    @PostMapping
    public ResponseEntity<M> post(@RequestBody D dto) throws URISyntaxException {
        M model = service.create(dto);
        return ResponseEntity.created(new URI("/" + model.getId())).body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<M> put(@PathVariable PK id, @RequestBody D dto) {
        return service
                .update(id, dto)
                .map(m -> ResponseEntity.accepted().body(m))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<M> patch(@PathVariable PK id, @RequestBody D dto) {
        return service
                .updatePartially(id, dto)
                .map(m -> ResponseEntity.accepted().body(m))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable PK id) {
        return service
                .delete(id)
                .map(_ -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }
}
