package com.codigo.mslopezaguero.domain.ports.out;

import com.codigo.mslopezaguero.domain.aggregates.dto.PersonaDto;
import com.codigo.mslopezaguero.domain.aggregates.request.PersonaRequest;

import java.util.List;
import java.util.Optional;

public interface PersonaServiceOut {
    PersonaDto crearPersonaOut(PersonaRequest personaRequest);
    Optional<PersonaDto> buscarxIdOut(Long id);
    List<PersonaDto> buscarTodosOut();
    PersonaDto actualizarOut(Long id, PersonaRequest personaRequest);
    PersonaDto eliminarOut(Long id);
}
