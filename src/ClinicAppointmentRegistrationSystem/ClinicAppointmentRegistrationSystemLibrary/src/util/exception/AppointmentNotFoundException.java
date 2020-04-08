/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package util.exception;

public class AppointmentNotFoundException extends Exception{
    public AppointmentNotFoundException() {
    }

    public AppointmentNotFoundException(String msg) {
        super(msg);
    }
}
