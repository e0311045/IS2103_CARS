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
import util.date.DateHelper;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import util.exception.InvalidDateTimeFormatException;
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

    public void walkIn() {
        registerByWalkIn();
    }

    public void consultApp() {
        registerByAppointment();
    }

    private void registerByWalkIn() {
       Integer response = 0;
        Calendar walkIn = Calendar.getInstance();
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Self-Service Kiosk :: Register Walk-In Consultation ***\n");
        
        System.out.println("Doctor:\n");
        // list all doctor in database id, firstname lastname
        List<DoctorEntity> allDoctors = doctorEntityControllerRemote.retrieveAllDoctors();
        System.out.printf("%3s%64s\n", "Id", "| Name");

        for (DoctorEntity doctorEntity : allDoctors) {
            System.out.printf("%3s%64s\n", doctorEntity.getDoctorId().toString(), "| " + doctorEntity.getFirstName() + " " + doctorEntity.getLastName());
        }

        System.out.println("Availability:\n");
        // list all slots available to book (3 hour mark from current time) that is within operation hours
        if (DateHelper.isOperational(walkIn)&&!DateHelper.isBreakTime(walkIn)) {
            try {
                String[][] availability = new String[7][allDoctors.size() + 1];

                DateHelper.prepareAvailabilityWalkInSlots(walkIn,availability);

                // if consultation slot taken insert X
                for (int i = 1; i < 7; i++) {
                    for (int j = 1; j < availability[0].length + 1; j++) {
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
                int closingHour = DateHelper.getClosingHour(DateHelper.getDayOfWeek(walkIn.getTime()));
                int closingMin = DateHelper.getClosingMinute(DateHelper.getDayOfWeek(walkIn.getTime()));
                String closingTime = String.valueOf(closingHour)+":"+String.valueOf(closingMin);
                // print availability table
                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < availability[0].length + 1; j++) {
                        if (availability[i][j].equals(closingTime)) { // near closing
                            System.out.println("No slots available after " + closingTime);
                            i = 6;
                            j = allDoctors.size();
                        } else {
                            System.out.print(availability[i][j] + " |");
                        }
                    }
                    System.out.println("");
                }
                while(true){
                    System.out.print("Enter Doctor Id> ");
                    response = scanner.nextInt();
                    if(response >= 1 && response <= allDoctors.size()){
                        break;
                        } else {
                        System.out.println("Invalid option, please try again!\n");
                    }
                }

                scanner.nextLine();
                System.out.print("Enter Patient Identity Number> ");
                String identityNumber = scanner.nextLine().trim();
                 // search patient
            
                currentPatientEntity = patientEntityControllerRemote.retrievePatientByIdentityNumber(identityNumber);
                int docId = allDoctors.get(response - 1).getDoctorId().intValue();
                // find next available consultation time -> add consultation at this timing
                Date currTime = new Date();

                for (int i = 1; i < 7; i++) {
                    if (availability[i][docId].equals("O")) {// available
                        currTime = DateHelper.convertToTime(availability[i][0]);
                        break;
                    }
                }

                System.out.println(currentPatientEntity.getFirstName() + " " + currentPatientEntity.getLastName() + " appointment with Dr. " + currentDoctorEntity.getFirstName() + " " + currentDoctorEntity.getLastName() + " has been booked at " + DateHelper.timeSDF.format(currTime) + ".\n Queue Number is: " + registrationControllerRemote.getQueue() + "\n");
                registrationControllerRemote.addQueue();

                // create consultation
                ConsultationEntity newConsultation = new ConsultationEntity();
                newConsultation.setTime(currTime);
                List<ConsultationEntity> consultations = consultationEntityControllerRemote.retrieveAllConsultations();
                consultationEntityControllerRemote.createConsultation(newConsultation, identityNumber, allDoctors.get(response - 1).getDoctorId());                
                } catch (PatientNotFoundException ex) {
                    System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
                } catch (InvalidDateTimeFormatException ex){
                    System.out.println(ex.getMessage());
                }     
            } else {
                System.out.println("There are no more consultations because clinic is either closed, closing soon or not opened yet.");
                System.out.print("Press any key to continue...> ");
                scanner.nextLine();
            }  
    }

    private void registerByAppointment() {


        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Self-Service Kiosk :: Register Consultation By Appointment ***\n");

        System.out.print("Enter Patient Identity Number> ");
        String identityNumber = scanner.nextLine().trim();
        // search appointment set by patient
        // search patient
        try {
            currentPatientEntity = patientEntityControllerRemote.retrievePatientByIdentityNumber(identityNumber);
        } catch (PatientNotFoundException ex) {
            System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
        }

        System.out.println("Appointments:\n");
        // list appointment
        List<AppointmentEntity> appointmentEntities = appointmentEntityControllerRemote.retrieveAllAppointments();
        System.out.printf("%3s%11s%6s%64s\n", "Id|", "Date      |", "Time  |", "Doctor");

        if (appointmentEntities != null) {
            for (AppointmentEntity appointmentEntity : appointmentEntities) {
                if (appointmentEntity.getPatient().getIdentityNumber().equals(identityNumber)) {
                    System.out.printf("%3s%11s%6s%64s\n", appointmentEntity.getAppointmentId().toString(), "| " + DateHelper.dateSDF.format(appointmentEntity.getAppointmentDate()), "| " + DateHelper.timeSDF.format(appointmentEntity.getAppointmentTime()), "| " + appointmentEntity.getDoctor().getFirstName() + " " + appointmentEntity.getDoctor().getLastName());
                }
            }
        } else {
            System.out.println("No appointments made.");
        }

        //check if within operation hours
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
                            System.out.println(currentPatientEntity.getFirstName() + " " + currentPatientEntity.getLastName() + " appointment is confirmed with Dr. " + currentAppointment.getDoctor().getFirstName() + " " + currentAppointment.getDoctor().getLastName() + " at " + DateHelper.timeSDF.format(currTime) + ".\n Queue Number is: " + registrationControllerRemote.getQueue() + "\n");
                            registrationControllerRemote.addQueue();

                            // create consultation
                            ConsultationEntity newConsultation = new ConsultationEntity();
                            newConsultation.setTime(currTime);
                            newConsultation.setConsultationId((long) consultations.size() + 1);
                            consultationEntityControllerRemote.createConsultation(newConsultation, identityNumber, currentAppointment.getDoctor().getDoctorId());
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
