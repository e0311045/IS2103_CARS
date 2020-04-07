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
import util.exception.DoctorAlreadyExistException;
import util.exception.PatientAlreadyExistException;
import util.exception.StaffAlreadyExistException;
import util.exception.StaffNotFoundException;
import util.exception.UnknownPersistenceException;

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

    private void initializeData()  {
        try{
            staffEntityControllerLocal.createNewStaff(new StaffEntity("Eric", "Some", "manager", "password"));
            staffEntityControllerLocal.createNewStaff(new StaffEntity("Victoria", "Newton", "nurse", "password"));

            patientEntityControllerLocal.createNewPatient(new PatientEntity("S9867027A", "001001","Sarah", "Yi",'F', 22, "93718799", "13, Clementi Road"));
            patientEntityControllerLocal.createNewPatient(new PatientEntity("G1314207T", "001101","Rajesh","Singh",'F',35, "93506839", "15, Mountbatten Road"));

            doctorEntityControllerLocal.createNewDoctor(new DoctorEntity("Tan", "Ming", "S10011", "BMBS"));
            doctorEntityControllerLocal.createNewDoctor(new DoctorEntity("Clair", "Hahn", "S41221", "MBBCh"));
            doctorEntityControllerLocal.createNewDoctor(new DoctorEntity("Robert", "Blake", "S58201", "MBBS"));
            doctorEntityControllerLocal.createNewDoctor(new DoctorEntity("Tan", "Min", "S31242", "MBSBS"));
        } catch (StaffAlreadyExistException | UnknownPersistenceException | PatientAlreadyExistException | DoctorAlreadyExistException ex){
            System.out.println(ex.getMessage());
        }

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
