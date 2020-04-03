/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package selfservicekioskterminalclient;

import ejb.session.stateful.RegistrationControllerRemote;
import ejb.session.stateless.AppointmentEntityControllerRemote;
import ejb.session.stateless.ConsultationEntityControllerRemote;
import ejb.session.stateless.DoctorEntityControllerRemote;
import ejb.session.stateless.PatientEntityControllerRemote;
import ejb.session.stateless.StaffEntityControllerRemote;
import entity.AppointmentEntity;
import entity.ConsultationEntity;
import entity.StaffEntity;
import entity.PatientEntity;
import entity.DoctorEntity;
import java.text.DateFormat;
import java.text.ParseException;
import util.date.DateHelper;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import util.exception.DoctorNotFoundException;
import util.exception.PatientNotFoundException;

public class RegistrationModule {

    private StaffEntityControllerRemote staffEntityControllerRemote;
    private DoctorEntityControllerRemote doctorEntityControllerRemote;
    private PatientEntityControllerRemote patientEntityControllerRemote;
    private RegistrationControllerRemote registrationControllerRemote;
    private ConsultationEntityControllerRemote consultationEntityControllerRemote;
    private AppointmentEntityControllerRemote appointmentEntityControllerRemote;
    
    //Entities Variable
    private StaffEntity currentStaffEntity;
    private PatientEntity currentPatientEntity;
    private DoctorEntity currentDoctorEntity;
    private AppointmentEntity currentAppointment;

    public RegistrationModule(StaffEntityControllerRemote staffEntityControllerRemote, DoctorEntityControllerRemote doctorEntityControllerRemote, PatientEntityControllerRemote patientEntityControllerRemote, RegistrationControllerRemote registrationControllerRemote, ConsultationEntityControllerRemote consultationEntityControllerRemote, AppointmentEntityControllerRemote appointmentEntityControllerRemote) {
        this.staffEntityControllerRemote = staffEntityControllerRemote;
        this.doctorEntityControllerRemote = doctorEntityControllerRemote;
        this.patientEntityControllerRemote = patientEntityControllerRemote;
        this.registrationControllerRemote = registrationControllerRemote;
        this.consultationEntityControllerRemote = consultationEntityControllerRemote;
        this.appointmentEntityControllerRemote = appointmentEntityControllerRemote;
    }

    public RegistrationModule(StaffEntity currentStaffEntity, RegistrationControllerRemote registrationControllerRemote) {
        //this();

        this.currentStaffEntity = currentStaffEntity;
        this.registrationControllerRemote = registrationControllerRemote;
    }

    public void walkIn() throws ParseException {
        consultByWalkIn();
    }

    public void consultApp() {
        consultByAppointment();
    }

    private void consultByWalkIn() throws ParseException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Self-Service Kiosk :: Register Walk-In Consultation ***\n");
        System.out.println("Doctor:");
        // list all doctor in database id, firstname lastname
        List<DoctorEntity> doctorEntities = doctorEntityControllerRemote.retrieveAllDoctors();
        System.out.printf("%s%s\n", "Id", "| Name");

        for (DoctorEntity doctorEntity : doctorEntities) {
            System.out.printf("%s%s\n", doctorEntity.getDoctorId().toString(), "| " + doctorEntity.getFirstName() + " " + doctorEntity.getLastName());
        }

        System.out.println();
        System.out.println("Availability:");
        // list all slots available to book (3 hour mark from current time)
        // opening hours 0900 to 1700

