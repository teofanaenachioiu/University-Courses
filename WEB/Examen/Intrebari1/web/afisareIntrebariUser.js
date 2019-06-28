function populeazaCuIntrebari(result) {
    for (var i = 0; i < result.length; i++) {
        var continut = '';

        var intrebare = result[i];
        var data = new Date(intrebare.data_intr);
        continut += '<div class="quiz"> <div class="intrebare" id=' + intrebare.id + '>\n' +
            '          <img class="avatar" src="' + intrebare.path_avatar + '">\n' +
            '        <p class="username">\n' +
            intrebare.username +
            '        </p>\n' +
            '        <p>' + data.getDate() + '/' + data.getMonth() + '/' + data.getFullYear() + '</p>\n' +
            '        <p >\n' +
            intrebare.mesaj +
            '        </p>\n' +
            '    </div></div>';
        $(".continut").append(continut);
    }

}

$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: 'getAllMessages',
        success: function (result) {
            populeazaCuIntrebari(JSON.parse(result));
        },
        error: function (result) {
            console.log(result);
        }
    });
});
var clicked = false;
var lastId = null;

function createForm(idIntrebare) {
    continut = '';
    continut += '<div class="raspuns">';
    continut += '<label>Your answer <br><textarea cols="50" rows="5" name="mesaj" placeholder="Type here" required form="myform"></textarea></label>';
    continut += '<form id = "myform" method="post" action="/raspundeIntrebare">';
    continut += '<input type="text" hidden name ="idIntrebare" value="' + idIntrebare + '">';
    continut += '<br>';
    continut += '<button type="submit">Send</button>';
    continut += '</form>';
    continut += '</div>';
    return continut;
}

function populeazaCuRaspunsuri(idIntrebare, element, result) {
    for (var i = 0; i < result.length; i++) {
        var continut = '';
        var rasp = result[i];
        var data = new Date(rasp.data_rasp);
        continut += ' <div class="raspuns">\n' +
            '          <img class="avatar" src="' + rasp.path_avatar + '">\n' +
            '        <p class="username">\n' +
            rasp.username +
            '        </p>\n' +
            '        <p>' + data.getDate() + '/' + data.getMonth() + '/' + data.getFullYear() + '</p>\n' +
            '        <p >\n' +
            rasp.mesaj +
            '        </p>\n' +
            '    </div>';
        $(element).append(continut);
    }
    $(element).append(createForm(idIntrebare));

}

$(document).on("click", ".intrebare", function () {
    var id = $(this).attr('id');
    var parinte = $(this).parent();
    if (clicked === true && lastId === id) {
        $(".raspuns").remove();
        clicked = false;
        return;
    }
    if (clicked === true && lastId !== id) {
        $(".raspuns").remove();
        clicked = false;
    }
    $.ajax({
        type: 'GET',
        data: {idIntrebare: id},
        url: 'getAllResponses',
        success: function (result) {

            populeazaCuRaspunsuri(id, parinte, JSON.parse(result));
            clicked = true;
            lastId = id;
        },
        error: function (result) {
            console.log(result);
        }
    });

});
