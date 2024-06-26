package com.codigo.mslopezaguero.domain.aggregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaRequest {
    private String tipoDoc;
    private String numDoc;
    private String empresa;
    private String email;
    private String telefono;
    private String direccion;
}
