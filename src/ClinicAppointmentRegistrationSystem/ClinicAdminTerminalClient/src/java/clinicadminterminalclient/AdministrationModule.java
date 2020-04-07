/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package clinicadminterminalclient;

import ejb.session.stateful.RegistrationControllerRemote;
import ejb.session.stateless.DoctorEntityControllerRemote;
import ejb.session.stateless.LeaveEntityControllerRemote;
import ejb.session.stateless.PatientEntityControllerRemote;
import ejb.session.stateless.StaffEntityControllerRemote;
import entity.StaffEntity;
import entity.PatientEntity;
import entity.DoctorEntity;
import entity.LeaveEntity;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.date.DateHelper;
import util.exception.DoctorAlreadyExistException;
import util.exception.DoctorNotFoundException;
import util.exception.InvalidDateTimeFormatException;
import util.exception.LeaveApplicationException;
import util.exception.LeaveExistException;
import util.exception.PatientAlreadyExistException;
import util.exception.PatientNotFoundException;
import util.exception.StaffAlreadyExistException;
import util.exception.StaffNotFoundException;
import util.exception.UnknownPersistenceException;

public class AdministrationModule {
    
    //SessionBeans
    private final StaffEntityControllerRemote staffEntityControllerRemote;
    private final DoctorEntityControllerRemote doctorEntityControllerRemote;
    private final PatientEntityControllerRemote patientEntityControllerRemote;
    private final RegistrationControllerRemote registrationControllerRemote;
    private final LeaveEntityControllerRemote leaveEntityControllerRemote;
    
    //Entities Variable
    private StaffEntity currentStaffEntity;
    private PatientEntity currentPatientEntity;
    private DoctorEntity currentDoctorEntity;
    private LeaveEntity currentLeaveEntity;
    
    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private Validator validator = validatorFactory.getValidator();

    public AdministrationModule(StaffEntityControllerRemote staffEntityControllerRemote, DoctorEntityControllerRemote doctorEntityControllerRemote, PatientEntityControllerRemote patientEntityControllerRemote, RegistrationControllerRemote registrationControllerRemote,LeaveEntityControllerRemote leaveEntityControllerRemote) {
        this.staffEntityControllerRemote = staffEntityControllerRemote;
        this.doctorEntityControllerRemote = doctorEntityControllerRemote;
        this.patientEntityControllerRemote = patientEntityControllerRemote;
        this.registrationControllerRemote = registrationControllerRemote;
        this.leaveEntityControllerRemote = leaveEntityControllerRemote;
    }

