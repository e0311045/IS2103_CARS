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
import util.exception.AppointmentNotFoundException;

public interface AppointmentEntityControllerRemote {

    Long createAppointment(AppointmentEntity newAppointmentEntity, String identityNumber, Long doctorId);

    List<AppointmentEntity> retrieveAllAppointments();

    void cancelAppointment(Long appointmentId) throws AppointmentNotFoundException;

    AppointmentEntity retrieveAppointmentByAppointmentId(Long appointmentId) throws AppointmentNotFoundException;
    
    public AppointmentEntity retrieveAppointmentByDoctorDate(Long doctorId, Date date);
}
