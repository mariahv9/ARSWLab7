app = (function () {
    var cinema;
    var dateF;
    var movieName1;
    var movieGen;
    var movieHour;
    var seats;
    var module = "js/apiclient.js";

    var stompClient = null;
    var row;
    var col;


    function setCinemaName() {
        cinema = newCinema;
    }

    function setDate() {
        dateF = newDate;
    }

    function getFunctionsByCinemaAndDate() {
        cinema = $("#cinema").val();
        dateF = $("#dateF").val();
        client.getFunctionsByCinemaAndDate(cinema, dateF, changeData);
    }

    function changeData(functions) {
        if(functions != null){
            var mapFunctions = functions.map(
            function (functions) {
                var nameOfMovie = functions.movie.name;
                var generMovie = functions.movie.genre;
                var date = functions.date.substring (0, 10);
                var hourDate = functions.date.substring(11, 16);
                return {movieName: nameOfMovie, gener: generMovie, hour: hourDate};
            })
        }
        setTable(mapFunctions);
    }

    function setTable(mapFunctions) {
        clearTable();
        var table = document.getElementById("table");
        if (table.rows.length > 1) for(var i = table.rows.length - 1; i > 0; i--) {table.deleteRow(i);}
        $("#cinemaName").text("Function cinema selected: "+cinema);
        $("#movieSelected").text("Availability of Functions: ");
        if(mapFunctions!=null){
            mapFunctions.map(function (film) {
                var onclick = "app.consultFunctionBySeats(\""+cinema+"\",\""+dateF+"\",\""+film.hour+"\",\""+film.movieName+"\",\""+film.gener+"\")";
                var boton = "<input type='button' class='show' value='Consult seats' onclick='" + onclick + "'></input>";
                var row = '<tr><td>' + film.movieName + '</td><td>' + film.gener + '</td><td>' + film.hour + '</td><td>' + boton + '</td></tr>';
                $("#table").append(row);
            })
        }
    }

    function  consultFunctionBySeats (nameOfCinema,dateF,hour,movieName,gener) {
        clearTable();
        movieName1 = movieName;
        movieGen = gener;
        movieHour = hour;

        var dateTarget = dateF.concat(" ",hour);
        $("#movieSelected").text("Availability of Functions: "+ movieName);
        ///////Parte 3
        connectAndSubscribe();

        $.getScript(module, function(){
            client.getFunctionByNameAndDateAndMovieName(nameOfCinema,dateTarget,movieName,drawSeats);
        });
    }

    function clearTable(){
        var pool = document.getElementById("canvas");
        var count = pool.getContext("2d");
        count.clearRect(0, 0, pool.width, pool.height);
        count.beginPath();
    }

    function drawSeats(functionToSeats) {
        seats = functionToSeats.seats;
        var c = document.getElementById("canvas");
        var count = c.getContext("2d");
        count.fillStyle = "deepskyblue";
        count.fillRect (140, 50, 600, 50);
        var d = document.getElementById("canvas");
        var dtx = d.getContext("2d");
        var sumPosition = 0;
        for (var x = 0; x < seats[0].length; x++) {
            for (var y = 0; y < seats.length; y++) {
                if(seats[y][x] == false){
                    dtx.fillStyle = "firebrick";
                } else{
                    dtx.fillStyle = "grey";
                }
                dtx.fillRect(x*50 + 30 +sumPosition, y*40 +120 , 30, 30);
            }
            sumPosition+=20;
        }
    }

    function putFunctionHour(){
        var hour = $("#hour").val();

        var func = {
            "movie": {
                    "name": movieName1,
                    "genre": movieGen
                    },
            "seats": seats,
            "date": dateF+" "+hour
        };
        var jsonfunc = JSON.stringify(func);

        $.getScript(module, function(){
            client.putFunctionHour(cinema,jsonfunc,resetValue);
        });

    }

    function createFunction(){
        dateF = $("#newDate").val();
        var func = {
            "movie": {
                    "name": $("#newMovieName").val(),
                    "genre": $("#newMovieGenre").val()
                    },
            "seats": [
                      [
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true
                      ],
                      [
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true
                      ],
                      [
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true
                      ],
                      [
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true
                      ],
                      [
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true
                      ],
                      [
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true
                      ],
                      [
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true,
                          true
                      ]
                 ],
            "date": dateF +" "+$("#newHour").val()
        };
        var jsonfunc = JSON.stringify(func);
        $.getScript(module, function(){
            client.postFunction(cinema,jsonfunc,resetValue);
        });
    }

    function delFunction(){
        var func = {
            "movie": {
                    "name": movieName1,
                    "genre": movieGen
                    },
            "seats": seats,
            "date": dateF+" "+movieHour
        };
        var jsonfunc = JSON.stringify(func);
        var dateTarget = dateF.concat(" ",movieHour);
        $.getScript(module, function(){
                client.deleteFunction(cinema,movieName1,dateTarget,resetValue);
            });
    }

    function buyTicket(){
        var func = {
            "row": $("#fila").val(),
            "col": $("#columna").val()
        };
        var jsonfunc = JSON.stringify(func);
        var dateTarget = dateF.concat(" ",movieHour);
        $.getScript(module, function(){
                client.buyTicket(cinema,movieName1,dateTarget,jsonfunc,resetValue);
            });
    }

// Stomp functions  //////////////////////


    var getMousePosition = function (evt) {
        $('#canvas').click(function (e) {
            var canvas = document.getElementById("canvas");
            var rect = canvas.getBoundingClientRect();
            var x = Math.floor(e.clientX - rect.x);
            var y = e.clientY - rect.y;
            PositionMouse(x,y);
            if(seats[row][col] == false){
               alert("Seat already rented");
            }else{
                seats[row][col] = false;
                buyTicketStomp();
            }
        });
    };

    function PositionMouse(pX,pY){
        row = -1;
        col = -1;

        var x1 = 30;
        var x2 = 60;
        var y1 = 120;
        var y2 = 150;

        for(var seatY=0; seatY<12; seatY++){
            for(var seatX=0; seatX<7; seatX++){
                if(x1<pX && pX<x2 && y1<pY && pY<y2){
                    row=seatX;
                    col=seatY;
                }
                y1 = y1 + 40;
                y2 = y2 + 40;
            }
            x1 = x1 + 70;
            x2 = x2 + 70;
            y1 = 120;
            y2 = 150;
        }
    }



    var connectAndSubscribe = function(){
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);

        //subscribe to /topic/TOPICXX when connections succeed
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/buyticket.'+cinema+'.'+dateF.concat(" ",movieHour)+'.'+movieName1, function (message) {
                    var theObject = JSON.parse(message.body);
                    consultFunctionBySeats(cinema,dateF,movieHour,movieName1,movieGen);
            });
        });
    };



    function buyTicketStomp(){
        var func = {
            "row": row,
            "col": col
        };
        stompClient.send('/app/buyticket.'+cinema+'.'+dateF.concat(" ",movieHour)+'.'+movieName1, {}, JSON.stringify(func));
    }



