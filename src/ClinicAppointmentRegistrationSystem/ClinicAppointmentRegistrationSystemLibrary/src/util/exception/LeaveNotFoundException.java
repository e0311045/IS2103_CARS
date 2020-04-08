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
public class LeaveNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>LeaveNotFoundException</code> without
     * detail message.
     */
    public LeaveNotFoundException() {
    }

    /**
     * Constructs an instance of <code>LeaveNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LeaveNotFoundException(String msg) {
        super(msg);
    }
}
