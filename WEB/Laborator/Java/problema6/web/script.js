

const tds = [];
let currentGame;
var init;
var winner = false;

function fill(values){
    console.log(values);
    if(values !== '') {
        const val = values.split(',');
        let cont = 0;
        for (let i = 0; i < 3; i++) {
            for (let j = 0; j < 3; j++) {
                const td = document.getElementById("" + i + j);
                $(td).text(val[cont]);
                cont++;
            }
        }
        console.log(val);
        if (val[cont] === 'none') {
            $('#win').text('');
        } else {
            winner = true;
            if (val[cont] !== 'draw') {
                console.log('winner');
                $('#win').text('Player ' + val[cont] + ' won!');
            } else {
                console.log('draw');
                $('#win').text('Draw!');
            }
        }
    }
}

function refreshTable(game) {
    var param = {game: game};
    $.get("table", $.param(param), function (responseText) {
        fill(responseText);
    });
}

function init(game, player) {
    console.log("init: ", game, player);
    for(let i = 0; i < 3; i++){
        const tr = document.createElement("tr");
        for(let j = 0; j < 3; j++){
            const td = document.createElement("td");
            tds.push(td);
            $(td).attr('id', "" + i + j);
            $(td).width(200);
            $(td).height(200);
            $(td).prop('align', 'center');
            tr.append(td);
            $(td).click(function () {
                if(ready && !winner) {
                    var param = {i: i, j: j, game: game, player: player};
                    console.log(param);
                    $.post("table", $.param(param), function (responseText) {
                        console.log(responseText);
                        fill(responseText);
                    });
                }
            });
        }
        $("#table").append(tr);
        init = true;
    }
    refreshTable(game);
    checkReady();
}

var ready;

function checkReady() {
    $.get("init", function (responseText) {
        if (responseText === 'ready') {
            console.log(responseText);
            $('#wait').text('Ready');
            ready = true;
        }
    });
}



$(document).ready(function(){
    ready = false;
    const button = document.getElementById("new");
    $(button).click(function () {
        $.post("init", function (resposeText) {
            const resp = resposeText.split(",");
            const game = resp[0];
            const player = resp[1];
            console.log("player: " + resposeText);
            init(game, player);

            setInterval(function () {
                console.log('refresh');
                if(!ready) {
                    checkReady();
                }
                refreshTable(game);
            }, 1000);

        });

    });

});