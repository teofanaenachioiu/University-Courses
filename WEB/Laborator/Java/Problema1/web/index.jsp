<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 29.05.2019
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Problema1</title>
    <link rel="stylesheet" type="text/css" href="mystyle.css">
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js">  </script>
    <script>

        $(document).ready(function(){
            $.ajax({
                type:'POST',
                url : 'getAllActive',
                success: function(result){
                    $('#myTable').html(result);
                }
            });
        });
    </script>
</head>
<body>
<div class="container">
    <table>
        <tr>
            <td>
                <h2>Authentication</h2>
                <form action="/login" method="POST">
                    <input type='hidden' name='tip' value='login'>
                    <label>Username</label><br>
                    <input type="text" name='username' placeholder="Enter Username" required><br>
                    <label>Password</label><br>
                    <input type="password" name='password' placeholder="Enter Password" required>
                    <br><br>
                    <button type="submit">Login</button>
                </form>
            </td>
            <td>
                <article>
                    <h1>Clatite clasice – reteta simpla</h1>
                    <p>Secretul celei mai bune retete de clatite? Sta in numarul de oua si raportul acestora cu restul
                        de ingrediente.
                        Cu alte cuvinte? Cu cat pui oua cu mai multa dare de inima in compozitia pentru clatite, cu atat
                        iti vor iesi mai moi,
                        mai pufoase si vei evita situatiile in care iti ies niste clatite gumoase sau chiar casante!</p>

                    <p>Folosind cantitatile ingredientelor mentionate in reteta de clatite obtii, in medie, 4 portii de
                        clatite. Daca astepti
                        musafiri si doresti sa pregatesti mai multe clatite, pur si simplu dubleaza sau tripleaza
                        cantitatile de ingrediente,
                        mentinand mereu raportul.</p>
                    <p>INGREDIENTE</p>
                    <cite>
                        <ul>
                            <li>130 g faina</li>
                            <li>2 oua mari</li>
                            <li>300 ml lapte</li>
                            <li>esenta vanilie</li>
                            <li>2 linguri ulei</li>
                            <li>o lingura zahar</li>
                            <li>un praf sare</li>
                        </ul>
                    </cite>

                    <p>
                        MOD DE PREPARARE
                    <ol>
                        <li>Combina ouale cu laptele, ambele la temperatura camerei. Incorporeaza zaharul, eenta de
                            vanilie si praful de sare, apoi adauga uleiul.
                        </li>
                        <li>Incorporeaza faina treptat si mixeaza aluatul pana la incorporarea fainii.</li>
                        <li>Incinge tigaia de clatite la foc mediu si unge-o cu o picatura de ulei, pe care o intinzi cu
                            o pensula de silicon.
                        </li>
                        <li>Intinde aluatul pentru clatite (cca un polonic/tigaie, in functie de cat de mare este tigaia
                            de clatite) si raspandeste aluatul rapid in tigaie.
                        </li>
                        <li>Frige clatitele pe ambele parti timp de cca 1 minut, apoi scoate-le si umple-le cu gem,
                            dulceata, crema de ciocolata, fructe si/sau inghetata.
                        </li>
                    </ol>
                </article>
                <h3> Comentarii</h3>
                <table id = 'myTable'>
                </table>
                <br>
                <h3> Adauga un nou comentariu </h3>
                <form action="/comment" method="POST">
                    <input type='hidden' name='tip' value='comentariu'>
                    <input type='text' name='username' placeholder='Username'><br>
                    <input type='text' name='message' placeholder='Comentariu'><br>

                    <button type='submit'>Submit</button>
                </form>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
