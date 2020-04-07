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
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.InvalidLoginException;
import util.exception.PatientAlreadyExistException;
import util.exception.PatientNotFoundException;
import util.exception.UnknownPersistenceException;

@Stateless
@Local(PatientEntityControllerLocal.class)
@Remote(PatientEntityControllerRemote.class)

public class PatientEntityController implements PatientEntityControllerLocal, PatientEntityControllerRemote {

    @PersistenceContext(unitName = "ClinicAppointmentRegistrationSystem-ejbPU")
    private EntityManager em;

    public PatientEntityController() {
    }

    @Override
    public Long createNewPatient(PatientEntity newPatientEntity) throws PatientAlreadyExistException, UnknownPersistenceException {
        try{
            em.persist(newPatientEntity);
            em.flush();

            return newPatientEntity.getPatientId();
        } catch (PersistenceException ex) {
            if(ex.getCause()!= null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")){
                    throw new PatientAlreadyExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {                
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public PatientEntity patientLogin(String identityNumber, String password) throws InvalidLoginException {
        try {
            PatientEntity patientEntity = retrievePatientByIdentityNumber(identityNumber);

            if (patientEntity.getPassword().equals(password)) {
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
        Query query = em.createQuery("SELECT p FROM PatientEntity p");

        return query.getResultList();
    }

    @Override
    public PatientEntity retrievePatientByIdentityNumber(String identityNumber) throws PatientNotFoundException {
        Query query = em.createQuery("SELECT p FROM PatientEntity p WHERE p.identityNumber = :inIdentitynumber");
        query.setParameter("inIdentitynumber", identityNumber);

        try {
            return (PatientEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new PatientNotFoundException("Patient Identity Number " + identityNumber + " does not exist!");
        }
    }

    @Override
    public void updatePatient(PatientEntity patientEntity) {
        em.merge(patientEntity);
    }

    @Override
    public void deletePatient(String identityNumber) throws PatientNotFoundException {
        PatientEntity patientEntityToRemove = retrievePatientByIdentityNumber(identityNumber);
        em.remove(patientEntityToRemove);
    }
    

}
