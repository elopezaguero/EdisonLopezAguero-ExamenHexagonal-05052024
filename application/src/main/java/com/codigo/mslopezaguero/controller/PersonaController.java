package com.codigo.mslopezaguero.controller;

import com.codigo.mslopezaguero.domain.aggregates.dto.PersonaDto;
import com.codigo.mslopezaguero.domain.aggregates.request.PersonaRequest;
import com.codigo.mslopezaguero.domain.ports.in.PersonaServiceIn;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ms-lopez-aguero/v1/persona")
@AllArgsConstructor
public class PersonaController {

    private final PersonaServiceIn personaService;

    @PostMapping("/save")
    public ResponseEntity<PersonaDto> crearPersona(@RequestBody PersonaRequest personaRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaService.crearPersonaIn(personaRequest));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PersonaDto> bsucarXId(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaService.buscarxIdIn(id).get());
    }

    @GetMapping("/todos")
    public ResponseEntity<List<PersonaDto>> buscartodos(){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaService.buscarTodosIn());
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<PersonaDto> actualizar(@PathVariable Long id, @RequestBody PersonaRequest personaRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaService.actualizarIn(id,personaRequest));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<PersonaDto> eliminar(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaService.eliminarIn(id));
    }
}
