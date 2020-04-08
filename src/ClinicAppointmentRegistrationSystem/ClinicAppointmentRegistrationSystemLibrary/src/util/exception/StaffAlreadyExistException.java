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
public class StaffAlreadyExistException extends Exception {

    /**
     * Creates a new instance of <code>StaffAlreadyExistException</code> without
     * detail message.
     */
    public StaffAlreadyExistException() {
    }

    /**
     * Constructs an instance of <code>StaffAlreadyExistException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public StaffAlreadyExistException(String msg) {
        super(msg);
    }
}
