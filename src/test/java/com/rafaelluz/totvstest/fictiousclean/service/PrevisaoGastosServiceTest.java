package com.rafaelluz.totvstest.fictiousclean.service;

import com.rafaelluz.totvstest.fictiousclean.dto.PrevisaoGastoInputDTO;
import com.rafaelluz.totvstest.fictiousclean.dto.PrevisaoGastoOutputDTO;
import com.rafaelluz.totvstest.fictiousclean.model.Marca;
import com.rafaelluz.totvstest.fictiousclean.model.Modelo;
import com.rafaelluz.totvstest.fictiousclean.model.Veiculo;
import com.rafaelluz.totvstest.fictiousclean.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrevisaoGastosServiceTest {

    @InjectMocks
    PrevisaoGastosService previsaoGastosService;

    @Mock
    VeiculoRepository veiculoRepository;

    List<Veiculo> veiculos = new ArrayList<>();

    @BeforeEach
    void setup() {

        var modelo = new Modelo();
        modelo.setNome("modelo");

        var marca = new Marca();
        marca.setNome("marca");

        var veiculo = new Veiculo();
        veiculo.setNome("Econômico Rodovia");
        veiculo.setId(UUID.fromString("cd50edea-53be-47c5-8d1d-52eb00f0c8bd"));
        veiculo.setModelo(modelo);
        veiculo.setMarca(marca);
        veiculo.setDataFabricacao(LocalDate.now());
        veiculo.setConsumoMedioRodovias(new BigDecimal("15.0"));
        veiculo.setConsumoMedioCidade(new BigDecimal("5.0"));
        veiculos.add(veiculo);

        veiculo = new Veiculo();
        veiculo.setNome("Econômico Cidade");
        veiculo.setId(UUID.fromString("27b67e36-1865-40e1-b789-4008da247c2c"));
        veiculo.setModelo(modelo);
        veiculo.setMarca(marca);
        veiculo.setDataFabricacao(LocalDate.now());
        veiculo.setConsumoMedioRodovias(new BigDecimal("5.0"));
        veiculo.setConsumoMedioCidade(new BigDecimal("15.0"));
        veiculos.add(veiculo);

        veiculo = new Veiculo();
        veiculo.setNome("Mediano");
        veiculo.setId(UUID.fromString("d47905e6-ff6d-456a-808e-7060d2be06cb"));
        veiculo.setModelo(modelo);
        veiculo.setMarca(marca);
        veiculo.setDataFabricacao(LocalDate.now());
        veiculo.setConsumoMedioRodovias(new BigDecimal("10.0"));
        veiculo.setConsumoMedioCidade(new BigDecimal("10.0"));
        veiculos.add(veiculo);

    }

    @Test
    @DisplayName("Deve listar o ranking de veículos, mostrando em primeiro o veículo mais econômico em ambos os tipos de trajeto")
    void testListarRanking_shouldListarRanking_whenTrajetosIguais() {

        // Arrange
        var previsaoGastoDTO = new PrevisaoGastoInputDTO();
        previsaoGastoDTO.setPrecoGasolina(new BigDecimal("5.00"));
        previsaoGastoDTO.setTotalPercorridoCidade(new BigDecimal("50.00"));
        previsaoGastoDTO.setTotalPercorridoRodovia(new BigDecimal("50.00"));

        when(veiculoRepository.findAll()).thenReturn(veiculos);

        // Act
        List<PrevisaoGastoOutputDTO> previsaoGastoOutputDTOS = previsaoGastosService.listarRanking(previsaoGastoDTO);

        // Assert
        assertEquals(3, previsaoGastoOutputDTOS.size());

        var primeiro = previsaoGastoOutputDTOS.get(0);
        assertEquals(new BigDecimal("50.00"), primeiro.getCustoTotal().setScale(2));
        assertEquals("Mediano", primeiro.getVeiculo().getNome());

    }

    @Test
    @DisplayName("Deve listar o ranking de veículos, mostrando em primeiro o mais econômico em cidade")
    void testListarRanking_shouldListarRanking_whenTrajetoCidadeForMaior() {

        // Arrange
        var previsaoGastoDTO = new PrevisaoGastoInputDTO();
        previsaoGastoDTO.setPrecoGasolina(new BigDecimal("5.00"));
        previsaoGastoDTO.setTotalPercorridoCidade(new BigDecimal("100.00"));
        previsaoGastoDTO.setTotalPercorridoRodovia(new BigDecimal("5.00"));

        when(veiculoRepository.findAll()).thenReturn(veiculos);

        // Act
        List<PrevisaoGastoOutputDTO> previsaoGastoOutputDTOS = previsaoGastosService.listarRanking(previsaoGastoDTO);

        // Assert
        assertEquals(3, previsaoGastoOutputDTOS.size());

        var primeiro = previsaoGastoOutputDTOS.get(0);
        assertEquals(new BigDecimal("38.35"), primeiro.getCustoTotal().setScale(2));
        assertEquals("Econômico Cidade", primeiro.getVeiculo().getNome());

    }

    @Test
    @DisplayName("Deve listar o ranking de veículos, mostrando em primeiro o mais econômico em rodovias")
    void testListarRanking_shouldListarRanking_whenTrajetoRodoviaForMaior() {

        // Arrange
        var previsaoGastoDTO = new PrevisaoGastoInputDTO();
        previsaoGastoDTO.setPrecoGasolina(new BigDecimal("5.00"));
        previsaoGastoDTO.setTotalPercorridoCidade(new BigDecimal("5.00"));
        previsaoGastoDTO.setTotalPercorridoRodovia(new BigDecimal("100.00"));

        when(veiculoRepository.findAll()).thenReturn(veiculos);

        // Act
        List<PrevisaoGastoOutputDTO> previsaoGastoOutputDTOS = previsaoGastosService.listarRanking(previsaoGastoDTO);

        // Assert
        assertEquals(3, previsaoGastoOutputDTOS.size());

        var primeiro = previsaoGastoOutputDTOS.get(0);
        assertEquals(new BigDecimal("38.35"), primeiro.getCustoTotal().setScale(2));
        assertEquals("Econômico Rodovia", primeiro.getVeiculo().getNome());

    }


}