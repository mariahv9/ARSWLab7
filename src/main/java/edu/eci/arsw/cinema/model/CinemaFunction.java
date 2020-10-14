package edu.eci.arsw.cinema.model;

import edu.eci.arsw.cinema.persistence.CinemaException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author cristian
 */
public class CinemaFunction {
    
    private Movie movie;
    private List<List<Boolean>> seats=new ArrayList<>();
    private String date;
    
    public CinemaFunction(){}
    
    public CinemaFunction(Movie movie, String date){
        this.movie=movie;
        this.date=date;
        for (int i=0;i<7;i++){
            List<Boolean> row= new ArrayList<>(Arrays.asList(new Boolean[12]));
            Collections.fill(row, Boolean.TRUE);
            this.seats.add(row);
        }
    }
    
    public void buyTicket(Seat seat) throws CinemaException{
        if (seats.get(seat.getRow()).get(seat.getCol()).equals(true)){
            seats.get(seat.getRow()).set(seat.getCol(),Boolean.FALSE);
        }
        else{
            throw new CinemaException("Seat booked");
        }
    }

    public int getSeatsState(Boolean state){
        int freeSeats = 0;
        for (List<Boolean> row : seats){

            for (Boolean seat : row){

                if (seat == state){
                    freeSeats ++;
                }
            }
        }
        return freeSeats;
    }

    public List<List<Boolean>> getSeats() {
        return this.seats;
    }
    
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}