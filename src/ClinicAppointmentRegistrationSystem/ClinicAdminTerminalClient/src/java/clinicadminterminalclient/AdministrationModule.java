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
import entity.AppointmentEntity;
import entity.StaffEntity;
import entity.PatientEntity;
import entity.DoctorEntity;
import entity.LeaveEntity;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import util.date.DateHelper;
import util.exception.DoctorNotFoundException;
import util.exception.InvalidDateTimeFormatException;
import util.exception.LeaveAlreadyExistException;
import util.exception.LeaveApplicationException;
import util.exception.LeaveDeniedException;
import util.exception.MaximumLeaveAppliedException;
import util.exception.PatientNotFoundException;
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
        PatientEntity newPatientEntity = new PatientEntity();

        System.out.println("*** CARS :: Administration Operation :: Add Patient ***\n");
        System.out.print("Enter Identity Number> ");
        newPatientEntity.setIdentityNumber(scanner.nextLine().trim());
        System.out.print("Enter First Name> ");
        newPatientEntity.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter Last Name> ");
        newPatientEntity.setLastName(scanner.nextLine().trim());
        System.out.print("Enter Gender> ");
        String gender = scanner.nextLine();
        newPatientEntity.setGender(gender.charAt(0));
        System.out.print("Enter Password> ");
        newPatientEntity.setPassword(scanner.nextLine().trim());
        System.out.print("Enter Age> ");
        newPatientEntity.setAge(scanner.nextInt());
        scanner.nextLine();
        System.out.print("Enter Phone> ");
        newPatientEntity.setPhone(scanner.nextLine().trim());
        System.out.print("Enter Address> ");
        newPatientEntity.setAddress(scanner.nextLine().trim());

        List<PatientEntity> patientEntities = patientEntityControllerRemote.retrieveAllPatients();

        newPatientEntity = patientEntityControllerRemote.createNewPatient(newPatientEntity);
        System.out.println("New patient created successfully!: " + newPatientEntity.getIdentityNumber() + "\n");

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
        PatientEntity patientEntity = null;

        System.out.println("*** CARS :: Administration Operation :: Update Patient ***\n");
        System.out.print("Enter Patient Identity Number> ");
        String idNumber = scanner.nextLine();

        try {
            patientEntity = patientEntityControllerRemote.retrievePatientByIdentityNumber(idNumber);
            System.out.printf("%8s%20s%20s%20s%20s%20s%20s%20s%20s\n", "Patient ID", "Identity Number", "First Name", "Last Name", "Gender", "Password", "Age", "Phone", "Address");
            System.out.printf("%8s%20s%20s%20s%20s%20s%20s%20s%20s\n", patientEntity.getPatientId().toString(), patientEntity.getIdentityNumber(), patientEntity.getFirstName(), patientEntity.getLastName(), patientEntity.getGender(), patientEntity.getPassword(), patientEntity.getAge(), patientEntity.getPhone(), patientEntity.getAddress());
        } catch (PatientNotFoundException ex) {
            System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
        }

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

        patientEntityControllerRemote.updatePatient(patientEntity);
        System.out.println("Patient updated successfully!\n");
    }

    private void deletePatient() {
        Scanner scanner = new Scanner(System.in);
        String input;
        PatientEntity patientEntity = null;

        System.out.println("*** CARS :: Administration Operation :: Delete Patient ***\n");
        System.out.print("Enter Patient Identity Number> ");
        String idNumber = scanner.nextLine();;

        try {
            patientEntity = patientEntityControllerRemote.retrievePatientByIdentityNumber(idNumber);
            System.out.printf("%8s%20s%20s%20s%20s%20s%20s%20s%20s\n", "Patient ID", "Identity Number", "First Name", "Last Name", "Gender", "Password", "Age", "Phone", "Address");
            System.out.printf("%8s%20s%20s%20s%20s%20s%20s%20s%20s\n", patientEntity.getPatientId().toString(), patientEntity.getIdentityNumber(), patientEntity.getFirstName(), patientEntity.getLastName(), patientEntity.getGender(), patientEntity.getPassword(), patientEntity.getAge(), patientEntity.getPhone(), patientEntity.getAddress());
        } catch (PatientNotFoundException ex) {
            System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
        }

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
    }

    private void viewAllPatients() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** CARS :: Administration Operation :: View All Patients ***\n");

        List<PatientEntity> patientEntities = patientEntityControllerRemote.retrieveAllPatients();
        System.out.printf("%8s%20s%20s%20s%20s%20s%20s%20s%20s\n", "Patient ID", "Identity Number", "First Name", "Last Name", "Gender", "Password", "Age", "Phone", "Address");

        for (PatientEntity patientEntity : patientEntities) {
            System.out.printf("%8s%20s%20s%20s%20s%20s%20s%20s%20s\n", patientEntity.getPatientId().toString(), patientEntity.getIdentityNumber(), patientEntity.getFirstName(), patientEntity.getLastName(), patientEntity.getGender(), patientEntity.getPassword(), patientEntity.getAge(), patientEntity.getPhone(), patientEntity.getAddress());
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }

    private void addDoctor() {
        Scanner scanner = new Scanner(System.in);
        DoctorEntity newDoctorEntity = new DoctorEntity();

        System.out.println("*** CARS :: Administration Operation :: Add Doctor ***\n");
        System.out.print("Enter First Name> ");
        newDoctorEntity.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter Last Name> ");
        newDoctorEntity.setLastName(scanner.nextLine().trim());
        System.out.print("Enter Registration> ");
        newDoctorEntity.setUserName(scanner.nextLine().trim());
        System.out.print("Enter Qualifications> ");
        newDoctorEntity.setQualifications(scanner.nextLine().trim());

        List<DoctorEntity> doctorEntities = doctorEntityControllerRemote.retrieveAllDoctors();
        newDoctorEntity.setDoctorId((long) doctorEntities.size() + 1);

        newDoctorEntity = doctorEntityControllerRemote.createNewDoctor(newDoctorEntity);
        System.out.println("New doctor created successfully!: " + newDoctorEntity.getDoctorId() + "\n");
    }

    private void viewDoctorDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** CARS :: Administration Operation :: View Doctor Details ***\n");
        System.out.print("Enter Doctor ID> ");
        Long doctorId = scanner.nextLong();

        try {
            DoctorEntity doctorEntity = doctorEntityControllerRemote.retrieveDoctorById(doctorId);
            System.out.printf("%8s%20s%20s%20s%20s\n", "Doctor ID", "First Name", "Last Name", "Registration", "Qualifications");
            System.out.printf("%8s%20s%20s%20s%20s\n", doctorEntity.getDoctorId().toString(), doctorEntity.getFirstName(), doctorEntity.getLastName(), doctorEntity.getRegistration(), doctorEntity.getQualifications());
        } catch (DoctorNotFoundException ex) {
            System.out.println("An error has occurred while retrieving doctor: " + ex.getMessage() + "\n");
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();

    }

    private void updateDoctor() {

        Scanner scanner = new Scanner(System.in);
        String input;
        DoctorEntity doctorEntity = null;

        System.out.println("*** CARS :: Administration Operation :: Update Doctor ***\n");
        System.out.print("Enter Doctor ID> ");
        Long doctorId = scanner.nextLong();

        try {
            doctorEntity = doctorEntityControllerRemote.retrieveDoctorById(doctorId);
            System.out.printf("%8s%20s%20s%20s%20s\n", "Doctor ID", "First Name", "Last Name", "Registration", "Qualification");
            System.out.printf("%8s%20s%20s%20s%20s\n", doctorEntity.getDoctorId().toString(), doctorEntity.getFirstName(), doctorEntity.getLastName(), doctorEntity.getRegistration(), doctorEntity.getQualifications());
        } catch (DoctorNotFoundException ex) {
            System.out.println("An error has occurred while retrieving doctor: " + ex.getMessage() + "\n");
        }

        scanner.nextLine();

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

        doctorEntityControllerRemote.updateDoctor(doctorEntity);
        System.out.println("Doctor updated successfully!\n");

    }

    private void deleteDoctor() {
        Scanner scanner = new Scanner(System.in);
        String input;
        DoctorEntity doctorEntity = null;

        System.out.println("*** CARS :: Administration Operation :: Delete Doctor ***\n");
        System.out.print("Enter Doctor ID> ");
        Long doctorId = scanner.nextLong();

        try {
            doctorEntity = doctorEntityControllerRemote.retrieveDoctorById(doctorId);
            System.out.printf("%8s%20s%20s%20s%20s\n", "Doctor ID", "First Name", "Last Name", "Registration", "Qualification");
            System.out.printf("%8s%20s%20s%20s%20s\n", doctorEntity.getDoctorId().toString(), doctorEntity.getFirstName(), doctorEntity.getLastName(), doctorEntity.getRegistration(), doctorEntity.getQualifications());
        } catch (DoctorNotFoundException ex) {
            System.out.println("An error has occurred while retrieving doctor: " + ex.getMessage() + "\n");
        }

        scanner.nextLine();

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

    }

    private void viewAllDoctors() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** CARS :: Administration Operation :: View All Doctors ***\n");

        List<DoctorEntity> doctorEntities = doctorEntityControllerRemote.retrieveAllDoctors();
        System.out.printf("%8s%20s%20s%20s%20s\n", "Doctor ID", "First Name", "Last Name", "Registration", "Qualification");

        doctorEntities.forEach((doctorEntity) -> {
            System.out.printf("%8s%20s%20s%20s%20s\n", doctorEntity.getDoctorId().toString(), doctorEntity.getFirstName(), doctorEntity.getLastName(), doctorEntity.getRegistration(), doctorEntity.getQualifications());
        });

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();

    }
    
    private void manageDoctorLeave(){
        Scanner scanner = new Scanner(System.in);
        String dateStr;
        Date inputDate = new Date();
        Integer response = 0;
        LeaveEntity newLeaveEntity = new LeaveEntity();

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
            try{
                System.out.print("Enter Date of Leave (eg. 2020-04-01) > ");
                dateStr = scanner.nextLine();
                inputDate = DateHelper.convertToDate(dateStr);
                break;
            } catch (InvalidDateTimeFormatException ex) {
                System.out.println(ex.getMessage());
            }
        }
        try{
            Calendar now = Calendar.getInstance();
            newLeaveEntity.setLeaveDate(inputDate);
            newLeaveEntity.setWeekNo(DateHelper.getWeekNo(inputDate));
            Long leaveId = leaveEntityControllerRemote.createNewLeave(newLeaveEntity, now.getTime());
            System.out.println("New Leave "+ leaveId+ " Successfully created!\n");              
            } catch (LeaveApplicationException | LeaveAlreadyExistException ex){
                System.out.println(ex.getMessage());
            }
    }

    private void addStaff() {
        Scanner scanner = new Scanner(System.in);
        StaffEntity newStaffEntity = new StaffEntity();

        System.out.println("*** CARS :: Administration Operation :: Add Staff ***\n");
        while(true){
            System.out.print("Enter First Name> ");
            String firstName = scanner.nextLine().trim();
            if(firstName.length()>32){
                System.out.println("Please keep First Name to 32 characters");
            }
            else {
                newStaffEntity.setFirstName(firstName);
                break;
            }     
        }
        while(true){
            System.out.print("Enter Last Name> ");
            String lastName = scanner.nextLine().trim();
            if(lastName.length()>32){
                System.out.println("Please keep Last Name to 32 characters");
            }
            else {
                newStaffEntity.setLastName(lastName);
                break;
            }     
        }
        while(true){
            System.out.print("Enter Username> ");      
            String userName = scanner.nextLine().trim();
            if(userName.length()>32){
                System.out.println("Please keep User Name to 32 characters");
            }
            else {
                newStaffEntity.setUsername(userName);
                break;
            }     
        }
        while(true){
            System.out.print("Enter Password> ");
            String password = scanner.nextLine().trim();
            if(password.length()>32){
                System.out.println("Please keep password to 32 characters");
            }
            else {
                newStaffEntity.setPassword(password);
                break;
            }     
        }
        List<StaffEntity> staffEntities = staffEntityControllerRemote.retrieveAllStaffs();
        
        boolean hasUser = false;
        for(StaffEntity se : staffEntities){
            if(se.getUsername().equals(newStaffEntity.getUsername())){
                System.out.println("Staff User Already Exist");
                hasUser = true;
                break;
            }
        }
        if(!hasUser){
            newStaffEntity = staffEntityControllerRemote.createNewStaff(newStaffEntity);
            System.out.println("New staff created successfully!: " + newStaffEntity.getStaffId() + "\n");
        }
    }

    private void viewStaffDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** CARS :: Administration Operation :: View Staff Details ***\n");
        System.out.print("Enter Staff ID> ");
        Long staffId = scanner.nextLong();

        try {
            StaffEntity staffEntity = staffEntityControllerRemote.retrieveStaffByStaffId(staffId);
            System.out.printf("%8s%20s%20s%20s%20s\n", "Staff ID", "First Name", "Last Name", "Username", "Password");
            System.out.printf("%8s%20s%20s%20s%20s\n", staffEntity.getStaffId().toString(), staffEntity.getFirstName(), staffEntity.getLastName(), staffEntity.getUsername(), staffEntity.getPassword());
        } catch (StaffNotFoundException ex) {
            System.out.println("An error has occurred while retrieving staff: " + ex.getMessage() + "\n");
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }

    private void updateStaff() {
        Scanner scanner = new Scanner(System.in);
        String input;
        StaffEntity staffEntity = null;

        System.out.println("*** CARS :: Administration Operation :: Update Staff ***\n");
        System.out.print("Enter Staff ID> ");
        Long staffId = scanner.nextLong();

        try {
            staffEntity = staffEntityControllerRemote.retrieveStaffByStaffId(staffId);
            System.out.printf("%8s%20s%20s%20s%20s\n", "Staff ID", "First Name", "Last Name", "Username", "Password");
            System.out.printf("%8s%20s%20s%20s%20s\n", staffEntity.getStaffId().toString(), staffEntity.getFirstName(), staffEntity.getLastName(), staffEntity.getUsername(), staffEntity.getPassword());
        } catch (StaffNotFoundException ex) {
            System.out.println("An error has occurred while retrieving staff: " + ex.getMessage() + "\n");
        }

        scanner.nextLine();

        System.out.print("Enter First Name (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            staffEntity.setFirstName(input);
        }

        System.out.print("Enter Last Name (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            staffEntity.setLastName(input);
        }

        System.out.print("Enter Username (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            staffEntity.setUsername(input);
        }

        System.out.print("Enter Password (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            staffEntity.setPassword(input);
        }

        staffEntityControllerRemote.updateStaff(staffEntity);
        System.out.println("Staff updated successfully!\n");
    }

    private void deleteStaff() {
        Scanner scanner = new Scanner(System.in);
        String input;
        StaffEntity staffEntity = null;

        System.out.println("*** CARS :: Administration Operation :: Delete Staff ***\n");
        System.out.print("Enter Staff ID> ");
        Long staffId = scanner.nextLong();

        try {
            staffEntity = staffEntityControllerRemote.retrieveStaffByStaffId(staffId);
            System.out.printf("%8s%20s%20s%20s%20s\n", "Staff ID", "First Name", "Last Name", "Username", "Password");
            System.out.printf("%8s%20s%20s%20s%20s\n", staffEntity.getStaffId().toString(), staffEntity.getFirstName(), staffEntity.getLastName(), staffEntity.getUsername(), staffEntity.getPassword());
        } catch (StaffNotFoundException ex) {
            System.out.println("An error has occurred while retrieving staff: " + ex.getMessage() + "\n");
        }

        scanner.nextLine();

        System.out.printf("Confirm Delete Staff %s %s (Staff ID: %d) (Enter 'Y' to Delete)> ", staffEntity.getFirstName(), staffEntity.getLastName(), staffEntity.getStaffId());

        input = scanner.nextLine().trim();

        if (input.equals("Y")) {
            try {
                staffEntityControllerRemote.deleteStaff(staffEntity.getStaffId());
                System.out.println("Staff deleted successfully!\n");
            } catch (StaffNotFoundException ex) {
                System.out.println("An error has occurred while deleting staff: " + ex.getMessage() + "\n");
            }
        } else {
            System.out.println("Staff NOT deleted!\n");
        }
    }

    private void viewAllStaffs() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** CARS :: Administration Operation :: View All Staffs ***\n");

        List<StaffEntity> staffEntities = staffEntityControllerRemote.retrieveAllStaffs();
        System.out.printf("%8s%20s%20s%20s%20s\n", "Staff ID", "First Name", "Last Name", "Username", "Password");

        for (StaffEntity staffEntity : staffEntities) {
            System.out.printf("%8s%20s%20s%20s%20s\n", staffEntity.getStaffId().toString(), staffEntity.getFirstName(), staffEntity.getLastName(), staffEntity.getUsername(), staffEntity.getPassword());
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }
}
