/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package ejb.session.stateful;

public interface RegistrationControllerRemote {

    void clearQueue();

    void addQueue();

    Integer getQueue();

    void setQueue(Integer queue);
}
