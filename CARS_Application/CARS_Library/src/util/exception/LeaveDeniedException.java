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
