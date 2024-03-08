package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoEspecialidadService {

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    EspecialidadRepository especialidadRepository;

    @Transactional
    public List<MedicoEntity> addEspecialidad(long medicoId,
            long especialidadId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de asociar una nueva especialidad al medico = {0}", medicoId);

        Optional<MedicoEntity> medico = medicoRepository.findById(medicoId);

        if (medico.isEmpty())
            throw new EntityNotFoundException("El medico no existe");

        Optional<EspecialidadEntity> especilidad = especialidadRepository.findById(especialidadId);

        if (especilidad.isEmpty())
            throw new EntityNotFoundException("la especialidad no existe");


        medico.get().getEspecialidades().add(especilidad.get());
        especilidad.get().getMedicos().add(medico.get());

        log.info("Termina proceso de asociar una nueva especialidad al medico = {0}", medicoId);
        return especilidad.get().getMedicos();
    }
    
}
