package com.codigo.mslopezaguero.domain.aggregates.dto;

import com.codigo.mslopezaguero.domain.aggregates.request.EmpresaRequest;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PersonaDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String tipoDoc;
    private String numDoc;
    private String email;
    private String telefono;
    private String direccion;
    private Integer estado;
    private String usuaCrea;
    private Timestamp dateCreate;
    private String usuaModif;
    private Timestamp dateModif;
    private String usuaDelet;
    private Timestamp dateDelet;
    private Long empresa_id;
    private EmpresaDto empresa;
}
