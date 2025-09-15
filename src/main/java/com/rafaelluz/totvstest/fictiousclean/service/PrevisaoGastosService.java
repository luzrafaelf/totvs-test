package com.rafaelluz.totvstest.fictiousclean.service;

import com.rafaelluz.totvstest.fictiousclean.dto.PrevisaoGastoInputDTO;
import com.rafaelluz.totvstest.fictiousclean.dto.PrevisaoGastoOutputDTO;
import com.rafaelluz.totvstest.fictiousclean.repository.VeiculoRepository;
import com.rafaelluz.totvstest.fictiousclean.util.EntityMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrevisaoGastosService {

    @Autowired
    VeiculoRepository veiculoRepository;

    public List<PrevisaoGastoOutputDTO> listarRanking(PrevisaoGastoInputDTO previsaoGastoDTO) {

        return veiculoRepository.findAll().stream().map(veiculo -> {

                    var consumoCidade = previsaoGastoDTO.getTotalPercorridoCidade().divide(veiculo.getConsumoMedioCidade(), RoundingMode.HALF_EVEN);
                    var consumoRodovia = previsaoGastoDTO.getTotalPercorridoRodovia().divide(veiculo.getConsumoMedioRodovias(), RoundingMode.HALF_EVEN);
                    var consumoTotal = consumoCidade.add(consumoRodovia);
                    var custoTotal = consumoTotal.multiply(previsaoGastoDTO.getPrecoGasolina());

                    return new PrevisaoGastoOutputDTO(EntityMapper.toVeiculoDTO(veiculo), custoTotal);

                })
                .sorted(Comparator.comparing(PrevisaoGastoOutputDTO::getCustoTotal))
                .toList();

    }
}
