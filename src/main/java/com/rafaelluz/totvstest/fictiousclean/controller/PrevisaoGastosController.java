package com.rafaelluz.totvstest.fictiousclean.controller;

import com.rafaelluz.totvstest.fictiousclean.dto.PrevisaoGastoInputDTO;
import com.rafaelluz.totvstest.fictiousclean.dto.PrevisaoGastoOutputDTO;
import com.rafaelluz.totvstest.fictiousclean.service.PrevisaoGastosService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/previsao-gastos")
public class PrevisaoGastosController {

    @Autowired
    PrevisaoGastosService previsaoGastosService;

    @PostMapping
    public List<PrevisaoGastoOutputDTO> listarRanking(@RequestBody PrevisaoGastoInputDTO previsaoGastoDTO) {
        return previsaoGastosService.listarRanking(previsaoGastoDTO);
    }
}
