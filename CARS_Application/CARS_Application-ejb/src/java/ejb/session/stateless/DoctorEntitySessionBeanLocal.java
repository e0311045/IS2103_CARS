/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DoctorEntity;
import java.util.List;
import util.exception.DoctorNotFoundException;


public interface DoctorEntitySessionBeanLocal {

    public void deleteDoctor(Long doctorId) throws DoctorNotFoundException;

    public DoctorEntity retrieveDoctorById(Long doctorId) throws DoctorNotFoundException;

    public List<DoctorEntity> retrieveAllDoctors();

    public Long createDoctor(DoctorEntity doctor);

    public void updateDoctor(DoctorEntity doctorEntity);
    
}
