package com.codigo.mslopezaguero.infraestructure.mapper;

import com.codigo.mslopezaguero.domain.aggregates.dto.PersonaDto;
import com.codigo.mslopezaguero.infraestructure.entity.PersonaEntity;

public class PersonaMapper {
    public static PersonaDto fromEntity(PersonaEntity entity){
        PersonaDto dto = new PersonaDto();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setTipoDoc(entity.getTipoDoc());
        dto.setNumDoc(entity.getNumDoc());
        dto.setEmail(entity.getEmail());
        dto.setTelefono(entity.getTelefono());
        dto.setDireccion(entity.getDireccion());
        dto.setEstado(entity.getEstado());
        dto.setUsuaCrea(entity.getUsuaCrea());
        dto.setDateCreate(entity.getDateCreate());
        dto.setUsuaModif(entity.getUsuaModif());
        dto.setDateModif(entity.getDateModif());
        dto.setUsuaDelet(entity.getUsuaDelet());
        dto.setDateDelet(entity.getDateDelet());
        dto.setEmpresa_id(entity.getEmpresa().getId());
        return dto;
    }
}
