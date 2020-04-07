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
import ejb.session.stateless.DoctorEntityControllerRemote;
import ejb.session.stateless.PatientEntityControllerRemote;
import ejb.session.stateless.StaffEntityControllerRemote;
import entity.AppointmentEntity;
import entity.StaffEntity;
import entity.PatientEntity;
import entity.DoctorEntity;
import util.date.DateHelper;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import util.exception.AppointmentNotFoundException;
import util.exception.CreateAppointmentException;
import util.exception.DoctorNotFoundException;
import util.exception.InvalidDateTimeFormatException;
import util.exception.PatientNotFoundException;
import util.exception.UnknownPersistenceException;

public class AppointmentModule {
    
    //SessionBeanRemoteController
    private final StaffEntityControllerRemote staffEntityControllerRemote;
    private final DoctorEntityControllerRemote doctorEntityControllerRemote;
    private final PatientEntityControllerRemote patientEntityControllerRemote;
    private final RegistrationControllerRemote registrationControllerRemote;
    private final AppointmentEntityControllerRemote appointmentEntityControllerRemote;
    
    //Entity Variables
    private StaffEntity currentStaffEntity;
    private PatientEntity currentPatientEntity;
    private DoctorEntity currentDoctorEntity;

    public AppointmentModule(StaffEntityControllerRemote staffEntityControllerRemote, DoctorEntityControllerRemote doctorEntityControllerRemote, PatientEntityControllerRemote patientEntityControllerRemote, RegistrationControllerRemote registrationControllerRemote, AppointmentEntityControllerRemote appointmentEntityControllerRemote) {
        this.staffEntityControllerRemote = staffEntityControllerRemote;
        this.doctorEntityControllerRemote = doctorEntityControllerRemote;
        this.patientEntityControllerRemote = patientEntityControllerRemote;
        this.registrationControllerRemote = registrationControllerRemote;
        this.appointmentEntityControllerRemote = appointmentEntityControllerRemote;
    }

    public void viewApp() {
        viewPatientAppointments();
    }

    public void registerApp(PatientEntity currPatientEntity) {
        addAppointment(currPatientEntity);
    }

    public void cancelApp(PatientEntity currPatientEntity) {
        cancelAppointment(currPatientEntity);
    }

    private void viewPatientAppointments() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Self-Service Kiosk :: View Appointments ***\n");

        System.out.print("Enter Patient Identity Number> ");
        String identityNumber = scanner.nextLine().trim();
        // Search for appointment record by patient
        try {
            currentPatientEntity = patientEntityControllerRemote.retrievePatientByIdentityNumber(identityNumber);
        } catch (PatientNotFoundException ex) {
            System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
        }

        System.out.println();
        System.out.println("Appointments:");
        // list appointment
        List<AppointmentEntity> appointmentEntities = appointmentEntityControllerRemote.retrieveAllAppointments();
        System.out.printf("%s%s%s%s\n", "Id|", "Date      |", "Time  |", "Doctor");

