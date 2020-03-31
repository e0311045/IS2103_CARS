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
public class ConsultationFullyBookedException extends Exception {

    /**
     * Creates a new instance of <code>ConsultationFullyBookedException</code>
     * without detail message.
     */
    public ConsultationFullyBookedException() {
    }

    /**
     * Constructs an instance of <code>ConsultationFullyBookedException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ConsultationFullyBookedException(String msg) {
        super(msg);
    }
}
