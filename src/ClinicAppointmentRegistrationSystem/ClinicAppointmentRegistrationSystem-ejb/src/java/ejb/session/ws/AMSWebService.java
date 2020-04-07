package ejb.session.ws;

import ejb.session.stateless.AppointmentEntityControllerRemote;
import ejb.session.stateless.DoctorEntityControllerRemote;
import ejb.session.stateless.PatientEntityControllerRemote;
import entity.AppointmentEntity;
import entity.DoctorEntity;
import entity.PatientEntity;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.AppointmentNotFoundException;
import util.exception.CreateAppointmentException;
import util.exception.DoctorNotFoundException;
import util.exception.InvalidLoginException;
import util.exception.PatientAlreadyExistException;
import util.exception.PatientNotFoundException;
import util.exception.UnknownPersistenceException;


@WebService(serviceName = "AMSWebService")
@Stateless()
public class AMSWebService {
    
    @EJB
    private PatientEntityControllerRemote patientEntityControllerRemote;
    
    @EJB
    private DoctorEntityControllerRemote doctorEntityControllerRemote;
    
    @EJB
    private AppointmentEntityControllerRemote appointmentEntityControllerRemote; 
   
    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private Validator validator = validatorFactory.getValidator();
    
    @WebMethod(operationName = "registerPatient")
    public String registerPatient(@WebParam(name = "identityNumber") String identityNumber, 
            @WebParam(name = "password") String password,
            @WebParam(name = "firstName") String firstName,
            @WebParam(name = "lastName") String lastName,
            @WebParam(name = "gender") char gender,
            @WebParam(name = "age") Integer age,
            @WebParam(name = "phone") String phone,
            @WebParam(name = "address") String address)  
    {
        System.out.println("*** AMS Client :: Register ***\n");
        Scanner scanner = new Scanner(System.in);
        PatientEntity newPatient = new PatientEntity();
        
        System.out.println("Enter Identity Number> ");
        System.out.println("Enter Security Code> ");
        System.out.println("Enter First Name> ");
        System.out.println("Enter Last Name> ");
        System.out.println("Enter Gender> ");
        System.out.println("Enter Age> ");
        System.out.println("Enter Phone> ");
        System.out.println("Enter Address> ");
        newPatient.setIdentityNumber(identityNumber);
        newPatient.setPassword(password);
        newPatient.setFirstName(firstName);
        newPatient.setLastName(lastName);
        newPatient.setGender(gender);
        newPatient.setAge(age);
        newPatient.setPhone(phone);
        newPatient.setAddress(address);
        
        Set<ConstraintViolation<PatientEntity>> errors = validator.validate(newPatient);
        
        if(errors.isEmpty()){
            try{
                Long patientId = patientEntityControllerRemote.createNewPatient(newPatient);
                
            } catch (PatientAlreadyExistException ex){
                System.out.println("An error has occurred while creating the new Patient!: The patient already exist");
            } catch (UnknownPersistenceException ex){
                System.out.println("An unknown error has occurred while creating a new Patient!: " + ex.getMessage());
            }
        } else {
            String msg = "Unable to create Patient due to the following validation errors: \n";
            for (ConstraintViolation error : errors) {
                msg += "Validation Error: " + error.getPropertyPath() + " - " + error.getInvalidValue() + ": " + error.getMessage() +"\n";
            }
            return msg;
        }
        return "Registration is successful!";
    }

    @WebMethod(operationName = "doLogin")
    public String doLogin(@WebParam(name = "identityNumber") String identityNumber, @WebParam(name = "password") String password) throws InvalidLoginException {
        
        System.out.println("*** AMS Client :: Login ***\n");
        System.out.println("Enter Identity Number> ");
        System.out.println("Enter Password> ");
        PatientEntity currentPatientEntity = new PatientEntity();
        
        try {
            currentPatientEntity = patientEntityControllerRemote.patientLogin(identityNumber, password);
            //System.out.println("Login successful!\n");
        } catch (InvalidLoginException ex) {
            System.out.println("Invalid login: " + ex.getMessage() + "\n");
            throw new InvalidLoginException();
        }
        
        System.out.println("*** AMS Client :: Main ***\n");    
        System.out.println("You are login as " + currentPatientEntity.getFirstName() + " " + currentPatientEntity.getLastName() + "\n");
        System.out.println("1: View Appointments");
        System.out.println("2: Add Appointment");
        System.out.println("3: Cancel Appointment");
        System.out.println("4: Logout\n");
        
         return "Login successful";
    }

