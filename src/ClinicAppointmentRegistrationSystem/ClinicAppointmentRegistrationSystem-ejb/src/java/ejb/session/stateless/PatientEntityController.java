/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package ejb.session.stateless;

import entity.PatientEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidLoginException;
import util.exception.PatientNotFoundException;

@Stateless
@Local(PatientEntityControllerLocal.class)
@Remote(PatientEntityControllerRemote.class)

public class PatientEntityController implements PatientEntityControllerLocal, PatientEntityControllerRemote {

    @PersistenceContext(unitName = "ClinicAppointmentRegistrationSystem-ejbPU")
    private EntityManager entityManager;

    public PatientEntityController() {
    }

    @Override
    public PatientEntity createNewPatient(PatientEntity newPatientEntity) {
        entityManager.persist(newPatientEntity);
        entityManager.flush();

        return newPatientEntity;
    }

    @Override
    public PatientEntity patientLogin(String identityNumber, String securityCode) throws InvalidLoginException {
        try {
            PatientEntity patientEntity = retrievePatientByIdentityNumber(identityNumber);

            if (patientEntity.getSecurityCode().equals(securityCode)) {
                return patientEntity;
            } else {
                throw new InvalidLoginException("Identity number does not exist or invalid security code!");
            }
        } catch (PatientNotFoundException ex) {
            throw new InvalidLoginException("Identity number does not exist or invalid security code!");
        }
    }

    @Override
    public List<PatientEntity> retrieveAllPatients() {
        Query query = entityManager.createQuery("SELECT p FROM PatientEntity p");

        return query.getResultList();
    }

    @Override
    public PatientEntity retrievePatientByIdentityNumber(String identityNumber) throws PatientNotFoundException {
        Query query = entityManager.createQuery("SELECT p FROM PatientEntity p WHERE p.identityNumber = :inIdentitynumber");
        query.setParameter("inIdentitynumber", identityNumber);

        try {
            return (PatientEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new PatientNotFoundException("Patient Identity Number " + identityNumber + " does not exist!");
        }
    }

    @Override
    public void updatePatient(PatientEntity patientEntity) {
        entityManager.merge(patientEntity);
    }

    @Override
    public void deletePatient(String identityNumber) throws PatientNotFoundException {
        PatientEntity patientEntityToRemove = retrievePatientByIdentityNumber(identityNumber);
        entityManager.remove(patientEntityToRemove);
    }
}
