package edu.eci.arsw.cinema.persistence;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Seat;

import java.util.List;
import java.util.Set;

/**
 * @author cristian
 */
public interface CinemaPersitence {
    
    /**
     * @param seat the function's seat
     * @param cinema the cinema's name
     * @param date the date of the function
     * @param movieName the name of the movie
     * 
     * @throws CinemaException if the seat is occupied,
     *    or any other low-level persistence error occurs.
     */
    public void buyTicket(Seat seat, String cinema, String date, String movieName) throws CinemaException;
    
    /**
     * 
     * @param cinema cinema's name
     * @param date date
     * @return the list of the functions of the cinema in the given date
     */
    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date);

    /**
     *
     * @param cinema cinema's name
     * @param date date
     * @param movie movie's name
     * @return the list of the functions of the cinema in the given date
     */
    public List<CinemaFunction> getFunction(String cinema, String date,String movie);
    
    /**
     * 
     * @param cinema new cinema
     * @throws  CinemaPersistenceException n if a cinema with the same name already exists
     */
    public void saveCinema(Cinema cinema) throws CinemaPersistenceException;
    
    /**
     * 
     * @param name name of the cinema
     * @return Cinema of the given name
     * @throws  CinemaPersistenceException if there is no such cinema
     */
    public Cinema getCinema(String name) throws CinemaPersistenceException;

    /**
     *
     * @return  All cinema's instances
     */
    public Set<Cinema> getAllCinema() throws CinemaException;

    public void addFunction(String cinema, CinemaFunction cinemaFunction) throws CinemaException;

    public void setFunction (String cinema, CinemaFunction cinemaFunction) throws CinemaException;

    public void deleteFunction (String cinema, String date, String movie) throws CinemaException;
}