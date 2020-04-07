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
public class LeaveExistException extends Exception {

    /**
     * Creates a new instance of <code>LeaveExistException</code> without detail
     * message.
     */
    public LeaveExistException() {
    }

    /**
     * Constructs an instance of <code>LeaveExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LeaveExistException(String msg) {
        super(msg);
    }
}
