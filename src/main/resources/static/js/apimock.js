api = (function (){
    var seats = [[true, true, true, true, true, true, true, true, true, true, true, true],
                   [true, true, true, true, true, true, true, true, true, true, true, true],
                   [true, true, true, true, true, true, true, true, true, true, true, true],
                   [true, true, true, true, true, true, true, true, true, true, true, true],
                   [true, true, true, true, true, true, true, true, true, true, true, true],
                   [true, true, true, true, true, true, true, true, true, true, true, true]];

    cinemas = [];
    var function1 = {"movie": {"name": "SuperHeroes Movie", "gener": "Action"}, "seats": seats, "date": "2018-12-18 15:30"};
    var function2 = {"movie": {"name": "The Night", "gener": "Horror"}, "seats": seats, "date": "2018-12-18 15:30"};
    var function3 = {"movie": {"name": "Fast&Furious5", "gener": "Action"}, "seats": seats, "date": "2020-12-18 20:30"};
    var function4 = {"movie": {"name": "LEGO movie", "gener": "Adventure"}, "seats": seats, "date": "2020-12-18 20:30"};

    cinemas["cinemaX"] = {"name": "cinemaX", "functions": [function1, function2]};
    cinemas["cinePolis"] = {"name": "cinePolis", "functions": [function3, function4]};

    return {
        getFunctionsByCinema: function (cinema, callback) {
            callback(cinemas[cinema]);
        },
        getFunctionsByCinemaAndDate: function (cinema, dateF, callback) {
            callback(
                    cinemas[cinema].functions.filter(
                    funct => funct.date.includes(dateF))
            );
        },
        getFunctionByNameAndDateAndMovieName: function (cinema, dateF, movie, callback) {
            var data = cinemas[cinema].functions.filter(
                funct => funct.date.includes(dateF));
            var functionCinema = data.filter (element => element.movie.name == movie);
            callback(functionCinema);
        }
    }
})();