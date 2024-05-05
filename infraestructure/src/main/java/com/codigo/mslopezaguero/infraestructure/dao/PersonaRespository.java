package com.codigo.mslopezaguero.infraestructure.dao;

import com.codigo.mslopezaguero.infraestructure.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRespository extends JpaRepository<PersonaEntity, Long> {

}
