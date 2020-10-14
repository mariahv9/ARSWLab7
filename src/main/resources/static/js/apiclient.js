client = (function () {
    function getFunctionsByCinema (cinema, callback) {
        $.ajax ({
            dataType: "json",
            url: "http://localhost:8080/cinemas/" + cinema,
            success: function(data){
                callback(data)
            }
        });
    }

    function getFunctionsByCinemaAndDate (cinema, dateF, callback) {
        $.ajax ({
            dataType: "json",
            url: "http://localhost:8080/cinemas/" + cinema + "/" + dateF,
            success: function(data){
            callback(data)
            }
        });
    }

    function getFunctionByNameAndDateAndMovieName (cinema, dateF, movie, callback){
        $.ajax ({
            dataType: "json",
            url: "http://localhost:8080/cinemas/" + cinema + "/" + dateF + "/" + movie,
            success: function(data){
            callback(data)
            }
        });
    }

    function putFunctionHour(cinema,jsonfunc,callback){
        const promise = new Promise((resolve, reject) => {
            $.ajax({
                    url: "http://localhost:8080/cinemas/" + cinema,
                    type: 'PUT',
                    data: jsonfunc,
                    contentType: "application/json"
                }).done(function () {
                    resolve('SUCCESS');

                }).fail(function (msg) {
                    reject('FAIL');
                });
        });

        promise
            .then(res => {
                callback();
            })
            .catch(error => {
                alert(error);
            });
    }

    function postFunction(cinema,jsonfunc,callback){
        const promise = new Promise((resolve, reject) => {
            $.ajax({
                    url: "http://localhost:8080/cinemas/" + cinema,
                    type: 'POST',
                    data: jsonfunc,
                    contentType: "application/json"
                }).done(function () {
                    resolve('SUCCESS');

                }).fail(function (msg) {
                    reject('FAIL');
                });
        });

        promise
            .then(res => {
                callback();
            })
            .catch(error => {
                alert(error);
            });
    }

    function deleteFunction(cinema,movieName1,dateTarget,callback){
        const promise = new Promise((resolve, reject) => {
            $.ajax({
                    url: "http://localhost:8080/cinemas/" + cinema +"/" + dateTarget + "/" + movieName1,
                    type: 'DELETE'

                }).done(function () {
                    resolve('SUCCESS');

                }).fail(function (msg) {
                    reject('FAIL');
                });
        });

        promise
            .then(res => {
                callback();
            })
            .catch(error => {
                alert(error);
            });
    }

    function buyTicket(cinema,movieName1,dateTarget,jsonfunc,callback){
            console.log(jsonfunc);
            const promise = new Promise((resolve, reject) => {
                $.ajax({
                        url: "http://localhost:8080/cinemas/" + cinema +"/" + dateTarget + "/" + movieName1,
                        type: 'PUT',
                        data: jsonfunc,
                        contentType: "application/json"
                    }).done(function () {
                        resolve('SUCCESS');

                    }).fail(function (msg) {
                        reject('FAIL');
                    });
            });

            promise
                .then(res => {
                    callback();
                })
                .catch(error => {
                    alert(error);
                });
        }

    return {
        getFunctionsByCinema: getFunctionsByCinema,
        getFunctionsByCinemaAndDate: getFunctionsByCinemaAndDate,
        getFunctionByNameAndDateAndMovieName: getFunctionByNameAndDateAndMovieName,
        putFunctionHour: putFunctionHour,
        postFunction: postFunction,
        deleteFunction: deleteFunction,
        buyTicket: buyTicket
    }
})();