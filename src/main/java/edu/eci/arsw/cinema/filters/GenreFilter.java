package edu.eci.arsw.cinema.filters;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("filterOfGenre")
public class GenreFilter implements Filter {

    @Override
    public List<CinemaFunction> filter(Cinema cinema,String date,String genre) {
        List<CinemaFunction> cinemaFunctions = new ArrayList<>();

        for (CinemaFunction function : cinema.getFunctions()){
            if(function.getMovie().getGenre().equals(genre)){
                cinemaFunctions.add(function);
            }
        }
        return cinemaFunctions;
    }

    @Override
    public List<CinemaFunction> filter(Cinema cinema,String date, Integer x) {
        return null;
    }
}