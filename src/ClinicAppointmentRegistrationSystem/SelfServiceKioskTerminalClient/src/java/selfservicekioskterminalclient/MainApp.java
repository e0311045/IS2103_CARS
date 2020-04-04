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
import java.util.regex.Pattern;
import util.exception.InvalidLoginException;

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
        PatientEntity newPatient = new PatientEntity();

        System.out.println("*** Self-Service Kiosk :: Register ***\n");
        List<PatientEntity> patients = patientEntityControllerRemote.retrieveAllPatients();

        System.out.print("Enter Identity Number> ");
        String identityNumber = scanner.nextLine().trim();
        for (PatientEntity patient : patients) {
            if (patient.getIdentityNumber().equals(identityNumber)) {
                System.out.println("Patient is already created.");
                break;
            } else {
                newPatient.setIdentityNumber(identityNumber);
                //Validating all inputs
                while(true){
                    System.out.print("Enter Password> ");
                    String password = scanner.nextLine().trim();
                    Pattern digitPattern = Pattern.compile("\\d{6}");
                    if(!digitPattern.matcher(password).matches()){
                        System.out.println("Password has to be 6 digit");
                    } else {
                        newPatient.setPassword(password);
                        break;
                    }                   
                }
                while(true){
                    System.out.print("Enter First Name> ");
                    String firstName = scanner.nextLine().trim();
                    if(firstName.length()>32){
                        System.out.println("Maximum first name length is 32");
                    }
                    else{
                        newPatient.setFirstName(firstName);
                        break;
                    }       
                }
                while(true){
                    System.out.print("Enter Last Name> ");
                    String lastName = scanner.nextLine().trim();
                    if(lastName.length()>32){
                        System.out.println("Maximum last name length is 32");
                    }
                    else{
                        newPatient.setLastName(lastName);
                        break;
                    }       
                }
                while(true){
                    System.out.print("Enter Gender> ");
                    String gender = scanner.nextLine().trim();
                    if(gender.length()>1){
                        System.out.println("Please input either 'F' or 'M'!");
                    }
                    else if(gender.charAt(0)=='F'||gender.charAt(0)=='M'){
                        newPatient.setGender(gender.charAt(0));
                        break;
                    } else {
                        System.out.println("Gender can only either be 'F' or 'M'!");
                    }                   
                }                
                while(true){
                    System.out.print("Enter Age> ");
                    Integer age = scanner.nextInt();
                    Pattern digitPattern = Pattern.compile("\\d{3}");
                    if(!digitPattern.matcher(age.toString()).matches()){
                        System.out.println("Age can only have max of 3 digits");
                    } else {
                        newPatient.setAge(age);
                        break;
                    }                   
                }
                scanner.nextLine();
                while(true){
                    System.out.print("Enter Phone> ");
                    String phone = scanner.nextLine().trim();
                    if(phone.length()>15){
                        System.out.println("Maximum phone length can only be 15");
                    } else {
                        newPatient.setPhone(phone);
                        break;
                    }                 
                }               
                while(true){
                    System.out.print("Enter Address> ");
                    String address = scanner.nextLine().trim();
                    if(address.length()>50){
                        System.out.println("Maximum address length can only be 50");
                    } else {
                        newPatient.setAddress(address);
                        break;
                    }                 
                }
                patientEntityControllerRemote.createNewPatient(newPatient);
                System.out.println("Patient has been registered successfully!\n");
                break;
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
