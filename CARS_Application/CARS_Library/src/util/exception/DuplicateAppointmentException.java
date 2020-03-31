/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author leege
 */
public class DuplicateAppointmentException extends Exception {

    /**
     * Creates a new instance of <code>DuplicateAppointmentException</code>
     * without detail message.
     */
    public DuplicateAppointmentException() {
    }

    /**
     * Constructs an instance of <code>DuplicateAppointmentException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public DuplicateAppointmentException(String msg) {
        super(msg);
    }
}
