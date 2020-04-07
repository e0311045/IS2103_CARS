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
import util.exception.InvalidLoginException;
import util.exception.PatientAlreadyExistException;
import util.exception.PatientNotFoundException;
import util.exception.UnknownPersistenceException;

public interface PatientEntityControllerRemote {

    public Long createNewPatient(PatientEntity newPatientEntity) throws PatientAlreadyExistException, UnknownPersistenceException;

    PatientEntity patientLogin(String identityNumber, String securityCode) throws InvalidLoginException;

    List<PatientEntity> retrieveAllPatients();

    PatientEntity retrievePatientByIdentityNumber(String identityNumber) throws PatientNotFoundException;

    void updatePatient(PatientEntity patientEntity);

    void deletePatient(String identityNumber) throws PatientNotFoundException;

}
