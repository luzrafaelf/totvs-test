package com.rafaelluz.totvstest.fictiousclean.service;

import com.rafaelluz.totvstest.fictiousclean.dto.MarcaDTO;
import com.rafaelluz.totvstest.fictiousclean.dto.ModeloDTO;
import com.rafaelluz.totvstest.fictiousclean.dto.VeiculoDTO;
import com.rafaelluz.totvstest.fictiousclean.model.Marca;
import com.rafaelluz.totvstest.fictiousclean.model.Modelo;
import com.rafaelluz.totvstest.fictiousclean.model.Veiculo;
import com.rafaelluz.totvstest.fictiousclean.repository.MarcaRepository;
import com.rafaelluz.totvstest.fictiousclean.repository.ModeloRepository;
import com.rafaelluz.totvstest.fictiousclean.repository.VeiculoRepository;
import com.rafaelluz.totvstest.fictiousclean.util.VeiculoValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para VeiculoService")
class VeiculoServiceTest {

    @InjectMocks
    private VeiculoService veiculoService;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private MarcaRepository marcaRepository;

    @Mock
    private ModeloRepository modeloRepository;

    @Mock
    private VeiculoValidator veiculoValidator;

    private Veiculo veiculo;
    private VeiculoDTO veiculoDTO;
    private UUID veiculoId;
    private Marca marca;
    private Modelo modelo;

    @BeforeEach
    void setup() {
        veiculoId = UUID.randomUUID();
        UUID marcaId = UUID.randomUUID();
        UUID modeloId = UUID.randomUUID();

        marca = new Marca(marcaId, "Fiat");
        modelo = new Modelo(modeloId, "Mobi");

        veiculo = new Veiculo(veiculoId, "Mobi Like", LocalDate.now(), new BigDecimal("10.0"), new BigDecimal("15.0"), marca, modelo);
        veiculoDTO = new VeiculoDTO(veiculoId, "Mobi Like", LocalDate.now(), new BigDecimal("10.0"), new BigDecimal("15.0"), new MarcaDTO(marcaId, "Fiat"), new ModeloDTO(modeloId, "Mobi"));
    }

