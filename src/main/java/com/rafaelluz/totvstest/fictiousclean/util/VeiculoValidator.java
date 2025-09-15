package com.rafaelluz.totvstest.fictiousclean.util;

import com.rafaelluz.totvstest.fictiousclean.dto.VeiculoDTO;
import com.rafaelluz.totvstest.fictiousclean.repository.MarcaRepository;
import com.rafaelluz.totvstest.fictiousclean.repository.ModeloRepository;
import com.rafaelluz.totvstest.fictiousclean.repository.VeiculoRepository;
import jakarta.validation.ValidationException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VeiculoValidator {

    final MarcaRepository marcaRepository;
    final ModeloRepository modeloRepository;
    final VeiculoRepository veiculoRepository;

    public VeiculoValidator(MarcaRepository marcaRepository, ModeloRepository modeloRepository, VeiculoRepository veiculoRepository) {
        this.marcaRepository = marcaRepository;
        this.modeloRepository = modeloRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public void validarNovoVeiculo(VeiculoDTO veiculoDTO) {

        if (veiculoDTO.getMarca() != null && veiculoDTO.getMarca().getId() == null && veiculoDTO.getMarca().getNome() != null && marcaRepository.existsByNome(veiculoDTO.getMarca().getNome())) {
            throw new ValidationException("Marca com o nome '" + veiculoDTO.getMarca().getNome() + "' já existe.");
        }

        if (veiculoDTO.getModelo() != null && veiculoDTO.getModelo().getId() == null && veiculoDTO.getModelo().getNome() != null && modeloRepository.existsByNome(veiculoDTO.getModelo().getNome())) {
            throw new ValidationException("Modelo com o nome '" + veiculoDTO.getModelo().getNome() + "' já existe.");
        }

    }
}