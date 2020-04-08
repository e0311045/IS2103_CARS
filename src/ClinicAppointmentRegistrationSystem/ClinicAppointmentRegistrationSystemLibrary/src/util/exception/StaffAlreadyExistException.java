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
