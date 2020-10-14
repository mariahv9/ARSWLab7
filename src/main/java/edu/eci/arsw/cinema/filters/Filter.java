package edu.eci.arsw.cinema.filters;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;

import java.util.List;

public interface Filter {

    public List<CinemaFunction> filter(Cinema cinema,String date,String genre);

    public List<CinemaFunction> filter(Cinema cinema,String date,Integer x);
}