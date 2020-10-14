package edu.eci.arsw.cinema.filters;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("filterOfSeats")
public class SeatsFilter implements Filter{

    @Override
    public List<CinemaFunction> filter(Cinema cinema,String date,String genre) {
        return null;
    }

    @Override
    public List<CinemaFunction> filter(Cinema cinema,String date, Integer x) {
        List<CinemaFunction> cinemaFunctions = new ArrayList<>();

        for (CinemaFunction function : cinema.getFunctions()){

            if(x < function.getSeatsState(true)){

                cinemaFunctions.add(function);
            }
        }
        return cinemaFunctions;
    }
}