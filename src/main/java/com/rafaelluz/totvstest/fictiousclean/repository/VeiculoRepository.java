package com.rafaelluz.totvstest.fictiousclean.repository;

import com.rafaelluz.totvstest.fictiousclean.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, UUID> {
    boolean existsByNome(String nome);
}