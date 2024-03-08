package co.edu.uniandes.dse.parcialejemplo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoService {

    @Autowired
    MedicoRepository medicoRepository;

    @Transactional
    public MedicoEntity createMedicos(MedicoEntity medicoEntity) throws IllegalOperationException {
        log.info("Inicia proceso de creación de un medico");

        if (medicoEntity.getNombre() == null)
            throw new IllegalOperationException("Nombre is not valid");

        if (medicoEntity.getApellido() == null)
            throw new IllegalOperationException("Apellido is not valid");

        if (medicoEntity.getRegistro_medico() == null)
            throw new IllegalOperationException("Registro medico is not valid");

        if (medicoEntity.getRegistro_medico().charAt(0) != 'R')
            throw new IllegalOperationException("Registro medico is not valid");
        
        if (medicoEntity.getRegistro_medico().charAt(1) != 'M')
            throw new IllegalOperationException("Registro medico is not valid");

        log.info("Termina proceso de creación de una medico");
        return medicoRepository.save(medicoEntity);
    }
    
}
