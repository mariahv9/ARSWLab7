package edu.eci.arsw.cinema.persistence.impl;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.model.Seat;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author cristian
 */
@Component
@Qualifier("InMemoryCinemaPersistence")
public class InMemoryCinemaPersistence implements CinemaPersitence{
    
    private final Map<String,Cinema> cinemas=new HashMap<>();

    public InMemoryCinemaPersistence() {
        //load stub data
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night","Horror"),functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c = new Cinema("cinemaX",functions);
        cinemas.put("cinemaX", c);
        //Creacion cinema 2
        String functionDate1 = "2020-12-18 20:30";
        List<CinemaFunction> functions1= new ArrayList<>();
        CinemaFunction funct12 = new CinemaFunction(new Movie("Fast&Furious5","Action"),functionDate1);
        CinemaFunction funct22 = new CinemaFunction(new Movie("LEGO movie","Adventure"),functionDate1);
        functions1.add(funct12);
        functions1.add(funct22);
        Cinema c2 = new Cinema("cinePolis",functions1);
        cinemas.put("cinePolis", c2);
    }    

    @Override
    public void buyTicket(Seat seat, String cinema, String date, String movieName) throws CinemaException {

        Cinema c = getCinema(cinema);
        if(c!=null) {
            boolean notfounded = true;
            for (int i=0;i<c.getFunctions().size() && notfounded ;i++) {

                if (c.getFunctions().get(i).getMovie().getName().equals(movieName)
                        && c.getFunctions().get(i).getDate().contains(date)) {

                    c.getFunctions().get(i).buyTicket(seat);
                    notfounded = false;
                }
            }
            if(notfounded) {
                throw new CinemaException("Movie,Date or Position non-existent");
            }
        }else {
            throw new CinemaException("Cinema non-existent");
        }
    }

    @Override
    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date)  {
        Cinema c = getCinema(cinema);
        if(c!=null) {
            List<CinemaFunction> listc = new ArrayList<>();

            for (CinemaFunction function : c.getFunctions()) {

                if (function.getDate().contains(date)) {
                    listc.add(function);
                }
            }
            return listc;
        }else {
            return null;
        }
    }

    @Override
    public List<CinemaFunction> getFunction(String cinema, String date, String movie) {
        Cinema c = getCinema(cinema);
        if(c!=null) {
            List<CinemaFunction> listc = new ArrayList<>();

            for (CinemaFunction function : c.getFunctions()) {

                if (function.getDate().contains(date) && function.getMovie().getName().equals(movie)) {
                    listc.add(function);
                }
            }
            return listc;
        }else {
            return null;
        }
    }

    @Override
    public void saveCinema(Cinema c) throws CinemaPersistenceException {
        if (cinemas.containsKey(c.getName())){
            throw new CinemaPersistenceException("The given cinema already exists: "+c.getName());
        }
        else{
            cinemas.put(c.getName(),c);
        }   
    }

    @Override
    public Cinema getCinema(String name){
        return cinemas.get(name);
    }

    @Override
    public Set<Cinema> getAllCinema()throws CinemaException {
        Set<Cinema> cinemaSet = new HashSet<>();
        for (String c: cinemas.keySet()){
            Cinema cinema= cinemas.get(c);
            cinemaSet.add(cinema);
        }
        return cinemaSet;
    }

    @Override
    public void addFunction(String cinema, CinemaFunction cinemaFunction) throws CinemaException {
        if(getCinema(cinema)!=null) {
            for (String c : cinemas.keySet()) {
                if (c.equals(cinema)) {
                    cinemas.get(c).addFunctions(cinemaFunction);
                }
            }
        }else{
            throw new CinemaException("Unexistent cinema");
        }
    }

    @Override
    public void setFunction(String cinema, CinemaFunction cinemaFunction) throws CinemaException {
        Cinema cine = getCinema(cinema);
        if (cine!= null && cinemaFunction != null){

            cine.setFunctions(cinemaFunction);
        }else {
            throw new CinemaException("Unexistent cinema");
        }
    }

    @Override
    public void deleteFunction(String cinema, String date, String movie) throws CinemaException {
        Cinema cine = getCinema(cinema);
        if (cine!= null){
            cine.deleteFunctions(date,movie);

        }else {
            throw new CinemaException("Unexistent cinema");
        }
    }


}