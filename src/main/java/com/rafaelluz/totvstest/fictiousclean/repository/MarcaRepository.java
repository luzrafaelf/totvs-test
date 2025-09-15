package com.rafaelluz.totvstest.fictiousclean.repository;

import com.rafaelluz.totvstest.fictiousclean.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, UUID> {
    boolean existsByNome(String nome);
}