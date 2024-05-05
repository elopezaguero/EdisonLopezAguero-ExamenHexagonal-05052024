package com.codigo.mslopezaguero.infraestructure.adapters;

import com.codigo.mslopezaguero.domain.aggregates.constants.Constants;
import com.codigo.mslopezaguero.domain.aggregates.dto.EmpresaDto;
import com.codigo.mslopezaguero.domain.aggregates.dto.PersonaDto;
import com.codigo.mslopezaguero.domain.aggregates.dto.ReniecDto;
import com.codigo.mslopezaguero.domain.aggregates.request.PersonaRequest;
import com.codigo.mslopezaguero.domain.ports.out.PersonaServiceOut;
import com.codigo.mslopezaguero.infraestructure.client.ClientReniec;
import com.codigo.mslopezaguero.infraestructure.dao.EmpresaRepository;
import com.codigo.mslopezaguero.infraestructure.dao.PersonaRespository;
import com.codigo.mslopezaguero.infraestructure.entity.EmpresaEntity;
import com.codigo.mslopezaguero.infraestructure.entity.PersonaEntity;
import com.codigo.mslopezaguero.infraestructure.mapper.EmpresaMapper;
import com.codigo.mslopezaguero.infraestructure.mapper.PersonaMapper;
import com.codigo.mslopezaguero.infraestructure.redis.RedisService;
import com.codigo.mslopezaguero.infraestructure.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaAdapter implements PersonaServiceOut {

    @Autowired
    PersonaRespository personaRespository;
    @Autowired
    EmpresaRepository empresaRepository;
    private final ClientReniec clientReniec;

    @Value("${token.reniec}")
    private String tokenReniec;
    @Autowired
    private RedisService redisService;

    @Transactional
    @Override
    public PersonaDto crearPersonaOut(PersonaRequest personaRequest) {
        PersonaEntity nuevaPersona = new PersonaEntity();
        PersonaEntity personaEntity = getEntity(personaRequest,false, null, nuevaPersona);
        return PersonaMapper.fromEntity(personaRespository.save(personaEntity));
    }

    private PersonaEntity getEntity(PersonaRequest personaRequest, boolean actualiza, Long id, PersonaEntity entity){
        ReniecDto reniecDto = getExecReniec(personaRequest.getNumDoc());
        entity.setNombre(reniecDto.getNombres());
        entity.setApellido(reniecDto.getApellidoMaterno());
        entity.setTipoDoc(reniecDto.getTipoDocumento());
        entity.setNumDoc(reniecDto.getNumeroDocumento());
        entity.setEmail(personaRequest.getEmail());
        entity.setTelefono(personaRequest.getTelefono());
        entity.setDireccion(personaRequest.getDireccion());
        entity.setEstado(Constants.STATUS_ACTIVE);
        if(actualiza){
            entity.setId(id);
            entity.setUsuaModif(Constants.USU_ADMIN);
            entity.setDateModif(getTimestamp());
        }else{
            entity.setUsuaCrea(Constants.USU_ADMIN);
            entity.setDateCreate(getTimestamp());
        }

        Optional<EmpresaEntity> optionalEmpresaEntity = empresaRepository.findByNumDoc(personaRequest.getEmpresa());
        if(optionalEmpresaEntity.isPresent()){
            EmpresaEntity empresaEntity = optionalEmpresaEntity.get();
            entity.setEmpresa(empresaEntity);
        }
        return entity;
    }

    private ReniecDto getExecReniec(String numDoc){
        String authorization = "Bearer "+ tokenReniec;
        return clientReniec.getInfoReniec(numDoc,authorization);
    }

    private Timestamp getTimestamp(){
        long currenTIme = System.currentTimeMillis();
        return new Timestamp(currenTIme);
    }

    @Override
    public Optional<PersonaDto> buscarxIdOut(Long id) {
        String redisInfo = redisService.getFromRedis(Constants.REDIS_KEY_OBTENERPERSONA+id);
        if(redisInfo != null){
            PersonaDto personaDto = Util.convertirDesdeString(redisInfo,PersonaDto.class);
            return Optional.of(personaDto);
        }else {
            Optional<PersonaEntity> optionalPersonaEntity = personaRespository.findById(id);
            if(optionalPersonaEntity.isPresent()){
                PersonaDto personaDto = PersonaMapper.fromEntity(optionalPersonaEntity.get());
                EmpresaDto empresaDto = EmpresaMapper.fromEntity(empresaRepository.findById(personaDto.getEmpresa_id()).get());
                personaDto.setEmpresa(empresaDto);

                String dataForRedis = Util.convertirAString(personaDto);
                redisService.saveInRedis(Constants.REDIS_KEY_OBTENERPERSONA+id,dataForRedis,10);
                return Optional.of(personaDto);
            }else{
                throw new RuntimeException("Persona no encontrada");
            }
        }
    }

    @Override
    public List<PersonaDto> buscarTodosOut() {
        List<PersonaDto> listaDto = new ArrayList<>();
        List<PersonaEntity> entidades = personaRespository.findAll();
            for(PersonaEntity dato : entidades){
                listaDto.add(PersonaMapper.fromEntity(dato));
            }
        return listaDto;
    }

    @Transactional
    @Override
    public PersonaDto actualizarOut(Long id, PersonaRequest personaRequest) {
        Optional<PersonaEntity> datoExtraido = personaRespository.findById(id);
        if(datoExtraido.isPresent()){
            PersonaEntity personaEntity = getEntity(personaRequest, true, id, datoExtraido.get());
            return PersonaMapper.fromEntity(personaRespository.save(personaEntity));
        }
        return null;
    }

    @Override
    public PersonaDto eliminarOut(Long id) {
        Optional<PersonaEntity> datoExtraido = personaRespository.findById(id);
        if(datoExtraido.isPresent()){
            datoExtraido.get().setEstado(0);
            datoExtraido.get().setUsuaDelet(Constants.USU_ADMIN);
            datoExtraido.get().setDateDelet(getTimestamp());
            return PersonaMapper.fromEntity(personaRespository.save(datoExtraido.get()));
        }else {
            throw new RuntimeException();
        }
    }
}