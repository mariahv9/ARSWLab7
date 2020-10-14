package edu.eci.arsw.cinema.persistence;

/**
 * @author cristian
 */
public class CinemaPersistenceException extends Exception{

    public CinemaPersistenceException(String message) {
        super(message);
    }

    public CinemaPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}