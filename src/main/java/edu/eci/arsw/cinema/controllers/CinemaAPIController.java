package edu.eci.arsw.cinema.controllers;

import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Seat;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.services.CinemaServicesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cristian
 */

@RestController
@RequestMapping(value = "/cinemas")
public class CinemaAPIController {

    @Autowired
    @Qualifier("cinemaServicies")
    CinemaServicesInterface cinemaServices;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllCinemas(){
        try {
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<>(cinemaServices.getAllCinemas(), HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{cinemaName}",method = RequestMethod.GET)
    public ResponseEntity<?> getCinemaByName(@PathVariable String cinemaName){
        try {
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<>(cinemaServices.getCinemaByName(cinemaName), HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error HTTP 404",HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{cinemaName}/{date}",method = RequestMethod.GET)
    public ResponseEntity<?> getFunctionsbyCinemaAndDate(@PathVariable String cinemaName,@PathVariable String date){
        try {
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<>(cinemaServices.getFunctionsbyCinemaAndDate(cinemaName,date), HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error HTTP 404",HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{cinemaName}/{date}/{movieName}",method = RequestMethod.GET)
    public ResponseEntity<?> getMovie(@PathVariable String cinemaName,@PathVariable String date,@PathVariable String movieName){
        try {
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<>(cinemaServices.getMovie(cinemaName, date, movieName), HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error HTTP 404",HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/{cinema}",method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPostRecursoXX(@PathVariable String cinema,@RequestBody CinemaFunction cinemaFunction){
        try {
            //registrar dato
            cinemaServices.addFunction(cinema, cinemaFunction);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping (value = "/{name}", method = RequestMethod.PUT)
    public ResponseEntity<?> manejadorPutRecursoXX(@PathVariable String name, @RequestBody CinemaFunction cinemaFunction){
        try {
            cinemaServices.setFunction (name, cinemaFunction);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{cinemaName}/{date}/{movieName}",method = RequestMethod.DELETE)
    public ResponseEntity<?> delFunction(@PathVariable String cinemaName,@PathVariable String date,@PathVariable String movieName){
        try {
            cinemaServices.delFunction(cinemaName, date, movieName);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error HTTP 404",HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{cinemaName}/{date}/{movieName}",method = RequestMethod.PUT)
    public ResponseEntity<?> Buyticket(@PathVariable String cinemaName,@PathVariable String date,@PathVariable String movieName, @RequestBody Seat seat){
        try {
            cinemaServices.buyTicket(seat,cinemaName, date, movieName);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error HTTP 404",HttpStatus.NOT_FOUND);
        }
    }
}