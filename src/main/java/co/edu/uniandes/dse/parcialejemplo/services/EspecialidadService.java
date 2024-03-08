package co.edu.uniandes.dse.parcialejemplo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EspecialidadService {

    @Autowired
    EspecialidadRepository especialidadRepository;

    @Transactional
    public EspecialidadEntity createEspecialidad(EspecialidadEntity especialidadEntity) throws IllegalOperationException {
        log.info("Inicia proceso de creación de una especialidad");

        if (especialidadEntity.getDescripcion().length() < 10)
            throw new IllegalOperationException("Descripcion is not valid");

        if (especialidadEntity.getNombre() == null)
            throw new IllegalOperationException("Nombre is not valid");

        log.info("Termina proceso de creación de una especialidad");
        return especialidadRepository.save(especialidadEntity);
    }
    
}
