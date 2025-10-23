package com.example.demo.generics;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface Repository<M extends Model<PK>, PK> extends JpaRepository<M, PK> {

    @Override
    @Query("SELECT m FROM #{#entityName} m WHERE m.active = TRUE")
    List<M> findAll();

    @Override
    @Query("SELECT m FROM #{#entityName} m WHERE m.id = :id AND m.active = TRUE")
    Optional<M> findById(@Param("id") PK id);

    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} m SET m.active = FALSE WHERE m.id = :id")
    void softDelete(@Param("id") PK id);
}