    public void menuAdministration() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** CARS :: Administration Operation ***\n");
            System.out.println("1: Patient Management");
            System.out.println("2: Doctor Management");
            System.out.println("3: Staff Management");
            System.out.println("4: Back\n");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    patientManagement();
                } else if (response == 2) {
                    doctorManagement();
                } else if (response == 3) {
                    staffManagement();
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

    private void patientManagement() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** CARS :: Administration Operation :: Patient Management ***\n");
            System.out.println("1: Add Patient");
            System.out.println("2: View Patient Details");
            System.out.println("3: Update Patient");
            System.out.println("4: Delete Patient");
            System.out.println("5: View All Patient");
            System.out.println("6: Back\n");
            response = 0;

            while (response < 1 || response > 6) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    addPatient();
                } else if (response == 2) {
                    viewPatientDetails();
                } else if (response == 3) {
                    updatePatient();
                } else if (response == 4) {
                    deletePatient();
                } else if (response == 5) {
                    viewAllPatients();
                } else if (response == 6) {
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

    private void doctorManagement() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** CARS :: Administration Operation :: Doctor Management ***\n");
            System.out.println("1: Add Doctor");
            System.out.println("2: View Doctor Details");
            System.out.println("3: Update Doctor");
            System.out.println("4: Delete Doctor");
            System.out.println("5: View All Doctor");
            System.out.println("6: Leave Management");
            System.out.println("7: Back\n");
            response = 0;

            while (response < 1 || response > 7) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    addDoctor();
                } else if (response == 2) {
                    viewDoctorDetails();
                } else if (response == 3) {
                    updateDoctor();
                } else if (response == 4) {
                    deleteDoctor();
                } else if (response == 5) {
                    viewAllDoctors();
                } else if (response == 6){
                    manageDoctorLeave();
                }
                else if (response == 7) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 7) {
                break;
            }
        }
    }

    private void staffManagement() {

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** CARS :: Administration Operation :: Staff Management ***\n");
            System.out.println("1: Add Staff");
            System.out.println("2: View Staff Details");
            System.out.println("3: Update Staff");
            System.out.println("4: Delete Staff");
            System.out.println("5: View All Staff");
            System.out.println("6: Back\n");
            response = 0;

            while (response < 1 || response > 6) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    addStaff();
                } else if (response == 2) {
                    viewStaffDetails();
                } else if (response == 3) {
                    updateStaff();
                } else if (response == 4) {
                    deleteStaff();
                } else if (response == 5) {
                    viewAllStaffs();
                } else if (response == 6) {
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

    private void addPatient() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        currentPatientEntity = new PatientEntity();

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

    private void viewPatientDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** CARS :: Administration Operation :: View Patient Details ***\n");
        System.out.print("Enter Patient Identity Number> ");
        String idNumber = scanner.nextLine();

        try {
            PatientEntity patientEntity = patientEntityControllerRemote.retrievePatientByIdentityNumber(idNumber);
            System.out.printf("%8s%20s%20s%20s%20s%18s%20s%25s\n", "Patient ID", "Identity Number", "First Name", "Last Name", "Gender", "Age", "Phone", "Address");
            System.out.printf("%8s%22s%20s%20s%20s%18s%20s%25s\n", patientEntity.getPatientId().toString(), patientEntity.getIdentityNumber(), patientEntity.getFirstName(), patientEntity.getLastName(), patientEntity.getGender(), patientEntity.getAge(), patientEntity.getPhone(), patientEntity.getAddress());
        } catch (PatientNotFoundException ex) {
            System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }

    private void updatePatient() {
        Scanner scanner = new Scanner(System.in);
        String input;
        Integer response = 0;
        currentPatientEntity = new PatientEntity();
        System.out.println("*** CARS :: Administration Operation :: Update Patient ***\n");
        
        List<PatientEntity> allPatientEntities = patientEntityControllerRemote.retrieveAllPatients();
        int totalPatients = allPatientEntities.size();
        
        while(true){
            System.out.printf("%8s%25s%32s%32s\n", "Patient ID", "Identity Number", "First Name", "Last Name");
            for(PatientEntity patientEntity : allPatientEntities){
                System.out.printf("%8s%25s%32s%32s\n", patientEntity.getPatientId().toString(), patientEntity.getIdentityNumber()
                                    , patientEntity.getFirstName(), patientEntity.getLastName());
            }
            
            System.out.print("Enter Patient Id > ");
            response = scanner.nextInt();
            
            if(response >= 1 && response <= totalPatients){
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }

        try {
            PatientEntity patientEntity = patientEntityControllerRemote.retrievePatientByIdentityNumber(allPatientEntities.get(response - 1).getIdentityNumber());
            System.out.printf("%8s%25s%32s%32s%1s%50s%3s%8s%32s\n", "Patient ID", "Identity Number", "First Name", "Last Name", "Gender", "Password", "Age", "Phone", "Address");
            System.out.printf("%8s%25s%32s%32s%1s%50s%3s%8s%32s\n", patientEntity.getPatientId().toString()
                            , patientEntity.getIdentityNumber(), patientEntity.getFirstName()
                            , patientEntity.getLastName(), patientEntity.getGender()
                            , patientEntity.getPassword(), patientEntity.getAge()
                            , patientEntity.getPhone(), patientEntity.getAddress());
            scanner.nextLine();

            System.out.print("Enter First Name (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                patientEntity.setFirstName(input);
            }

            System.out.print("Enter Last Name (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                patientEntity.setLastName(input);
            }

            System.out.print("Enter Gender (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                patientEntity.setGender(input.charAt(0));
            }

            System.out.print("Enter Password (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                patientEntity.setPassword(input);
            }

            System.out.print("Enter Age (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                patientEntity.setAge(Integer.parseInt(input));
                scanner.nextLine();
            }

            System.out.print("Enter Phone (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                patientEntity.setPhone(input);
            }

            System.out.print("Enter Address (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                patientEntity.setAddress(input);
            }
            //bean validation
            Set<ConstraintViolation<PatientEntity>> errors = validator.validate(patientEntity);

            if (errors.isEmpty()) {
                patientEntityControllerRemote.updatePatient(patientEntity);
                System.out.println("Patient is successfully updated!");
            } else {
                System.out.println("Unable to update model due to the following validation errors: ");
                for (ConstraintViolation error : errors) {
                    System.out.println("Validation Error: " + error.getPropertyPath() + " - " + error.getInvalidValue() + ": " + error.getMessage());
                }
            }
        } catch (PatientNotFoundException ex) {
            System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
        }
    }

    private void deletePatient() {
        Scanner scanner = new Scanner(System.in);
        String input;
        Integer response = 0;
        currentPatientEntity = new PatientEntity();

        System.out.println("*** CARS :: Administration Operation :: Delete Patient ***\n");
        
        List<PatientEntity> allPatientEntities = patientEntityControllerRemote.retrieveAllPatients();
        int totalPatients = allPatientEntities.size();
        
        while(true){
            System.out.printf("%8s%25s%32s%32s\n", "Patient ID", "Identity Number", "First Name", "Last Name");
            for(PatientEntity patientEntity : allPatientEntities){
                System.out.printf("%8s%25s%32s%32s\n", patientEntity.getPatientId().toString(), patientEntity.getIdentityNumber()
                                    , patientEntity.getFirstName(), patientEntity.getLastName());
            }
            
            System.out.print("Enter Patient Id > ");
            response = scanner.nextInt();
            
            if(response >= 1 && response <= totalPatients){
                try{
                    PatientEntity patientEntity = patientEntityControllerRemote.retrievePatientByIdentityNumber(allPatientEntities.get(response -1 ).getIdentityNumber());
                    System.out.printf("%8s%25s%32s%32s%1s%50s%3s%8s%32s\n", "Patient ID", "Identity Number", "First Name", "Last Name", "Gender", "Password", "Age", "Phone", "Address");
                    System.out.printf("%8s%25s%32s%32s%1s%50s%3s%8s%32s\n", patientEntity.getPatientId().toString()
                                    , patientEntity.getIdentityNumber(), patientEntity.getFirstName()
                                    , patientEntity.getLastName(), patientEntity.getGender()
                                    , patientEntity.getPassword(), patientEntity.getAge()
                                    , patientEntity.getPhone(), patientEntity.getAddress());
                    scanner.nextLine();
                    
                    System.out.printf("Confirm Delete Patient %s %s (Patient Identity Number:" + patientEntity.getIdentityNumber() + ")" + "(Enter 'Y' to Delete)> ", patientEntity.getFirstName(), patientEntity.getLastName(), patientEntity.getPatientId(), patientEntity.getIdentityNumber());

                    input = scanner.nextLine().trim();

                    if (input.equals("Y")) {
                        try {
                            patientEntityControllerRemote.deletePatient(patientEntity.getIdentityNumber());
                            System.out.println("Patient deleted successfully!\n");
                        } catch (PatientNotFoundException ex) {
                            System.out.println("An error has occurred while deleting patient: " + ex.getMessage() + "\n");
                        }
                    } else {
                        System.out.println("Patient NOT deleted!\n");
                    }
                } catch (PatientNotFoundException ex){
                    System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
                }
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }
   
    }

    private void viewAllPatients() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** CARS :: Administration Operation :: View All Patients ***\n");

        List<PatientEntity> patientEntities = patientEntityControllerRemote.retrieveAllPatients();
        System.out.printf("%8s%25s%32s%32s%1s%50s%3s%8s%32s\n", "Patient ID", "Identity Number", "First Name", "Last Name", "Gender", "Password", "Age", "Phone", "Address");
   
        for (PatientEntity patientEntity : patientEntities) {
            System.out.printf("%8s%25s%32s%32s%1s%50s%3s%8s%32s\n", patientEntity.getPatientId().toString()
                                    , patientEntity.getIdentityNumber(), patientEntity.getFirstName()
                                    , patientEntity.getLastName(), patientEntity.getGender()
                                    , patientEntity.getPassword(), patientEntity.getAge()
                                    , patientEntity.getPhone(), patientEntity.getAddress());
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }

    private void addDoctor() {
        Scanner scanner = new Scanner(System.in);
        currentDoctorEntity = new DoctorEntity();

        System.out.println("*** CARS :: Administration Operation :: Add Doctor ***\n");
        System.out.print("Enter First Name> ");
        currentDoctorEntity.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter Last Name> ");
        currentDoctorEntity.setLastName(scanner.nextLine().trim());
        System.out.print("Enter Registration> ");
        currentDoctorEntity.setUserName(scanner.nextLine().trim());
        System.out.print("Enter Qualifications> ");
        currentDoctorEntity.setQualifications(scanner.nextLine().trim());
        
        Set<ConstraintViolation<DoctorEntity>> errors = validator.validate(currentDoctorEntity);
        
        if(errors.isEmpty()){
            try{
                Long doctorId = doctorEntityControllerRemote.createNewDoctor(currentDoctorEntity);
                System.out.println("Doctor " + doctorId + " has been created!");
            } catch (DoctorAlreadyExistException ex){
                System.out.println("An error has occurred while creating the new Patient!: The patient already exist\n");
            } catch (UnknownPersistenceException ex){
                System.out.println("An unknown error has occurred while creating a new Patient!: " + ex.getMessage() + "\n");
            }
        } else {
            System.out.println("Unable to create Doctor due to the following validation errors: ");
            for (ConstraintViolation error : errors) {
                System.out.println("Validation Error: " + error.getPropertyPath() + " - " + error.getInvalidValue() + ": " + error.getMessage());
            }
        }
    }

    private void viewDoctorDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** CARS :: Administration Operation :: View Doctor Details ***\n");
        System.out.print("Enter Doctor ID> ");
        Long doctorId = scanner.nextLong();

        try {
            DoctorEntity doctorEntity = doctorEntityControllerRemote.retrieveDoctorById(doctorId);
            System.out.printf("%8s%32s%32s%6s%20s\n", "Doctor ID", "First Name", "Last Name", "Registration", "Qualifications");
            System.out.printf("%8s%32s%32s%6s%20s\n", doctorEntity.getDoctorId().toString(), doctorEntity.getFirstName(), doctorEntity.getLastName(), doctorEntity.getRegistration(), doctorEntity.getQualifications());
        } catch (DoctorNotFoundException ex) {
            System.out.println("An error has occurred while retrieving doctor: " + ex.getMessage() + "\n");
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();

    }

    private void updateDoctor() {
        Scanner scanner = new Scanner(System.in);
        String input;
        Integer response = 0;
        currentDoctorEntity = new DoctorEntity();
        System.out.println("*** CARS :: Administration Operation :: Update Doctor ***\n");
        
        List<DoctorEntity> allDoctorEntities = doctorEntityControllerRemote.retrieveAllDoctors();
        int totalDoctors = allDoctorEntities.size();
        
        while(true){
            System.out.printf("%8s%32s%32s%6s%20s\n", "Doctor ID", "First Name", "Last Name", "Registration", "Qualification");
            for(DoctorEntity doctorEntity : allDoctorEntities){
                System.out.printf("%8s%32s%32s%6s%20s\n", doctorEntity.getDoctorId().toString()
                                , doctorEntity.getFirstName(), doctorEntity.getLastName()
                                , doctorEntity.getRegistration(), doctorEntity.getQualifications());
            }          
            System.out.print("Enter Doctor Id > ");
            response = scanner.nextInt();
            
            if(response >= 1 && response <= totalDoctors){
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }
        scanner.nextLine();
        
        try {
            DoctorEntity doctorEntity = doctorEntityControllerRemote.retrieveDoctorById(allDoctorEntities.get(response - 1).getDoctorId());
            System.out.printf("%8s%32s%32s%6s%20s\n", "Doctor ID", "First Name", "Last Name", "Registration", "Qualification");
            System.out.printf("%8s%32s%32s%6s%20s\n", doctorEntity.getDoctorId().toString()
                                , doctorEntity.getFirstName(), doctorEntity.getLastName()
                                , doctorEntity.getRegistration(), doctorEntity.getQualifications());
            System.out.print("Enter First Name (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                doctorEntity.setFirstName(input);
            }

            System.out.print("Enter Last Name (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                doctorEntity.setLastName(input);
            }

            System.out.print("Enter Registration (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                doctorEntity.setUserName(input);
            }

            System.out.print("Enter Qualifications (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                doctorEntity.setQualifications(input);
            }
            
            //bean validation
            Set<ConstraintViolation<DoctorEntity>> errors = validator.validate(doctorEntity);

            if (errors.isEmpty()) {
                doctorEntityControllerRemote.updateDoctor(doctorEntity);
                System.out.println("Doctor updated successfully!\n");
            } else {
                System.out.println("Unable to update car due to the following validation errors: ");
                for (ConstraintViolation error : errors) {
                    System.out.println("Validation Error: " + error.getPropertyPath() + " - " + error.getInvalidValue() + ": " + error.getMessage());
                }
            }      
        } catch (DoctorNotFoundException ex) {
            System.out.println("An error has occurred while retrieving doctor: " + ex.getMessage() + "\n");
        }
    }

    private void deleteDoctor() {       
        Scanner scanner = new Scanner(System.in);
        String input;
        Integer response = 0;
        currentDoctorEntity = new DoctorEntity();
        System.out.println("*** CARS :: Administration Operation :: Delete Doctor ***\n");
        
        List<DoctorEntity> allDoctorEntities = doctorEntityControllerRemote.retrieveAllDoctors();
        int totalDoctors = allDoctorEntities.size();
        
        while(true){
            System.out.printf("%8s%32s%32s\n", "Doctor ID", "First Name", "Last Name");
            for(DoctorEntity doctorEntity : allDoctorEntities){
                System.out.printf("%8s%32s%32s\n", doctorEntity.getDoctorId().toString()
                                , doctorEntity.getFirstName(), doctorEntity.getLastName());
            }          
            System.out.print("Enter Doctor Id to be Deleted> ");
            response = scanner.nextInt();
            
            if(response >= 1 && response <= totalDoctors){
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }
        scanner.nextLine();

        try {
            DoctorEntity doctorEntity = doctorEntityControllerRemote.retrieveDoctorById(allDoctorEntities.get(response - 1).getDoctorId());
            System.out.printf("%8s%32s%32s%6s%20s\n", "Doctor ID", "First Name", "Last Name", "Registration", "Qualification");
            System.out.printf("%8s%32s%32s%6s%20s\n", doctorEntity.getDoctorId().toString(), doctorEntity.getFirstName(), doctorEntity.getLastName(), doctorEntity.getRegistration(), doctorEntity.getQualifications());
            System.out.printf("Confirm Delete Doctor %s %s (Staff ID: %d) (Enter 'Y' to Delete)> ", doctorEntity.getFirstName(), doctorEntity.getLastName(), doctorEntity.getDoctorId());

            input = scanner.nextLine().trim();

            if (input.equals("Y")) {
                try {
                    doctorEntityControllerRemote.deleteDoctor(doctorEntity.getDoctorId());
                    System.out.println("Doctor deleted successfully!\n");
                } catch (DoctorNotFoundException ex) {
                    System.out.println("An error has occurred while deleting doctor: " + ex.getMessage() + "\n");
                }
            } else {
                System.out.println("Doctor NOT deleted!\n");
            }
        } catch (DoctorNotFoundException ex) {
            System.out.println("An error has occurred while retrieving doctor: " + ex.getMessage() + "\n");
        }
    }

    private void viewAllDoctors() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** CARS :: Administration Operation :: View All Doctors ***\n");

        List<DoctorEntity> doctorEntities = doctorEntityControllerRemote.retrieveAllDoctors();
        System.out.printf("%8s%32s%32s%6s%20s\n", "Doctor ID", "First Name", "Last Name", "Registration", "Qualification");

        doctorEntities.forEach((doctorEntity) -> {
            System.out.printf("%8s%32s%32s%6s%20s\n", doctorEntity.getDoctorId().toString(), doctorEntity.getFirstName(), doctorEntity.getLastName(), doctorEntity.getRegistration(), doctorEntity.getQualifications());
        });

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();

    }
    
    private void manageDoctorLeave(){
        Scanner scanner = new Scanner(System.in);
        String input;
        Integer response = 0;
        LeaveEntity newLeaveEntity = new LeaveEntity();
        Date date = new Date();
        currentDoctorEntity = new DoctorEntity();
        System.out.println("*** CARS :: Administration Operation :: Manage Doctor Leave ***\n");
        
        List<DoctorEntity> allDoctorEntities = doctorEntityControllerRemote.retrieveAllDoctors();
        int totalDoctors = allDoctorEntities.size();
        
        while(true){
            System.out.printf("%8s%32s%32s\n", "Doctor ID", "First Name", "Last Name");
            for(DoctorEntity doctorEntity : allDoctorEntities){
                System.out.printf("%8s%32s%32s\n", doctorEntity.getDoctorId().toString()
                                , doctorEntity.getFirstName(), doctorEntity.getLastName());
            }          
            System.out.print("Enter Doctor Id to be Deleted> ");
            response = scanner.nextInt();
            
            if(response >= 1 && response <= totalDoctors){        
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }
        scanner.nextLine();      
        System.out.println();
        while(true){
            System.out.print("Enter Date of Leave (eg. 2020-04-01) > ");
            String dateStr = scanner.nextLine();
            try{
                date = DateHelper.convertToDate(dateStr);
                newLeaveEntity.setLeaveDate(date);
                newLeaveEntity.setWeekNo(DateHelper.getWeekNo(date));
                Long leaveId = leaveEntityControllerRemote.createNewLeave(newLeaveEntity);
                System.out.println("New Leave "+ leaveId+ " Successfully created!\n");              
                break;
            } catch (InvalidDateTimeFormatException | LeaveApplicationException | UnknownPersistenceException | LeaveExistException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    private void addStaff() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** CARS :: Administration Operation :: Add Staff ***\n");
        
        currentStaffEntity = new StaffEntity();
        
        System.out.print("Enter First Name > ");
        currentStaffEntity.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter Last Name> ");
        currentStaffEntity.setLastName( scanner.nextLine().trim());
        System.out.print("Enter Username> ");      
        currentStaffEntity.setUsername(scanner.nextLine().trim());
        System.out.print("Enter Password> ");
        currentStaffEntity.setPassword(scanner.nextLine().trim());
        
        Set<ConstraintViolation<StaffEntity>> errors = validator.validate(currentStaffEntity);
        if(errors.isEmpty()){
            try{
                Long staffId = staffEntityControllerRemote.createNewStaff(currentStaffEntity);
                System.out.println("Staff " + staffId + " has been created!");
            } catch (StaffAlreadyExistException ex){
                System.out.println("An error has occurred while creating the new staff!: The staff already exist\n");
            } catch (UnknownPersistenceException ex){
                System.out.println("An unknown error has occurred while creating a new staff!: " + ex.getMessage() + "\n");
            }
        } else {
            System.out.println("Unable to create staff due to the following validation errors: ");
            for (ConstraintViolation error : errors) {
                System.out.println("Validation Error: " + error.getPropertyPath() + " - " + error.getInvalidValue() + ": " + error.getMessage());
            }
        }
    }

    private void viewStaffDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** CARS :: Administration Operation :: View Staff Details ***\n");
        System.out.print("Enter Staff ID> ");
        Long staffId = scanner.nextLong();

        try {
            StaffEntity staffEntity = staffEntityControllerRemote.retrieveStaffByStaffId(staffId);
            System.out.printf("%8s%16s%16s%16s%32s\n", "Staff ID", "First Name", "Last Name", "Username", "Password");
            System.out.printf("%8s%16s%16s%16s%32s\n", staffEntity.getStaffId().toString(), staffEntity.getFirstName(), staffEntity.getLastName(), staffEntity.getUsername(), staffEntity.getPassword());
        } catch (StaffNotFoundException ex) {
            System.out.println("An error has occurred while retrieving staff: " + ex.getMessage() + "\n");
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }

    private void updateStaff() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        String input;
        currentStaffEntity = null;

        System.out.println("*** CARS :: Administration Operation :: Update Staff ***\n");
        
        List<StaffEntity> allStaffEntities = staffEntityControllerRemote.retrieveAllStaffs();
        int totalStaff = allStaffEntities.size();
        
        while(true){
            System.out.printf("%8s%16s%16s%16s%32s\n", "Staff ID", "First Name", "Last Name", "Username", "Password");
            for(StaffEntity staffEntity : allStaffEntities){
                System.out.printf("%8s%16s%16s%16s%32s\n", staffEntity.getStaffId().toString(), staffEntity.getFirstName()
                        , staffEntity.getLastName(), staffEntity.getUsername(), staffEntity.getPassword());
            }
            System.out.print("Enter Staff ID> ");
            response = scanner.nextInt();
            if (response >= 1 && response <= totalStaff) {
                break;
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }

        try {
            currentStaffEntity = staffEntityControllerRemote.retrieveStaffByStaffId(allStaffEntities.get(response - 1).getStaffId());
            System.out.printf("%8s%16s%16s%16s%32s\n", "Staff ID", "First Name", "Last Name", "Username", "Password");
            System.out.printf("%8s%16s%16s%16s%32s\n", currentStaffEntity.getStaffId().toString(), currentStaffEntity.getFirstName(), 
                                currentStaffEntity.getLastName(), currentStaffEntity.getUsername(), currentStaffEntity.getPassword());
            scanner.nextLine();

            System.out.print("Enter First Name (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                currentStaffEntity.setFirstName(input);
            }

            System.out.print("Enter Last Name (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                currentStaffEntity.setLastName(input);
            }

            System.out.print("Enter Username (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                currentStaffEntity.setUsername(input);
            }

            System.out.print("Enter Password (blank if no change)> ");
            input = scanner.nextLine().trim();
            if (input.length() > 0) {
                currentStaffEntity.setPassword(input);
            }
            //bean validation
            Set<ConstraintViolation<StaffEntity>> errors = validator.validate(currentStaffEntity);
            if(errors.isEmpty()){
                staffEntityControllerRemote.updateStaff(currentStaffEntity);
                System.out.println("Staff updated successfully!\n");
            } else {
                System.out.println("Unable to update model due to the following validation errors: ");
                for (ConstraintViolation error : errors) {
                    System.out.println("Validation Error: " + error.getPropertyPath() + " - " + error.getInvalidValue() + ": " + error.getMessage());
                }
            }        
        } catch (StaffNotFoundException ex) {
            System.out.println("An error has occurred while retrieving staff: " + ex.getMessage() + "\n");
        }
    }

    private void deleteStaff() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        String responseStr = "";
        currentStaffEntity = null;
        
        List<StaffEntity> allStaffEntities = staffEntityControllerRemote.retrieveAllStaffs();
        int totalStaff = allStaffEntities.size();

        System.out.println("*** CARS :: Administration Operation :: Delete Staff ***\n");
        
        while(true){
            System.out.print("Enter Staff ID> ");
            response = scanner.nextInt();
            if (response >= 1 && response <= totalStaff){
                try{
                    currentStaffEntity = staffEntityControllerRemote.retrieveStaffByStaffId(allStaffEntities.get(response - 1).getStaffId());
                    System.out.printf("%8s%16s%16s%16s%32s\n", "Staff ID", "First Name", "Last Name", "Username", "Password");
                    System.out.printf("%8s%16s%16s%16s%32s\n", currentStaffEntity.getStaffId().toString(), currentStaffEntity.getFirstName(), 
                                        currentStaffEntity.getLastName(), currentStaffEntity.getUsername(), currentStaffEntity.getPassword());
                    scanner.nextLine();
                    
                    System.out.printf("Confirm Delete Staff %s %s (Staff ID: %d) (Enter 'Y' to Delete)> ", currentStaffEntity.getFirstName(), currentStaffEntity.getLastName(), currentStaffEntity.getStaffId());
                    responseStr = scanner.nextLine().trim();

                    if (responseStr.equals("Y")) {
                        break;
                    } else {
                        System.out.println("Staff is not deleted!\n");
                    }
                } catch (StaffNotFoundException ex){
                    System.out.println("An error has occurred while retrieving the staff: " + ex.getMessage() + "\n");
                }
            } else {
                System.out.println("Invalid option, please try again!\n");
            }
        }
        
        try {
            staffEntityControllerRemote.deleteStaff(currentStaffEntity.getStaffId());
            System.out.println("Model is successfully deleted!");
        } catch (StaffNotFoundException ex) {
            System.out.println("An error has occurred while deleting the staff: " + ex.getMessage() + "\n");
        }
    }

    private void viewAllStaffs() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** CARS :: Administration Operation :: View All Staffs ***\n");

        List<StaffEntity> staffEntities = staffEntityControllerRemote.retrieveAllStaffs();
        System.out.printf("%8s%16s%16s%16s%32s\n", "Staff ID", "First Name", "Last Name", "Username", "Password");

        for (StaffEntity staffEntity : staffEntities) {
            System.out.printf("%8s%16s%16s%16s%32s\n", staffEntity.getStaffId().toString()
                                , staffEntity.getFirstName(), staffEntity.getLastName(), staffEntity.getUsername(), staffEntity.getPassword());
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }
}
