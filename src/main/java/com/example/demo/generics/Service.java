package com.example.demo.generics;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class Service<M extends Model<PK>, PK ,R extends Repository<M, PK>, D> {

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
                    BeanUtils.copyProperties(dto, m);
                    mapper.updateModel(dto, m);
                    return repository.save(m);
                });
    }

    public Optional<M> updatePartially(PK id, D dto) {
        Optional<M> model = repository.findById(id);
        return model
                .map(m -> {
                    mapper.updateModelPartially(dto, m);
                    return repository.save(m);
                });
    }

    public Optional<M> delete(PK id) {
        Optional<M> model = repository.findById(id);
        return model
                .map(m -> {
                    repository.softDelete(m);
                    return m;
                });
    }
}
