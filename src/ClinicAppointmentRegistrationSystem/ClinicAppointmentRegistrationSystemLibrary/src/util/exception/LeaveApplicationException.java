/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
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
