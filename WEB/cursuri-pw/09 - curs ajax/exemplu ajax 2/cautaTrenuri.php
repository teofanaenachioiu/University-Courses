<?php
    error_reporting(0);
    require_once("connect.php");

    $plecare = mysql_real_escape_string($_GET['plecare']);
    $sosire = mysql_real_escape_string($_GET['sosire']);

    $result = mysql_query("SELECT * FROM trenuri where plecare=\"$plecare\" and sosire=\"$sosire\"");

    printf("{ \"trenuri\": [ ");
    while ($row = mysql_fetch_array($result)) {
        printf("{ \"plecare\": \"%s\", \"sosire\": \"%s\", \"ora\": %d, \"minut\": %d}, ", $row['plecare'], $row['sosire'], $row['ora'], $row['minut']);
    }
    printf(" ] }");

    // se poate folosi de asemenea si json_encode pentru a genera JSON-ul trimis client-ului
