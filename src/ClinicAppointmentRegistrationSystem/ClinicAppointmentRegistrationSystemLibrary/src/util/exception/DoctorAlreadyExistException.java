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
public class DoctorAlreadyExistException extends Exception {

    /**
     * Creates a new instance of <code>DoctorAlreadyExistException</code>
     * without detail message.
     */
    public DoctorAlreadyExistException() {
    }

    /**
     * Constructs an instance of <code>DoctorAlreadyExistException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public DoctorAlreadyExistException(String msg) {
        super(msg);
    }
}