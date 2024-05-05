package com.codigo.mslopezaguero.domain.impl;

import com.codigo.mslopezaguero.domain.aggregates.dto.EmpresaDto;
import com.codigo.mslopezaguero.domain.aggregates.request.EmpresaRequest;
import com.codigo.mslopezaguero.domain.ports.in.EmpresaServiceIn;
import com.codigo.mslopezaguero.domain.ports.out.EmpresaServiceOut;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmpresaServiceImpl implements EmpresaServiceIn {

    EmpresaServiceOut empresaServiceOut;

    @Override
    public EmpresaDto crearEmpresaIn(EmpresaRequest empresaRequest) {
        return empresaServiceOut.crearEmpresaOut(empresaRequest);
    }

    @Override
    public Optional<EmpresaDto> buscarxIdIn(Long id) {
        return empresaServiceOut.buscarxIdOut(id);
    }

    @Override
    public List<EmpresaDto> buscarTodosIn() {
        return empresaServiceOut.buscarTodosOut();
    }

    @Override
    public EmpresaDto actualizarIn(Long id, EmpresaRequest empresaRequest) {
        return empresaServiceOut.actualizarOut(id, empresaRequest);
    }

    @Override
    public EmpresaDto eliminarIn(Long id) {
        return empresaServiceOut.eliminarOut(id);
    }
}
