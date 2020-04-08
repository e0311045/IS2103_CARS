/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package ejb.session.stateless;

import entity.ConsultationEntity;
import java.util.List;

public interface ConsultationEntityControllerLocal {

    Long createConsultation(ConsultationEntity newConsultationEntity, String identityNumber, Long doctorId); //throws PatientAddConsultationException;

    List<ConsultationEntity> retrieveAllConsultations();
// void createConsultationInDoctor(ConsultationEntity newConsultationEntity, Long doctorId); // throws DoctorAddConsultationException;
}
