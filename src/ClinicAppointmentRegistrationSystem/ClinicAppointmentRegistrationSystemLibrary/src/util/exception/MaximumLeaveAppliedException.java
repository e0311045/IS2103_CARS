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
public class MaximumLeaveAppliedException extends Exception {

    /**
     * Creates a new instance of <code>MaximumLeaveAppliedException</code>
     * without detail message.
     */
    public MaximumLeaveAppliedException() {
    }

    /**
     * Constructs an instance of <code>MaximumLeaveAppliedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public MaximumLeaveAppliedException(String msg) {
        super(msg);
    }
}
