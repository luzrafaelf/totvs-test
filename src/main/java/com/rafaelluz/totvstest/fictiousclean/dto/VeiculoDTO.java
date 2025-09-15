package com.rafaelluz.totvstest.fictiousclean.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class VeiculoDTO {
    UUID id;
    String nome;
    LocalDate dataFabricacao;
    BigDecimal consumoMedioCidade;
    BigDecimal consumoMedioRodovias;
    MarcaDTO marca;
    ModeloDTO modelo;
}