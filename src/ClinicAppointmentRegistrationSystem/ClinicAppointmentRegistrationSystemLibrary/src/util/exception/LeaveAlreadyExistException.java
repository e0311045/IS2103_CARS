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
public class LeaveAlreadyExistException extends Exception {

    /**
     * Creates a new instance of <code>LeaveAlreadyExistException</code> without
     * detail message.
     */
    public LeaveAlreadyExistException() {
    }

    /**
     * Constructs an instance of <code>LeaveAlreadyExistException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public LeaveAlreadyExistException(String msg) {
        super(msg);
    }
}
