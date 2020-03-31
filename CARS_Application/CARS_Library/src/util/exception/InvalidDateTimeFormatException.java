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
public class InvalidDateTimeFormatException extends Exception {

    /**
     * Creates a new instance of <code>InvalidDateTimeFormatException</code>
     * without detail message.
     */
    public InvalidDateTimeFormatException() {
    }

    /**
     * Constructs an instance of <code>InvalidDateTimeFormatException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidDateTimeFormatException(String msg) {
        super(msg);
    }
}
