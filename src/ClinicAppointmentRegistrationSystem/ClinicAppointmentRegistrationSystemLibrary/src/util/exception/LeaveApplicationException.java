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
public class LeaveApplicationException extends Exception {

    /**
     * Creates a new instance of <code>LeaveApplicationException</code> without
     * detail message.
     */
    public LeaveApplicationException() {
    }

    /**
     * Constructs an instance of <code>LeaveApplicationException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LeaveApplicationException(String msg) {
        super(msg);
    }
}
