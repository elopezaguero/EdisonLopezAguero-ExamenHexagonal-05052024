package com.codigo.mslopezaguero.infraestructure.mapper;

import com.codigo.mslopezaguero.domain.aggregates.dto.EmpresaDto;
import com.codigo.mslopezaguero.infraestructure.entity.EmpresaEntity;

public class EmpresaMapper {

    public static EmpresaDto fromEntity(EmpresaEntity entity){
        EmpresaDto dto = new EmpresaDto();
        dto.setId(entity.getId());
        dto.setRazonSocial(entity.getRazonSocial());
        dto.setTipoDoc(entity.getTipoDoc());
        dto.setNumDoc(entity.getNumDoc());
        dto.setEstado(entity.getEstado());
        dto.setCondicion(entity.getCondicion());
        dto.setDireccion(entity.getDireccion());
        dto.setDistrito(entity.getDistrito());
        dto.setProvincia(entity.getProvincia());
        dto.setDepartamento(entity.getDepartamento());
        dto.setEsAgenteRetencion(entity.isEsAgenteRetencion());
        dto.setUsuaCrea(entity.getUsuaCrea());
        dto.setDateCreate(entity.getDateCreate());
        dto.setUsuaModif(entity.getUsuaModif());
        dto.setDateModif(entity.getDateModif());
        dto.setUsuaDelet(entity.getUsuaDelet());
        dto.setDateDelet(entity.getDateDelet());
        return dto;
    }
}
