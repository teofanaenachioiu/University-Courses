function doRequest(src, dst, url, method) {
    // src - id-ului inputului sursa
    // dst - id-ului inputului destinatie
    // url - URL-ul cerut prin apelul AJAX
    // method - metoda prin carei invoc URL-ul de la serverul Web

    var source = document.getElementById(src);
    var destination = document.getElementById(dst);

    var request = new XMLHttpRequest();

    request.onreadystatechange = function() {
        if (request.readyState == 4) { // cerere rezolvata
            if (request.status == 200) // raspuns Ok
                destination.value = request.responseText;
            else
                console.log('Eroare request.status: ' + request.status);
        }
    };

    if (method == 'GET') {
        request.open(method, url + '?' + source.name + '=' + source.value, true);
        request.send('');
        return;
    }
    if (method == 'POST') {
        request.open(method, url, true);
        request.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        request.send(source.name + '=' + source.value);
        return;
    }
    console.log('Metoda trebuie sa fie GET sau POST');
}
