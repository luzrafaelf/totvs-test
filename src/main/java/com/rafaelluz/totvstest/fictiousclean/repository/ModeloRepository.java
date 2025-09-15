package com.rafaelluz.totvstest.fictiousclean.repository;

import com.rafaelluz.totvstest.fictiousclean.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, UUID> {
    boolean existsByNome(String nome);
}