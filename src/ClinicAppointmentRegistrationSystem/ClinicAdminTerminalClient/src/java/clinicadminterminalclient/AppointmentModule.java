/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package clinicadminterminalclient;

import ejb.session.stateful.RegistrationControllerRemote;
import ejb.session.stateless.AppointmentEntityControllerRemote;
import ejb.session.stateless.DoctorEntityControllerRemote;
import ejb.session.stateless.PatientEntityControllerRemote;
import ejb.session.stateless.StaffEntityControllerRemote;
import entity.AppointmentEntity;
import entity.StaffEntity;
import entity.PatientEntity;
import entity.DoctorEntity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import util.date.DateHelper;
import util.exception.AppointmentNotFoundException;
import util.exception.CreateAppointmentException;
import util.exception.DoctorNotFoundException;
import util.exception.InvalidDateTimeFormatException;
import util.exception.PatientNotFoundException;
import util.exception.UnknownPersistenceException;

public class AppointmentModule {

    private StaffEntityControllerRemote staffEntityControllerRemote;
    private DoctorEntityControllerRemote doctorEntityControllerRemote;
    private PatientEntityControllerRemote patientEntityControllerRemote;
    private RegistrationControllerRemote registrationControllerRemote;
    private AppointmentEntityControllerRemote appointmentEntityControllerRemote;
    
    //Entity Attributes
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

    public void menuAppointment() throws AppointmentNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** CARS :: Appointment Operation ***\n");
            System.out.println("1: View Patient Appointments");
            System.out.println("2: Add Appointment");
            System.out.println("3: Cancel Appointment");
            System.out.println("4: Back\n");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    viewPatientAppointments();
                } else if (response == 2) {
                    addAppointment();
                } else if (response == 3) {
                    cancelAppointment();
                } else if (response == 4) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 4) {
                break;
            }
        }
    }

    private void viewPatientAppointments() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** CARS :: Appointment Operation :: View Patient Appointments ***\n");

        System.out.print("Enter Patient Identity Number> ");
        String identityNumber = scanner.nextLine().trim();
        // search appointment set by patient
        try {
            currentPatientEntity = patientEntityControllerRemote.retrievePatientByIdentityNumber(identityNumber);
        } catch (PatientNotFoundException ex) {
            System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
        }

        System.out.println();
        System.out.println("Appointments:");
        // list appointment
        List<AppointmentEntity> appointmentEntities = appointmentEntityControllerRemote.retrieveAllAppointments();
        System.out.printf("%3s%11s%6s%64s\n", "Id|", "Date      |", "Time  |", "Doctor");

        if (appointmentEntities != null) {
            for (AppointmentEntity appointmentEntity : appointmentEntities) {
                if (appointmentEntity.getPatient().getIdentityNumber().equals(identityNumber)) {
                    System.out.printf("%3s%11s%6s%64s\n", appointmentEntity.getAppointmentId().toString(), "| " + DateHelper.dateSDF.format(appointmentEntity.getAppointmentDate())
                                        , "| " + DateHelper.timeSDF.format(appointmentEntity.getAppointmentTime()), "| " + appointmentEntity.getDoctor().getFirstName() + " " + appointmentEntity.getDoctor().getLastName());
                }
            }
        } else {
            System.out.println("No appointments made.");
        }
        System.out.println();
    }

    private void addAppointment() {
        Scanner scanner = new Scanner(System.in);
        AppointmentEntity newAppointmentEntity = new AppointmentEntity();
        Date inputDate;
        Date inputTime;
        Date currentDate = new Date();

        System.out.println("*** CARS :: Appointment Operation :: Add Appointment ***\n");
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

    private void cancelAppointment() throws AppointmentNotFoundException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** CARS :: Appointment Operation :: Cancel Appointment ***\n");
        System.out.println();
        System.out.print("Enter Patient Identity Number> ");
        String identityNumber = scanner.nextLine().trim();
        // search appointment set by patient
        // search patient
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

        DateFormat df = new SimpleDateFormat("HH:mm");
        DateFormat datef = new SimpleDateFormat("yyyy-MM-dd");

        if (appointmentEntities != null) {
            for (AppointmentEntity appointmentEntity : appointmentEntities) {
                if (appointmentEntity.getPatient().getIdentityNumber().equals(identityNumber)) {
                    System.out.printf("%s%s%s%s\n", appointmentEntity.getAppointmentId().toString(), "| " + datef.format(appointmentEntity.getAppointmentDate()), "| " + df.format(appointmentEntity.getAppointmentTime()), "| " + appointmentEntity.getDoctor().getFirstName() + " " + appointmentEntity.getDoctor().getLastName());
                }
            }

            System.out.println();
            System.out.print("Enter Appointment Id> ");
            Long appId = scanner.nextLong();

            try {
                AppointmentEntity appointmentEntity = appointmentEntityControllerRemote.retrieveAppointmentByAppointmentId(appId);
                appointmentEntityControllerRemote.cancelAppointment(appId);
                System.out.println("Appointment: " + currentPatientEntity.getFirstName() + " " + currentPatientEntity.getLastName() + " and " + appointmentEntity.getDoctor().getFirstName() + " " + appointmentEntity.getDoctor().getLastName() + " at " + df.format(appointmentEntity.getAppointmentTime()) + " on " + datef.format(appointmentEntity.getAppointmentDate()) + " has been cancelled.");
            } catch (AppointmentNotFoundException ex) {
                System.out.println("No appointment found.\n");
            }

        } else {
            System.out.println("No appointments made.");
        }
        System.out.println();
    }
}