    @WebMethod(operationName = "viewAppointments")
    public List<AppointmentEntity> viewAppointments(@WebParam(name = "identityNumber") String identityNumber, @WebParam(name = "securityCode") String securityCode) throws InvalidLoginException {

        System.out.println("*** AMS Client :: View Appointments ***\n");
        
        System.out.println("Enter Patient Identity Number> ");
        
        PatientEntity currentPatientEntity = new PatientEntity();
         
        try {
                currentPatientEntity = patientEntityControllerRemote.patientLogin(identityNumber, securityCode);
                System.out.println("Login successful!\n");
            } catch (InvalidLoginException ex) {
                System.out.println("Invalid login: " + ex.getMessage() + "\n");
                throw new InvalidLoginException();
            }

        try {
            currentPatientEntity = patientEntityControllerRemote.retrievePatientByIdentityNumber(identityNumber);      
        } catch(PatientNotFoundException ex) {
            System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
        }
        System.out.println("Appointments:");
         
        return appointmentEntityControllerRemote.retrieveAllAppointments();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addAppointment")
    public AppointmentEntity addAppointment(@WebParam(name = "identityNumber") String identityNumber, @WebParam(name = "password") String password, @WebParam(name = "doctorId") long doctorId, @WebParam(name = "inputDate") String inputDate, @WebParam(name = "inputTime") String inputTime) throws CreateAppointmentException {
        
        System.out.println("*** AMS Client :: Add Appointment ***\n");
        AppointmentEntity newAppointmentEntity = new AppointmentEntity();
        Date currentDate = new Date();
        DoctorEntity currentDoctorEntity = new DoctorEntity();
        
        PatientEntity currentPatientEntity = new PatientEntity();
         
         try {
            currentPatientEntity = patientEntityControllerRemote.retrievePatientByIdentityNumber(identityNumber);      
        } catch(PatientNotFoundException ex) {
            System.out.println("An error has occurred while retrieving patient: " + ex.getMessage() + "\n");
        }
        
        List<DoctorEntity> doctorEntities = doctorEntityControllerRemote.retrieveAllDoctors();  
        try {
            currentDoctorEntity = doctorEntityControllerRemote.retrieveDoctorById(doctorId);      
        } catch(DoctorNotFoundException ex) {
            System.out.println("An error has occurred while retrieving doctor: " + ex.getMessage() + "\n");
        }
        
        try {
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(inputDate);
                currentDate = sdf.parse(sdf.format(new Date()));

                if (date.after(currentDate)) {
                    newAppointmentEntity.setAppointmentDate(date);
                    
                    Calendar cal = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        String availability[] = new String[16];

        long ONE_MINUTE_IN_MILLIS = 60000;
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 00);
        
        long t = cal.getTimeInMillis();
        Date tempTime = new Date(t);
        DateFormat df = new SimpleDateFormat("HH:mm"); // dd-MM-yy date
        
        for (int i = 0; i < 16; i ++) { // an array of time slots from 0900 to 1600
            availability[i] = df.format(tempTime);
            //System.out.println(availability[i]);
            t += 30 * ONE_MINUTE_IN_MILLIS;
            tempTime = new Date(t);
        }
        
        long timeToday = today.getTimeInMillis();
        Date dateToday = new Date(timeToday);

        //availability
        System.out.println("Availability for " + currentDoctorEntity.getFirstName() + " " + currentDoctorEntity.getLastName() + " on " + inputDate + ":");
        List<AppointmentEntity> appointments = appointmentEntityControllerRemote.retrieveAllAppointments();
        if (appointments != null) {
            // for loop to keep track of 30 mins interval
            for (int i = 0; i < 16; i++) { // i == 16 so it will only stop at 4.30pm
            for (AppointmentEntity appointment: appointments) { // date check
                // if date compare == 0, time not equal, print time
                Date temp = appointment.getAppointmentTime();
                String stringTime = df.format(temp);
                 if (date.compareTo(appointment.getAppointmentDate()) == 0 && stringTime.equals(availability[i]) && (Objects.equals(appointment.getDoctor().getDoctorId(), doctorId))) {
                     availability[i] = "";
            }
            }
        }
        }
        
        
        for (int i = 0; i < 16; i ++) { // print availabililty from array
            if (availability[i].equals(""))
                System.out.print("");
            else
            System.out.print(availability[i] + " ");
        }

        try {
                DateFormat formatter = new SimpleDateFormat("HH:mm");
                Date time = formatter.parse(inputTime);
                newAppointmentEntity.setAppointmentTime(time);

            } catch (ParseException ex) {
                System.out.println("An error has occurred while retrieving date: " + ex.getMessage() + "\n");
            }
        
            newAppointmentEntity.setAppointmentId((long)appointments.size() + 1);
                //add appointment
                try{
                    appointmentEntityControllerRemote.createAppointment(newAppointmentEntity, currentPatientEntity.getIdentityNumber(), doctorId);
                    System.out.println("Appointment: " + currentPatientEntity.getFirstName() + " " + currentPatientEntity.getLastName() + " and " + currentDoctorEntity.getFirstName() + " " + currentDoctorEntity.getLastName() + " at " + inputTime + " on " + inputDate + " has been added.");
                    System.out.println();
                    

                } catch (CreateAppointmentException ex) {
                    System.out.println(ex.getMessage() + "Appointment can only be booked at least one day in advanced!");
                }

            }
        } catch (ParseException ex) {
                System.out.println("An error has occurred while retrieving date: " + ex.getMessage() + "\n");
            } catch (UnknownPersistenceException ex){
                System.out.println(ex.getMessage());
            }
        return newAppointmentEntity;
    }
    /**
     * Web service operation
     */
    @WebMethod(operationName = "cancelAppointment")
    public String cancelAppointment(@WebParam(name = "appointmentId") long appId, @WebParam(name = "identityNumber") String identityNumber, @WebParam(name = "securityCode") String securityCode) throws InvalidLoginException {
        
         System.out.println("*** AMS Client :: Cancel Appointment ***\n");
        PatientEntity currentPatientEntity = new PatientEntity();
         
        try {
                currentPatientEntity = patientEntityControllerRemote.patientLogin(identityNumber, securityCode);
                System.out.println("Login successful!\n");
            } catch (InvalidLoginException ex) {
                System.out.println("Invalid login: " + ex.getMessage() + "\n");
                throw new InvalidLoginException();
            }
        
        System.out.println("*** CARS :: Appointment Operation :: Cancel Appointment ***\n");
        
        System.out.println("Appointments:");
        // list appointment
        List<AppointmentEntity> appointmentEntities = appointmentEntityControllerRemote.retrieveAllAppointments();
        System.out.printf("%s%s%s%s\n", "Id|", "Date      |", "Time  |", "Doctor" );
        
        DateFormat df = new SimpleDateFormat("HH:mm");
        DateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
        
        if (appointmentEntities != null) {
        for (AppointmentEntity appointmentEntity:appointmentEntities)
        {
            if (appointmentEntity.getPatient().getIdentityNumber().equals(currentPatientEntity.getIdentityNumber())) {
            System.out.printf("%s%s%s%s\n", appointmentEntity.getAppointmentId().toString(), "| " + datef.format(appointmentEntity.getAppointmentDate()), "| " + df.format(appointmentEntity.getAppointmentTime()), "| " + appointmentEntity.getDoctor().getFirstName() + " " + appointmentEntity.getDoctor().getLastName());
            }
        }
        
        System.out.println(); 
        System.out.print("Enter Appointment Id> ");
        
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
        
        return "Appointment cancelled";
    }
    
}
