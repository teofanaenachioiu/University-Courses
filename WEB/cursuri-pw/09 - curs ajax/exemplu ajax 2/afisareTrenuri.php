<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Un alt exemplu cu AJAX si PHP</title>
    <link href="style.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="ajax.js">
    </script>
</head>
<?php
    error_reporting(0);
    require_once("connect.php");
?>
<body>
<table border="0">
    <tr>
        <td>Statie de plecare:</td>
        <td>
            <select id="plecare" name="plecare" onChange="doRequest('plecare', 'sosire')">
            <option selected="selected">Plecare</option>
            <?php
                $result = mysql_query("SELECT plecare FROM trenuri group by plecare order by plecare");
                while ($row = mysql_fetch_array($result)) {
                    printf("<option value=\"%s\">%s</option>\n", $row['plecare'], $row['plecare']);
                }
            ?>
            </select>
        </td>
  </tr>
  <tr>
        <td>Statie de sosire:</td>
        <td>
            <select id="sosire" name="sosire">
            </select>
        </td>
  </tr>
  <tr>
        <td colspan="2">
            <input type="button" value="Afiseaza Trenurile" disabled="disabled" id="afiseaza" onClick="doAnotherRequest('plecare', 'sosire')"/>
        </td>
  </tr>
  </table>

<br>

<div id="mersulTrenurilor">
</div>

</body>
</html>
