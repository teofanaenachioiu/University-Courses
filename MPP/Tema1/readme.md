<h2> TEMA 1 </h2>

<ul>
<li> Creare proiect Gradle</li>

- In build.gradle punem plugin-urile java si application
- Din meniul din dreapta se alege Gradle pentru a vizuliza task-urile
- Se executa task-ul <i>build</i> din pachetul build, apoi task-ul run (dublu click)

<li> Creare fisier jar executabil</li>

- In fisierul build.gradle scriem configurarea <i>
    jar{
          manifest{
               attributes('Main-Class':'ClasaCuMain')
          }
          from {
               configuration.compile.ollect{it.isDirectory() ?it : zipTree(it)}
          }
    }</i>
- Se executa task-ul <i>jar</i> din pachetul build (dublu click)
- Fisierul jar executabil il gasim in ./build/libs
- Executia jar-ului: click dreapta pe jar -> run

<li> Teste automate rulate cu Gradle </li>

- Se creaza testele cu junit
- Ne asiguram ca in build.gradle exista dependenta 
<i>testCompile group: 'junit', name: 'junit', version: '4.12'</i> 
(sau ceva asemanator)
- Se executa task-ul <i>test</i> din pachetul verification

<li> Jurnalizare clasa folosind Gradle si log4j2 </li>

- In /main/resources adaugam configurarea log4j2.xml
- Adaugam loggerul inainte de metoda main:
<i>private static final Logger logger = LogManager.getLogger();</i>
- Urmarirea fluxului executiei se face cu metodele: traceEntry(), traceExit() etc.
- Mesajele sunt salvate in fisierul /logs/app.log

</ul>