package edu.eci.arsw.cinema.model;

import java.util.List;

/**
 *
 * @author cristian
 */
public class Cinema {
    private String name;

    private List<CinemaFunction> functions;

    public Cinema(){}
    
    public Cinema(String name,List<CinemaFunction> functions){
        this.name=name;
        this.functions=functions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CinemaFunction> getFunctions() {
        return this.functions;
    }

    public void setFunctions(CinemaFunction cinemaFunction) {
        for (int i=0;i<functions.size(); i++){
            if(cinemaFunction.getMovie().getName().equals(functions.get(i).getMovie().getName())){
                functions.get(i).setDate(cinemaFunction.getDate());
            }
        }
    }


    public void addFunctions(CinemaFunction cinemaFunction){
        functions.add(cinemaFunction);
    }

    public void deleteFunctions(String date, String movie){
        int function = -1;
        for (int i=0;i<functions.size(); i++){
            if(movie.equals(functions.get(i).getMovie().getName()) && date.equals(functions.get(i).getDate())){
                function=i;
            }
        }
        if (function>-1){
            functions.remove(function);
        }
    }

    public void setSchedule(List<CinemaFunction> functions) {
        this.functions = functions;
    }
}