/*
 * Group 3 IS2103 Assignment 2
 * Group members:
 * - Loon Hai Qi , A0160483H
 * - Madeline Tooh Weiping , A0160349E
 * - Nurul Afiqah Binte Rashid , A0160361R
 * 
 */
package util.exception;


public class InvalidLoginException extends Exception {

    public InvalidLoginException() {
    }

    public InvalidLoginException(String msg) {
        super(msg);
    }
}
