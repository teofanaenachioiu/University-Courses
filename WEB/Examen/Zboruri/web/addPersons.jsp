<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 24.06.2019
  Time: 13:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Zboruri</title>
    <link rel="stylesheet" type="text/css" href="css/formstyle.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="js/jquery.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script>
        <%
           String nume = (String) session.getAttribute("nume");
           String prenume = (String) session.getAttribute("prenume");
           String email = (String) session.getAttribute("email");
      %>
        var nume = '<%= nume %>';
        var prenume = '<%= prenume %>';
        var email = '<%= email %>';

        $(document).ready(function () {
            $("#numeUser").val(nume);
            $("#prenumeUser").val(prenume);

            $("#adaugaPersoana").click(function () {
                $('input[name="numePers"]').val(null).removeAttr('hidden');
                $('input[name="prenumePers"]').val(null).removeAttr('hidden');
                $("#saveButton").removeAttr("hidden");
                $("#deleteButton").attr("hidden", true);
            });
            $("#stergePersoana").click(function () {
                $('input[name="numePers"]').val(null).removeAttr('hidden');
                $('input[name="prenumePers"]').val(null).removeAttr('hidden');
                $("#saveButton").attr("hidden", true);
                $("#deleteButton").removeAttr("hidden");
            });
            $("#saveButton").on("click", function () {
                var nume = $('input[name="numePers"]');
                var prenume = $('input[name="prenumePers"]');
                var pasageri = $("#pasageri").val();
                $("#pasageri").val(pasageri + nume.val() + "," + prenume.val() + ";");
                nume.attr('hidden', true);
                prenume.attr('hidden', true);
                $("#saveButton").attr("hidden", true);
            });
            $("#deleteButton").on("click", function () {
                var nume = $('input[name="numePers"]');
                var prenume = $('input[name="prenumePers"]');
                var string = nume.val()+','+prenume.val()+';';
                var pasageri = $("#pasageri").val();
                console.log(pasageri);
                console.log(string);
                pasageri = pasageri.replace(string,'');
                console.log(pasageri);
                $("#pasageri").val(pasageri);
                nume.attr('hidden', true);
                prenume.attr('hidden', true);
                $("#deleteButton").attr("hidden", true);
            });
        })
    </script>
</head>
<body>
<div class="container">
    <h1> Finalizare Rezervare </h1><br>
    <h3>Titular</h3>
    <label>
        Nume:<br>
        <input id="numeUser" type="text" name="nume" readonly/>
    </label>
    <label>
        Prenume:<br>
        <input id="prenumeUser" type="text" name="prenume" readonly/>
    </label>
    <br>
    <h3>Alti pasageri:
        <button style="border-radius: 50px" id="adaugaPersoana">+</button>
        <button style="border-radius: 50px" id="stergePersoana">-</button>
    </h3>

    <input type="text" name="numePers" placeholder="Nume pasager" hidden>
    <input type="text" name="prenumePers" placeholder="Prenume pasager" hidden>
    <button id="saveButton" hidden>Add</button>
    <button id="deleteButton" hidden>Delete</button>
    <form method="post" action="/addRezervare">
        <label for="pasageri" id="labelPasageri" >Pasageri:</label>
            <input width="100%" type="text" id="pasageri" name="pasageri" >
        <br>
        <button type="submit">Save</button>
    </form>
</div>

</body>
</html>
