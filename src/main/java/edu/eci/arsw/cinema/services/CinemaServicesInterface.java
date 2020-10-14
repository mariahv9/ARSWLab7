package edu.eci.arsw.cinema.services;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Seat;
import edu.eci.arsw.cinema.persistence.CinemaException;

import java.util.List;
import java.util.Set;

public interface CinemaServicesInterface {

    public void addNewCinema(Cinema c);

    public Set<Cinema> getAllCinemas() throws CinemaException;

    public Cinema getCinemaByName(String name)throws CinemaException;

    public void buyTicket(Seat seat, String cinema, String date, String movieName) throws CinemaException;

    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) throws CinemaException;

    public List<CinemaFunction> getFunctionsFilter(Cinema c,String date,String genre);

    public List<CinemaFunction> geFunctionsFilter(Cinema c,String date, Integer x);

    public CinemaFunction getMovie(String cinema,String date, String moviename)throws  CinemaException;

    public void addFunction(String cinema, CinemaFunction cinemaFunction) throws CinemaException;

    void setFunction(String cinema, CinemaFunction cinemaFunction) throws CinemaException;

    void delFunction(String cinema, String date, String movie) throws CinemaException;

}