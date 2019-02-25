<?php
session_start();
$m = $_SESSION["camp_minat"];

$l = $_GET["l"];
$c = $_GET["c"];

$row = $m[$l];
if ($row[$c] == -1)
    print $row[$c];
else {
    $b = 0;
    if ($l > 0)
        $prow = $m[$l - 1];
    else
        $prow = array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    
    if ($l < 9)
        $nrow = $m[$l + 1];
    else
        $nrow = array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

    if ($c == 0) {
        $b = $prow[$c] + $prow[$c + 1] + $row[$c + 1] + $nrow[$c + 1] + $nrow[$c];
        print abs($b);
        return;
    }

    if ($c == 9) {
        $b = $prow[$c] + $prow[$c - 1] + $row[$c - 1] + $nrow[$c -1 ] + $nrow[$c];
        print abs($b);
        return;
    }
  
    $b = $row[$c - 1] + $prow[$c - 1] + $prow[$c] + $prow[$c + 1] + $row[$c + 1] + $nrow[$c + 1] + $nrow[$c] + $nrow[$c - 1];
    print abs($b);
    return;
}
?>
