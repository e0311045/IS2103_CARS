/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package util.exception;


public class InvalidDateTimeFormatException extends Exception {

    /**
     * Creates a new instance of <code>InvalidDateTimeException</code> without
     * detail message.
     */
    public InvalidDateTimeFormatException() {
    }

    /**
     * Constructs an instance of <code>InvalidDateTimeException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidDateTimeFormatException(String msg) {
        super(msg);
    }
}
