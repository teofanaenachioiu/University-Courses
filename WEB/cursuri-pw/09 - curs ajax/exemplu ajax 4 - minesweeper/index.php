<?php
    session_start();
?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script type="text/javascript" src="jquery.min.js"></script>
    <script type="text/javascript">
    
    var bombe;

    function verifica(td) { // td is already a jquery obbject
        if (td.length == 0)
            return;
        
        if (td[0].vizitat)
            return;

        td[0].vizitat = true;

        var i = td.attr("linie");
        var j = td.attr("coloana");
        $.ajax({
            type: "GET",
            url: "intreaba.php",
            data: "l=" + i + "&c=" + j,
            success: function(msg){
                if (msg == -1) {
                    td.html('<img src="bomb.png"/>');
                    alert('Ai murit. Click for a new game.');
                    window.location = 'index.php';
                    return;
                }
                if (msg == 0) {
                    td.css("background-color", "#DDDDDD");
                    if (j > 0)
                        verifica(td.prev()); // stanga
                    if (j < 9)
                        verifica(td.next()); // dreapta
                    if (i > 0)
                        verifica($(td.parent().prev().find("td")[j])); // sus
                    if (i < 9)
                        verifica($(td.parent().next().find("td")[j])); // jos
                    if (i > 0 && j > 0)
                        verifica($(td.parent().prev().find("td")[j-1])); // stanga sus
                    if (i > 0 && j < 9)
                        verifica($(td.parent().prev().find("td")[j+1])); // dreapta sus
                    if (i < 9 && j > 0)
                        verifica($(td.parent().next().find("td")[j-1])); // stanga jos
                    if (i < 9 && j < 9)
                        verifica($(td.parent().next().find("td")[j+1])); // dreapta jos

                    return;
                }
                td.html('<img src="' + msg + '.gif"/>');
            }
        });
    }
    
    $(document).ready(function() {
        bombe = $("#tabel").attr("bombe");
        $("#bombe").html(bombe);

        $("#tabel td").each(function() {
            this.vizitat = false;
        });
        
        $("#tabel td").mousedown(function(e) {              
            if (e.button == 2) {
                if (!this.vizitat) {
                    this.vizitat = true;
                    bombe--;
                    $("#bombe").html(bombe);
                    $(this).html('<img src="flag.png"/>');
                    if (bombe == 0) {
                        alert('Ai castigat. Click for a new game.');
                        window.location = 'index.php';
                        return;
                    }
                } else {
                    this.vizitat = false;
                    bombe++;
                    $("#bombe").html(bombe);
                    $(this).html('');
                }
                return;
            }
            verifica($(this));
        });

    });
    </script>

    <style type="text/css">
    table {
        border: 1px solid black;
        border-collapse: collapse;
    }

    table td {
        border: 1px solid black;
        width: 16px;
        height: 16px;
    }

    body {
        font-family: Tahoma, sans-serif;
        font-size: 12px;
    }
    </style>

</head>
<body oncontextmenu="return false;">
<?php
    $m = array();
    $b = 0;

    for ($i = 0; $i < 10; $i++) {
        $row = array();
        for ($j = 0; $j < 10; $j++) {
            $r = rand (0, 6);
            if ($r == 0) {
                $row[$j] = -1;
                $b++;
            }
            else
            $row[$j] = 0;
        }
        $m[$i] = $row;
    }

    print "<table id=\"tabel\" cellspacing=\"0\" cellpadding=\"0\" bombe=\"$b\">";
    for ($i = 0; $i < 10; $i++) {
        print "<tr>";
        for ($j = 0; $j < 10; $j++)
            print "<td linie=\"$i\" coloana=\"$j\"></td>";
        print "</tr>";
    }
    print "</table>";

    $_SESSION["camp_minat"] = $m;
?>

<br/>
Bombe ramase: <span id="bombe"></span>
<br/>
<br/>
Click stanga - nu e bomba<br/>
Click dreapta - e bomba<br/>
Al doilea click dreapta - anuleaza steguletul<br/>
</body>
</html>
        