function creareThumb(parse) {
    var continut = '';
    for (var i = 0 ;i<parse.length; i++){
        var incarcare = parse[i];
        var keywords = incarcare.descriere.split(",");
        var cuv = '';

        for(var j=0;j<keywords.length;j++){
            var key = keywords[j];
            cuv+='<span class="abc" id="' +key +
                '" >' +key +
                '</span>';
        }

        continut+='<div class="content">' +
            '' +
            '<div class="image"><a target="_blank" href="' +incarcare.path_poza+
            '">\n' +
            '<img src="' + incarcare.path_poza +
            '" alt="Poza">\n' +
            '</a></div>' +
            '<div class="text"><p>' + cuv +
            '</p></div>' +
            '</div>' +
            '<br>';
    }
    $("#continut").html(continut);
    $(".abc").click(function () {
        var id = $(this).attr("id");
        $.ajax({
            type: 'POST',
            data: {keyword: id},
            url: '/setKey',
            success: function (result) {
               window.location.href = "http://localhost:8080/oras.jsp"
            }
        });

    })

}
