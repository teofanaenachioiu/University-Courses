
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

function populeazaCuRaspunsuri(element, result) {
    for (var i = 0; i < result.length; i++) {
        var continut = '';
        var rasp = result[i];
        var data = new Date(rasp.data_rasp);
        continut += ' <div class="raspuns" id=' + rasp.id + '>\n' +
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


}

$(document).on("click", ".intrebare", function () {
    var id = $(this).attr('id');
    var parinte = $(this).parent();
    if (clicked === true && lastId  === id) {
        $(".raspuns").remove();
        clicked = false;
        return;
    }
    if (clicked === true && lastId  !== id){
        $(".raspuns").remove();
        clicked = false;
    }
    $.ajax({
        type: 'GET',
        data: {idIntrebare: id},
        url: 'getAllResponses',
        success: function (result) {

            populeazaCuRaspunsuri(parinte, JSON.parse(result));
            clicked = true;
            lastId = id;
        },
        error: function (result) {
            console.log(result);
        }
    });

});