        // check current time
        Calendar cal = Calendar.getInstance();
        Calendar operating = Calendar.getInstance();
        if ((operating.get(Calendar.HOUR_OF_DAY) == 9 && operating.get(Calendar.MINUTE) >= 00) || (operating.get(Calendar.HOUR_OF_DAY) == 16 && operating.get(Calendar.MINUTE) < 30)) {

            String availability[][] = new String[7][doctorEntities.size() + 1];
            availability[0][0] = "Time ";

            // doctorid in table row 0
            for (int i = 1; i < doctorEntities.size() + 1; i++) {
                availability[0][i] = Integer.toString(i);
            }

            long ONE_MINUTE_IN_MILLIS = 60000;
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int min = cal.get(Calendar.MINUTE);

            if (min >= 00 && min <= 29) {
                cal.set(Calendar.MINUTE, 30);
            } else {
                cal.set(Calendar.HOUR_OF_DAY, hour + 1);
                cal.set(Calendar.MINUTE, 00);
            }

            long t = cal.getTimeInMillis();

            // time in table column 0
            for (int i = 1; i < 7; i++) {
                Date time = new Date(t);
                DateFormat df = new SimpleDateFormat("HH:mm"); // dd-MM-yy date
                availability[i][0] = df.format(time);
                t += 30 * ONE_MINUTE_IN_MILLIS;
            }

            // Initialise all empty sets first to avoid null exception in accessing array
            for (int i = 1; i < 7; i++) {
                for (int j = 1; j < doctorEntities.size() + 1; j++) {
                    availability[i][j] = "O"; // all empty
                }
            }

            // if consultation slot taken mark X
            for (int i = 1; i < 7; i++) {
                for (int j = 1; j < doctorEntities.size() + 1; j++) {
                    // if find(doctor, time) , print X // ConsultationNotFound
                    List<ConsultationEntity> consultations = consultationEntityControllerRemote.retrieveAllConsultations();
                    if (consultations != null) {
                        for (ConsultationEntity consultation : consultations) {
                            Date temp = consultation.getTime();
                            String tempTime = DateHelper.timeSDF.format(temp);
                            if ((consultation.getDoctor().getDoctorId().toString().equals(Integer.toString(j))) && tempTime.equals(availability[i][0])) {
                                availability[i][j] = "X";
                            }
                        }
                    }
                }
            }

            // print all availability table
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < doctorEntities.size() + 1; j++) {
                    if (availability[i][j].equals("09:00")) {// opening
                        System.out.print(availability[i][j] + " |");
                    } else if (availability[i][j].equals("17:00")) { // near closing
                        System.out.println("No slots available after 5pm");
                        i = 6;
                        j = doctorEntities.size();

                    } else {
                        System.out.print(availability[i][j] + " |");
                    }
                }
                System.out.println("");
            }

            System.out.print("Enter Doctor Id> ");
            Long doctorId = scanner.nextLong();

            // Look for doctor
            try {
                currentDoctorEntity = doctorEntityControllerRemote.retrieveDoctorById(doctorId);
            } catch (DoctorNotFoundException ex) {
                System.out.println("An error has occurred while retrieving doctor: " + ex.getMessage() + "\n");
            }

            scanner.nextLine();
            int docId = doctorId.intValue();

            // if Time avaiable is "O" -> add consultation at this timing
            Date currTime = new Date();

            for (int i = 1; i < 7; i++) {
                if (availability[i][docId].equals("O")) {// available
                    currTime = DateHelper.timeSDF.parse(availability[i][0]);
                    i = 6;
                }
            }

            System.out.println(currentPatientEntity.getFirstName() + " " + currentPatientEntity.getLastName() + " is going to see " + currentDoctorEntity.getFirstName() + " " + currentDoctorEntity.getLastName() + " at " + DateHelper.timeSDF.format(currTime) + ". Queue Number is: " + registrationControllerRemote.getQueue() + "\n");
            registrationControllerRemote.addQueue();

            // create consultation
            ConsultationEntity newConsultation = new ConsultationEntity();
            newConsultation.setTime(currTime);
            List<ConsultationEntity> consultations = consultationEntityControllerRemote.retrieveAllConsultations();
            newConsultation.setConsultationId((long) consultations.size() + 1);
            consultationEntityControllerRemote.createConsultation(newConsultation, currentPatientEntity.getIdentityNumber(), doctorId);
        } else {
            System.out.println("There are no more consultations because clinic is either closed, closing soon or not opened yet.");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void consultByAppointment() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Self-Service Kiosk :: Register Consultation By Appointment ***\n");

        System.out.println("Appointments:");
        // list appointment
        List<AppointmentEntity> appointmentEntities = appointmentEntityControllerRemote.retrieveAllAppointments();
        System.out.printf("%s%s%s%s\n", "Id|", "Date      |", "Time  |", "Doctor");

        if (appointmentEntities != null) {
            for (AppointmentEntity appointmentEntity : appointmentEntities) {
                if (appointmentEntity.getPatient().getIdentityNumber().equals(currentPatientEntity.getIdentityNumber())) {
                    System.out.printf("%s%s%s%s\n", appointmentEntity.getAppointmentId().toString(), "| " + DateHelper.dateSDF.format(appointmentEntity.getAppointmentDate()), "| " + DateHelper.timeSDF.format(appointmentEntity.getAppointmentTime()), "| " + appointmentEntity.getDoctor().getFirstName() + " " + appointmentEntity.getDoctor().getLastName());
                }
            }
            System.out.println();
        }

        //CHECK IF WITHIN OPERATION HOURS
        Calendar operating = Calendar.getInstance();
        if ((operating.get(Calendar.HOUR_OF_DAY) == 9 && operating.get(Calendar.MINUTE) >= 00) || (operating.get(Calendar.HOUR_OF_DAY) == 16 && operating.get(Calendar.MINUTE) < 30)) {
            System.out.print("Enter Appointment Id> ");
            int appointmentId = scanner.nextInt();

            // get doctorId from appointment to constrict table availability
            if (appointmentEntities != null) {
                for (AppointmentEntity appointmentEntity : appointmentEntities) {
                    if (appointmentEntity.getAppointmentId() == appointmentId) {
                        currentAppointment = appointmentEntity;
                    }
                }

                // get time check against doctor availability in consultation
                Date currTime = currentAppointment.getAppointmentTime();

                List<ConsultationEntity> consultations = consultationEntityControllerRemote.retrieveAllConsultations();
                if (consultations != null) {
                    for (ConsultationEntity consultation : consultations) {
                        Date temp = consultation.getTime();
                        String tempTime = DateHelper.timeSDF.format(temp);
                        //consultation slot not available
                        if ((consultation.getDoctor().getDoctorId().toString().equals(currentAppointment.getDoctor().getDoctorId().toString())) && tempTime.equals(DateHelper.timeSDF.format(currTime))) {
                            System.out.println("Consultation slot is already taken.");
                        } else { // consultation available for appointment
                            System.out.println(currentPatientEntity.getFirstName() + " " + currentPatientEntity.getLastName() + " is going to see " + currentAppointment.getDoctor().getFirstName() + " " + currentAppointment.getDoctor().getLastName() + " at " + DateHelper.timeSDF.format(currTime) + ". Queue Number is: " + registrationControllerRemote.getQueue() + "\n");
                            registrationControllerRemote.addQueue();

                            // create consultation
                            ConsultationEntity newConsultation = new ConsultationEntity();
                            newConsultation.setTime(currTime);
                            newConsultation.setConsultationId((long) consultations.size() + 1);
                            consultationEntityControllerRemote.createConsultation(newConsultation, currentPatientEntity.getIdentityNumber(), currentAppointment.getDoctor().getDoctorId());
                        }
                    }
                }
            }
        } else {
            System.out.println("There are no more consultations because clinic is either closed, closing soon or not opened yet.");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }
}
