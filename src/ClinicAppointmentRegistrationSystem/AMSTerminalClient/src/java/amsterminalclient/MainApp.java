/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package amsterminalclient;

import ejb.session.stateless.AppointmentEntityControllerRemote;
import ejb.session.stateless.DoctorEntityControllerRemote;
import ejb.session.stateless.PatientEntityControllerRemote;
import ejb.session.stateless.StaffEntityControllerRemote;
import entity.AppointmentEntity;
import entity.DoctorEntity;
import entity.PatientEntity;
import entity.StaffEntity;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import util.date.DateHelper;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.AppointmentNotFoundException;
import util.exception.CreateAppointmentException;
import util.exception.DoctorNotFoundException;
import util.exception.InvalidDateTimeFormatException;
import util.exception.InvalidLoginException;
import util.exception.PatientAlreadyExistException;
import util.exception.PatientNotFoundException;
import util.exception.UnknownPersistenceException;

public class MainApp {

    private StaffEntityControllerRemote staffEntityControllerRemote;
    private DoctorEntityControllerRemote doctorEntityControllerRemote;
    private PatientEntityControllerRemote patientEntityControllerRemote;
    private AppointmentEntityControllerRemote appointmentEntityControllerRemote;

    private PatientEntity currentPatientEntity;
    private StaffEntity currentStaffEntity;
    private DoctorEntity currentDoctorEntity;
    
    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private Validator validator = validatorFactory.getValidator();

    public MainApp() {
    }

    MainApp(StaffEntityControllerRemote staffEntityControllerRemote, DoctorEntityControllerRemote doctorEntityControllerRemote, PatientEntityControllerRemote patientEntityControllerRemote, AppointmentEntityControllerRemote appointmentEntityControllerRemote) {
        this.staffEntityControllerRemote = staffEntityControllerRemote;
        this.doctorEntityControllerRemote = doctorEntityControllerRemote;
        this.patientEntityControllerRemote = patientEntityControllerRemote;
        this.appointmentEntityControllerRemote = appointmentEntityControllerRemote;
    }

    public void runApp() {

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to AMS Client ***\n");
            System.out.println("1: Register");
            System.out.println("2: Login");
            System.out.println("3: Exit\n");
            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    registerPatient();
                } else if (response == 2) {

                    try {
                        doLogin();
                        menuMain();
                    } catch (InvalidLoginException ex) {
                    }
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 3) {
                break;
            }
        }
    }

    private void doLogin() throws InvalidLoginException {
        Scanner scanner = new Scanner(System.in);
        String identityNumber = "";
        String securityCode = "";

        System.out.println("*** AMS Client :: Login ***\n");
        System.out.print("Enter Identity Number> ");
        identityNumber = scanner.nextLine().trim();
        System.out.print("Enter Security Code> ");
        securityCode = scanner.nextLine().trim();

        if (identityNumber.length() > 0 && securityCode.length() > 0) {
            try {
                currentPatientEntity = patientEntityControllerRemote.patientLogin(identityNumber, securityCode);
                System.out.println("Login successful!\n");
            } catch (InvalidLoginException ex) {
                System.out.println("Invalid login: " + ex.getMessage() + "\n");
                throw new InvalidLoginException();
            }
        } else {
            System.out.println("Invalid login!");
        }
    }

    // get details and register patient into database
    private void registerPatient() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        currentPatientEntity = new PatientEntity();

        System.out.println("*** CARS :: Registration Operation :: Register Patient ***\n");

        System.out.print("Enter Identity Number> ");
        currentPatientEntity.setIdentityNumber(scanner.nextLine().trim());
        System.out.print("Enter First Name> ");
        currentPatientEntity.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter Last Name> ");
        currentPatientEntity.setLastName(scanner.nextLine().trim());
        System.out.print("Enter Gender (please enter only F/M)> ");
        String gender = scanner.nextLine();
        currentPatientEntity.setGender(gender.charAt(0));
        System.out.print("Enter Password> ");
        currentPatientEntity.setPassword(scanner.nextLine().trim());
        System.out.print("Enter Age (please enter only numeric digits eg. 20)> ");
        currentPatientEntity.setAge(scanner.nextInt());
        scanner.nextLine();
        System.out.print("Enter Phone> ");
        currentPatientEntity.setPhone(scanner.nextLine().trim());
        System.out.print("Enter Address> ");
        currentPatientEntity.setAddress(scanner.nextLine().trim());

        Set<ConstraintViolation<PatientEntity>> errors = validator.validate(currentPatientEntity);
        
        if(errors.isEmpty()){
            try{
                Long patientId = patientEntityControllerRemote.createNewPatient(currentPatientEntity);
                System.out.println("Patient has been registered successfully!");
            } catch (PatientAlreadyExistException ex){
                System.out.println("An error has occurred while creating the new Patient!: The patient already exist\n");
            } catch (UnknownPersistenceException ex){
                System.out.println("An unknown error has occurred while creating a new Patient!: " + ex.getMessage() + "\n");
            }
        } else {
            System.out.println("Unable to create Patient due to the following validation errors: ");
            for (ConstraintViolation error : errors) {
                System.out.println("Validation Error: " + error.getPropertyPath() + " - " + error.getInvalidValue() + ": " + error.getMessage());
            }
        }
    }

    private void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** AMS Client :: Main ***\n");
            System.out.println("You are login as " + currentPatientEntity.getFirstName() + " " + currentPatientEntity.getLastName() + "\n");
            System.out.println("1: View Appointments");
            System.out.println("2: Add Appointment");
            System.out.println("3: Cancel Appointment");
            System.out.println("4: Logout\n");
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

        System.out.println("*** AMS Client :: View Appointments ***\n");

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


        if (appointmentEntities != null) {
            for (AppointmentEntity appointmentEntity : appointmentEntities) {
                if (appointmentEntity.getPatient().getIdentityNumber().equals(identityNumber)) {
                    System.out.printf("%s%s%s%s\n", appointmentEntity.getAppointmentId().toString(), "| " + DateHelper.dateSDF.format(appointmentEntity.getAppointmentDate()), "| " + DateHelper.timeSDF.format(appointmentEntity.getAppointmentTime()), "| " + appointmentEntity.getDoctor().getFirstName() + " " + appointmentEntity.getDoctor().getLastName());
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

        System.out.println("*** AMS Client:: Add Appointment ***\n");
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

    private void cancelAppointment() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** AMS Client:: Cancel Appointment ***\n");

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
