package com.example.demo.services;

import com.example.demo.mappers.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class Service<M, PK ,R extends JpaRepository<M, PK>, D> {

    private final R repository;
    private final Mapper<D, M> mapper;

    public Service(R repository, Mapper<D, M> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<M> read() {
        return repository.findAll();
    }

    public Optional<M> read(PK id) {
        return repository.findById(id);
    }

    public M create(D dto) {
        M model = mapper.toModel(dto);
        return repository.save(model);
    }

    public Optional<M> update(PK id, D dto) {
        Optional<M> model = repository.findById(id);
        return model
                .map(m -> {
                   m = mapper.toModel(dto);
                   return repository.save(m);
                });
    }

    public Optional<M> updatePartially(PK id, D dto) {
        Optional<M> model = repository.findById(id);
        return model
                .map(m -> {
                    m = mapper.toModelPartially(dto, m);
                    return repository.save(m);
                });
    }

    public Optional<M> delete(PK id) {
        Optional<M> model = repository.findById(id);
        return model
                .map(m -> {
                    repository.delete(m);
                    return m;
                });
    }
}
