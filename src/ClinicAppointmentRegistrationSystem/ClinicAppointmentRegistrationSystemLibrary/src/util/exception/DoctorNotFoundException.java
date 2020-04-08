/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package util.exception;

public class DoctorNotFoundException extends Exception {
    public DoctorNotFoundException() {
    }

    public DoctorNotFoundException(String msg) {
        super(msg);
    }
}
