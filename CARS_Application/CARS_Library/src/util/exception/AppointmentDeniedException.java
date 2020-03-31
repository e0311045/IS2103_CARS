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
public class AppointmentDeniedException extends Exception {

    /**
     * Creates a new instance of <code>AppointmentDeniedException</code> without
     * detail message.
     */
    public AppointmentDeniedException() {
    }

    /**
     * Constructs an instance of <code>AppointmentDeniedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public AppointmentDeniedException(String msg) {
        super(msg);
    }
}
