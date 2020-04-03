/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package ejb.session.stateless;

import entity.DoctorEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.DoctorNotFoundException;

@Stateless
@Local(DoctorEntityControllerLocal.class)
@Remote(DoctorEntityControllerRemote.class)

public class DoctorEntityController implements DoctorEntityControllerLocal, DoctorEntityControllerRemote {

    @PersistenceContext(unitName = "ClinicAppointmentRegistrationSystem-ejbPU")
    private EntityManager entityManager;

    public DoctorEntityController() {
    }

    @Override
    public DoctorEntity createNewDoctor(DoctorEntity newDoctorEntity) {
        entityManager.persist(newDoctorEntity);
        entityManager.flush();

        return newDoctorEntity;
    }

    @Override
    public List<DoctorEntity> retrieveAllDoctors() {
        Query query = entityManager.createQuery("SELECT d FROM DoctorEntity d");

        return query.getResultList();
    }

    @Override
    public DoctorEntity retrieveDoctorById(Long id) throws DoctorNotFoundException {
        DoctorEntity doctorEntity = entityManager.find(DoctorEntity.class, id);

        if (doctorEntity != null) {
            return doctorEntity;
        } else {
            throw new DoctorNotFoundException("Doctor ID " + id + " does not exist!");
        }
    }

    @Override
    public void updateDoctor(DoctorEntity doctorEntity) {
        entityManager.merge(doctorEntity);
    }

    @Override
    public void deleteDoctor(Long doctorId) throws DoctorNotFoundException {
        DoctorEntity doctorEntityToRemove = retrieveDoctorById(doctorId);
        entityManager.remove(doctorEntityToRemove);
    }
}
