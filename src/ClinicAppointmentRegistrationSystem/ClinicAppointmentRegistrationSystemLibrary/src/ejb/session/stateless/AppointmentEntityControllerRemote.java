/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package ejb.session.stateless;

import entity.AppointmentEntity;
import java.util.Date;
import java.util.List;
import util.exception.CreateAppointmentException;
import util.exception.AppointmentNotFoundException;
import util.exception.UnknownPersistenceException;

public interface AppointmentEntityControllerRemote {

    public Long createAppointment(AppointmentEntity newAppointmentEntity);
    
    public Long createAppointment(AppointmentEntity newAppointmentEntity, String identityNumber, Long doctorId) throws CreateAppointmentException, UnknownPersistenceException;

    List<AppointmentEntity> retrieveAllAppointments();

    void cancelAppointment(Long appointmentId) throws AppointmentNotFoundException;

    AppointmentEntity retrieveAppointmentByAppointmentId(Long appointmentId) throws AppointmentNotFoundException;

    public AppointmentEntity retrieveAppointmentByDoctorDate(Long doctorId, Date date);
}
