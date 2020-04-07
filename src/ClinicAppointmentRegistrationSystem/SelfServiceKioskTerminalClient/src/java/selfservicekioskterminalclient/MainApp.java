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
import entity.PatientEntity;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InvalidLoginException;
import util.exception.PatientAlreadyExistException;
import util.exception.UnknownPersistenceException;

public class MainApp {

    private StaffEntityControllerRemote staffEntityControllerRemote;
    private DoctorEntityControllerRemote doctorEntityControllerRemote;
    private PatientEntityControllerRemote patientEntityControllerRemote;
    private RegistrationControllerRemote registrationControllerRemote;
    private ConsultationEntityControllerRemote consultationEntityControllerRemote;
    private AppointmentEntityControllerRemote appointmentEntityControllerRemote;

    private PatientEntity currentPatientEntity;
    private RegistrationModule registrationModule;
    private AppointmentModule appointmentModule;
    
    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private Validator validator = validatorFactory.getValidator();
    
    public int queue = 0;

    public MainApp() {
    }

    MainApp(StaffEntityControllerRemote staffEntityControllerRemote, DoctorEntityControllerRemote doctorEntityControllerRemote, PatientEntityControllerRemote patientEntityControllerRemote, RegistrationControllerRemote registrationControllerRemote, ConsultationEntityControllerRemote consultationEntityControllerRemote, AppointmentEntityControllerRemote appointmentEntityControllerRemote) {
        this.staffEntityControllerRemote = staffEntityControllerRemote;
        this.doctorEntityControllerRemote = doctorEntityControllerRemote;
        this.patientEntityControllerRemote = patientEntityControllerRemote;
        this.registrationControllerRemote = registrationControllerRemote;
        this.consultationEntityControllerRemote = consultationEntityControllerRemote;
        this.appointmentEntityControllerRemote = appointmentEntityControllerRemote;
    }

    public void runApp() throws ParseException {

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Self-Service Kiosk ***\n");
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
                        registrationModule = new RegistrationModule(staffEntityControllerRemote, doctorEntityControllerRemote, patientEntityControllerRemote, registrationControllerRemote, consultationEntityControllerRemote, appointmentEntityControllerRemote);
                        appointmentModule = new AppointmentModule(staffEntityControllerRemote, doctorEntityControllerRemote, patientEntityControllerRemote, registrationControllerRemote, appointmentEntityControllerRemote);
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
        String password = "";

        System.out.println("*** Self-Service Kiosk :: Login ***\n");
        System.out.print("Enter Identity Number> ");
        identityNumber = scanner.nextLine().trim();
        System.out.print("Enter Password> ");
        password = scanner.nextLine().trim();

        if (identityNumber.length() > 0 && password.length() > 0) {
            try {
                currentPatientEntity = patientEntityControllerRemote.patientLogin(identityNumber, password);
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

        System.out.println("*** Self-Service Kiosk :: Register ***\n");


        System.out.println("*** CARS :: Administration Operation :: Add Patient ***\n");
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
                System.out.println("Patient " + patientId + " has been created!");
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

    private void menuMain() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Self-Service Kiosk :: Main ***\n");
            System.out.println("You are login as " + currentPatientEntity.getFirstName() + " " + currentPatientEntity.getLastName() + "\n");
            System.out.println("1: Register Walk-In Consultation");
            System.out.println("2: Register Consultation By Appointment");
            System.out.println("3: View Appointments");
            System.out.println("4: Add Appointment");
            System.out.println("5: Cancel Appointment");
            System.out.println("6: Logout\n");
            response = 0;

            while (response < 1 || response > 6) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) { //Register Walk-In Consultation
                    registrationModule.walkIn();
                } else if (response == 2) { //Register Consultation By Appointment
                    registrationModule.consultApp();
                } else if (response == 3) { //View Appointments
                    appointmentModule.viewApp();
                } else if (response == 4) { //Add appointment
                    appointmentModule.registerApp(currentPatientEntity);
                } else if (response == 5) { //cancel appointemnt
                    appointmentModule.cancelApp(currentPatientEntity);
                } else if (response == 6) { // Logout
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 6) {
                break;
            }
        }
    }
}
