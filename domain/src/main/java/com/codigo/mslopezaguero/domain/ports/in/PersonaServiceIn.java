package com.codigo.mslopezaguero.domain.ports.in;

import com.codigo.mslopezaguero.domain.aggregates.dto.PersonaDto;
import com.codigo.mslopezaguero.domain.aggregates.request.PersonaRequest;

import java.util.List;
import java.util.Optional;

public interface PersonaServiceIn {
    PersonaDto crearPersonaIn(PersonaRequest personaRequest);
    Optional<PersonaDto> buscarxIdIn(Long id);
    List<PersonaDto> buscarTodosIn();
    PersonaDto actualizarIn(Long id, PersonaRequest personaRequest);
    PersonaDto eliminarIn(Long id);
}
