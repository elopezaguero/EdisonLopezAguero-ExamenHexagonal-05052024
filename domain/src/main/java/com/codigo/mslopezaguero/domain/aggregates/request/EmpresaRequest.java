package com.codigo.mslopezaguero.domain.aggregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaRequest {
    private String tipoDoc;
    private String numDoc;
}
