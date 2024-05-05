package com.codigo.mslopezaguero.controller;

import com.codigo.mslopezaguero.domain.aggregates.dto.EmpresaDto;
import com.codigo.mslopezaguero.domain.aggregates.dto.PersonaDto;
import com.codigo.mslopezaguero.domain.aggregates.request.EmpresaRequest;
import com.codigo.mslopezaguero.domain.ports.in.EmpresaServiceIn;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ms-lopez-aguero/v1/empresa")
@AllArgsConstructor
public class EmpresaController {

    EmpresaServiceIn empresaServiceIn;

    @PostMapping("/save")
    public ResponseEntity<EmpresaDto> crearEmpresa(@RequestBody EmpresaRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empresaServiceIn.crearEmpresaIn(request));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Optional<EmpresaDto>> bsucarXId(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empresaServiceIn.buscarxIdIn(id));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<EmpresaDto>> buscartodos(){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empresaServiceIn.buscarTodosIn());
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<EmpresaDto> actualizar(@PathVariable Long id, @RequestBody EmpresaRequest empresaRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empresaServiceIn.actualizarIn(id, empresaRequest));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<EmpresaDto> eliminar(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empresaServiceIn.eliminarIn(id));
    }

}
