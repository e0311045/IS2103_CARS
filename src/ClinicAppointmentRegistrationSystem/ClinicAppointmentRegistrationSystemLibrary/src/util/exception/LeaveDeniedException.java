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
public class LeaveDeniedException extends Exception {

    /**
     * Creates a new instance of <code>LeaveDeniedException</code> without
     * detail message.
     */
    public LeaveDeniedException() {
    }

    /**
     * Constructs an instance of <code>LeaveDeniedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LeaveDeniedException(String msg) {
        super(msg);
    }
}
