package com.rafaelluz.totvstest.fictiousclean.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "veiculo")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(name = "nome", nullable = false)
    String nome;

    @Column(name = "data_fabricacao", nullable = false)
    LocalDate dataFabricacao;

    /**
     * Valor em KM
     */
    @Column(name = "consumo_medio_cidade", nullable = false)
    BigDecimal consumoMedioCidade;

    /**
     * Valor em KM
     */
    @Column(name = "consumo_medio_rodovias", nullable = false)
    BigDecimal consumoMedioRodovias;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "marca_id")
    Marca marca;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "modelo_id")
    Modelo modelo;


}
