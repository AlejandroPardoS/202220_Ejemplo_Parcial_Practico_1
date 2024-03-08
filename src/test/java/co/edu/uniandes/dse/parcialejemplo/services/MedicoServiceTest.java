package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class) 
@DataJpaTest
@Transactional
@Import(MedicoService.class)

class MedicoServiceTest {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<MedicoEntity> medicoList = new ArrayList<> ();
    private List<EspecialidadEntity> especialidadList = new ArrayList<> ();
    
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from MedicoEntity");
    }


    private void insertData() {
        for (int i = 0; i < 3; i++) {
                MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
                entityManager.persist(medicoEntity);
                medicoList.add(medicoEntity);
            }

        for (int i = 0; i < 3; i++) {
                EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
                entityManager.persist(especialidadEntity);
                especialidadList.add(especialidadEntity);
            }
    }

    @Test
    void TestCreateMedico() throws IllegalOperationException {
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        newEntity.setEspecialidades(especialidadList);
        newEntity.setRegistro_medico("RM1745");
        MedicoEntity result = medicoService.createMedicos(newEntity);
        assertNotNull(result);
        MedicoEntity entity = entityManager.find(MedicoEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getApellido(), entity.getApellido());
        assertEquals(newEntity.getRegistro_medico(), entity.getRegistro_medico());
    }

    @Test
    void TestCreateMedicoWithInvalidReg() {
        assertThrows(IllegalOperationException.class, () -> {
                MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
                newEntity.setEspecialidades(especialidadList);
                newEntity.setRegistro_medico("R456843");
                medicoService.createMedicos(newEntity);
        });
    }
}
