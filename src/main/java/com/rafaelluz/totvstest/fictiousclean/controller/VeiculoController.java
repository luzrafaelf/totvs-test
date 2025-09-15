package com.rafaelluz.totvstest.fictiousclean.controller;

import com.rafaelluz.totvstest.fictiousclean.dto.VeiculoDTO;
import com.rafaelluz.totvstest.fictiousclean.service.VeiculoService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    VeiculoService veiculoService;

    @GetMapping
    public List<VeiculoDTO> listarTodos() {
        return veiculoService.findAll();
    }

    @PostMapping
    public VeiculoDTO criar(@RequestBody VeiculoDTO veiculo) {
        return veiculoService.create(veiculo);
    }

    @PutMapping("/{uuid}")
    public VeiculoDTO atualizar(@RequestBody VeiculoDTO veiculo, @PathVariable("uuid") String uuid ) {
        return veiculoService.update(veiculo, UUID.fromString(uuid));
    }

    @DeleteMapping("/{uuid}")
    public void excluir(@PathVariable("uuid") String uuid ) {
        veiculoService.remove(UUID.fromString(uuid));
    }
}