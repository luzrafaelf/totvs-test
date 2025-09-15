package com.rafaelluz.totvstest.fictiousclean.service;

import com.rafaelluz.totvstest.fictiousclean.dto.VeiculoDTO;
import com.rafaelluz.totvstest.fictiousclean.model.Marca;
import com.rafaelluz.totvstest.fictiousclean.model.Modelo;
import com.rafaelluz.totvstest.fictiousclean.model.Veiculo;
import com.rafaelluz.totvstest.fictiousclean.repository.MarcaRepository;
import com.rafaelluz.totvstest.fictiousclean.repository.ModeloRepository;
import com.rafaelluz.totvstest.fictiousclean.repository.VeiculoRepository;
import com.rafaelluz.totvstest.fictiousclean.util.EntityMapper;
import com.rafaelluz.totvstest.fictiousclean.util.VeiculoValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VeiculoService {

    @Autowired
    VeiculoRepository veiculoRepository;

    @Autowired
    MarcaRepository marcaRepository;

    @Autowired
    ModeloRepository modeloRepository;

    @Autowired
    VeiculoValidator veiculoValidator;

    public List<VeiculoDTO> findAll() {
        return veiculoRepository.findAll().stream()
                .map(EntityMapper::toVeiculoDTO)
                .toList();
    }

    @Transactional
    public VeiculoDTO create(VeiculoDTO veiculo) {

        veiculoValidator.validarNovoVeiculo(veiculo);

        var veiculoEntity = EntityMapper.toVeiculoEntity(veiculo);
        var veiculoPersistido = veiculoRepository.saveAndFlush(veiculoEntity);
        return EntityMapper.toVeiculoDTO(veiculoPersistido);
    }

    @Transactional
    public VeiculoDTO update(VeiculoDTO veiculo, UUID uuid) {

        veiculoValidator.validarNovoVeiculo(veiculo);

        Veiculo veiculoToUpdate = veiculoRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Veículo com ID %s não encontrado", uuid)));

        veiculoToUpdate.setNome(veiculo.getNome());
        veiculoToUpdate.setDataFabricacao(veiculo.getDataFabricacao());
        veiculoToUpdate.setConsumoMedioCidade(veiculo.getConsumoMedioCidade());
        veiculoToUpdate.setConsumoMedioRodovias(veiculo.getConsumoMedioRodovias());

        if (veiculo.getMarca() != null) {
            if (veiculo.getMarca().getId() != null) {
                Marca marca = marcaRepository.findById(veiculo.getMarca().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));
                veiculoToUpdate.setMarca(marca);
            } else if (veiculo.getMarca().getNome() != null) {
                veiculoToUpdate.setMarca(new Marca(null, veiculo.getMarca().getNome()));
            }
        }

        if (veiculo.getModelo() != null) {
            if (veiculo.getModelo().getId() != null) {
                Modelo modelo = modeloRepository.findById(veiculo.getModelo().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Modelo não encontrado"));
                veiculoToUpdate.setModelo(modelo);
            } else if (veiculo.getModelo().getNome() != null) {
                veiculoToUpdate.setModelo(new Modelo(null, veiculo.getModelo().getNome()));
            }
        }

        Veiculo updatedVeiculo = veiculoRepository.saveAndFlush(veiculoToUpdate);

        return EntityMapper.toVeiculoDTO(updatedVeiculo);
    }

    public void remove(UUID uuid) {
        veiculoRepository.deleteById(uuid);
    }
}
