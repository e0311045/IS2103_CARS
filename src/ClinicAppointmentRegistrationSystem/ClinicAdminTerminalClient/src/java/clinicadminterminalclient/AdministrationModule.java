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
import ejb.session.stateless.PatientEntityControllerRemote;
import ejb.session.stateless.StaffEntityControllerRemote;
import entity.StaffEntity;
import entity.PatientEntity;
import entity.DoctorEntity;
import java.util.List;
import java.util.Scanner;
import util.exception.DoctorNotFoundException;
import util.exception.PatientNotFoundException;
import util.exception.StaffNotFoundException;

public class AdministrationModule {

    private StaffEntityControllerRemote staffEntityControllerRemote;
    private DoctorEntityControllerRemote doctorEntityControllerRemote;
    private PatientEntityControllerRemote patientEntityControllerRemote;
    private RegistrationControllerRemote registrationControllerRemote;
    private StaffEntity currentStaffEntity;
    private PatientEntity currentPatientEntity;
    private DoctorEntity currentDoctorEntity;

    public AdministrationModule(StaffEntityControllerRemote staffEntityControllerRemote, DoctorEntityControllerRemote doctorEntityControllerRemote, PatientEntityControllerRemote patientEntityControllerRemote, RegistrationControllerRemote registrationControllerRemote) {
        this.staffEntityControllerRemote = staffEntityControllerRemote;
        this.doctorEntityControllerRemote = doctorEntityControllerRemote;
        this.patientEntityControllerRemote = patientEntityControllerRemote;
        this.registrationControllerRemote = registrationControllerRemote;
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
            System.out.println("6: Back\n");
            response = 0;

            while (response < 1 || response > 6) {
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
        newPatientEntity.setGender(scanner.nextLine().trim());
        System.out.print("Enter Security Code> ");
        newPatientEntity.setSecurityCode(scanner.nextLine().trim());
        System.out.print("Enter Age> ");
        newPatientEntity.setAge(scanner.nextInt());
        scanner.nextLine();
        System.out.print("Enter Phone> ");
        newPatientEntity.setPhone(scanner.nextLine().trim());
        System.out.print("Enter Address> ");
        newPatientEntity.setAddress(scanner.nextLine().trim());

        List<PatientEntity> patientEntities = patientEntityControllerRemote.retrieveAllPatients();
        newPatientEntity.setPatientId((long) patientEntities.size() + 1);

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
            System.out.printf("%8s%20s%20s%20s%20s%20s%20s%20s%20s\n", "Patient ID", "Identity Number", "First Name", "Last Name", "Gender", "Security Code", "Age", "Phone", "Address");
            System.out.printf("%8s%20s%20s%20s%20s%20s%20s%20s%20s\n", patientEntity.getPatientId().toString(), patientEntity.getIdentityNumber(), patientEntity.getFirstName(), patientEntity.getLastName(), patientEntity.getGender(), patientEntity.getSecurityCode(), patientEntity.getAge(), patientEntity.getPhone(), patientEntity.getAddress());
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
            patientEntity.setGender(input);
        }

        System.out.print("Enter Security Code (blank if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            patientEntity.setSecurityCode(input);
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
            System.out.printf("%8s%20s%20s%20s%20s%20s%20s%20s%20s\n", "Patient ID", "Identity Number", "First Name", "Last Name", "Gender", "Security Code", "Age", "Phone", "Address");
            System.out.printf("%8s%20s%20s%20s%20s%20s%20s%20s%20s\n", patientEntity.getPatientId().toString(), patientEntity.getIdentityNumber(), patientEntity.getFirstName(), patientEntity.getLastName(), patientEntity.getGender(), patientEntity.getSecurityCode(), patientEntity.getAge(), patientEntity.getPhone(), patientEntity.getAddress());
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
        System.out.printf("%8s%20s%20s%20s%20s%20s%20s%20s%20s\n", "Patient ID", "Identity Number", "First Name", "Last Name", "Gender", "Security Code", "Age", "Phone", "Address");

        for (PatientEntity patientEntity : patientEntities) {
            System.out.printf("%8s%20s%20s%20s%20s%20s%20s%20s%20s\n", patientEntity.getPatientId().toString(), patientEntity.getIdentityNumber(), patientEntity.getFirstName(), patientEntity.getLastName(), patientEntity.getGender(), patientEntity.getSecurityCode(), patientEntity.getAge(), patientEntity.getPhone(), patientEntity.getAddress());
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
        System.out.printf("%8s%20s%20s%20s%20s\n", "Staff ID", "First Name", "Last Name", "Registration", "Qualification");

        for (DoctorEntity doctorEntity : doctorEntities) {
            System.out.printf("%8s%20s%20s%20s%20s\n", doctorEntity.getDoctorId().toString(), doctorEntity.getFirstName(), doctorEntity.getLastName(), doctorEntity.getRegistration(), doctorEntity.getQualifications());
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();

    }

    private void addStaff() {
        Scanner scanner = new Scanner(System.in);
        StaffEntity newStaffEntity = new StaffEntity();

        System.out.println("*** CARS :: Administration Operation :: Add Staff ***\n");
        System.out.print("Enter First Name> ");
        newStaffEntity.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter Last Name> ");
        newStaffEntity.setLastName(scanner.nextLine().trim());
        System.out.print("Enter Username> ");
        newStaffEntity.setUsername(scanner.nextLine().trim());
        System.out.print("Enter Password> ");
        newStaffEntity.setPassword(scanner.nextLine().trim());

        List<StaffEntity> staffEntities = staffEntityControllerRemote.retrieveAllStaffs();
        newStaffEntity.setStaffId((long) staffEntities.size() + 1);

        newStaffEntity = staffEntityControllerRemote.createNewStaff(newStaffEntity);
        System.out.println("New staff created successfully!: " + newStaffEntity.getStaffId() + "\n");
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