        if (appointmentEntities != null) {
            for (AppointmentEntity appointmentEntity : appointmentEntities) {
                if (appointmentEntity.getPatient().getIdentityNumber().equals(identityNumber)) {
                    System.out.printf("%s%s%s%s\n", appointmentEntity.getAppointmentId().toString(), "| " + DateHelper.dateSDF.format(appointmentEntity.getAppointmentDate()), "| " + DateHelper.timeSDF.format(appointmentEntity.getAppointmentTime()), "| " + appointmentEntity.getDoctor().getFirstName() + " " + appointmentEntity.getDoctor().getLastName());
                }
            }
        } else {
            System.out.println("No appointments have been made.");
        }
        System.out.println();
    }

    private void addAppointment(PatientEntity currentPatientEntity) {
       Scanner scanner = new Scanner(System.in);
        AppointmentEntity newAppointmentEntity = new AppointmentEntity();
        Date inputDate;
        Date inputTime;
        Date currentDate = new Date();

        System.out.println("*** Self-Service Kiosk :: Add Appointment ***\n");
        System.out.println();
        System.out.println("Doctor:");
        // list all doctor in database (id, firstname lastname)
        List<DoctorEntity> doctorEntities = doctorEntityControllerRemote.retrieveAllDoctors();
        System.out.printf("%s%s\n", "Id", "| Name");

        for (DoctorEntity doctorEntity : doctorEntities) {
            System.out.printf("%s%s\n", doctorEntity.getDoctorId().toString(), "| " + doctorEntity.getFirstName() + " " + doctorEntity.getLastName());
        }

        System.out.println();
        System.out.print("Enter Doctor Id> ");
        Long doctorId = scanner.nextLong();

        try {
            currentDoctorEntity = doctorEntityControllerRemote.retrieveDoctorById(doctorId);
        } catch (DoctorNotFoundException ex) {
            System.out.println("An error has occurred while retrieving doctor: " + ex.getMessage() + "\n");
        }

        scanner.nextLine();
        
        while(true){
            try{
                System.out.print("Enter Date> ");
                inputDate = DateHelper.convertToDate(scanner.nextLine().trim());               
                break;
            } catch (InvalidDateTimeFormatException ex){
                System.out.println(ex.getMessage());
            }
        }
        if (inputDate.after(currentDate)) {
            try {          
                newAppointmentEntity.setAppointmentDate(inputDate);

                String[] availability = DateHelper.getAvailabilitySlots(inputDate);
                //availability
                System.out.println("Availability for " + currentDoctorEntity.getFirstName() + " " 
                                    + currentDoctorEntity.getLastName() + " on " + inputDate.toString() + ":");
                List<AppointmentEntity> appointments = appointmentEntityControllerRemote.retrieveAllAppointments();
                if (appointments != null) {
                    // for loop to keep track of 30 mins interval
                    for (int i = 0; i < availability.length ; i++) { // Depending on the day
                        for (AppointmentEntity appointment : appointments) { // date check        
                            Date apptTime = appointment.getAppointmentTime();
                            String apptTimeStr = DateHelper.timeSDF.format(apptTime);
                            // if date compare == 0, time not equal, print time
                            if (inputDate.compareTo(appointment.getAppointmentDate()) == 0 && apptTimeStr.equals(availability[i]) 
                                && (Objects.equals(appointment.getDoctor().getDoctorId(), doctorId))) {
                                availability[i] = "";
                            }
                        }
                        if (availability[i].equals("")) {
                        System.out.print("");
                        } else {
                        System.out.print(availability[i] + " ");
                        }
                    }
                }
//
//                for (int i = 0; i < 16; i++) { // print availabililty from array
//                    if (availability[i].equals("")) {
//                        System.out.print("");
//                    } else {
//                        System.out.print(availability[i] + " ");
//                    }
//                }
                System.out.println();
                
                while(true){
                    try{
                        System.out.print("Enter Time > ");
                        inputTime = DateHelper.convertToTime(scanner.nextLine().trim());               
                        break;
                    } catch (InvalidDateTimeFormatException ex){
                        System.out.println(ex.getMessage());
                    }
                }
                
                System.out.print("Enter Patient Identity Number> ");
                String identityNumber = scanner.nextLine().trim();
                currentPatientEntity = patientEntityControllerRemote.retrievePatientByIdentityNumber(identityNumber);
                
                //add appointment
                appointmentEntityControllerRemote.createAppointment(newAppointmentEntity, identityNumber, doctorId);
                System.out.println(currentPatientEntity.getFirstName() + " " + currentPatientEntity.getLastName() 
                                    + " appointment with " + currentDoctorEntity.getFirstName() + " " + currentDoctorEntity.getLastName() 
                                    + " at " + inputTime + " on " + inputDate + " has been added.");
                System.out.println();               
            } catch (CreateAppointmentException | UnknownPersistenceException ex){
                System.out.println(ex.getMessage());
            } catch (PatientNotFoundException ex) {
                System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
            } 
        } else {
            System.out.println("Appointment can only be booked at least one day in advanced!");
        }
    }

    private void cancelAppointment(PatientEntity currentPatientEntity) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Self-Service Kiosk :: Cancel Appointment ***\n");

        System.out.println();
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
            System.out.print("Enter Appointment Id> ");
            Long inputApptId = scanner.nextLong();

            try {
                AppointmentEntity appointmentEntity = appointmentEntityControllerRemote.retrieveAppointmentByAppointmentId(inputApptId);
                appointmentEntityControllerRemote.cancelAppointment(inputApptId);
                System.out.println("Appointment: " + currentPatientEntity.getFirstName() + " " + currentPatientEntity.getLastName() + " and " + appointmentEntity.getDoctor().getFirstName() + " " + appointmentEntity.getDoctor().getLastName() + " at " + DateHelper.timeSDF.format(appointmentEntity.getAppointmentTime()) + " on " + DateHelper.dateSDF.format(appointmentEntity.getAppointmentDate()) + " has been cancelled.");
            } catch (AppointmentNotFoundException ex) {
                System.out.println("No appointment found.\n");
            }

        } else {
            System.out.println("No appointments made.");
        }
        System.out.println();
    }
}