//////////////////////////////////////////
    function resetValue(){
        $("#newMovieName").val( "" );
        $("#newMovieGenre").val( "" );
        $("#newDate").val( "" );
        $("#newHour").val( "" );
        $("#hour").val( "" );
        $("#dateF").val(dateF);
        getFunctionsByCinemaAndDate()
        clearTable()
    }


    return {
        setCinemaName: setCinemaName,
        setDate: setDate,
        getFunctionsByCinemaAndDate: getFunctionsByCinemaAndDate,
        consultFunctionBySeats : consultFunctionBySeats,
        putFunctionHour: putFunctionHour,
        createFunction: createFunction,
        delFunction: delFunction,
        buyTicket: buyTicket,
        getMousePosition: getMousePosition,
        buyTicketStomp: buyTicketStomp
    }





/////////////////--------------// parte2 lab////////////////////////////////////////////////////////////////
//    //get the x, y positions of the mouse click relative to the canvas
//    var getMousePosition = function (evt) {
//        $('#canvas').click(function (e) {
//            var canvas = document.getElementById("canvas");
//            var rect = canvas.getBoundingClientRect();
//            var x = Math.floor(e.clientX - rect.x);
//            var y = e.clientY - rect.y;
//            PositionMouse(x,y);
//            seats[row][col] = false;
//            buyTicketStomp();
//            alert("Seat on row= "+row+" col= "+col+" Rented")
//        });
//    };
//
//    function PositionMouse(pX,pY){
//        row = -1;
//        col = -1;
//
//        var x1 = 30;
//        var x2 = 60;
//        var y1 = 120;
//        var y2 = 150;
//
//        for(var seatY=0; seatY<12; seatY++){
//            for(var seatX=0; seatX<7; seatX++){
//                if(x1<pX && pX<x2 && y1<pY && pY<y2){
//                    row=seatX;
//                    col=seatY;
//                }
//                y1 = y1 + 40;
//                y2 = y2 + 40;
//            }
//            x1 = x1 + 70;
//            x2 = x2 + 70;
//            y1 = 120;
//            y2 = 150;
//        }
//    }
//
//    function RedrawSeats() {
//        var c = document.getElementById("canvas");
//        var count = c.getContext("2d");
//        count.fillStyle = "deepskyblue";
//        count.fillRect (140, 50, 600, 50);
//        var d = document.getElementById("canvas");
//        var dtx = d.getContext("2d");
//        var sumPosition = 0;
//        for (var x = 0; x < seats[0].length; x++) {
//            for (var y = 0; y < seats.length; y++) {
//                if(seats[y][x] == false){
//                    dtx.fillStyle = "firebrick";
//                } else{
//                    dtx.fillStyle = "grey";
//                }
//                dtx.fillRect(x*50 + 30 +sumPosition, y*40 +120 , 30, 30);
//            }
//            sumPosition+=20;
//        }
//    }
//
//
//
//    var connectAndSubscribe = function(){
//        console.info('Connecting to WS...');
//        var socket = new SockJS('/stompendpoint');
//        stompClient = Stomp.over(socket);
//
//        //subscribe to /topic/TOPICXX when connections succeed
//        stompClient.connect({}, function (frame) {
//            console.log('Connected: ' + frame);
//            stompClient.subscribe('/topic/buyticket.'+cinema+'.'+dateF+'.'+movieName1, function (message) {
//                    var theObject = JSON.parse(message.body);
//                    RedrawSeats();
//            });
//        });
//    };
//
//
//
//    function buyTicketStomp(){
//        var func = {
//            "row": row,
//            "col": col
//        };
//        stompClient.send('/app/buyticket.'+cinema+'.'+dateF+'.'+movieName1, {}, JSON.stringify(func));
//    }
})();