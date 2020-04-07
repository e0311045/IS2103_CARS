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
import ejb.session.stateless.LeaveEntityControllerLocal;
import ejb.session.stateless.PatientEntityControllerLocal;
import ejb.session.stateless.StaffEntityControllerLocal;
import entity.AppointmentEntity;
import  util.date.DateHelper;
import entity.ConsultationEntity;
import entity.DoctorEntity;
import entity.PatientEntity;
import entity.StaffEntity;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @EJB
    private LeaveEntityControllerLocal leaveEntityController;

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
        staffEntityControllerLocal.createNewStaff(new StaffEntity("Eric", "Some", "manager", "password"));
        staffEntityControllerLocal.createNewStaff(new StaffEntity("Victoria", "Newton", "nurse", "password"));

        patientEntityControllerLocal.createNewPatient(new PatientEntity("S9867027A", "001001","Sarah", "Yi",'F', 22, "93718799", "13, Clementi Road"));
        patientEntityControllerLocal.createNewPatient(new PatientEntity("G1314207T", "001101","Rajesh","Singh",'F',35, "93506839", "15, Mountbatten Road"));

        doctorEntityControllerLocal.createNewDoctor(new DoctorEntity("Tan", "Ming", "S10011", "BMBS"));
        doctorEntityControllerLocal.createNewDoctor(new DoctorEntity("Clair", "Hahn", "S41221", "MBBCh"));
        doctorEntityControllerLocal.createNewDoctor(new DoctorEntity("Robert", "Blake", "S58201", "MBBS"));
        doctorEntityControllerLocal.createNewDoctor(new DoctorEntity("Tan", "Min", "S31242", "MBSBS"));

////        /* // test adding consultation
//        Calendar cal = Calendar.getInstance(); // this is current Day instance
////        try {
////            DateHelper.setCurrentDate(DateHelper.dateSDF.parse("2020-04-06"));
////        } catch (ParseException ex) {
////            Logger.getLogger(DataInitializationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
////        }
//        long ONE_MINUTE_IN_MILLIS = 60000;
//        //Walk-in timings
//        cal.set(2020, 4, 6, 14, 00);
//        long t = cal.getTimeInMillis();
//        Date time = new Date(t);
//        consultationEntityControllerLocal.createConsultation(new ConsultationEntity(1l, time), "S9867027A",1l);
//        cal.set(Calendar.HOUR_OF_DAY, 13);
//        cal.set(Calendar.MINUTE, 30);
//        t = cal.getTimeInMillis();
//        time = new Date(t);
//        consultationEntityControllerLocal.createConsultation(new ConsultationEntity(2l, time), "G1314207T",3l); 
//        
// // test adding appointment
////        Calendar cal = Calendar.getInstance();
////        long ONE_MINUTE_IN_MILLIS = 60000;
//        cal.set(2020, 4, 6, 12, 00);
//        t = cal.getTimeInMillis();
//        time = new Date(t);
//        Date date = new Date(t);
//        appointmentEntityControllerLocal.createAppointment(new AppointmentEntity(1l, date, time), "G1314207T", 1l); 
//        cal.set(Calendar.HOUR_OF_DAY, 13);
//        cal.set(Calendar.MINUTE, 30);
//        t = cal.getTimeInMillis();
//        time = new Date(t);
//        date = new Date(t);
//        appointmentEntityControllerLocal.createAppointment(new AppointmentEntity(2l, date, time), "S9867027A", 2l);
//        cal.set(Calendar.HOUR_OF_DAY, 15);
//        cal.set(Calendar.MINUTE, 30);
//        t = cal.getTimeInMillis();
//        time = new Date(t);
//        date = new Date(t);
//        appointmentEntityControllerLocal.createAppointment(new AppointmentEntity(3l, date, time), "S9867027A",3l);
    }
}
