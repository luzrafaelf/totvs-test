package com.rafaelluz.totvstest.fictiousclean.util;

import com.rafaelluz.totvstest.fictiousclean.dto.VeiculoDTO;
import com.rafaelluz.totvstest.fictiousclean.dto.MarcaDTO;
import com.rafaelluz.totvstest.fictiousclean.dto.ModeloDTO;
import com.rafaelluz.totvstest.fictiousclean.model.Veiculo;
import com.rafaelluz.totvstest.fictiousclean.model.Marca;
import com.rafaelluz.totvstest.fictiousclean.model.Modelo;
import lombok.experimental.UtilityClass;

@UtilityClass
/**
 * Classe responsável pela conversão de DTOs para Entities e vice-versa.
 */
public class EntityMapper {

    public static VeiculoDTO toVeiculoDTO(Veiculo veiculo) {
        if (veiculo == null) {
            return null;
        }
        VeiculoDTO dto = new VeiculoDTO();
        dto.setId(veiculo.getId());
        dto.setNome(veiculo.getNome());
        dto.setDataFabricacao(veiculo.getDataFabricacao());
        dto.setConsumoMedioCidade(veiculo.getConsumoMedioCidade());
        dto.setConsumoMedioRodovias(veiculo.getConsumoMedioRodovias());

        if (veiculo.getMarca() != null) {
            dto.setMarca(toMarcaDTO(veiculo.getMarca()));
        }
        if (veiculo.getModelo() != null) {
            dto.setModelo(toModeloDTO(veiculo.getModelo()));
        }
        return dto;
    }

    public static MarcaDTO toMarcaDTO(Marca marca) {
        if (marca == null) {
            return null;
        }
        MarcaDTO dto = new MarcaDTO();
        dto.setId(marca.getId());
        dto.setNome(marca.getNome());
        return dto;
    }

    public static ModeloDTO toModeloDTO(Modelo modelo) {
        if (modelo == null) {
            return null;
        }
        ModeloDTO dto = new ModeloDTO();
        dto.setId(modelo.getId());
        dto.setNome(modelo.getNome());
        return dto;
    }

    public static Veiculo toVeiculoEntity(VeiculoDTO veiculoDTO) {
        return toVeiculoEntity(veiculoDTO, new Veiculo());
    }


    public static Veiculo toVeiculoEntity(VeiculoDTO veiculoDTO, Veiculo veiculo) {
        if (veiculoDTO == null) {
            return null;
        }
        veiculo.setId(veiculoDTO.getId());
        veiculo.setNome(veiculoDTO.getNome());
        veiculo.setDataFabricacao(veiculoDTO.getDataFabricacao());
        veiculo.setConsumoMedioCidade(veiculoDTO.getConsumoMedioCidade());
        veiculo.setConsumoMedioRodovias(veiculoDTO.getConsumoMedioRodovias());

        if (veiculoDTO.getMarca() != null) {
            veiculo.setMarca(toMarcaEntity(veiculoDTO.getMarca()));
        }
        if (veiculoDTO.getModelo() != null) {
            veiculo.setModelo(toModeloEntity(veiculoDTO.getModelo()));
        }
        return veiculo;
    }

    public static Marca toMarcaEntity(MarcaDTO marcaDTO) {
        if (marcaDTO == null) {
            return null;
        }
        Marca marca = new Marca();
        marca.setId(marcaDTO.getId());
        marca.setNome(marcaDTO.getNome());
        return marca;
    }

    public static Modelo toModeloEntity(ModeloDTO modeloDTO) {
        if (modeloDTO == null) {
            return null;
        }
        Modelo modelo = new Modelo();
        modelo.setId(modeloDTO.getId());
        modelo.setNome(modeloDTO.getNome());
        return modelo;
    }
}