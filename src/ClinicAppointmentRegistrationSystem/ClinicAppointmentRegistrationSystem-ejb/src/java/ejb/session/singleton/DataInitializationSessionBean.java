/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package ejb.session.singleton;

import ejb.session.stateless.AppointmentEntityControllerLocal;
import ejb.session.stateless.ConsultationEntityControllerLocal;
import ejb.session.stateless.DoctorEntityControllerLocal;
import ejb.session.stateless.PatientEntityControllerLocal;
import ejb.session.stateless.StaffEntityControllerLocal;
import entity.DoctorEntity;
import entity.PatientEntity;
import entity.StaffEntity;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.StaffNotFoundException;

@Singleton
@LocalBean
@Startup

public class DataInitializationSessionBean {

    @PersistenceContext(unitName = "ClinicAppointmentRegistrationSystem-ejbPU")
    private EntityManager entityManager;

    @EJB
    private StaffEntityControllerLocal staffEntityControllerLocal;

    @EJB
    private PatientEntityControllerLocal patientEntityControllerLocal;

    @EJB
    private DoctorEntityControllerLocal doctorEntityControllerLocal;

    @EJB
    private ConsultationEntityControllerLocal consultationEntityControllerLocal;

    @EJB
    private AppointmentEntityControllerLocal appointmentEntityControllerLocal;

    public DataInitializationSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            staffEntityControllerLocal.retrieveStaffByUsername("manager");
        } catch (StaffNotFoundException ex) {
            initializeData();
        }
    }

    private void initializeData() {
        staffEntityControllerLocal.createNewStaff(new StaffEntity(1l, "Linda", "Chua", "manager", "password"));
        staffEntityControllerLocal.createNewStaff(new StaffEntity(2l, "Barbara", "Durham", "nurse", "password"));

        patientEntityControllerLocal.createNewPatient(new PatientEntity(1l, "Tony", "Teo", "Male", "123", 44, "S7483027A", "87297373", "11 Tampines Ave 3"));
        patientEntityControllerLocal.createNewPatient(new PatientEntity(2l, "Wendy", "Tan", "Female", "321", 35, "S8381028X", "97502837", "15 Computing Dr"));

        doctorEntityControllerLocal.createNewDoctor(new DoctorEntity(1l, "Peter", "Lee", "S18018", "MBBS"));
        doctorEntityControllerLocal.createNewDoctor(new DoctorEntity(2l, "Cindy", "Leong", "S64921", "BMedSc"));
        doctorEntityControllerLocal.createNewDoctor(new DoctorEntity(3l, "Matthew", "Liu", "S38101", "MBBS"));

        /* // test adding consultation
        Calendar cal = Calendar.getInstance();
        long ONE_MINUTE_IN_MILLIS = 60000;
        cal.set(Calendar.HOUR_OF_DAY, 14);
        cal.set(Calendar.MINUTE, 00);
        long t = cal.getTimeInMillis();
        Date time = new Date(t);
        consultationEntityControllerLocal.createConsultation(new ConsultationEntity(1l, time), "S7483027A",1l);
        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 30);
        t = cal.getTimeInMillis();
        time = new Date(t);
        consultationEntityControllerLocal.createConsultation(new ConsultationEntity(2l, time), "S8381028X",3l); */
 /*// test adding appointment
        Calendar cal = Calendar.getInstance();
        long ONE_MINUTE_IN_MILLIS = 60000;
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 00);
        long t = cal.getTimeInMillis();
        Date time = new Date(t);
        Date date = new Date(t);
        appointmentEntityControllerLocal.createAppointment(new AppointmentEntity(1l, date, time), "S7483027A", 1l); 
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 30);
        t = cal.getTimeInMillis();
        time = new Date(t);
        date = new Date(t);
        appointmentEntityControllerLocal.createAppointment(new AppointmentEntity(2l, date, time), "S7483027A", 2l);
        cal.set(Calendar.HOUR_OF_DAY, 15);
        cal.set(Calendar.MINUTE, 30);
        t = cal.getTimeInMillis();
        time = new Date(t);
        date = new Date(t);
        appointmentEntityControllerLocal.createAppointment(new AppointmentEntity(3l, date, time), "S8381028X",3l); */
    }
}
