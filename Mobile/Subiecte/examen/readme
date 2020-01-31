Orders

Pentru introducerea comenzilor intr-un restaurant, o firma s-a gandit la un sistem client-server.
Serverul expune prin http (localhost:3000) un API REST peste resursele MenuItem si OrderItem.
Un MenuItem este reprezentat prin: code - numar intreg, name - sir de caractere.
Un OrderItem reprezinta informatii despre un element comandat la o anume masa: code - codul elementului
din meniu, quantity - numar intreg, table - masa de la care s-a comandat, free - boolean.
Dezvoltati o aplicatie mobila (client) dupa cum urmeaza.

1. Primul ecran al aplicatiei permite utilizatorului sa stabileasca masa (table) la care se afla.
Utilizatorul introduce masa si declanseaza un buton 'login'. Aplicatia transmite serverului
prin http POST /auth, incluzand { table } in corpul cererii.
Daca serverul raspunde cu success (201), el va returna un { token } care va trebui pus in header-ul tuturor
cererilor http urmatoare.

2. Operatia (1) poate esua (400) daca exista un alt utilizator deja logat cu aceeasi masa.
Aplicatia va informa utilizatorului si-i va permite reluarea operatiei.

3. Pentru a comanda un element din meniu, utilizatorul introduce cateva
caractere din numele elementului. Dupa 2 secunde de la ultimul caracter introdus aplicatia
afiseaza primele 5 elemente de meniu care au in nume secventa de caractere introdusa.

4. Elementele din meniu de la (2) sunt aduse de pe server, via http
GET /MenuItem?q=c, unde c este secventa de caractere introdusa de utilizator.

5. Utilizatorul selecteaza un element, introduce cantitatea si declanseaza un buton 'Order'
pentru a adauga un OrderItem in lista elementelor comandata. Aceasta lista este prezentata
in interfata cu utilizatorul.

6. Aplicatia client va trimite elementul comandat la (5) prin http POST /OrderItem,
incluzand in corpul cererii { code, quantity, free: false }.

7. Daca operatia de la (6) esueaza, aplicatia va indica in lista faptul ca elementul
comandat nu a fost trimis pe server - 'not sent'. Utilizatorul poate relua trimiterea
trimiterea elementelor comandate prin touch pe textul 'not sent'.

8. Aplicatia persista local elementele comandate si masa de la care s-a comandat.
Astfel, daca utilizatorul inchide aplicatia si apoi o reporneste, aceasta va trece automat la
al doilea ecran si va prezenta elementele comandate anterior.

9. Serverul emite notificari prin ws pe localhost:3000. O notificare contine un element din meniu oferit free
de restaurant. La primirea unei notificari, aplicatia client va afisa timp de 3 secunde un 'special offer',
precizand elementul din meniu oferit si va permite utilizatorului sa faca click pe acea oferta pentru a inregistra
{ code, quantity: 1, free: true }.
