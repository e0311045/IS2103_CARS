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
import util.exception.DoctorAlreadyExistException;
import util.exception.DoctorNotFoundException;
import util.exception.UnknownPersistenceException;

public interface DoctorEntityControllerLocal {

    public Long createNewDoctor(DoctorEntity newDoctorEntity) throws DoctorAlreadyExistException, UnknownPersistenceException;

    DoctorEntity retrieveDoctorById(Long id) throws DoctorNotFoundException;

    List<DoctorEntity> retrieveAllDoctors();

    void updateDoctor(DoctorEntity doctorEntity);

    void deleteDoctor(Long doctorId) throws DoctorNotFoundException;

}
