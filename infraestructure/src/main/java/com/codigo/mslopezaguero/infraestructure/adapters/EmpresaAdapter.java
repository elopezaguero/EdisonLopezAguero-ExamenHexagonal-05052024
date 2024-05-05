package com.codigo.mslopezaguero.infraestructure.adapters;

import com.codigo.mslopezaguero.domain.aggregates.constants.Constants;
import com.codigo.mslopezaguero.domain.aggregates.dto.EmpresaDto;
import com.codigo.mslopezaguero.domain.aggregates.dto.PersonaDto;
import com.codigo.mslopezaguero.domain.aggregates.dto.SunatDto;
import com.codigo.mslopezaguero.domain.aggregates.request.EmpresaRequest;
import com.codigo.mslopezaguero.domain.ports.out.EmpresaServiceOut;
import com.codigo.mslopezaguero.infraestructure.client.ClientSunat;
import com.codigo.mslopezaguero.infraestructure.dao.EmpresaRepository;
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
public class EmpresaAdapter implements EmpresaServiceOut {

    private final EmpresaRepository empresaRepository;
    private final ClientSunat clientSunat;

    @Value("${token.sunac}")
    private String tokenSunat;
    @Autowired
    private RedisService redisService;

    @Transactional
    @Override
    public EmpresaDto crearEmpresaOut(EmpresaRequest empresaRequest) {
        EmpresaEntity nuevaEmpresa = new EmpresaEntity();
        EmpresaEntity empresaEntity = getEntity(empresaRequest, false, null, nuevaEmpresa);
        return EmpresaMapper.fromEntity(empresaRepository.save(empresaEntity));
    }

    private EmpresaEntity getEntity(EmpresaRequest empresaRequest, boolean actualiza, Long id, EmpresaEntity entity) {
        SunatDto sunatDto = getExecSunat(empresaRequest.getNumDoc());
        entity.setRazonSocial(sunatDto.getRazonSocial());
        entity.setRazonSocial(sunatDto.getRazonSocial());
        entity.setTipoDoc(sunatDto.getTipoDocumento());
        entity.setNumDoc(sunatDto.getNumeroDocumento());
        entity.setEstado(Constants.STATUS_ACTIVE);
        entity.setCondicion(sunatDto.getCondicion());
        entity.setDireccion(sunatDto.getDireccion());
        entity.setDistrito(sunatDto.getDistrito());
        entity.setProvincia(sunatDto.getProvincia());
        entity.setDepartamento(sunatDto.getDepartamento());
        entity.setEsAgenteRetencion(sunatDto.isEsAgenteRetencion());

        if(actualiza){
            entity.setId(id);
            entity.setUsuaModif(Constants.USU_ADMIN);
            entity.setDateModif(getTimestamp());
        }else {
            entity.setUsuaCrea(Constants.USU_ADMIN);
            entity.setDateCreate(getTimestamp());
        }
        return entity;
    }

    private SunatDto getExecSunat(String numDoc){
        String authorization = "Bearer " + tokenSunat;
        return clientSunat.getInfoSunat(numDoc, authorization);
    }

    private Timestamp getTimestamp(){
        long currenTIme = System.currentTimeMillis();
        return new Timestamp(currenTIme);
    }

    @Override
    public Optional<EmpresaDto> buscarxIdOut(Long id) {
        String redisInfo = redisService.getFromRedis(Constants.REDIS_KEY_OBTENEREMPRESA+id);
        if(redisInfo != null){
            EmpresaDto empresaDto = Util.convertirDesdeString(redisInfo,EmpresaDto.class);
            return Optional.of(empresaDto);
        }else {
            EmpresaDto empresaDto = EmpresaMapper.fromEntity(empresaRepository.findById(id).get());
            String dataForRedis = Util.convertirAString(empresaDto);
            redisService.saveInRedis(Constants.REDIS_KEY_OBTENEREMPRESA+id,dataForRedis,10);
            return Optional.of(empresaDto);
        }
    }

    @Override
    public List<EmpresaDto> buscarTodosOut() {
        List<EmpresaDto> listaDto = new ArrayList<>();
        List<EmpresaEntity> entidades = empresaRepository.findAll();
            for(EmpresaEntity dato : entidades){
                listaDto.add(EmpresaMapper.fromEntity(dato));
            }
        return listaDto;
    }

    @Transactional
    @Override
    public EmpresaDto actualizarOut(Long id, EmpresaRequest empresaRequest) {
        Optional<EmpresaEntity> datoExtraido = empresaRepository.findById(id);
        if(datoExtraido.isPresent()){
            EmpresaEntity empresaEntity = getEntity(empresaRequest, true, id, datoExtraido.get());
            return EmpresaMapper.fromEntity(empresaRepository.save(empresaEntity));
        }else{
            throw new RuntimeException("No se puede actualizar esta empresa");
        }
    }

    @Override
    public EmpresaDto eliminarOut(Long id) {
        Optional<EmpresaEntity> datoExtraido = empresaRepository.findById(id);
        if(datoExtraido.isPresent()){
               datoExtraido.get().setEstado(0);
               datoExtraido.get().setUsuaDelet(Constants.USU_ADMIN);
               datoExtraido.get().setDateDelet(getTimestamp());
               return EmpresaMapper.fromEntity(empresaRepository.save(datoExtraido.get()));
        }else {
            throw new RuntimeException("No se puede eliminar esta empresa");
        }
    }
}