    @Test
    @DisplayName("Deve retornar uma lista de VeiculoDTO quando veículos existirem")
    void testFindAll_shouldReturnVeiculoDTOList_whenVehiclesExist() {
        when(veiculoRepository.findAll()).thenReturn(List.of(veiculo));

        List<VeiculoDTO> result = veiculoService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(veiculoDTO.getNome(), result.get(0).getNome());
        verify(veiculoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando nenhum veículo for encontrado")
    void testFindAll_shouldReturnEmptyList_whenNoVehiclesExist() {
        when(veiculoRepository.findAll()).thenReturn(Collections.emptyList());

        List<VeiculoDTO> result = veiculoService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(veiculoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve criar um novo veículo com sucesso")
    void testCreate_shouldCreateNewVehicleSuccessfully() {
        doNothing().when(veiculoValidator).validarNovoVeiculo(any(VeiculoDTO.class));
        when(veiculoRepository.saveAndFlush(any(Veiculo.class))).thenReturn(veiculo);

        VeiculoDTO createdVeiculo = veiculoService.create(veiculoDTO);

        assertNotNull(createdVeiculo);
        assertEquals(veiculo.getNome(), createdVeiculo.getNome());
        verify(veiculoValidator, times(1)).validarNovoVeiculo(any(VeiculoDTO.class));
        verify(veiculoRepository, times(1)).saveAndFlush(any(Veiculo.class));
    }

    @Test
    @DisplayName("Deve lançar exceção de validação ao tentar criar um veículo com nome já existente")
    void testCreate_shouldThrowValidationException_whenVehicleAlreadyExists() {
        doThrow(new ValidationException("Veículo já existe")).when(veiculoValidator).validarNovoVeiculo(any(VeiculoDTO.class));

        assertThrows(ValidationException.class, () -> veiculoService.create(veiculoDTO));

        verify(veiculoValidator, times(1)).validarNovoVeiculo(any(VeiculoDTO.class));
        verify(veiculoRepository, never()).saveAndFlush(any(Veiculo.class));
    }

    @Test
    @DisplayName("Deve atualizar um veículo existente com sucesso, buscando marca e modelo por ID")
    void testUpdate_shouldUpdateExistingVehicle_withExistingMarcaAndModelo() {
        // Arrange
        UUID existingMarcaId = UUID.randomUUID();
        UUID existingModeloId = UUID.randomUUID();
        Marca existingMarca = new Marca(existingMarcaId, "Ford");
        Modelo existingModelo = new Modelo(existingModeloId, "Ka");

        VeiculoDTO updateDTO = new VeiculoDTO(veiculoId, "Ford Ka", LocalDate.now(), new BigDecimal("12.0"), new BigDecimal("16.0"), new MarcaDTO(existingMarcaId, null), new ModeloDTO(existingModeloId, null));

        when(veiculoRepository.findById(veiculoId)).thenReturn(Optional.of(veiculo));
        when(marcaRepository.findById(existingMarcaId)).thenReturn(Optional.of(existingMarca));
        when(modeloRepository.findById(existingModeloId)).thenReturn(Optional.of(existingModelo));
        when(veiculoRepository.saveAndFlush(any(Veiculo.class))).thenReturn(veiculo);

        // Act
        VeiculoDTO result = veiculoService.update(updateDTO, veiculoId);

        // Assert
        assertNotNull(result);
        assertEquals("Ford Ka", result.getNome());
        assertEquals("Ford", result.getMarca().getNome());
        assertEquals("Ka", result.getModelo().getNome());

        verify(veiculoRepository, times(1)).findById(veiculoId);
        verify(veiculoRepository, times(1)).saveAndFlush(veiculo);
    }

    @Test
    @DisplayName("Deve atualizar um veículo e criar uma nova marca e modelo se os IDs forem nulos")
    void testUpdate_shouldUpdateAndCreateNewMarcaAndModelo() {
        // Arrange
        VeiculoDTO updateDTO = new VeiculoDTO(veiculoId, "Uno", LocalDate.now(), new BigDecimal("9.0"), new BigDecimal("14.0"), new MarcaDTO(null, "Fiat Novo"), new ModeloDTO(null, "Uno Novo"));

        when(veiculoRepository.findById(veiculoId)).thenReturn(Optional.of(veiculo));
        when(veiculoRepository.saveAndFlush(any(Veiculo.class))).thenReturn(veiculo);

        // Act
        VeiculoDTO result = veiculoService.update(updateDTO, veiculoId);

        // Assert
        assertNotNull(result);
        assertEquals("Uno", result.getNome());
        assertEquals("Fiat Novo", result.getMarca().getNome());
        assertEquals("Uno Novo", result.getModelo().getNome());

        verify(veiculoRepository, times(1)).findById(veiculoId);
        verify(veiculoRepository, times(1)).saveAndFlush(veiculo);
        verify(marcaRepository, never()).findById(any(UUID.class));
        verify(modeloRepository, never()).findById(any(UUID.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o veículo a ser atualizado não for encontrado")
    void testUpdate_shouldThrowException_whenVehicleNotFound() {
        when(veiculoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> veiculoService.update(veiculoDTO, UUID.randomUUID()));

        verify(veiculoRepository, times(1)).findById(any(UUID.class));
        verify(veiculoRepository, never()).saveAndFlush(any(Veiculo.class));
    }

    @Test
    @DisplayName("Deve remover um veículo com sucesso")
    void testRemove_shouldRemoveVehicleSuccessfully() {
        doNothing().when(veiculoRepository).deleteById(veiculoId);

        veiculoService.remove(veiculoId);

        verify(veiculoRepository, times(1)).deleteById(veiculoId);
    }
}