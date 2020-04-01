/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PatientEntity;
import java.util.List;
import util.exception.PatientNotFoundException;


public interface PatientEntitySessionBeanRemote {
    public void deletePatient(Long patientId) throws PatientNotFoundException;

    public void updatePatient(PatientEntity patientEntity);

    public List<PatientEntity> retrieveAllPatient();

    public Long createPatient(PatientEntity patientEntity);

    public PatientEntity retrievePatientById(Long patientId) throws PatientNotFoundException;
    
    public PatientEntity retrievePatientByIdentity(String identityNo) throws PatientNotFoundException;
}
