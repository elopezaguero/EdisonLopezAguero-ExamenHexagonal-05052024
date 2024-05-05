package com.codigo.mslopezaguero.infraestructure.dao;

import com.codigo.mslopezaguero.infraestructure.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Long> {
    Optional<EmpresaEntity> findByNumDoc(String empresa);


}
