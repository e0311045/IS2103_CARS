
package util.exception;

/**
 *
 * @author leege
 */
public class CreateAppointmentException extends Exception {

    /**
     * Creates a new instance of <code>AppointmentAlreadyExistException</code>
     * without detail message.
     */
    public CreateAppointmentException() {
    }

    /**
     * Constructs an instance of <code>AppointmentAlreadyExistException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateAppointmentException(String msg) {
        super(msg);
    }
}
