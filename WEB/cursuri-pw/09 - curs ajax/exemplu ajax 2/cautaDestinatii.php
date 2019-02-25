<?php
    error_reporting(0);
    require_once("connect.php");

    $plecare = mysql_real_escape_string($_GET['plecare']);
    $result = mysql_query("SELECT sosire FROM trenuri where plecare=\"$plecare\" group by sosire order by sosire");

    printf("{\"sosiri\": [ ");
    while ($row = mysql_fetch_array($result))
        printf("\"%s\",", $row['sosire']);
    printf(" ] }");

    // se poate folosi de asemenea si json_encode pentru a genera JSON-ul trimis client-ului
