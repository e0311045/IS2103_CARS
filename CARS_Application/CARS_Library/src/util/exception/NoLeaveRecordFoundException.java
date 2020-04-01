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
public class NoLeaveRecordFoundException extends Exception {

    /**
     * Creates a new instance of <code>NoLeaveRecordFoundException</code>
     * without detail message.
     */
    public NoLeaveRecordFoundException() {
    }

    /**
     * Constructs an instance of <code>NoLeaveRecordFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public NoLeaveRecordFoundException(String msg) {
        super(msg);
    }
}
